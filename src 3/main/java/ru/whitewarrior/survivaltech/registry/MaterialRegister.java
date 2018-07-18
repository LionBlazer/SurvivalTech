package ru.whitewarrior.survivaltech.registry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import ru.whitewarrior.survivaltech.api.common.container.slot.SlotSolidFuel;
import ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe.ToolRepairerRecipeManager;

import java.util.HashMap;

/**
 * Date: 2017-12-29. Time: 19:18:16.
 * 
 * @author WhiteWarrior
 */
public class MaterialRegister {
	public static HashMap<Item, Integer> fuel = new HashMap<Item, Integer>();
	public static ItemArmor.ArmorMaterial ENERGY = EnumHelper.addArmorMaterial("energy", "st:textures/gui/overlay/glass.png", 1000, new int[]{3, 6, 8, 3}, 0, SoundType.GLASS.getPlaceSound(), 0);
	public static final Material MECHANISM = new Material(MapColor.IRON);
	public static final ToolMaterial WRENCH = EnumHelper.addToolMaterial("wrench", 0, 100, 1, 0, 0);
	public static final ToolMaterial ELECTRIC = EnumHelper.addToolMaterial("electric", 2, 100, 1, 0, 0);
	public static void preInit() {
		fuel.put(Items.COAL, 100);
		fuel.put(Item.getItemFromBlock(Blocks.COAL_BLOCK), fuel.get(Items.COAL)*9);
		SlotSolidFuel.itemsValiSet.add(Items.COAL);
		SlotSolidFuel.itemsValiSet.add(Item.getItemFromBlock(Blocks.COAL_BLOCK));
		SlotSolidFuel.itemsValiSet.add(Item.getItemFromBlock(Blocks.PLANKS));
		SlotSolidFuel.itemsValiSet.add(Item.getItemFromBlock(Blocks.LOG));
		SlotSolidFuel.itemsValiSet.add(Item.getItemFromBlock(Blocks.LOG2));
		
	}
	
	

}
