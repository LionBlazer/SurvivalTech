package ru.whitewarrior.survivaltech.api.common.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;

/**
 * Date: 2017-12-30.
 * Time: 19:49:03.
 * @author WhiteWarrior
 */
public class EnergySlot extends InventorySlot{

	public EnergySlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		.setBackgroundName(Constants.TEXTURE+ "textures/gui/inventory/slot/energy.png");
		.setBackgroundLocation(new ResourceLocation(Constants.MODID, "textures/gui/inventory/slot/energy.png"));
	}

	public boolean isItemValid(ItemStack stack){
		if(stack.getItem() instanceof IItemElectricEnergyStorage && stack.hasCapability(CapabilityElectricEnergy.ENERGY, null))
			return super.isItemValid(stack);
		return false;
    }
}
