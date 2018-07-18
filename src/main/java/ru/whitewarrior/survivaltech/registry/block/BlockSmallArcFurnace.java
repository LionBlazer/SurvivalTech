package ru.whitewarrior.survivaltech.registry.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.api.common.block.BlockEnergyHorizontal;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.network.game.NetworkProviderType;
import ru.whitewarrior.survivaltech.api.common.network.game.NetworkType;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.TileEntitySmallArcFurnace;

import javax.annotation.Nullable;

public class BlockSmallArcFurnace extends BlockEnergyHorizontal {
    public BlockSmallArcFurnace(BlockType type, String name) {
        super(type);
        name = name.toLowerCase();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabRegister.MECHANISM);
        this.setHardness(0.7f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(BlockSolidFuelGenerator.BURNING, false));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int value = meta;
        boolean isBurn = false;
        EnumFacing enumfacing = EnumFacing.values()[value >> 1];//0100
        value -= (enumfacing.ordinal() << 1);
        if (value == 1)
            isBurn = true;
        return this.getDefaultState().withProperty(BlockHorizontal.FACING, enumfacing).withProperty(BlockSolidFuelGenerator.BURNING, isBurn);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockSolidFuelGenerator.BURNING, BlockHorizontal.FACING);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (state.getValue(BlockSolidFuelGenerator.BURNING) == Boolean.valueOf(true))
            return 10;
        return 0;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int value = state.getValue(BlockHorizontal.FACING).ordinal();
        value <<= 1;
        value += state.getValue(BlockSolidFuelGenerator.BURNING) ? 1 : 0;
        return value;
    }

    @Nullable
    @Override
    public TileEntityBlock createTileEntity(World world, IBlockState state) {
        return new TileEntitySmallArcFurnace();
    }

    @Override
    public NetworkProviderType getProviderType(IBlockAccess world, BlockPos pos) {
        return NetworkProviderType.RECEIVER;
    }

    @Override
    public EnumFacing[] getInputFacing(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return new EnumFacing[]{state.getValue(BlockHorizontal.FACING).getOpposite()};
    }

    @Override
    public EnumFacing[] getOutputFacing(IBlockAccess world, BlockPos pos) {
        return new EnumFacing[0];
    }


    @Override
    public NetworkType getNetworkType() {
        return NetworkType.ELECTRICITY;
    }
}
