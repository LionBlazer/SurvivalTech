package ru.whitewarrior.survivaltech.api.common.network.game;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Date: 2017-12-23. Time: 23:53:10.
 * 
 * @author WhiteWarrior
 */
public interface INetworkConductor extends INetworkProvider {
	int getConductivity(IBlockAccess world, BlockPos pos);

	@Override
	default NetworkType getNetworkType() {
		return NetworkType.ELECTRICITY;
	}

	default NetworkProviderType getProviderType(IBlockAccess world, BlockPos pos) {
		return NetworkProviderType.CONDUCTOR;
	}
}
