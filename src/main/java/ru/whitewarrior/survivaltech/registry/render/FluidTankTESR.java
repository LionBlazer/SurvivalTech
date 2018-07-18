package ru.whitewarrior.survivaltech.registry.render;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelFluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.helper.RendererHelper;
import ru.whitewarrior.survivaltech.registry.tileentity.fluidtank.TileEntityFluidTank;

/**
 * Date: 2018-04-06. Time: 15:52:50.
 *
 * @author WhiteWarrior
 */
public class FluidTankTESR extends TileEntitySpecialRenderer<TileEntityFluidTank> {
    ModelChest b = new ModelChest();
    ModelFluid flm;
    public static ResourceLocation texture = new ResourceLocation(Constants.MODID, "textures/gui/inventory/basic.png");

    public FluidTankTESR() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        flm = new ModelFluid(FluidRegistry.LAVA);
    }

    @Override
    public void renderTileEntityFast(TileEntityFluidTank te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        System.out.println("fast");
    }

    @Override
    public void render(TileEntityFluidTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();
        // ff


        //System.out.println(te.getFluidStacks());

        GlStateManager.disableLighting();
        //GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);


        float fta = 0;
        float ftag = 0;
        for (int i = 0; i < te.getFluidStacks().size(); i++) {
            FluidStack stack = te.getFluidStacks().get(i);
            if (stack.amount > 0) {
                if (stack.getFluid().isGaseous()) {
                    GL11.glRotatef(180, 1, 0, 1);
                    GL11.glTranslated(0, -1 + ftag, 0);
                    RendererHelper.renderFluid(stack.amount / 10000f, 1, 1, 1, 0.001f, stack, true, false, true, true);
                    GL11.glTranslated(0, 1 - ftag, 0);
                    GL11.glRotatef(-180, 1, 0, 1);
                    ftag += stack.amount / 10000f;
                } else {
                    GL11.glTranslatef(0, fta, 0);
                    RendererHelper.renderFluid(stack.amount / 10000f, 1, 1, 1, 0.001f, stack, true, false, true, false);
                    GL11.glTranslatef(0, -fta, 0);
                    fta += stack.amount / 10000f;
                }

            }
        }

        GlStateManager.disableBlend();
        //GlStateManager.popMatrix();
        GlStateManager.enableLighting();

        GL11.glTranslated(-x, -y, -z);
        GL11.glPopMatrix();
    }
}
