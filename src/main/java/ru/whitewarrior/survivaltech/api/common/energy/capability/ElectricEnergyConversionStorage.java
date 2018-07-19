package ru.whitewarrior.survivaltech.api.common.energy.capability;

import net.minecraft.item.ItemStack;
import ru.whitewarrior.survivaltech.api.common.energy.IEnergyElectric;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;

/**
 * Date: 2017-12-31.
 * Time: 17:18:25.
 * @author WhiteWarrior
 */
public class ElectricEnergyConversionStorage implements IEnergyElectric {

	public ItemStack itemStack;

	public IItemElectricEnergyStorage item;

	public ElectricEnergyConversionStorage(IItemElectricEnergyStorage item, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.item = item;
	}

	@Override
	public double receiveEnergy(double maxReceive, boolean simulate) {
		return item.receiveEnergy(itemStack, maxReceive, simulate);
	}

	@Override
	public double extractEnergy(double maxExtract, boolean simulate) {
		return item.extractEnergy(itemStack, maxExtract, simulate);
	}

	@Override
	public double getEnergyStoredMod() {
		return item.getEnergyStored(itemStack);
	}

	@Override
	public double getMaxEnergyStoredMod() {
		return item.getMaxEnergyStored(itemStack);
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	
}