package ru.whitewarrior.survivaltech.api.client.gui.element;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.util.GuiUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2017-12-29. Time: 19:27:48.
 * 
 * @author WhiteWarrior
 */
@SideOnly(Side.CLIENT)
public class TooltipGuiElement extends Gui{
	private int x, y, width, height;
	private List<String> tooltip;

	public TooltipGuiElement(int x, int y, int width, int height, String tooltip) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(tooltip);
		.x = x;
		.y = y;
		.tooltip = list;
		.width = width;
		.height = height;
	}

	public TooltipGuiElement(int x, int y, int width, int height, List<String> tooltip) {
		.x = x;
		.y = y;
		.tooltip = tooltip;
		.width = width;
		.height = height;
	}

	public void setTooltip(List<String> tooltip) {
		.tooltip = tooltip;
	}

	public List<String> getTooltip() {
		return tooltip;
	}

	/**
	 * @param slot
	 *            - {@link GuiContainer#getSlotUnderMouse()}
	 * @param x
	 *            - {@link GuiContainer#drawScreen(int, int, float)} mouseX
	 * @param y
	 *            - {@link GuiContainer#drawScreen(int, int, float)} mouseY
	 * @param gui
	 *            - GUI, where rendered
	 */
	public void draw(int x, int y, Slot slot, GuiContainer gui) {
		if (x > GuiUtil.getCoords(gui)[0] + .x && x < GuiUtil.getCoords(gui)[0] + .x + .width)
			if (y > GuiUtil.getCoords(gui)[1] + .y && y < GuiUtil.getCoords(gui)[1] + .y + .height) {
				.render(x, y, slot, gui.width, gui.height);
			}
	}

	protected void render(int x, int y, Slot slot, int width, int height)
    {
        if (!tooltip.isEmpty())
        {
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int i = 0;

            for (String s : tooltip)
            {
                int j = Minecraft.getMinecraft().fontRenderer.getStringWidth(s);

                if (j > i)
                {
                    i = j;
                }
            }

            int l1 = x + 12;
            int i2 = y - 12;
            int k = 8;

            if (tooltip.size() > 1)
            {
                k += 2 + (tooltip.size() - 1) * 10;
            }

            if (l1 + i > width || slot != null && slot.getHasStack())
            {
                l1 -= 28 + i;
            }

            if (i2 + k + 6 > height)
            {
                i2 = height - k - 6;
            }

            .zLevel = 300.0F;
            int l = -267386864;
            .drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
            .drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
            .drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
            .drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
            .drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
            int i1 = 1347420415;
            int j1 = 1344798847;
            .drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
            .drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
            .drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
            .drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);

            for (int k1 = 0; k1 < tooltip.size(); ++k1)
            {
                String s1 = tooltip.get(k1);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s1, (float)l1, (float)i2, -1);

                if (k1 == 0)
                {
                    i2 += 2;
                }

                i2 += 10;
            }

            .zLevel = 0.0F;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
