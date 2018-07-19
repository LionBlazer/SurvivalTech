package ru.whitewarrior.survivaltech.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NbtUtil {

    public static NBTTagCompound getTagCompound(ItemStack itemStack){
        if(!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());
        return itemStack.getTagCompound();
    }
}
