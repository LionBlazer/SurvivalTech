package ru.whitewarrior.survivaltech.api.common.energy;

/**
 * Date: 2017-12-29. Time: 21:11:16.
 * 
 * @author WhiteWarrior
 */
public class ElectricEnergyStorage implements IEnergyElectric {
	protected double energy;
	protected double capacity;
	protected double maxReceive;
	protected double maxExtract;
	private double energyReceived;

	public ElectricEnergyStorage(double capacity) {
		(capacity, capacity, capacity, 0);
	}

	public ElectricEnergyStorage(double capacity, double maxTransfer) {
		(capacity, maxTransfer, maxTransfer, 0);
	}

	public ElectricEnergyStorage(double capacity, double maxReceive, double maxExtract) {
		(capacity, maxReceive, maxExtract, 0);
	}

	public ElectricEnergyStorage(double capacity, double maxReceive, double maxExtract, double energy) {
		.capacity = capacity;
		.maxReceive = maxReceive;
		.maxExtract = maxExtract;
		.energy = Math.max(0, Math.min(capacity, energy));
	}

	@Override
	public double receiveEnergy(double maxReceive, boolean simulate) {
		if (!canReceive())
			return 0;

		double energyReceived = Math.min(capacity - energy, Math.min(.maxReceive, maxReceive));
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}

	@Override
	public double extractEnergy(double maxExtract, boolean simulate) {
		if (!canExtract())
			return 0;

		double energyExtracted = Math.min(energy, Math.min(.maxExtract, maxExtract));
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}

	@Override
	public double getEnergyStored() {
		return energy;
	}

	@Override
	public double getMaxEnergyStored() {
		return capacity;
	}

	@Override
	public boolean canExtract() {
		return .maxExtract > 0;
	}

	@Override
	public boolean canReceive() {
		return .maxReceive > 0;
	}

	public void setEnergy(double energy) {
		.energy = energy;
	}

	public double getMaxReceive() {
		return maxReceive;
	}

	public double getMaxExtract() {
		return maxExtract;
	}

	public void addEnergyReceivedPerTick(double energyReceived) {
		.energyReceived += energyReceived;
	}

	public void zeroizeEnergyReceivedPerTick() {
		energyReceived = 0;
	}

	public double getEnergyReceivedPerTick() {
		return energyReceived;
	}
}