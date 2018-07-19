package ru.whitewarrior.survivaltech.api.common.automatic;

import net.minecraft.item.Item;
import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.api.common.automatic.block.BasicTypeBlock;
import ru.whitewarrior.survivaltech.api.common.automatic.block.BasicTypeBlockCutout;
import ru.whitewarrior.survivaltech.api.common.automatic.item.BasicTypeItem;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.registry.item.ItemHammer;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2017-12-25.
 * Time: 12:46:52.
 * @author WhiteWarrior
 */
public class BasicGameMaterial {
    private List<BasicTypeBlockCutout> ores = new ArrayList<>();
    private List<Item> itemsMaterial = new ArrayList<>();
    private BasicTypeBlock fullBlock;
    private BasicTypeItem ingot;
    private BasicTypeItem nugget;
    private BasicTypeItem crushedOre;
    private Item.ToolMaterial toolMaterial;

    public BasicGameMaterial(String materialName, float hardness, int level) {
        this(materialName, hardness, level, true, true);
    }

    public BasicGameMaterial(String materialName, float hardness, int level, boolean isIngot, boolean isFullblock) {
        if (isFullblock)
            fullBlock = new BasicTypeBlock(BlockType.FULL_BLOCK, materialName, hardness * 0.7f, hardness * 1.2f, level, 0, 0);
        if (isIngot)
            ingot = new BasicTypeItem(materialName, ItemType.INGOT, 64, 0);
    }

    public List<BasicTypeBlockCutout> getOresBlock() {
        return ores;
    }

    public BasicTypeBlock getFullBlock() {
        return fullBlock;
    }

    public BasicTypeItem getIngot() {
        return ingot;
    }

    public BasicTypeItem getNugget() {
        return nugget;
    }

    public void setCrushedOre(BasicTypeItem crushedOre) {
        this.crushedOre = crushedOre;
    }

    public BasicTypeItem getCrushedOre() {
        return crushedOre;
    }

    public void addHammer(ItemHammer hammer){
        itemsMaterial.add(hammer);
    }

    public void addChunk(Item chunk){
        itemsMaterial.add(chunk);
    }

    public List<Item> getItemsMaterial() {
        return itemsMaterial;
    }

    public void setNugget(BasicTypeItem nugget) {
        this.nugget = nugget;
    }

    public Item.ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public void setToolMaterial(Item.ToolMaterial toolMaterial) {
        this.toolMaterial = toolMaterial;
    }

    public void register() {
        for (int i = 0; i < ores.size(); i++) {
            AdvancedRegistry.register(ores.get(i));
        }
        for (int i = 0; i < itemsMaterial.size(); i++) {
            AdvancedRegistry.register(itemsMaterial.get(i));
        }
        if (ingot != null)
            AdvancedRegistry.register(ingot);
        if (fullBlock != null)
            AdvancedRegistry.register(fullBlock);
        if (nugget != null)
            AdvancedRegistry.register(nugget);
        if(crushedOre != null)
            AdvancedRegistry.register(crushedOre);
    }

    public void registerRender() {
        for (int i = 0; i < ores.size(); i++) {
            AdvancedRegistry.registerRender(ores.get(i));
        }
        for (int i = 0; i < itemsMaterial.size(); i++) {
            AdvancedRegistry.registerRender(itemsMaterial.get(i));
        }
        if (ingot != null)
            AdvancedRegistry.registerRender(ingot);
        if (fullBlock != null)
            AdvancedRegistry.registerRender(fullBlock);
        if (nugget != null)
            AdvancedRegistry.registerRender(nugget);
        if(crushedOre != null)
            AdvancedRegistry.registerRender(crushedOre);
    }
}
