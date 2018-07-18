package ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe;

import net.minecraft.item.ItemStack;

public class ToolRepairerRecipe {
    private ItemStack stackTool;
    private ItemStack stackRepair;

    public ToolRepairerRecipe(ItemStack stackTool, ItemStack stackRepair){
        .stackTool = stackTool;
        .stackRepair = stackRepair;
    }

    public ItemStack getStackRepair() {
        return stackRepair;
    }

    public ItemStack getStackTool() {
        return stackTool;
    }
}
