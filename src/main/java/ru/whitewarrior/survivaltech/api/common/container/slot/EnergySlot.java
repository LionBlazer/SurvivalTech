package ru.whitewarrior.survivaltech.api.common.container.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;

/**
 * Date: 2017-12-30.
 * Time: 19:49:03.
 * @author WhiteWarrior
 */
public class EnergySlot extends SlotItemHandler {

	public EnergySlot(IItemHandler dinventoryIn, int index, int xPosition, int yPosition) {
		super(dinventoryIn, index, xPosition, yPosition);

	}

	public boolean isItemValid(ItemStack stack){
		if(stack.getItem() instanceof IItemElectricEnergyStorage && stack.hasCapability(CapabilityElectricEnergy.ENERGY, null))
			return super.isItemValid(stack);
		return false;
    }
}
