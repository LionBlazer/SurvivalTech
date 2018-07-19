package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.IElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.IEnergyElectricNetworkManager;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Date: 2017-12-29. Time: 21:43:42.
 * 
 * @author WhiteWarrior
 */
public abstract class TileEntityEnergyManager extends TileEntityEnergyStandard implements IEnergyElectricNetworkManager, ITickable, IElectricEnergyStorage {
	private HashMap<BlockPos,Integer> receiversPos = new HashMap<>();
	private HashMap<BlockPos,Integer> activeReceiversPos = new HashMap<>();

	public TileEntityEnergyManager() {

	}

	public TileEntityEnergyManager(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		for (int i = 0; i < compound.getInteger("pos_size"); i++) {
			receiversPos.put(new BlockPos(compound.getInteger("positions_x_" + i),
					compound.getInteger("positions_y_" + i), compound.getInteger("positions_z_" + i)), compound.getInteger("cond_" + i));

		}

		for (int i = 0; i < compound.getInteger("active_pos_size"); i++) {
			activeReceiversPos.put(new BlockPos(compound.getInteger("activepositions_x_" + i),
					compound.getInteger("activepositions_y_" + i), compound.getInteger("activepositions_z_" + i)),
					compound.getInteger("activecond_"+i));

		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		int i = 0;
		for (Entry<BlockPos, Integer> pos : receiversPos.entrySet()) {
			compound.setInteger("positions_x_" + i, pos.getKey().getX());
			compound.setInteger("positions_y_" + i, pos.getKey().getY());
			compound.setInteger("positions_z_" + i, pos.getKey().getZ());
			i++;
		}
		compound.setInteger("pos_size", receiversPos.size());
        i=0;
		for (Entry<BlockPos, Integer> pos : activeReceiversPos.entrySet()) {
			compound.setInteger("activepositions_x_" + i, pos.getKey().getX());
			compound.setInteger("activepositions_y_" + i, pos.getKey().getY());
			compound.setInteger("activepositions_z_" + i, pos.getKey().getZ());
		}
		compound.setInteger("active_pos_size", activeReceiversPos.size());
		return compound;
	}

	// перенести в тик эвент
	@Override
	public void update() {
		if (!world.isRemote) {
			if (receiversPos.size() > 0) {
				// Хранилище текущего блока
				ElectricEnergyStorage storage = this.getStorage();
				// Если энергии в блоке хватает на импульс
				if (storage.getEnergyStoredMod() >= storage.getMaxExtract()) {
					// получить кол-во энергии текущего блока
					Double energyold2 = new Double(storage.getEnergyStoredMod());
					//Цикл по всем потребителям
					for (Entry<BlockPos, Integer> input : receiversPos.entrySet()) {
						//Если тайл потребителя существует и потребитель не текущий блок(такое может быть если текущий блок - энергохранилище)
						if (input != null && input.getKey() != null && this.getWorld().getTileEntity(input.getKey()) != null && this.getWorld().getTileEntity(input.getKey()) != this
								&& this.getWorld().getTileEntity(input.getKey()) instanceof IElectricEnergyStorage) {
							// Тайл потребителя
							IElectricEnergyStorage tile2 = (IElectricEnergyStorage) this.getWorld().getTileEntity(input.getKey());
							// Энергохранилище потребителя
							ElectricEnergyStorage storage2 = tile2.getStorage();
							// Если хранилище потребителя получило меньше энергии чем может получать в тик, и кол-во энергии потребителя не равно максимуму
							if (storage2.getEnergyReceivedPerTick() < storage2.getMaxReceive() && storage2.getEnergyStoredMod() < storage2.getMaxEnergyStoredMod()) {
								//остаток = максимальное кол-во энергии которое потребитель может получит в тик - энергию которую он уже получил в текущий тик
								//т.е это то, сколько реально может получить потребитель в текущий тик
								double rest = (storage2.getMaxReceive() - storage2.getEnergyReceivedPerTick());
								// d01 - сколько реально может получить потребитель в текущий тик с учетом ВСЕХ ФАКТОРОВ
								double d01 = Math.min(storage2.receiveEnergy(storage2.getMaxReceive(), true), rest);
								/*
									energyForEach = Меньшее из (сколько может выдать энергии текущий блок |
									сколько реально может получить потребитель в текущий тик с учетом ВСЕХ ФАКТОРОВ)
									/ кол-во потребителей, которые способны получать энергию
								*/
								// т.е усредненное значение энергии для которое получит каждый потребитель
								double energyForEach = Math.min(storage.extractEnergy(storage.getMaxExtract(), true),
										d01) / activeReceiversPos.size();
								if(energyForEach - (((double)input.getValue())/100d) <= 0 ) energyForEach = 0;
								// Сколько может выдать текущий продюсер данному потребителю с учетом ВСЕХ ФАКТОРОВ
								double energyold = storage.extractEnergy(energyForEach, false);
								storage2.addEnergyReceivedPerTick(Math.max(energyold-(((double)input.getValue())/100d),0));
								storage2.receiveEnergy(Math.max(energyold-(((double)input.getValue())/100d),0), false);

								if (energyForEach != 0) {
									if (!activeReceiversPos.containsKey(input))
										activeReceiversPos.put(input.getKey(), receiversPos.get(input));
								} else
									activeReceiversPos.remove(input);

								if (storage2.getEnergyStoredMod() < storage2.getMaxEnergyStoredMod())
									if (!activeReceiversPos.containsKey(input))
										activeReceiversPos.put(input.getKey(), receiversPos.get(input));

								if (!energyold2.equals(this.getStorage().getEnergyStoredMod())) {
									updateVars();
									if (world.getTileEntity(input.getKey()) instanceof TileEntityBlock) {
										((TileEntityBlock) world.getTileEntity(input.getKey())).updateVars();
									}
								}
							}
						} else {
							receiversPos.remove(input);
							activeReceiversPos.remove(input);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public HashMap<BlockPos,Integer> getReceiversPos() {
		return receiversPos;
	}

	@Override
	public HashMap<BlockPos,Integer> getActiveReceiversPos() {
		return activeReceiversPos;
	}

}
