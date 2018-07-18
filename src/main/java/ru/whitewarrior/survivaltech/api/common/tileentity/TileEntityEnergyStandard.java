package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import ru.whitewarrior.survivaltech.NBTConstants;
import ru.whitewarrior.survivaltech.api.common.energy.IElectricEnergyStorage;

/**
 * Date: 2017-12-30.
 * Time: 22:07:45.
 *
 * @author WhiteWarrior
 */
public abstract class TileEntityEnergyStandard extends TileEntityInventory implements IElectricEnergyStorage {

    public TileEntityEnergyStandard() {

    }

    public TileEntityEnergyStandard(String name) {
        super(name);
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
