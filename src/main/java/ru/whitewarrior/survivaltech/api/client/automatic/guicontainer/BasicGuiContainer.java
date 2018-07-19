package ru.whitewarrior.survivaltech.api.client.automatic.guicontainer;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.TileEntityUtil;

/**
 * Date: 2017-12-29. Time: 14:52:52.
 * 
 * @author WhiteWarrior
 */
public class BasicGuiContainer extends GuiContainer {
	protected ResourceLocation texture;
    protected TileEntityInventory inventory;
	EntityPlayer player;
	public BasicGuiContainer(Container inventorySlotsIn, TileEntityInventory inventory, EntityPlayer player) {
		super(inventorySlotsIn);

		this.inventory=inventory;
		this.player=player;

        ySize = sizeY();
        if(ySize >= 200){
            this.texture = new ResourceLocation(Constants.MODID, "textures/gui/inventory/basic_large.png");
        }
        else
            this.texture = new ResourceLocation(Constants.MODID, "textures/gui/inventory/basic.png");
	}

	public int sizeY(){
	    return ySize;
    }

	protected void addTextureSlots() {
		for (int slot = 0; slot < inventory.getSlots(); slot++) {
			this.drawTexturedModalRect(GuiUtil.getCoords(this)[0] +inventorySlots.getSlot(slot+36).xPos - 1, GuiUtil.getCoords(this)[1] + inventorySlots.getSlot(slot+36).yPos - 1, 238, 0, 18,18);
		}
	}

	protected void addFire(int x, int y, int[] burntime) {
		this.addDeactiveFire(x, y);
		this.addActiveFire(x, y, burntime);
	}

	protected void addDeactiveFire(int x, int y) {
		this.drawTexturedModalRect(GuiUtil.getCoords(this)[0] + x, GuiUtil.getCoords(this)[1] + y, 191, 1, 13, 13);
	}

	protected void addActiveFire(int x, int y, int[] burntime) {
		int k = TileEntityUtil.getBurnLeftScaled(13, burntime);
		if (burntime[0] > 0)
			this.drawTexturedModalRect(GuiUtil.getCoords(this)[0] + x - 1, GuiUtil.getCoords(this)[1] + y + 11 - k, 176, 12 - k, 14, 2 + k);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
		init(mouseX, mouseY);
	}
	
	protected void init(int x, int y) {

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(inventory.getName(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.player.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }
}
