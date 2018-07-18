package ru.whitewarrior.survivaltech.api.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.util.BlockBoundUtil;
import ru.whitewarrior.survivaltech.util.NetworkUtil;

import java.util.List;

/**
 * Date: 2017-12-23. Time: 23:51:45.
 * 
 * @author WhiteWarrior
 */
public abstract class BlockRenderCable extends BlockCable  {
		public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");

	public BlockRenderCable(Material materialIn) {
		super(materialIn);
		.setDefaultState(getBlockState().getBaseState().withProperty(DOWN, false).withProperty(UP, false).withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(EAST, false));
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(DOWN, NetworkUtil.canConnectTo(world, pos, EnumFacing.DOWN)).withProperty(UP, NetworkUtil.canConnectTo(world, pos, EnumFacing.UP)).withProperty(NORTH, NetworkUtil.canConnectTo(world, pos, EnumFacing.NORTH)).withProperty(SOUTH, NetworkUtil.canConnectTo(world, pos, EnumFacing.SOUTH)).withProperty(WEST, NetworkUtil.canConnectTo(world, pos, EnumFacing.WEST)).withProperty(EAST, NetworkUtil.canConnectTo(world, pos, EnumFacing.EAST));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(, UP, DOWN, NORTH, SOUTH, WEST, EAST);
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean bool) {
		BlockBoundUtil.addCollisionBoxToListCable(worldIn, pos, entityBox, collidingBoxes);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BlockBoundUtil.getBoundingBoxCable(state, source, pos);
	}
}
