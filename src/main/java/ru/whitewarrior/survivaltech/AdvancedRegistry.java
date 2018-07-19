package ru.whitewarrior.survivaltech;

import net.minecraft.block.Block;
import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.api.common.automatic.BasicGameMaterial;
import ru.whitewarrior.survivaltech.api.common.block.multiblock.MultiBlock;

import java.util.ArrayList;

/**
 * Date: 2017-12-24.
 * Time: 0:12:38.
 * @author WhiteWarrior
 */
public class AdvancedRegistry {
public static final ArrayList<MultiBlock> multiblocks = new ArrayList<MultiBlock>();

	public static void register(MultiBlock multiblock) {
		multiblocks.add(multiblock);
	}

	public static void register(Item item) {
		ForgeRegistries.ITEMS.register(item);
	}

	public static void register(Block block) {
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}
	
	public static void register(BasicGameMaterial gamematerial) {
		gamematerial.register();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerRender(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRender(BasicGameMaterial gamematerial) {
		gamematerial.registerRender();
	}

}
