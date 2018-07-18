package ru.whitewarrior.survivaltech.api.client.automatic.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

/**
 * Date: 2017-12-23. Time: 23:30:52.
 *
 * @author WhiteWarrior
 */
public class BasicCreativeTab extends CreativeTabs {
    ItemStack stack;

    public BasicCreativeTab(String label, ItemStack stack, boolean img) {
        super(label);
        if (img)
            this.setBackgroundImageName(label + ".png");
        this.stack = stack;
    }

    @Override
    public ItemStack getTabIconItem() {
        return stack;
    }

}
