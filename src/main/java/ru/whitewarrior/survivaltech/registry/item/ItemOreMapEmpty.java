package ru.whitewarrior.survivaltech.registry.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;

public class ItemOreMapEmpty extends ItemEmptyMap implements IAdvancedItem {
    public ItemOreMapEmpty(String name) {
        setRegistryName(name + "_" + getItemType().getPrefix());
        setUnlocalizedName(name + "_" + getItemType().getPrefix());
        this.setCreativeTab(getItemType().getCreativeTab());
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = ItemOreMap.setupNewMap(worldIn, playerIn.posX, playerIn.posZ, (byte) 2, true, true);
        ItemStack itemstack1 = playerIn.getHeldItem(handIn);
        itemstack1.shrink(1);

        if (itemstack1.isEmpty()) {
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        } else {
            if (!playerIn.inventory.addItemStackToInventory(itemstack.copy())) {
                playerIn.dropItem(itemstack, false);
            }

            playerIn.addStat(StatList.getObjectUseStats(this));
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack1);
        }
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MISCELLANEA;
    }
}
