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
import ru.whitewarrior.survivaltech.registry.tileentity.ledmachine.TileEntityLedMachine;

public class BlockLedMachine extends BlockEnergyHorizontal {


    public BlockLedMachine(BlockType type, String name) {
        super(type);
        name = name.toLowerCase();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabRegister.MECHANISM);
        this.setHardness(0.5f);
    }


    @Override
    public TileEntityBlock createTileEntity(World worldIn, IBlockState state)  {
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
        return new EnumFacing[]{world.getBlockState(pos).getValue(BlockHorizontal.FACING).getOpposite()};
    }

    @Override
    public NetworkType getNetworkType() {
        return NetworkType.ELECTRICITY;
    }
}
