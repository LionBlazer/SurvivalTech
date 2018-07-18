package ru.whitewarrior.survivaltech.registry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Date: 2017-12-23. Time: 23:27:14.
 * 
 * @author WhiteWarrior
 */
public class CreativeTabRegister {
	public static CreativeTabs MECHANISM = new CreativeTabs("mechanism") {

		@Override
		public ItemStack getTabIconItem() {
			// TODO Auto-generated method stub
			return new ItemStack(BlockRegister.copperCable);
		}
	}.setBackgroundImageName("mechanism.png");
	public static CreativeTabs ORE = new CreativeTabs("ore") {

		@Override
		public ItemStack getTabIconItem() {
			// TODO Auto-generated method stub
			return new ItemStack(GameMaterialRegister.copper.getOreBlock());
		}
	};
	public static CreativeTabs INGOT = new CreativeTabs("ingot") {

		@Override
		public ItemStack getTabIconItem() {
			// TODO Auto-generated method stub
			return new ItemStack(GameMaterialRegister.copper.getIngot());
		}
	};
	public static CreativeTabs TOOL = new CreativeTabs("tool") {

		@Override
		public ItemStack getTabIconItem() {
			// TODO Auto-generated method stub
			return new ItemStack(ItemRegister.chainsaw);
		}
	};
	public static CreativeTabs ARMOR = new CreativeTabs("armor") {

		@Override
		public ItemStack getTabIconItem() {
			// TODO Auto-generated method stub
			return new ItemStack(Items.DIAMOND_CHESTPLATE);
		}
	};
	public static CreativeTabs UTILITIES = new CreativeTabs("utilities") {

		@Override
		public ItemStack getTabIconItem() {
			// TODO Auto-generated method stub
			return new ItemStack(BlockRegister.fluidtank);
		}
	};

	public static void preInit() {
	}
}
