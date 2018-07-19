package ru.whitewarrior.survivaltech.registry.item.armor;

import net.minecraft.client.model.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.registry.item.armor.render.RenderIridescentArmorModel;

/**
 * Date: 2018-01-09.
 * Time: 9:54:55.
 * @author WhiteWarrior
 */
public class ArmorCustomModel extends ItemArmor implements IAdvancedItem{

	public ArmorCustomModel(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
		ModelBiped armorModel = new RenderIridescentArmorModel(armorSlot.getIndex(), itemStack);
	    return armorModel;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "st:textures/energy_armor_model.png";
	}
	
	@Override
	public ItemType getItemType() {
		return ItemType.ARMOR;
	}
}
