package ru.whitewarrior.survivaltech.api.common.automatic.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Date: 2017-12-24.
 * Time: 17:17:30.
 *
 * @author WhiteWarrior
 */
public class BasicBlock extends Block {

    public BasicBlock(Material materialIn, String name, String regname, CreativeTabs tab, float hardness, float resistance, String toolClass, int toollevel, float lightlevel, int lightopacity) {
        super(materialIn);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(regname);
        this.setUnlocalizedName(name);
        this.setLightLevel(lightlevel);
        this.setLightOpacity(lightOpacity);
        this.setCreativeTab(tab);
        this.setHarvestLevel(toolClass, toollevel);
    }

}
