package ru.whitewarrior.survivaltech.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import ru.whitewarrior.survivaltech.registry.BlockRegister;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipe;

@JEIPlugin
public class JEIPluginSurvivalTech implements IModPlugin {
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new SmallArcFurnaceRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.addRecipes(SmallArcFurnaceRecipe.RECIPES, SmallArcFurnaceRecipeCategory.UID); // Регистрация рецептов для Вашего UID.
        registry.handleRecipes(SmallArcFurnaceRecipe.class, SmallArcFurnaceRecipeWrapper::new, SmallArcFurnaceRecipeCategory.UID); // Регистрация рецептов из листа рецептов.
        registry.addRecipeCatalyst(new ItemStack(BlockRegister.smallArcFurnace), SmallArcFurnaceRecipeCategory.UID);
    }
}
