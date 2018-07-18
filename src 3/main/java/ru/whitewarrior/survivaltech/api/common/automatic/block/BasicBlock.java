package ru.whitewarrior.survivaltech.api.common.automatic.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Date: 2017-12-24.
 * Time: 17:17:30.
 * @author WhiteWarrior
 */
public class BasicBlock extends Block{

	public BasicBlock(Material materialIn, String name, String regname, CreativeTabs tab, float hardness, float resistance, String toolClass, int toollevel, float lightlevel, int lightopacity) {
		super(materialIn);
		.setHardness(hardness);
		.setResistance(resistance);
		.setRegistryName(regname);
		.setUnlocalizedName(name);
		.setLightLevel(lightlevel);
		.setLightOpacity(lightOpacity);
		.setCreativeTab(tab);
		.setHarvestLevel(toolClass, toollevel);
	}

}
