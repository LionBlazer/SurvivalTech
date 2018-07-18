package ru.whitewarrior.survivaltech.registry.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.block.BlockWithTileEntity;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;
import ru.whitewarrior.survivaltech.registry.tileentity.fluidtank.TileEntityFluidTank;

/**
 * Date: 2018-03-24. Time: 21:43:24.
 *
 * @author WhiteWarrior
 */
public class BlockFluidTank extends BlockWithTileEntity {

    public BlockFluidTank(BlockType type, String name) {
        super(type);
        name = name.toLowerCase();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(0.6f);
        this.setCreativeTab(getBlockType().getCreativeTab());
    }


    @Override
    public TileEntityBlock createTileEntity(World worldIn, IBlockState state) {
        return new TileEntityFluidTank();
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        // TODO Auto-generated method stub
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        // TODO Auto-generated method stub
        return false;
    }
}
