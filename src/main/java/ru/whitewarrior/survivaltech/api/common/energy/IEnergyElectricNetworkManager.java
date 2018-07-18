package ru.whitewarrior.survivaltech.api.common.energy;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

/**
 * Date: 2017-12-29.
 * Time: 21:45:49.
 *
 * @author WhiteWarrior
 */
public interface IEnergyElectricNetworkManager {
    HashMap<BlockPos, Integer> getReceiversPos();

    HashMap<BlockPos, Integer> getActiveReceiversPos();
}
