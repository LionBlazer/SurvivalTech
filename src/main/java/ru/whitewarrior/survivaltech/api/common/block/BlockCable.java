package ru.whitewarrior.survivaltech.api.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkCable;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkConductor;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkProvider;
import ru.whitewarrior.survivaltech.api.common.network.game.NetworkProviderType;
import ru.whitewarrior.survivaltech.util.NetworkUtil;

/**
 * Date: 2017-12-30.
 * Time: 13:36:47.
 * @author WhiteWarrior
 */
public abstract class BlockCable extends Block implements INetworkCable, IAdvancedBlock, INetworkProvider{
	BlockType type;
	public BlockCable(Material materialIn) {
		super(materialIn);
		// TODO Auto-generated constructor stub
	}


	@Override
	public BlockType getBlockType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

		for (EnumFacing facing : EnumFacing.VALUES) {
			if (worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof INetworkProvider && NetworkUtil.canConnectTo(worldIn, pos, facing)) {
				NetworkProviderType type = (((INetworkProvider) worldIn.getBlockState(pos.offset(facing)).getBlock()).getProviderType(worldIn, pos.offset(facing)));
				if (type == NetworkProviderType.MULTI)
					NetworkUtil.buildNetworkMulti(worldIn, pos.offset(facing));
				else if (type == NetworkProviderType.PRODUCER)
					NetworkUtil.buildNetworkProducer(worldIn, pos.offset(facing));
				else if (type == NetworkProviderType.RECEIVER)
					NetworkUtil.buildNetworkReceiver(worldIn, pos.offset(facing));
			}
			if (worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof INetworkConductor && NetworkUtil.canConnectTo(worldIn, pos, facing)) {
				NetworkUtil.buildNetworkConductor(worldIn, pos.offset(facing));
			}
		}
		super.breakBlock(worldIn, pos, state);
	}


}
