package ru.whitewarrior.survivaltech.api.common.automatic.item;

import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.survivaltech.api.common.IOreDictionaryObject;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.util.TextUtil;

/**
 * Date: 2017-12-27.
 * Time: 18:13:46.
 * @author WhiteWarrior
 */
public class BasicTypeItem extends BasicItem implements IAdvancedItem, IOreDictionaryObject{
private ItemType type;
private String oreName;
private String originalName;
	public BasicTypeItem(String name, ItemType type, int maxStackSize, int maxDamageIn) {
		super(name+"_"+type.getPrefix(), name+"_"+type.getPrefix(), type.getCreativeTab(), maxStackSize, maxDamageIn);
		if(type.getOreDictionary() != null)
			oreName = type.getOreDictionary() + TextUtil.firstUpperCase(name);
		this.originalName = name;
		this.type=type;
	}
    public BasicTypeItem(String name, ItemType type, int maxStackSize, int maxDamageIn, String oreName) {
        super(name+"_"+type.getPrefix(), name+"_"+type.getPrefix(), type.getCreativeTab(), maxStackSize, maxDamageIn);
        this.oreName = type.getOreDictionary() + TextUtil.firstUpperCase(oreName);
        this.originalName = name;
        this.type=type;
    }

    public String getOriginalName() {
        return originalName;
    }

    @Override
	public ItemType getItemType() {
		return type;
	}

	@Override
	public void registerOreDictionary() {
		if (oreName != null)
			OreDictionary.registerOre(oreName, this);
	}

}
