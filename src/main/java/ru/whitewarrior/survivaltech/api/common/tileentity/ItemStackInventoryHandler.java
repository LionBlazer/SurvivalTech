package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class ItemStackInventoryHandler extends ItemStackHandler {

    public ItemStackInventoryHandler() {
    }

    public ItemStackInventoryHandler(int size) {
        super(size);
    }

    public ItemStackInventoryHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    public NonNullList<ItemStack> getStacks(){
        return stacks;
    }
}
