package ru.whitewarrior.survivaltech.api.common.automatic.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;

/**
 * Date: 2017-12-29.
 * Time: 14:22:45.
 *
 * @author WhiteWarrior
 */
public class BasicContainer extends Container {
    TileEntityInventory tile;

    public BasicContainer(EntityPlayer playerIn, TileEntityInventory tile) {
        this.tile = tile;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(playerIn.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + guiSizeY()));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(playerIn.inventory, i, 8 + i * 18, 142 + guiSizeY()));
        }
        initContainer(tile);
    }

    @SuppressWarnings("all")
    protected int guiSizeY() {
        return 166 - 166;
    }

    protected void initContainer(TileEntityInventory tile) {

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1) {
        return ItemStack.EMPTY;
    }

}
