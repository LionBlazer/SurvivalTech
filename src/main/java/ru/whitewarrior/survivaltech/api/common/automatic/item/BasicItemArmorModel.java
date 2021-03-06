package ru.whitewarrior.survivaltech.api.common.automatic.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.jsoncreator.PatternLoader;
import ru.whitewarrior.survivaltech.registry.item.armor.ItemArmorPower;
import ru.whitewarrior.survivaltech.util.NumberUtil;

/**
 * Date: 2018-01-09.
 * Time: 10:37:06.
 * @author WhiteWarrior
 */
public class BasicItemArmorModel extends ItemArmorPower {

	public BasicItemArmorModel(ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn, String name) {
		super(materialIn, equipmentSlotIn.getIndex(), equipmentSlotIn, Constants.TEXTURE+"textures/energy_armor_model.png",NumberUtil.toK(80),16);
        this.setRegistryName(name + "_" + this.getItemType().getPrefix() + "_" + equipmentSlotIn.getName());
        this.setUnlocalizedName(name + "_" + this.getItemType().getPrefix() + "_" + equipmentSlotIn.getName());
        this.setCreativeTab(this.getItemType().getCreativeTab());
		PatternLoader.createItem(name + "_" + this.getItemType().getPrefix()+ "_" + equipmentSlotIn.getName(), "st", this.getItemType().getPrefix()+"/"+name);
	}

}
