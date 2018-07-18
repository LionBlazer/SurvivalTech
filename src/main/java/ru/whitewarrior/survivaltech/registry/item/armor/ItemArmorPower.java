package ru.whitewarrior.survivaltech.registry.item.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.client.item.IOverlayItem;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.registry.item.armor.render.RenderIridescentArmorModel;
import ru.whitewarrior.survivaltech.util.LocalizationUtil;

import java.util.List;

/**
 * Date: 2018-01-09.
 * Time: 9:54:55.
 *
 * @author WhiteWarrior
 */
public class ItemArmorPower extends ItemArmorEnergy implements IAdvancedItem, IOverlayItem {
    private String texture;
    private static final ResourceLocation loc = new ResourceLocation(Constants.MODID, "textures/gui/overlay/glass.png");

    public ItemArmorPower(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String texture, int maxEnergy, int maxTransfer) {
        super(materialIn, renderIndexIn, equipmentSlotIn, maxEnergy, maxTransfer);
        this.texture = texture;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
        ModelBiped armorModel = new RenderIridescentArmorModel(armorSlot.getIndex(), itemStack);
        return armorModel;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound compound = stack.getTagCompound();
        if (stack.hasTagCompound()) {
            tooltip.add(LocalizationUtil.readFromLang("red") + ": " + compound.getByte("red"));
            tooltip.add(LocalizationUtil.readFromLang("green") + ": " + compound.getByte("green"));
            tooltip.add(LocalizationUtil.readFromLang("blue") + ": " + compound.getByte("blue"));
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return texture;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.ARMOR;
    }


    @Override
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    @Override
    public ResourceLocation getOverlay(ItemStack stack, EntityPlayer player, byte slotId) {
        if (slotId == 3 && this.getEnergyStored(stack) > 0)
            return loc;
        return null;
    }
}
