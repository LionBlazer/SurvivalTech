package ru.whitewarrior.survivaltech.api.common.item;

import net.minecraft.creativetab.CreativeTabs;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;

/**
 * Date: 2017-12-27.
 * Time: 18:14:54.
 *
 * @author WhiteWarrior
 */
public enum ItemType {
    INGOT("ingot", CreativeTabRegister.INGOT, "ingot"), NUGGET("nugget", CreativeTabRegister.INGOT, "nugget"), CHUNK("chunk", CreativeTabRegister.INGOT, "chunk"), MISCELLANEA("miscellanea", CreativeTabs.MISC, null), SMALL_CHUNK("small_chunk", CreativeTabRegister.INGOT, "smallChunk"), TOOL("tool", CreativeTabRegister.TOOL, null), ARMOR("armor", CreativeTabRegister.ARMOR, null);

    private String prefix;
    private String oreDictionary;
    private CreativeTabs tab;

    ItemType(String prefix, CreativeTabs tab, String oreDictionary) {
        this.prefix = prefix;
        this.tab = tab;
        this.oreDictionary = oreDictionary;
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
