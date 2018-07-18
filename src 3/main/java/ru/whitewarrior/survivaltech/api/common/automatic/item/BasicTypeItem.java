package ru.whitewarrior.survivaltech.api.common.automatic.item;

import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.IOreDictionaryObject;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.api.jsoncreator.PatternLoader;
import ru.whitewarrior.survivaltech.util.TextUtil;

/**
 * Date: 2017-12-27.
 * Time: 18:13:46.
 * @author WhiteWarrior
 */
public class BasicTypeItem extends BasicItem implements IAdvancedItem, IOreDictionaryObject{
private ItemType type;
private String orename;
	public BasicTypeItem(String name, ItemType type, int maxStackSize, int maxDamageIn) {
		super(name+"_"+type.getPrefix(), name+"_"+type.getPrefix(), type.getCreativeTab(), maxStackSize, maxDamageIn);
		if(type.getOreDictionary() != null)
			orename = type.getOreDictionary() + TextUtil.firstUpperCase(name);
		PatternLoader.createItem(name+"_"+type.getPrefix(), Constants.MODID, "item/" + type.getPrefix() + "/" + name);
		.type=type;
	}

	@Override
	public ItemType getItemType() {
		return type;
	}

	@Override
	public void registerOreDictionary() {
		if (orename != null)
			OreDictionary.registerOre(orename, );
	}

}
