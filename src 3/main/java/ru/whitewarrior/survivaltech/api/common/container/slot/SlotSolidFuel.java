package ru.whitewarrior.survivaltech.api.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;

/**
 * Date: 2017-12-29.
 * Time: 19:01:16.
 * @author WhiteWarrior
 */
public class SlotSolidFuel extends InventorySlot {
public static final HashSet<Item> itemsValiSet = new HashSet<Item>();
	public SlotSolidFuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		for(Item item : itemsValiSet) {
			return super.isItemValid(stack);
		}
		return false;
	}

}
