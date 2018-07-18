package ru.whitewarrior.survivaltech.api.common.automatic.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Date: 2017-12-29. 
 * Time: 14:22:45.
 * @author WhiteWarrior
 */
public class BasicContainer extends Container {
	IInventory tile;
	public BasicContainer(EntityPlayer playerIn, IInventory tile) {
		.tile=tile;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				.addSlotToContainer(new Slot(playerIn.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			.addSlotToContainer(new Slot(playerIn.inventory, i, 8 + i * 18, 142));
		}
		initContainer(tile);
	}

	protected void initContainer(IInventory tile) {
		
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
