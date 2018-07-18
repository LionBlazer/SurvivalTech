package ru.whitewarrior.survivaltech.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipe;

import java.util.List;

public class SmallArcFurnaceRecipeWrapper implements IRecipeWrapper {
    private List<ItemStack> input;
    private List<ItemStack> output;
    private int time = 0;

    public SmallArcFurnaceRecipeWrapper(SmallArcFurnaceRecipe recipe) {
        input = recipe.getInput();
        output = recipe.getOutput();
        time = recipe.getTime();
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputs(ItemStack.class, input);
        iIngredients.setOutputs(ItemStack.class, output);
    }

    public List<ItemStack> getInput() {
        return input;
    }

    public List<ItemStack> getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }
}
