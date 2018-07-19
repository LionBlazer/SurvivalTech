package ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe;

import net.minecraft.item.Item;

import java.util.HashMap;

/**
 * Date: 2017-12-31.
 * Time: 20:59:35.
 * @author WhiteWarrior
 */
public class ToolRepairerRecipes {
public static final HashMap<Item, Item> recipes = new HashMap<Item, Item>();

	public static void registerRecipe(Item materialstack, Item stack) {
		recipes.put(materialstack, stack);
	}
}
