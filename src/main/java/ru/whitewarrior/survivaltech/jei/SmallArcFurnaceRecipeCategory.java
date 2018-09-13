package ru.whitewarrior.survivaltech.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.util.LocalizationUtil;

import java.util.ArrayList;
import java.util.List;

public class SmallArcFurnaceRecipeCategory implements IRecipeCategory<SmallArcFurnaceRecipeWrapper> {
    public static final String UID = Constants.MODID + ":smallarcfurnace"; // Сам UID рецепт.
    private final IDrawableStatic bg;
    IGuiHelper h;
    SmallArcFurnaceRecipeWrapper recipes;
    public SmallArcFurnaceRecipeCategory(IGuiHelper h) {
        this.h = h;
        bg = h.createDrawable(new ResourceLocation(Constants.MODID, "textures/gui/inventory/jei/arc_furnace.png"), 10, 10, 160, 104);  // Объявление background'а.
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return LocalizationUtil.readFromLang("TileEntitySmallArcFurnace");
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        minecraft.fontRenderer.drawStringWithShadow(LocalizationUtil.readFromLang("info.time")+": " + (recipes.getTime()/20f)+" " + LocalizationUtil.readFromLang("info.sec"), 0, 60, 0xffffffff);
    }

    @Override
    public String getModName() {
        return Constants.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return bg;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, SmallArcFurnaceRecipeWrapper recipes, IIngredients ingredients) {
        this.recipes = recipes;
        IGuiItemStackGroup isg = layout.getItemStacks();
        List<ItemStack> input0 = new ArrayList<>();
        List<ItemStack> input1 = new ArrayList<>();
        List<ItemStack> input2 = new ArrayList<>();
        List<ItemStack> input3 = new ArrayList<>();
        int i = 0;
        for (Object object : recipes.getInput()) {
            List<ItemStack> current = i == 0 ? input0 : (i == 1 ? input1 : (i == 2 ? input2 : input3));
            if (object instanceof ItemStack) {
                current.add((ItemStack) object);
            } else if (object instanceof Pair) {
                Pair<String, Integer> pair = (Pair<String, Integer>) object;
                for (ItemStack stackOre : OreDictionary.getOres(pair.getKey())) {
                    ItemStack newStack = stackOre.copy();
                    newStack.setCount(pair.getValue());
                    current.add(newStack);
                }
            }
            i++;
        }

        isg.init(0, true, 8, 8);

        isg.set(0, input0);

        isg.init(1, true, 8, 28);

        isg.set(1, input1);

        isg.init(2, true, 28, 8);
        isg.set(2, input2);
        isg.init(3, true, 28, 28);

        isg.set(3, input3);


        isg.init(4, false, 88, 8);
        if (recipes.getOutput().size() > 0)
            isg.set(4, recipes.getOutput().get(0));

        isg.init(5, false, 88, 28);
        if (recipes.getOutput().size() > 1)
            isg.set(5, recipes.getOutput().get(1));

        isg.init(6, false, 108, 8);
        if (recipes.getOutput().size() > 2)
            isg.set(6, recipes.getOutput().get(2));

        isg.init(7, false, 108, 28);
        if (recipes.getOutput().size() > 3)
            isg.set(7, recipes.getOutput().get(3));
    }
}
