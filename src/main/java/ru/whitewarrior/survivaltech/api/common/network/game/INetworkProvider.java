package ru.whitewarrior.survivaltech.api.common.network.game;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Date: 2017-12-30.
 * Time: 12:41:52.
 * @author WhiteWarrior
 */
public interface INetworkProvider extends INetworkConnection{
	NetworkProviderType getProviderType(IBlockAccess world, BlockPos pos);
}
