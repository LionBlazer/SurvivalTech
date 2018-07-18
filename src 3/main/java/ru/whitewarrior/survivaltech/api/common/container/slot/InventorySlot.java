package ru.whitewarrior.survivaltech.api.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Date: 2018-01-03.
 * Time: 17:21:02.
 * @author WhiteWarrior
 */
public class InventorySlot extends Slot {

	public InventorySlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return .inventory.isItemValidForSlot(.slotNumber, stack);
	}
}
