package ru.whitewarrior.survivaltech.api.common.automatic.block;

import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.IOreDictionaryObject;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.block.IAdvancedBlock;
import ru.whitewarrior.survivaltech.api.jsoncreator.PatternLoader;
import ru.whitewarrior.survivaltech.util.TextUtil;

/**
 * Date: 2017-12-25.
 * Time: 12:21:50.
 * @author WhiteWarrior
 */
public class BasicTypeBlock extends BasicBlock implements IAdvancedBlock, IOreDictionaryObject{

private BlockType type;
private String orename;
	public BasicTypeBlock(BlockType type, String name, float hardness, float resistance, int toollevel, float lightlevel, int lightopacity) {
		super(type.getMaterial(), name + "_" + type.getPrefix(), name + "_" + type.getPrefix(), type.getCreativeTab(), type.getHardness() + hardness,type.getResistance() + resistance, type.getTool(), toollevel, lightlevel, lightopacity);
		if(type.getOreDictionary() != null)
			orename = type.getOreDictionary() + TextUtil.firstUpperCase(name);
		.type=type;
		PatternLoader.createBlock(name + "_" + type.getPrefix(), Constants.MODID, "block/" + type.getPrefix() + "/" + name);
		PatternLoader.createBlockState(name + "_" + type.getPrefix(), Constants.MODID, name + "_" + type.getPrefix());
		PatternLoader.createItemBlock(name + "_" + type.getPrefix(), Constants.MODID, name + "_" + type.getPrefix());
	}

	@Override
	public BlockType getBlockType() {
		return type;
	}

	@Override
	public void registerOreDictionary() {
		if (orename != null)
			OreDictionary.registerOre(orename, );
	}

}
