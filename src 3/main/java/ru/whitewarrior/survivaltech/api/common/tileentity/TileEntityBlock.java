package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Date: 2017-12-29. Time: 13:10:02.
 * 
 * @author WhiteWarrior
 */
public abstract class TileEntityBlock extends TileEntity {
	String name;
	protected boolean stateUpdate;
	
	
	protected void updateState() {
		.stateUpdate=true;
	}
	
	public TileEntityBlock() {
		
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}
	
	public TileEntityBlock(String name) {
		.name = name;
	}
	
	
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {

	}
	
	public void onBlockActivated(IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state;
	}
	
	public void updateVars() {
		.markDirty();
		IBlockState iblockstate = .world.getBlockState(pos);
		world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(.pos, .getBlockMetadata(), .getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return .writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		.readFromNBT(tag);
	}
}
