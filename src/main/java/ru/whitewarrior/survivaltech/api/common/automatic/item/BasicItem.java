package ru.whitewarrior.survivaltech.api.common.automatic.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Date: 2017-12-27. 
 * Time: 17:53:09.
 * @author WhiteWarrior
 */
public class BasicItem extends Item {
	public BasicItem(String name, String unlocalizedName, CreativeTabs tab, int maxStackSize, int maxDamageIn) {
        this.setCreativeTab(tab);
        this.setMaxStackSize(maxStackSize);
        this.setRegistryName(name);
        this.setUnlocalizedName(unlocalizedName);
        this.setMaxDamage(maxDamageIn);
	}
}
