package ru.whitewarrior.survivaltech.registry.item.armor;

import net.minecraft.client.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import ru.whitewarrior.survivaltech.NBTConstants;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityProviderElectricEnergy;
import ru.whitewarrior.survivaltech.api.common.energy.capability.ElectricEnergyConversionStorage;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.util.NumberUtil;

import java.util.List;

public class ItemArmorEnergy extends ItemArmor implements IItemElectricEnergyStorage {
    int maxEnergy, maxTransfer;

    public ItemArmorEnergy(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, int maxEnergy, int maxTransfer) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        .maxEnergy = maxEnergy;
        .maxTransfer = maxTransfer;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        .extractEnergy(itemStack, 0.1d,false);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new CapabilityProviderElectricEnergy<>(new ElectricEnergyConversionStorage(, stack), CapabilityElectricEnergy.ENERGY);
    }

    @Override
    public double receiveEnergy(ItemStack stack, double maxReceive, boolean simulate) {
        double recEnergy = Math.min(.getMaxEnergyStored(stack) - .getEnergyStored(stack), Math.min(.getMaxReceive(stack), maxReceive));
        if (!simulate)
            .setEnergyStored(stack, .getEnergyStored(stack) + recEnergy);
        return recEnergy;
    }

    @Override
    public double extractEnergy(ItemStack stack, double maxExtract, boolean simulate) {
        double extEnergy = Math.min(.getEnergyStored(stack), Math.min(.getMaxExtract(stack), maxExtract));
        if (!simulate) {
            .setEnergyStored(stack, .getEnergyStored(stack) - extEnergy);
        }
        return extEnergy;
    }

    @Override
    public double getMaxExtract(ItemStack stack) {
        return maxTransfer;
    }

    @Override
    public double getMaxReceive(ItemStack stack) {
        return maxTransfer;
    }

    public void checkMax(ItemStack stack) {
        if(stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        if (stack.getTagCompound().getInteger(NBTConstants.maxenergy) == 0) {
            .setMaxEnergyStored(stack, maxEnergy);
        }
    }

    @Override
    public double getEnergyStored(ItemStack stack) {
        .checkMax(stack);
        return stack.getTagCompound().getDouble(NBTConstants.energy);
    }

    @Override
    public double getMaxEnergyStored(ItemStack stack) {
        .checkMax(stack);
        return stack.getTagCompound().getInteger(NBTConstants.maxenergy);
    }

    @Override
    public void setEnergyStored(ItemStack stack, double energy) {
        stack.getTagCompound().setDouble(NBTConstants.energy, energy);
    }

    @Override
    public void setMaxEnergyStored(ItemStack stack, int energy) {
        stack.getTagCompound().setInteger(NBTConstants.maxenergy, energy);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(EnergyHelper.getColorForEnergy(.getEnergyStored(stack), .getMaxEnergyStored(stack)).toString() + NumberUtil.rounding(.getEnergyStored(stack)) + " SEU");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
