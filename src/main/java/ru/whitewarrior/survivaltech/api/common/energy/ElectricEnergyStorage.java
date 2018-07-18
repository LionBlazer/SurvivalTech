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
        this(capacity, capacity, capacity, 0);
    }

    public ElectricEnergyStorage(double capacity, double maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public ElectricEnergyStorage(double capacity, double maxReceive, double maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public ElectricEnergyStorage(double capacity, double maxReceive, double maxExtract, double energy) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.max(0, Math.min(capacity, energy));
    }

    @Override
    public double receiveEnergy(double maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        double energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public double extractEnergy(double maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        double energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public double getEnergyStoredMod() {
        return energy;
    }

    @Override
    public double getMaxEnergyStoredMod() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getMaxReceive() {
        return maxReceive;
    }

    public double getMaxExtract() {
        return maxExtract;
    }

    public void addEnergyReceivedPerTick(double energyReceived) {
        this.energyReceived += energyReceived;
    }

    public void zeroEnergyReceivedPerTick() {
        energyReceived = 0;
    }

    public double getEnergyReceivedPerTick() {
        return energyReceived;
    }
}