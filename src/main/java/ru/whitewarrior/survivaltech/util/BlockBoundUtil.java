package ru.whitewarrior.survivaltech.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Date: 2017-12-24.
 * Time: 14:00:57.
 *
 * @author WhiteWarrior
 */
public class BlockBoundUtil {
    public static void addCollisionBoxToListCable(IBlockAccess world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.377, 0.377, 0.377, 0.623D, 0.623D, 0.623D));
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.UP)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.377, 0, 0.377, 0.623D, 0.623D, 0.623D));
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.UP)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.377, 0.377, 0.377, 0.623D, 1, 0.623D));
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.NORTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.377, 0.377, 0, 0.623D, 0.623D, 0.623D));
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.SOUTH)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.377, 0.377, 0.377, 0.623D, 0.623D, 1));
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.EAST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.377, 0.377, 0.377, 1, 0.623D, 0.623D));
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.WEST)) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0, 0.377, 0.377, 0.623D, 0.623D, 0.623D));
        }
    }

    public static AxisAlignedBB getBoundingBoxCable(IBlockState state, IBlockAccess world, BlockPos pos) {
        double[] sidebound = new double[6];
        sidebound[0] = sidebound[1] = sidebound[2] = 0.377D;
        sidebound[3] = sidebound[4] = sidebound[5] = 0.6231D;

        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.DOWN) == true) {
            sidebound[1] = 0;
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.UP) == true) {
            sidebound[4] = 1;
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.NORTH) == true) {
            sidebound[2] = 0;
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.SOUTH) == true) {
            sidebound[5] = 1;
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.WEST) == true) {
            sidebound[0] = 0;
        }
        if (NetworkUtil.canConnectTo(world, pos, EnumFacing.EAST) == true) {
            sidebound[3] = 1;
        }

        return new AxisAlignedBB(sidebound[0], sidebound[1], sidebound[2], sidebound[3], sidebound[4], sidebound[5]);
    }

    public static void addCollisionBoxToList(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable AxisAlignedBB blockBox) {
        if (blockBox != Block.NULL_AABB) {
            AxisAlignedBB axisalignedbb = blockBox.offset(pos);

            if (entityBox.intersects(axisalignedbb)) {
                collidingBoxes.add(axisalignedbb);
            }
        }
    }
}
