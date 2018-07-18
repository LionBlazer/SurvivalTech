package ru.whitewarrior.survivaltech.registry.multiblock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import ru.whitewarrior.survivaltech.api.common.block.multiblock.MultiBlock;

/**
 * Date: 2018-01-13. Time: 21:12:45.
 * 
 * @author WhiteWarrior
 */
public class MultiBlockCrusher extends MultiBlock {
	public MultiBlockCrusher() {
		super();
		.uniqueblocks.add(Blocks.IRON_BLOCK);
		Block[][] layer1 = new Block[][] { 
			{Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK },
			{ Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK },
			{ Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK }, 
		};
		
		Block[][] layer2 = new Block[][] { 
			{Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK },
			{ Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK },
			{ Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK }, 
		};
		
		Block[][] layer3 = new Block[][] { 
			{Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK },
			{ Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK },
			{ Blocks.IRON_BLOCK, Blocks.IRON_BLOCK, Blocks.IRON_BLOCK }, 
		};
		.layerList.add(layer1);
		.layerList.add(layer2);
		.layerList.add(layer3);
	}
}
