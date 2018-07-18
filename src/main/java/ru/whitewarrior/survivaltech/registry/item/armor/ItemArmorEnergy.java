package ru.whitewarrior.survivaltech.registry.item.armor;

import net.minecraft.client.util.*;
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
        this.maxEnergy = maxEnergy;
        this.maxTransfer = maxTransfer;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        this.extractEnergy(itemStack, 0.1d, false);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new CapabilityProviderElectricEnergy<>(new ElectricEnergyConversionStorage(this, stack), CapabilityElectricEnergy.ENERGY);
    }

    @Override
    public double receiveEnergy(ItemStack stack, double maxReceive, boolean simulate) {
        double recEnergy = Math.min(this.getMaxEnergyStored(stack) - this.getEnergyStored(stack), Math.min(this.getMaxReceive(stack), maxReceive));
        if (!simulate)
            this.setEnergyStored(stack, this.getEnergyStored(stack) + recEnergy);
        return recEnergy;
    }

    @Override
    public double extractEnergy(ItemStack stack, double maxExtract, boolean simulate) {
        double extEnergy = Math.min(this.getEnergyStored(stack), Math.min(this.getMaxExtract(stack), maxExtract));
        if (!simulate) {
            this.setEnergyStored(stack, this.getEnergyStored(stack) - extEnergy);
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
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
        if (stack.getTagCompound().getInteger(NBTConstants.maxenergy) == 0) {
            this.setMaxEnergyStored(stack, maxEnergy);
        }
    }

    @Override
    public double getEnergyStored(ItemStack stack) {
        this.checkMax(stack);
        return stack.getTagCompound().getDouble(NBTConstants.energy);
    }

    @Override
    public double getMaxEnergyStored(ItemStack stack) {
        this.checkMax(stack);
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
        tooltip.add(EnergyHelper.getColorForEnergy(this.getEnergyStored(stack), this.getMaxEnergyStored(stack)).toString() + NumberUtil.rounding(this.getEnergyStored(stack)) + " SEU");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
