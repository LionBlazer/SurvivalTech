package ru.whitewarrior.survivaltech.registry;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import ru.whitewarrior.survivaltech.parser.RecipeParser;
import ru.whitewarrior.survivaltech.proxy.CommonProxy;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipe;

import java.util.List;

public class ConfigParserRegister {
    public static void init(FMLInitializationEvent event) {
        List<SmallArcFurnaceRecipe> recipesSAF = RecipeParser.parseRecipeSmallArcFurnace(CommonProxy.configRecipeArcFurnace);
        SmallArcFurnaceRecipe.RECIPES.addAll(recipesSAF);
    }
}
