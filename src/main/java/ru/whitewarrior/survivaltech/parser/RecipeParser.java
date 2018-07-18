package ru.whitewarrior.survivaltech.parser;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipe;
import ru.whitewarrior.survivaltech.util.FileUtil;
import ru.whitewarrior.survivaltech.util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecipeParser {

    public static List<SmallArcFurnaceRecipe> parseRecipeSmallArcFurnace(File file) {
        List<SmallArcFurnaceRecipe> recipes = new ArrayList<>();
        String fileText = FileUtil.getFileContent(file);
        int id = 0;
        while (fileText.contains("recipe[" + id + "]:")) {
            String body = fileText.substring(fileText.indexOf("{") + 1, fileText.indexOf("}"));
            String inputBody = body.substring(body.indexOf("input:") + "input:".length(), body.indexOf(";"));
            List input = new ArrayList();
            List<ItemStack> output = new ArrayList();
            while (inputBody.contains("[")) {
                String elementBody = inputBody.substring(inputBody.indexOf("[") + 1, inputBody.indexOf("]"));
                String typeName = elementBody.substring(0, elementBody.indexOf(":"));
                String oreName = null;
                Item item = null;
                int count = 0;
                int meta = 0;
                if (typeName.equals("ore")) {
                    oreName = elementBody.substring(elementBody.indexOf("\"") + 1, elementBody.indexOf(",") - 1);
                    count = Integer.parseInt(elementBody.substring(elementBody.indexOf("count") + "count:".length()));
                } else if (typeName.equals("item")) {
                    if (elementBody.contains(",")) {
                        String registryName = elementBody.substring(elementBody.indexOf("\"") + 1, elementBody.indexOf(",") - 1);
                        ResourceLocation registryLocation = new ResourceLocation(registryName);
                        if (registryName.contains(":")) {
                            registryLocation = new ResourceLocation(registryName.substring(0, registryName.indexOf(":")), registryName.substring(registryName.indexOf(":") + 1));
                        }
                        item = ForgeRegistries.ITEMS.getValue(registryLocation);
                        meta = Integer.parseInt(elementBody.substring(elementBody.indexOf("meta:") + "meta:".length(), StringUtil.indexOf(elementBody, ",", 2)));
                        count = Integer.parseInt(elementBody.substring(elementBody.indexOf("count") + "count:".length()));
                    } else {
                        item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(elementBody.substring(elementBody.indexOf("\""), elementBody.length() - 2)));
                    }
                }
                if (item != null) {
                    input.add(new ItemStack(item, count, meta));
                } else {
                    input.add(new Pair(oreName, count));
                }
                inputBody = inputBody.substring(inputBody.indexOf("]") + 1);
            }
            int startBodyOutput = body.indexOf("output:") + "output:".length();
            int endBodyOutput = StringUtil.indexOf(body, ";", 2);
            String outputBody = body.substring(startBodyOutput, endBodyOutput);
            while (outputBody.contains("[")) {
                String elementBody = outputBody.substring(outputBody.indexOf("[") + 1, outputBody.indexOf("]"));
                String typeName = elementBody.substring(0, elementBody.indexOf(":"));
                Item item = null;
                int count = 0;
                int meta = 0;
                if (typeName.equals("item")) {
                    if (elementBody.contains(",")) {
                        String registryName = elementBody.substring(elementBody.indexOf("\"") + 1, elementBody.indexOf(",") - 1);
                        ResourceLocation registryLocation = new ResourceLocation(registryName);
                        if (registryName.contains(":")) {
                            registryLocation = new ResourceLocation(registryName.substring(0, registryName.indexOf(":")), registryName.substring(registryName.indexOf(":") + 1));
                        }
                        item = ForgeRegistries.ITEMS.getValue(registryLocation);
                        meta = Integer.parseInt(elementBody.substring(elementBody.indexOf("meta:") + "meta:".length(), StringUtil.indexOf(elementBody, ",", 2)));
                        count = Integer.parseInt(elementBody.substring(elementBody.indexOf("count") + "count:".length()));
                    } else {
                        item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(elementBody.substring(elementBody.indexOf("\""), elementBody.length() - 2)));
                    }
                }
                if (item != null) {
                    output.add(new ItemStack(item, count, meta));
                }
                outputBody = outputBody.substring(outputBody.indexOf("]") + 1);
            }
            int time = Integer.parseInt(body.substring(body.indexOf("time:") + "time:".length(), StringUtil.indexOf(body, ";", 3)));
            recipes.add(new SmallArcFurnaceRecipe(input, output, time));
            fileText = fileText.substring(fileText.indexOf("}") + 1);
            id++;
        }


        return recipes;
    }
}
