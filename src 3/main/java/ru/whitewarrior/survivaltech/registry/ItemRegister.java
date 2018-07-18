package ru.whitewarrior.survivaltech.registry;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.api.common.automatic.item.BasicItemArmorModel;
import ru.whitewarrior.survivaltech.registry.item.ItemBattery;
import ru.whitewarrior.survivaltech.registry.item.ItemToolWrench;
import ru.whitewarrior.survivaltech.registry.item.armor.ItemArmorPower;
import ru.whitewarrior.survivaltech.registry.item.ItemChainsaw;
import ru.whitewarrior.survivaltech.util.NumberUtil;

/**
 * Date: 2017-12-28.
 * Time: 15:00:16.
 * @author WhiteWarrior
 */
public class ItemRegister {
	public static ItemBattery battery = new ItemBattery("smallbattery", NumberUtil.toK(10), 8);
	public static Item wrench = new ItemToolWrench("wrench");
	public static Item chainsaw = new ItemChainsaw("chainsaw");
	public static ItemArmorPower armorchest = new BasicItemArmorModel(MaterialRegister.ENERGY, EntityEquipmentSlot.CHEST, "energy"),
			 armorboots = new BasicItemArmorModel(MaterialRegister.ENERGY, EntityEquipmentSlot.FEET, "energy"),
					 armorlegs = new BasicItemArmorModel(MaterialRegister.ENERGY, EntityEquipmentSlot.LEGS, "energy"),
							 armorhelm = new BasicItemArmorModel(MaterialRegister.ENERGY, EntityEquipmentSlot.HEAD, "energy");
	
	
	public static void preInit() {
		AdvancedRegistry.register(battery);
		AdvancedRegistry.register(wrench);
		AdvancedRegistry.register(chainsaw);
		AdvancedRegistry.register(armorchest);
		AdvancedRegistry.register(armorboots);
		AdvancedRegistry.register(armorlegs);
		AdvancedRegistry.register(armorhelm);
	}

	public static void initClient() {
		AdvancedRegistry.registerRender(battery);
		AdvancedRegistry.registerRender(wrench);
		AdvancedRegistry.registerRender(chainsaw);
		AdvancedRegistry.registerRender(armorchest);
		AdvancedRegistry.registerRender(armorboots);
		AdvancedRegistry.registerRender(armorlegs);
		AdvancedRegistry.registerRender(armorhelm);
	}

}
