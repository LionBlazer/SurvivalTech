package ru.whitewarrior.survivaltech.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import ru.whitewarrior.survivaltech.api.common.IOreDictionaryObject;

/**
 * Date: 2017-12-29.
 * Time: 12:32:20.
 * @author WhiteWarrior
 */
public class OreDictionaryRegister {

	public static void init() {
		for(Block block : Block.REGISTRY) {
			if(block instanceof IOreDictionaryObject) {
				((IOreDictionaryObject) block).registerOreDictionary();
			}
		}
		for(Item item : Item.REGISTRY) {
			if(item instanceof IOreDictionaryObject) {
				((IOreDictionaryObject) item).registerOreDictionary();
			}
		}
	}

}
