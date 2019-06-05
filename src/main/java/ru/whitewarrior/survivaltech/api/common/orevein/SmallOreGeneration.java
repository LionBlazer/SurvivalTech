package ru.whitewarrior.survivaltech.api.common.orevein;

import net.minecraft.block.state.IBlockState;

public class SmallOreGeneration {
    private IBlockState state;
    private float change;
    private int minY;
    private int maxY;

    public SmallOreGeneration(IBlockState state, float change, int minY, int maxY) {
        this.state = state;
        this.change = change;
        this.minY = minY;
        this.maxY = maxY;
    }

    public IBlockState getState() {
        return state;
    }

    public float getChange() {
        return change;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}
