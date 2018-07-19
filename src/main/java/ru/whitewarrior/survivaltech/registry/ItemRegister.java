package ru.whitewarrior.survivaltech.registry;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.api.common.automatic.item.BasicItemArmorModel;
import ru.whitewarrior.survivaltech.registry.item.*;
import ru.whitewarrior.survivaltech.registry.item.armor.ItemArmorPower;
import ru.whitewarrior.survivaltech.util.NumberUtil;

/**
 * Date: 2017-12-28.
 * Time: 15:00:16.
 * @author WhiteWarrior
 */
public class ItemRegister {
	public static ItemBattery battery = new ItemBattery("smallbattery", NumberUtil.toK(10), 10);
	public static Item wrench = new ItemToolWrench("wrench");
	public static Item chainsaw = new ItemChainsaw("chainsaw");
	public static ItemArmorPower armorchest = new BasicItemArmorModel(ArmorMaterial.DIAMOND, EntityEquipmentSlot.CHEST, "energy"),
			 armorboots = new BasicItemArmorModel(ArmorMaterial.DIAMOND, EntityEquipmentSlot.FEET, "energy"),
					 armorlegs = new BasicItemArmorModel(ArmorMaterial.DIAMOND, EntityEquipmentSlot.LEGS, "energy"),
							 armorhelm = new BasicItemArmorModel(ArmorMaterial.DIAMOND, EntityEquipmentSlot.HEAD, "energy");
    public static ItemHammer ironHammer = new ItemHammer(Item.ToolMaterial.IRON, "iron_hammer", 200);
    public static ItemOreMap oreMap = new ItemOreMap("ore_map");
    public static ItemOreMapEmpty oreMapEmpty = new ItemOreMapEmpty("ore_map_empty");


	public static void preInit() {
		AdvancedRegistry.register(battery);
		AdvancedRegistry.register(wrench);
		AdvancedRegistry.register(chainsaw);
		AdvancedRegistry.register(armorchest);
		AdvancedRegistry.register(armorboots);
		AdvancedRegistry.register(armorlegs);
		AdvancedRegistry.register(armorhelm);
		AdvancedRegistry.register(ironHammer);
		AdvancedRegistry.register(oreMap);
		AdvancedRegistry.register(oreMapEmpty);
	}


	@SideOnly(Side.CLIENT)
    public static void preInitClient() {
        GameMaterialRegister.registerItemToolModel(ironHammer);
        GameMaterialRegister.registerBasicItemModel(oreMap);
        GameMaterialRegister.registerBasicItemModel(oreMapEmpty);
        ModelLoader.setCustomMeshDefinition(oreMap, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation("filled_map", "inventory");
            }
        });
    }

	public static void initClient() {
        AdvancedRegistry.registerRender(battery);
        AdvancedRegistry.registerRender(wrench);
        AdvancedRegistry.registerRender(chainsaw);
        AdvancedRegistry.registerRender(armorchest);
        AdvancedRegistry.registerRender(armorboots);
        AdvancedRegistry.registerRender(armorlegs);
        AdvancedRegistry.registerRender(armorhelm);
        AdvancedRegistry.registerRender(oreMapEmpty);
        AdvancedRegistry.registerRender(ironHammer);
        AdvancedRegistry.registerRender(oreMap);

    }
}
