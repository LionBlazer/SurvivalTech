package ru.whitewarrior.survivaltech.registry.item;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.survivaltech.api.common.IOreDictionaryObject;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;

public class ItemHammer extends ItemPickaxe implements IAdvancedItem, IOreDictionaryObject {
    public ItemHammer(ToolMaterial material, String name, int durability) {
        super(material);
        setContainerItem(this);
        setMaxDamage(durability);
        setNoRepair();
        setMaxStackSize(1);
        setRegistryName(name + "_" + getItemType().getPrefix());
        setUnlocalizedName(name + "_" + getItemType().getPrefix());
        this.setCreativeTab(getItemType().getCreativeTab());
    }

    @Override
    public ItemStack getContainerItem(ItemStack is) {
        ItemStack stack = super.getContainerItem(is);
        if (stack.getItemDamage() >= 0) {
            stack.setItemDamage(is.getItemDamage() + 4);
            return stack;
        }
        return super.getContainerItem(is);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TOOL;
    }

    @Override
    public void registerOreDictionary() {
        OreDictionary.registerOre("hammer", new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE));
    }
}

