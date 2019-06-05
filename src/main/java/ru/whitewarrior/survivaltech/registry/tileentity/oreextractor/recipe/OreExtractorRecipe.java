package ru.whitewarrior.survivaltech.registry.tileentity.oreextractor.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

import java.util.ArrayList;
import java.util.List;

public class OreExtractorRecipe {
    public static final List<OreExtractorRecipe> RECIPES = new ArrayList<>();
    public static final List<OreRecipe> BAKED_RECIPES = new ArrayList<>();
    private int change;
    private ItemStack result;

    public OreExtractorRecipe(int change, ItemStack result) {
        this.change = change;
        this.result = result;
    }

    public int getChange() {
        return change;
    }

    public ItemStack getResult() {
        return result;
    }

    public static void bake(){
        RECIPES.forEach(r -> BAKED_RECIPES.add(new OreRecipe(r.change, r.result)));
    }

    public static class OreRecipe extends WeightedRandom.Item {
        private ItemStack stack;

        public OreRecipe(int itemWeightIn, ItemStack stack) {
            super(itemWeightIn);
            this.stack = stack;
        }

        public ItemStack getStack() {
            return stack;
        }
    }
}

