package ru.whitewarrior.survivaltech.util;

import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;

/**
 * Date: 2017-12-30.
 * Time: 19:41:46.
 * @author WhiteWarrior
 */
public class EnergyUtil {
	public static boolean isMaxEnergyStorage(ElectricEnergyStorage storage) {
        return !(storage.getEnergyStoredMod() < storage.getMaxEnergyStoredMod());
    }
	
	public static boolean extractEnergyInStorage(ElectricEnergyStorage storage) {
        return !(storage.getEnergyStoredMod() < storage.getMaxEnergyStoredMod());
    }
}
