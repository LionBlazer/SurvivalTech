package ru.whitewarrior.survivaltech.api.common.energy;

/**
 * Date: 2017-12-29. Time: 21:10:02.
 * 
 * @author WhiteWarrior
 */
public interface IElectricEnergyStorage extends IEnergyElectric {
	ElectricEnergyStorage getStorage();

	@Override
	default double receiveEnergy(double maxReceive, boolean simulate) {
		return getStorage().receiveEnergy(maxReceive, simulate);
	}

	@Override
	default double extractEnergy(double maxExtract, boolean simulate) {
		return getStorage().extractEnergy(maxExtract, simulate);
	}

	@Override
	default double getEnergyStored() {
		return getStorage().getEnergyStored();
	}

	@Override
	default double getMaxEnergyStored() {
		return getStorage().getMaxEnergyStored();
	}

	@Override
	default boolean canExtract() {
		return getStorage().canExtract();
	}

	@Override
	default boolean canReceive() {
		return getStorage().canReceive();
	}
}
