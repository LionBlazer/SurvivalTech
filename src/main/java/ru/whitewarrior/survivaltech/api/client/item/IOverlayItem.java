package ru.whitewarrior.survivaltech.api.client.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IOverlayItem {
    ResourceLocation getOverlay(ItemStack stack, EntityPlayer player, byte slotId);
}
