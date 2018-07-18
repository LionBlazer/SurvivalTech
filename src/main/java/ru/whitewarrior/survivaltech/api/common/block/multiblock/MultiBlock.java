package ru.whitewarrior.survivaltech.api.common.block.multiblock;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Date: 2018-01-13. Time: 21:03:33.
 *
 * @author WhiteWarrior
 */
public abstract class MultiBlock {
    protected final ArrayList<Block[][]> layerList = new ArrayList<Block[][]>();
    private BlockPos mainBlock;
    protected final HashSet<Block> uniqueblocks = new HashSet<Block>();

    public MultiBlock() {

    }

    public HashSet<Block> getUniqueblocks() {
        return uniqueblocks;
    }

    public final ArrayList<Block[][]> getBlockList() {
        return layerList;
    }

    public BlockPos getMainBlock() {
        return mainBlock;
    }

    public void setMainBlock(int layer, int x, int y) {
        mainBlock = new BlockPos(x, layer, y);
    }
}
