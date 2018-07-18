package ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Date: 2017-12-31.
 * Time: 20:59:35.
 * @author WhiteWarrior
 */
public class ToolRepairerRecipeManager {
public static final List<ToolRepairerRecipe> recipes = new ArrayList<ToolRepairerRecipe>();

	public static void registerRecipe(ToolRepairerRecipe recipe) {
		recipes.add(recipe);
	}

    public static void registerRecipe(ItemStack stackTool, ItemStack stackRepair) {
        recipes.add(new ToolRepairerRecipe(stackTool,stackRepair));
    }

    public static void registerRecipe(Item stackTool, Item stackRepair) {
        recipes.add(new ToolRepairerRecipe(new ItemStack(stackTool),new ItemStack(stackRepair)));
    }
}
