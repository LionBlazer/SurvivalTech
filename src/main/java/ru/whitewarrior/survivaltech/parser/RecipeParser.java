package ru.whitewarrior.survivaltech.parser;


import net.minecraft.init.Blocks;
import ru.whitewarrior.survivaltech.api.common.config.Conf;
import ru.whitewarrior.survivaltech.api.common.item.ItemPattern;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipe;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipeConf;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeParser {

    public static List<SmallArcFurnaceRecipe> parseRecipeSmallArcFurnace(File dir){
        if(dir.mkdirs()){
            SmallArcFurnaceRecipeConf recipe = new SmallArcFurnaceRecipeConf(
                    Arrays.asList(
                            new ItemPattern(Blocks.STONE.getRegistryName().toString(), 0, 4),
                            new ItemPattern(Blocks.STONE.getRegistryName().toString(), 0, 4)
                    ),
                    Arrays.asList(
                            new ItemPattern(Blocks.FURNACE.getRegistryName().toString(), 0),
                            new ItemPattern(Blocks.COAL_BLOCK.getRegistryName().toString(), 0)
                    ), 1);
            Conf.serObj(recipe, new File(dir, "test.json"));
            return parseRecipeSmallArcFurnace(dir);
        }
        else {
            File[] list = dir.listFiles();
            List<SmallArcFurnaceRecipe> recipes = new ArrayList<>();
            if(list == null) return recipes;
            for(File child : list){
                SmallArcFurnaceRecipeConf recipe = Conf.deserObj(child, SmallArcFurnaceRecipeConf.class);
                recipes.add(recipe.recipe());
            }
            return recipes;
        }
    }
}
