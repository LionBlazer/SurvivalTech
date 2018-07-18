package ru.whitewarrior.survivaltech.api.common.network;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * Date: 2017-12-24.
 * Time: 13:43:11.
 * @author WhiteWarrior
 */
public interface INetworkConnection {
	EnumFacing[] getOutputFacing(IBlockAccess world, BlockPos pos);
	EnumFacing[] getInputFacing(IBlockAccess world, BlockPos pos);
	boolean allOutputInputFacing(IBlockAccess world, BlockPos pos);
	NetworkType getNetworkType();
}
