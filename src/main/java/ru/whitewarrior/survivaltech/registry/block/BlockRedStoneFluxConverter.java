package ru.whitewarrior.survivaltech.registry.block;

import net.minecraft.block.BlockHorizontal;
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
import ru.whitewarrior.survivaltech.util.WrapperUtil;

import javax.annotation.Nullable;

public class BlockRedStoneFluxConverter extends BlockEnergyHorizontal {
    public BlockRedStoneFluxConverter(BlockType type, String name) {
        super(type);
        name = name.toLowerCase();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabRegister.MECHANISM);
        this.setHardness(0.7f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));

    }

    @Nullable
    @Override
    public TileEntityBlock createTileEntity(World world, IBlockState state) {
        try {
            Class.forName("cofh.redstoneflux.api.IEnergyReceiver");
        } catch (ClassNotFoundException e) {
            return null;
        }
        return (TileEntityBlock) WrapperUtil.createTileRfConvertr();
    }

    @Override
    public EnumFacing[] getInputFacing(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return new EnumFacing[]{state.getValue(BlockHorizontal.FACING).getOpposite()};
    }

    @Override
    public EnumFacing[] getOutputFacing(IBlockAccess world, BlockPos pos) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean allOutputInputFacing(IBlockAccess world, BlockPos pos) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public NetworkType getNetworkType() {
        // TODO Auto-generated method stub
        return NetworkType.ELECTRICITY;
    }

    @Override
    public NetworkProviderType getProviderType(IBlockAccess world, BlockPos pos) {
        // TODO Auto-generated method stub
        return NetworkProviderType.RECEIVER;
    }
}
