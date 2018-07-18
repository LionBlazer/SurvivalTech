package ru.whitewarrior.survivaltech.api.common.container.slot;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.HashSet;

/**
 * Date: 2017-12-29.
 * Time: 19:01:16.
 *
 * @author WhiteWarrior
 */
public class SlotSolidFuel extends SlotItemHandler {
    public static final HashSet<Item> itemsValidSet = new HashSet<Item>();

    public SlotSolidFuel(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        for (Item item : itemsValidSet) {
            if (stack.getItem() == item)
                return super.isItemValid(stack);
        }
        return false;
    }

}
