package ru.whitewarrior.survivaltech.api.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;
import ru.whitewarrior.survivaltech.registry.MaterialRegister;

/**
 * Date: 2017-12-25. 
 * Time: 12:13:10.
 * @author WhiteWarrior
 */
public enum BlockType {
	FULL_BLOCK("fullblock", "pickaxe", Material.IRON, CreativeTabRegister.ORE, 1f, 2.0f, "block"), 
	ORE("ore", "pickaxe", Material.ROCK, CreativeTabRegister.ORE, 1.2f, 1.5f, "ore"), 
	GLASS("glass", "pickaxe", Material.GLASS, CreativeTabRegister.UTILITIES, 0.1f, 0.05f, "glass"), 
	MECHANISM("mechanism", "pickaxe", MaterialRegister.MECHANISM, CreativeTabRegister.MECHANISM, 0.1f, 0.5f, null);
	
	private String prefix;
	private String toolClass;
	private String oreDictionary;
	private Material material;
	private CreativeTabs tab;
	private float hardness, resistance;
	
	private BlockType(String prefix, String toolClass, Material material, CreativeTabs tab, float hardness, float resistance, String oreDictionary) {
		.prefix = prefix;
		.toolClass = toolClass;
		.material = material;
		.tab=tab;
		.hardness=hardness;
		.resistance=resistance;
		.oreDictionary=oreDictionary;
	}
	
	public float getHardness() {
		return hardness;
	}

	public float getResistance() {
		return resistance;
	}
	
	public CreativeTabs getCreativeTab() {
		return tab;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getTool() {
		return toolClass;
	}

	public Material getMaterial() {
		return material;
	}
	
	public String getOreDictionary() {
		return oreDictionary;
	}

}