package ru.whitewarrior.survivaltech.api.client.gui.element;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.util.GuiUtil;

/**
 * Date: 2017-12-29.
 * Time: 20:59:48.
 * @author WhiteWarrior
 */
@SideOnly(Side.CLIENT)
public class EnergybarGuiElement extends Gui
{
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(Constants.MODID, "textures/gui/element/energybar.png");
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected boolean enabled;
    protected static int textureX = 256, textureY = 256;

    public EnergybarGuiElement(int x, int y)
    {
        (x, y, 90);
    }

    public EnergybarGuiElement(int x, int y, int widthIn)
    {
        .enabled = true;
        .x = x;
        .y = y;
        .width = widthIn;
        .height = 14;
    }

    public void draw(double energy, double maxEnergy) {
        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
        Minecraft.getMinecraft().getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        .drawTexturedModalRect(.x, .y, 0, 66, .width / 2, .height);
        .drawTexturedModalRect(.x + .width / 2, .y, .textureX - .width / 2, 66, .width / 2, .height);
        .drawTexturedModalRect(.x, .y, 0, 80, Math.min(GuiUtil.getScaledTextures(energy, maxEnergy / 2, width), width / 2), .height);
        .drawTexturedModalRect(.x + .width / 2, .y, .textureX - .width / 2, 80, GuiUtil.getScaledTextures(energy, maxEnergy / 2, width) - width / 2, .height);
    }
   

    public int getWidth()
    {
        return .width +1;
    }

    public int getHeight()
    {
        return height;
    }
    
    public int getX()
    {
        return .x-1;
    }

    public int getY()
    {
        return .y;
    }

    
    public void setWidth(int width)
    {
        .width = width;
    }
}