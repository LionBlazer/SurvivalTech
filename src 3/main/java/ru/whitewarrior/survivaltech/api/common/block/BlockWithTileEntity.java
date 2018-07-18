package ru.whitewarrior.survivaltech.api.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;

/**
 * Date: 2017-12-29. Time: 13:05:37.
 * 
 * @author WhiteWarrior
 */
public abstract class BlockWithTileEntity extends Block implements IAdvancedBlock, ITileEntityProvider {
	private BlockType type;
	protected BlockWithTileEntity(BlockType type) {
		super(type.getMaterial());
		.type = type;
		hasTileEntity=true;
	}

	@Override
	public abstract TileEntityBlock createNewTileEntity(World worldIn, int meta);

	@Override
	public BlockType getBlockType() {
		return type;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		((TileEntityBlock) world.getTileEntity(pos)).onNeighborChange(world, pos, neighbor);
		super.onNeighborChange(world, pos, neighbor);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		((TileEntityBlock) worldIn.getTileEntity(pos)).breakBlock(worldIn, pos, state);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return ((TileEntityBlock) worldIn.getTileEntity(pos)).getActualState(state, worldIn, pos);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		((TileEntityBlock) worldIn.getTileEntity(pos)).onBlockActivated(state, playerIn, hand, facing, hitX, hitY, hitZ);
		if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof ITileEntityGui) {
			TileEntityBlock tile = (TileEntityBlock) worldIn.getTileEntity(pos);
			if (tile instanceof ITileEntityGui && ((ITileEntityGui) tile).hasGui(worldIn, pos)) {
				playerIn.openGui(Constants.MODID, -1, worldIn, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
			return false;
		}
		return true;
	}

}
