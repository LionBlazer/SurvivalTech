package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import ru.whitewarrior.survivaltech.NBTConstants;
import ru.whitewarrior.survivaltech.api.common.energy.IElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;

/**
 * Date: 2017-12-30.
 * Time: 22:07:45.
 * @author WhiteWarrior
 */
public abstract class TileEntityEnergyStandard extends TileEntityInventory implements IElectricEnergyStorage{
	
	public TileEntityEnergyStandard() {

	}

	public TileEntityEnergyStandard(String name) {
		super(name);
	}

	protected void extractEnergyFromSlot(int slot){
        if(this.getStackInSlot(slot).getItem() instanceof IItemElectricEnergyStorage) {
            double energyReceive = this.getStackInSlot(slot).getCapability(CapabilityElectricEnergy.ENERGY, null).extractEnergy(this.getStorage().receiveEnergy(this.getStorage().getMaxReceive(), true), false);
            this.getStorage().receiveEnergy(energyReceive, false);
            updateVars();
            markDirty();
        }
    }

    protected void extractEnergyToSlot(int slot){
        if(this.getStackInSlot(slot).getItem() instanceof IItemElectricEnergyStorage) {
            double energyReceive = this.getStackInSlot(slot).getCapability(CapabilityElectricEnergy.ENERGY, null).receiveEnergy(this.getStorage().extractEnergy(this.getStorage().getMaxExtract(), true), false);
            this.getStorage().extractEnergy(energyReceive, false);
            updateVars();
            markDirty();
        }
    }

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
        this.getStorage().setEnergy(compound.getDouble(NBTConstants.energy));
		
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setDouble(NBTConstants.energy, this.getEnergyStoredMod());
		return compound;
	}
}
