package ru.whitewarrior.survivaltech.api.common.energy;

/**
 * Date: 2017-12-29.
 * Time: 21:03:28.
 *
 * @author WhiteWarrior
 */
public interface IEnergyElectric {

    double receiveEnergy(double maxReceive, boolean simulate);

    double extractEnergy(double maxExtract, boolean simulate);

    double getEnergyStoredMod();

    double getMaxEnergyStoredMod();

    boolean canExtract();

    boolean canReceive();

}
