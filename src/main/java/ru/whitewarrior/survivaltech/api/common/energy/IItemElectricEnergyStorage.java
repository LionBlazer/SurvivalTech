package ru.whitewarrior.survivaltech.api.common.energy;

import net.minecraft.item.ItemStack;

/**
 * Date: 2017-12-31.
 * Time: 17:14:07.
 * @author WhiteWarrior
 */
public interface IItemElectricEnergyStorage {
	double receiveEnergy(ItemStack stack, double maxReceive, boolean simulate);
	double extractEnergy(ItemStack stack, double maxExtract, boolean simulate);
	double getEnergyStored(ItemStack stack);
	double getMaxExtract(ItemStack stack);
	double getMaxReceive(ItemStack stack);
	double getMaxEnergyStored(ItemStack stack);
	
	//advanced
	default void setEnergyStored(ItemStack stack, double energy) {}

    default void setMaxEnergyStored(ItemStack stack, int energy) {}
}
