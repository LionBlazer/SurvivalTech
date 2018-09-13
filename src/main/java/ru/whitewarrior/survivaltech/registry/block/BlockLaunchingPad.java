package ru.whitewarrior.survivaltech.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.block.IAdvancedBlock;

import javax.annotation.Nullable;

public class BlockLaunchingPad extends Block implements IAdvancedBlock {

    BlockType type;
    public BlockLaunchingPad(BlockType type, String name) {
        super(Material.IRON);
        this.type = type;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(getBlockType().getCreativeTab());
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0,0,0,1,0.2, 1);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(0,0,0,1,0.185, 1);
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
    public BlockType getBlockType() {
        return type;
    }
}
