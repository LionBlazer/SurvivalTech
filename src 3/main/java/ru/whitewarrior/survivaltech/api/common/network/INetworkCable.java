package ru.whitewarrior.survivaltech.api.common.network;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Date: 2017-12-24.
 * Time: 13:45:57.
 * @author WhiteWarrior
 */
public interface INetworkCable extends INetworkConductor{
	
	@Override
	public default EnumFacing[] getInputFacing(IBlockAccess world, BlockPos pos) {
		return null;
	}
	
	@Override
	public default EnumFacing[] getOutputFacing(IBlockAccess world, BlockPos pos) {
		return null;
	}
	@Override
	public default boolean allOutputInputFacing(IBlockAccess world, BlockPos pos) {
		return true;
	}
}
