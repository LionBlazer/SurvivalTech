package ru.whitewarrior.survivaltech.registry.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.api.common.block.BlockEnergy;
import ru.whitewarrior.survivaltech.api.common.block.BlockEnergyHorizontal;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.network.NetworkProviderType;
import ru.whitewarrior.survivaltech.api.common.network.NetworkType;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;
import ru.whitewarrior.survivaltech.registry.tileentity.ledmachine.TileEntityLedMachine;

public class BlockLedMachine extends BlockEnergyHorizontal {

    public BlockLedMachine(BlockType type, String name) {
        super(type);
        name = name.toLowerCase();
        .setRegistryName(name);
        .setUnlocalizedName(name);
        .setCreativeTab(CreativeTabRegister.MECHANISM);
        .setHardness(0.3f);
    }

    @Override
    public TileEntityBlock createNewTileEntity(World worldIn, int meta) {
        return new TileEntityLedMachine();
    }

    @Override
    public NetworkProviderType getProviderType(IBlockAccess world, BlockPos pos) {
        return NetworkProviderType.RECEIVER;
    }

    @Override
    public EnumFacing[] getOutputFacing(IBlockAccess world, BlockPos pos) {
        return new EnumFacing[0];
    }

    @Override
    public EnumFacing[] getInputFacing(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return new EnumFacing[] { state.getValue(BlockHorizontal.FACING)};
    }

    @Override
    public NetworkType getNetworkType() {
        return NetworkType.ELECTRICITY;
    }
}
