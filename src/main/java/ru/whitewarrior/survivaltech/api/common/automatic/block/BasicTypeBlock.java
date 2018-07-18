package ru.whitewarrior.survivaltech.api.common.automatic.block;

import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.survivaltech.api.common.IOreDictionaryObject;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.block.IAdvancedBlock;
import ru.whitewarrior.survivaltech.util.TextUtil;

public class BasicTypeBlock extends BasicBlock implements IAdvancedBlock, IOreDictionaryObject {

    private BlockType type;
    private String oreName;

    public BasicTypeBlock(BlockType type, String name, float hardness, float resistance, int toolLevel, float lightLevel, int lightOpacity) {
        super(type.getMaterial(), name + "_" + type.getPrefix(), name + "_" + type.getPrefix(), type.getCreativeTab(), type.getHardness() + hardness, type.getResistance() + resistance, type.getTool(), toolLevel, lightLevel, lightOpacity);
        if (type.getOreDictionary() != null)
            oreName = type.getOreDictionary() + TextUtil.firstUpperCase(name.replaceAll("_", ""));
        this.type = type;
    }

    public BasicTypeBlock(BlockType type, String name, float hardness, float resistance, int toolLevel, float lightLevel, int lightOpacity, String oreDictionary) {
        super(type.getMaterial(), name + "_" + type.getPrefix(), name + "_" + type.getPrefix(), type.getCreativeTab(), type.getHardness() + hardness, type.getResistance() + resistance, type.getTool(), toolLevel, lightLevel, lightOpacity);
        if (type.getOreDictionary() != null)
            oreName = type.getOreDictionary() + TextUtil.firstUpperCase(oreDictionary);
        this.type = type;
    }

    @Override
    public BlockType getBlockType() {
        return type;
    }

    @Override
    public void registerOreDictionary() {
        if (oreName != null)
            OreDictionary.registerOre(oreName, this);
    }
}