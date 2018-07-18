package ru.whitewarrior.survivaltech.registry.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.api.common.block.BlockEnergyHorizontal;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.network.NetworkProviderType;
import ru.whitewarrior.survivaltech.api.common.network.NetworkType;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;
import ru.whitewarrior.survivaltech.registry.tileentity.solidfuelgenerator.TileEntitySolidFuelGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Date: 2017-12-29. Time: 15:07:12.
 * 
 * @author WhiteWarrior
 */
public class BlockSolidFuelGenerator extends BlockEnergyHorizontal {
	public static final PropertyBool BURNING = PropertyBool.create("burning");

	public BlockSolidFuelGenerator(BlockType type, String name) {
		super(type);
		name = name.toLowerCase();
		.setRegistryName(name);
		.setUnlocalizedName(name);
		.setCreativeTab(CreativeTabRegister.MECHANISM);
		.setDefaultState(.blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(BURNING, Boolean.valueOf(false)));
		.setHardness(0.4f);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing enumfacing = (EnumFacing) state.getValue(BlockHorizontal.FACING);
		switch (enumfacing) {
		case WEST:
			return new AxisAlignedBB(0, 0, 0.13, 1, 1, 0.87);
		case EAST:
			return new AxisAlignedBB(0, 0, 0.13, 1, 1, 0.87);
		case NORTH:
			return new AxisAlignedBB(0.13, 0, 0, 0.87, 1, 1);
		case SOUTH:
			return new AxisAlignedBB(0.13, 0, 0, 0.87, 1, 1);
		default:
			return FULL_BLOCK_AABB;
		}

	}

	@Override
	public TileEntityBlock createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySolidFuelGenerator();
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state.getValue(BURNING) == Boolean.valueOf(true))
			return 10;
		return 0;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		EnumFacing enumfacing = (EnumFacing) state.getValue(BlockHorizontal.FACING);
		switch (enumfacing) {
		case WEST:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0.15, 1, 0.6, 0.85));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.6, 0.3, 1, 0.8, 0.7));
			break;
		case EAST:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0, 0.15, 1, 0.6, 0.85));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.6, 0.3, 1, 0.8, 0.7));
			break;
		case NORTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.15, 0, 0, 0.85, 0.6, 1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.3, 0.6, 0, 0.7, 0.8, 1));
			break;
		case SOUTH:
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.15, 0, 0, 0.85, 0.6, 1));
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.3, 0.6, 0, 0.7, 0.8, 1));
		default:
			break;
		}

	}

	@Override
	public NetworkProviderType getProviderType(IBlockAccess world, BlockPos pos) {
		return NetworkProviderType.PRODUCER;
	}

	@Override
	public boolean allOutputInputFacing(IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public NetworkType getNetworkType() {
		return NetworkType.ELECTRICITY;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(BURNING) == true) {
			EnumFacing enumfacing = (EnumFacing) stateIn.getValue(BlockHorizontal.FACING);
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = (double) pos.getY() + 0.35D;
			double d2 = (double) pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.3D - 0.2D;
			double d6 = 0.62D;

			if (rand.nextDouble() < 0.2D) {
				worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			switch (enumfacing) {
			case WEST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.3, d1 + d6, d2 + d4, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case EAST:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.3, d1 + d6, d2 + d4, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
				break;
			case NORTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + d6, d2 - 0.3, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
				break;
			case SOUTH:
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + d6, d2 + 0.3, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
			default:
				break;
			}
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(, new IProperty[] { BURNING, BlockHorizontal.FACING });
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumFacing[] getOutputFacing(IBlockAccess world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return new EnumFacing[] { state.getValue(BlockHorizontal.FACING).getOpposite()};
	}

	@Override
	public EnumFacing[] getInputFacing(IBlockAccess world, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

}
