package ru.whitewarrior.survivaltech.util;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Date: 2017-12-30. Time: 19:42:46.
 *
 * @author WhiteWarrior
 */
public class TileEntityUtil {
    public static int getBurnLeftScaled(int pixels, int burntime[]) {
        if (burntime[0] != 0 && burntime[1] != 0)
            return pixels * burntime[0] / burntime[1];
        return 0;
    }

    public static void setState(World worldIn, BlockPos pos, IProperty prop, boolean active) {
        if (!worldIn.isRemote) {

            if (!worldIn.getBlockState(pos).getValue(prop).equals(active)) {
                TileEntity tileentity = worldIn.getTileEntity(pos);
                tileentity.markDirty();
                IBlockState oldState = worldIn.getBlockState(pos);

                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState().withProperty(BlockHorizontal.FACING, oldState.getValue(BlockHorizontal.FACING)).withProperty(prop, active), 3);
                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState().withProperty(BlockHorizontal.FACING, oldState.getValue(BlockHorizontal.FACING)).withProperty(prop, active), 3);
                //worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState().withProperty(BlockHorizontal.FACING, oldState.getValue(BlockHorizontal.FACING)).withProperty(prop, Boolean.valueOf(active)),3);

                tileentity.validate();
                worldIn.setTileEntity(pos, tileentity);

            }

        }
    }
}
