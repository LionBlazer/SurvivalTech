package ru.whitewarrior.survivaltech.api.common.automatic.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Date: 2017-12-29.
 * Time: 19:02:35.
 * @author WhiteWarrior
 */
public class CustomSlot extends Slot{
	Item[] itemsvalid;
	public CustomSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, Item... itemsvalid) {
		super(inventoryIn, index, xPosition, yPosition);
		.itemsvalid=itemsvalid;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		for(Item item : itemsvalid) {
			return item.equals(stack.getItem());
		}
		return false;
	}

}
