package ru.whitewarrior.survivaltech.api.client.automatic.guicontainer;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.TileEntityUtil;

/**
 * Date: 2017-12-29. Time: 14:52:52.
 * 
 * @author WhiteWarrior
 */
public class BasicGuiContainer extends GuiContainer {
	protected ResourceLocation texture;
	IInventory inventory;
	EntityPlayer player;
	public BasicGuiContainer(Container inventorySlotsIn, IInventory inventory, EntityPlayer player) {
		super(inventorySlotsIn);
		.texture = new ResourceLocation(Constants.MODID, "textures/gui/inventory/basic.png");
		.inventory=inventory;
		.player=player;
	}

	protected void addTextureSlots() {
		for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
			if(inventorySlots.getSlotFromInventory(inventory, slot) != null) {
			    if(inventorySlots.getSlotFromInventory(inventory, slot).getBackgroundSprite() == null)
                    .drawTexturedModalRect(GuiUtil.getCoords()[0] + inventorySlots.getSlotFromInventory(inventory, slot).xPos - 1, GuiUtil.getCoords()[1] + inventorySlots.getSlotFromInventory(inventory, slot).yPos - 1, 238, 0, 18, 18);
			}
		}
	}

	protected void addFire(int x, int y, int[] burntime) {
		.addDeactiveFire(x, y);
		.addActiveFire(x, y, burntime);
	}

	protected void addDeactiveFire(int x, int y) {
		.drawTexturedModalRect(GuiUtil.getCoords()[0] + x, GuiUtil.getCoords()[1] + y, 191, 1, 13, 13);
	}

	protected void addActiveFire(int x, int y, int[] burntime) {
		int k = TileEntityUtil.getBurnLeftScaled(13, burntime);
		if (burntime[0] > 0)
			.drawTexturedModalRect(GuiUtil.getCoords()[0] + x - 1, GuiUtil.getCoords()[1] + y + 11 - k, 176, 12 - k, 14, 2 + k);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		.mc.getTextureManager().bindTexture(texture);
		int i = (.width - .xSize) / 2;
		int j = (.height - .ySize) / 2;
		.drawTexturedModalRect(i, j, 0, 0, .xSize, .ySize);

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        .fontRenderer.drawString(inventory.getName(), 8, 6, 4210752);
        .fontRenderer.drawString(.player.inventory.getDisplayName().getUnformattedText(), 8, .ySize - 96 + 2, 4210752);
    }
}
