package ru.whitewarrior.survivaltech.util;

import net.minecraft.block.Block;
import net.minecraft.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.whitewarrior.survivaltech.api.common.network.INetworkConnection;
import ru.whitewarrior.survivaltech.api.common.network.INetworkProvider;
import ru.whitewarrior.survivaltech.api.common.network.NetworkProviderType;

import java.util.LinkedHashMap;

/**
 * Date: 2017-12-24.
 * Time: 13:49:15.
 * @author WhiteWarrior
 */
public class NetworkUtil {
	// �������� �� ����(� ������������ �����������) ������ ������� � ������� �����
	public static boolean canInputConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		if (facing != null) {
			BlockPos pos2 = pos.offset(facing);
			if (world.getBlockState(pos2).getBlock() instanceof INetworkConnection) {
				INetworkConnection block = (INetworkConnection) world.getBlockState(pos2).getBlock();
				if (block.allOutputInputFacing(world, pos2) == true)
					return true;
				if (block.getInputFacing(world, pos2) != null)
					for (int i = 0; i < block.getInputFacing(world, pos2).length; ++i) {
						if (block.getInputFacing(world, pos2)[i] == facing.getOpposite()) {
							return true;
						}
					}
			}
		}
		return false;
	}
	//�������� �� ����(� ������������ �����������) ������� ������� � ������� �����
	public static boolean canOutputConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		if (facing != null) {
			BlockPos pos2 = pos.offset(facing);
			if (world.getBlockState(pos2).getBlock() instanceof INetworkConnection) {
				INetworkConnection block = (INetworkConnection) world.getBlockState(pos2).getBlock();
				if (block.allOutputInputFacing(world, pos2) == true)
					return true;
				if (block.getOutputFacing(world, pos2) != null)
					for (int i = 0; i < block.getOutputFacing(world, pos2).length; ++i) {
						if (block.getOutputFacing(world, pos2)[i] == facing.getOpposite()) {
							return true;
						}
					}
			}
		}
		return false;
	}

	public static boolean canConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		return NetworkUtil.canInputConnectTo(world, pos, facing) || NetworkUtil.canOutputConnectTo(world, pos, facing);
	}



	@Deprecated
	public static LinkedHashMap<BlockPos, Integer> test(IBlockAccess world, BlockPos pos, LinkedHashMap<BlockPos, Integer> set, int level) {
		for(EnumFacing facing : EnumFacing.VALUES) {
			BlockPos po = pos.offset(facing);
			if(world.getBlockState(po).getBlock() instanceof INetworkProvider){
				if (!set.containsKey(po) || set.get(po) > level+ 1) {
					set.put(po, level+ 1);
					test(world, po, set, level + 1);
				}
			}
		}
		return set;
	}

    public static void buildNetworkConductor(IBlockAccess world, BlockPos pos) {
        FindPathUtil.buildNetwork(pos, world, false);
    }

	public static void buildNetworkMulti(IBlockAccess world, BlockPos pos) {
        FindPathUtil.buildNetwork(pos, world, true);
	}

	public static void buildNetworkProducer(IBlockAccess world, BlockPos pos) {
        FindPathUtil.buildNetwork(pos, world, true);
	}

	public static void buildNetworkReceiver(IBlockAccess world, BlockPos pos) {
        FindPathUtil.buildNetwork(pos, world, true);
	}
}