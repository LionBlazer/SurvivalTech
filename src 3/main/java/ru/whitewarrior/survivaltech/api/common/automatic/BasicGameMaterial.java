package ru.whitewarrior.survivaltech.api.common.automatic;

import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.api.common.automatic.block.BasicTypeBlock;
import ru.whitewarrior.survivaltech.api.common.automatic.item.BasicTypeItem;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;

/**
 * Date: 2017-12-25.
 * Time: 12:46:52.
 * @author WhiteWarrior
 */
public class BasicGameMaterial {
private BasicTypeBlock ore;
private BasicTypeBlock fullblock;
private BasicTypeItem ingot;

	public BasicGameMaterial(String mateiralName, float hardness, int level) {
		ore = new BasicTypeBlock(BlockType.ORE, mateiralName, hardness, hardness * 0.8f, level, 0, 0);
		fullblock = new BasicTypeBlock(BlockType.FULL_BLOCK, mateiralName, hardness * 0.7f, hardness * 1.2f, level, 0, 0);
		ingot = new BasicTypeItem(mateiralName, ItemType.INGOT, 64, 0);
	}
	
	public BasicTypeBlock getOreBlock() {
		return ore;
	}
	
	public BasicTypeBlock getFullBlock() {
		return fullblock;
	}
	
	public BasicTypeItem getIngot() {
		return ingot;
	}
	
	public void register() {
		AdvancedRegistry.register(ore);
		AdvancedRegistry.register(ingot);
		AdvancedRegistry.register(fullblock);
	}
	
	public void registerRender() {
		AdvancedRegistry.registerRender(ore);
		AdvancedRegistry.registerRender(ingot);
		AdvancedRegistry.registerRender(fullblock);
	}
}
