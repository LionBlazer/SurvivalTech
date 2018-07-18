package ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SmallArcFurnaceRecipe {
    public static final List<SmallArcFurnaceRecipe> RECIPES = new ArrayList<>();
    private List input;
    private List<ItemStack> output;
    private int time;

    public SmallArcFurnaceRecipe(List input, List<ItemStack> output, int time) {
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public List getInput() {
        return input;
    }

    public List<ItemStack> getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }
}
