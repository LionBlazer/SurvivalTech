package ru.whitewarrior.survivaltech.api.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.api.common.network.INetworkConductor;
import ru.whitewarrior.survivaltech.api.common.network.INetworkProvider;
import ru.whitewarrior.survivaltech.api.common.network.NetworkProviderType;
import ru.whitewarrior.survivaltech.util.NetworkUtil;

/**
 * Date: 2017-12-30.
 * Time: 13:49:40.
 * @author WhiteWarrior
 */

public abstract class BlockEnergy extends BlockWithTileEntity implements INetworkProvider{

	protected BlockEnergy(BlockType type) {
		super(type);
		// TODO Auto-generated constructor stub
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
    @Override
    public boolean allOutputInputFacing(IBlockAccess world, BlockPos pos) {
        return false;
    }

}
