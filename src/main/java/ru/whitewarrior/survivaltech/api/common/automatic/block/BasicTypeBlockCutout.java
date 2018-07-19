package ru.whitewarrior.survivaltech.api.common.automatic.block;

import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.survivaltech.api.common.IOreDictionaryObject;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.block.IAdvancedBlock;
import ru.whitewarrior.survivaltech.util.TextUtil;

/**
 * Date: 2017-12-25.
 * Time: 12:21:50.
 * @author WhiteWarrior
 */
public class BasicTypeBlockCutout extends BasicBlock implements IAdvancedBlock, IOreDictionaryObject{

private BlockType type;
private String orename;
	public BasicTypeBlockCutout(BlockType type, String name, float hardness, float resistance, int toolLevel, float lightLevel, int lightOpacity) {
		super(type.getMaterial(), name + "_" + type.getPrefix(), name + "_" + type.getPrefix(), type.getCreativeTab(), type.getHardness() + hardness,type.getResistance() + resistance, type.getTool(), toolLevel, lightLevel, lightOpacity);
		if(type.getOreDictionary() != null)
			orename = type.getOreDictionary() + TextUtil.firstUpperCase(name.replaceAll("_", ""));
        this.type=type;
	}

    public BasicTypeBlockCutout(BlockType type, String name, float hardness, float resistance, int toolLevel, float lightLevel, int lightOpacity, String oreDictionary) {
        super(type.getMaterial(), name + "_" + type.getPrefix(), name + "_" + type.getPrefix(), type.getCreativeTab(), type.getHardness() + hardness,type.getResistance() + resistance, type.getTool(), toolLevel, lightLevel, lightOpacity);
        if(type.getOreDictionary() != null && oreDictionary != null)
            orename = type.getOreDictionary() + TextUtil.firstUpperCase(oreDictionary);
        this.type=type;
    }

	@Override
	public BlockType getBlockType() {
		return type;
	}

	@Override
	public void registerOreDictionary() {
		if (orename != null)
			OreDictionary.registerOre(orename, this);
	}

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
