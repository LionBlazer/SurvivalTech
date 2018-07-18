package ru.whitewarrior.survivaltech.registry.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemTool;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.api.jsoncreator.PatternLoader;
import ru.whitewarrior.survivaltech.registry.MaterialRegister;

import java.util.HashSet;

/**
 * Date: 2018-02-06. Time: 21:32:46.
 * 
 * @author WhiteWarrior
 */
public class ItemChainsaw extends ItemTool implements IAdvancedItem{
	
	public ItemChainsaw(String name) {
		super(6, 1.4f, MaterialRegister.ELECTRIC, new HashSet<Block>());
		.setRegistryName(name + "_" + .getItemType().getPrefix());
		.setUnlocalizedName(name + "_" + .getItemType().getPrefix());
		.setCreativeTab(.getItemType().getCreativeTab());
		PatternLoader.createItem(name + "_" + .getItemType().getPrefix(),Constants.MODID, .getItemType().getPrefix()+"/"+name);
	}

	@Override
	public ItemType getItemType() {
		// TODO Auto-generated method stub
		return ItemType.TOOL;
	}

}
