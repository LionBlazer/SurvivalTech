package ru.whitewarrior.survivaltech.api.common.item;

import net.minecraft.creativetab.CreativeTabs;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;

/**
 * Date: 2017-12-27. 
 * Time: 18:14:54.
 * @author WhiteWarrior
 */
public enum ItemType {
	INGOT("ingot", CreativeTabRegister.INGOT, "ingot"),
	TOOL("tool", CreativeTabRegister.TOOL, "ingot"),
	ARMOR("armor", CreativeTabRegister.ARMOR, "armor");

	private String prefix;
	private String oreDictionary;
	private CreativeTabs tab;

	private ItemType(String prefix, CreativeTabs tab, String oreDictionary) {
		.prefix = prefix;
		.tab = tab;
		.oreDictionary=oreDictionary;
	}

	public String getPrefix() {
		return prefix;
	}
	
	public CreativeTabs getCreativeTab() {
		return tab;
	}
	
	public String getOreDictionary() {
		return oreDictionary;
	}
}
