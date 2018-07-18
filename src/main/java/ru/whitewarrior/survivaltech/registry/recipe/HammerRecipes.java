package ru.whitewarrior.survivaltech.registry.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.survivaltech.registry.BlockRegister;

import java.util.ArrayList;
import java.util.List;

public class HammerRecipes extends ShapelessRecipes implements IRecipe {
    ItemStack stackInput;
    ItemStack stackOutput;

    public HammerRecipes(ItemStack stackInput, ItemStack stackOutput) {
        super("", new ItemStack(BlockRegister.copperCable), NonNullList.create());
        this.stackInput = stackInput;
        this.stackOutput = stackOutput;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean hasHammer = false;
        boolean hasItem = false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {

            ItemStack itemStack1 = inv.getStackInSlot(i);
            if (itemStack1.isEmpty())
                continue;

            if (!hasHammer) {
                int[] ores = OreDictionary.getOreIDs(itemStack1);

                if (ores.length > 0) {
                    for (int i1 = 0; i1 < ores.length; i1++) {
                        if (OreDictionary.getOreName(ores[i1]).equals("hammer")) {
                            hasHammer = true;
                            continue;
                        }
                    }
                    if (hasHammer)
                        continue;
                }
            }
            if (ItemStack.areItemsEqual(stackInput, itemStack1)) {
                if (hasItem)
                    return false;
                hasItem = true;
            } else
                return false;


        }
        return hasHammer && hasItem;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return stackOutput.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height > 1;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return stackOutput;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            nonnulllist.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonnulllist;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(Ingredient.fromStacks(stackInput));
        List<Ingredient> ingredients = new ArrayList<>();
        for (ItemStack itemStack : OreDictionary.getOres("hammer")) {
            ingredients.add(Ingredient.fromStacks(itemStack));
        }
        nonNullList.add(Ingredient.merge(ingredients));
        //  nonNullList.add(Ingredient.fromStacks((ItemStack[]) OreDictionary.getOres("hammer").toArray()));
        return nonNullList;
    }

    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public String getGroup() {
        return "";
    }

}
