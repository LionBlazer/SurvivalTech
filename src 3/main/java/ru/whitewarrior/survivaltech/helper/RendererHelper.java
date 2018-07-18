package ru.whitewarrior.survivaltech.helper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.color.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Date: 2018-01-10.
 * Time: 14:54:08.
 * @author WhiteWarrior
 */
public class RendererHelper {
    public static void renderOverlay(ResourceLocation res) {
        ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, (double) scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos((double) scaledRes.getScaledWidth(), (double) scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos((double) scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
    }

	private static void preRenderDamagedBlocks()
    {
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.doPolygonOffset(-3.0F, -3.0F);
        GlStateManager.enablePolygonOffset();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
    }

    private static void postRenderDamagedBlocks()
    {
        GlStateManager.disableAlpha();
        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
	}

	public static void drawBlockTexture(Entity entityIn, float partialTicks, BlockPos blockpos, World world, String texture) {
		double d3 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double) partialTicks;
		double d4 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double) partialTicks;
		double d5 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double) partialTicks;
		Tessellator tessellatorIn = Tessellator.getInstance();
		BufferBuilder bufferBuilderIn = tessellatorIn.getBuffer();
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		preRenderDamagedBlocks();
		bufferBuilderIn.begin(7, DefaultVertexFormats.BLOCK);
		bufferBuilderIn.setTranslation(-d3, -d4, -d5);
		bufferBuilderIn.noColor();
		IBlockState iblockstate = world.getBlockState(blockpos);
		TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, texturemap.getAtlasSprite("minecraft:blocks/"+texture), world);
		tessellatorIn.draw();
		bufferBuilderIn.setTranslation(0.0D, 0.0D, 0.0D);
		postRenderDamagedBlocks();
	}



    private static Tessellator tessellator = Tessellator.getInstance();
    private static BufferBuilder worldRenderer = tessellator.getBuffer();
    private static BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();

    public static boolean renderFluid(IBlockAccess blockAccess, IBlockState blockStateIn, BlockPos blockPosIn, BufferBuilder bufferBuilderIn, FluidStack stack)
    {
        TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[2];
        atextureatlassprite[0] = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(stack.getFluid().getStill().toString());
        atextureatlassprite[1] = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(stack.getFluid().getFlowing().toString());
        int i = blockColors.colorMultiplier(blockStateIn, blockAccess, blockPosIn, 0);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float f1 = (float)(i >> 8 & 255) / 255.0F;
        float f2 = (float)(i & 255) / 255.0F;
        boolean flag1 = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.UP);
        boolean flag2 = blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.DOWN);
        boolean[] aboolean = new boolean[] {blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.NORTH), blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.SOUTH), blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.WEST), blockStateIn.shouldSideBeRendered(blockAccess, blockPosIn, EnumFacing.EAST)};

        if (!flag1 && !flag2 && !aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3])
        {
            return false;
        }
        else
        {
            boolean flag3 = false;
            float f3 = 0.5F;
            float f4 = 1.0F;
            float f5 = 0.8F;
            float f6 = 0.6F;
            Material material = Material.WATER;
            float f7 = 1;
            float f8 = 1;
            float f9 = 1;
            float f10 = 1;
            double d0 = (double)blockPosIn.getX();
            double d1 = (double)blockPosIn.getY();
            double d2 = (double)blockPosIn.getZ();
            float f11 = 0.001F;

            if (flag1)
            {
                flag3 = true;
                float f12 = 0;
                TextureAtlasSprite textureatlassprite = f12 > -999.0F ? atextureatlassprite[1] : atextureatlassprite[0];
                f7 -= 0.001F;
                f8 -= 0.001F;
                f9 -= 0.001F;
                f10 -= 0.001F;
                float f13;
                float f14;
                float f15;
                float f16;
                float f17;
                float f18;
                float f19;
                float f20;

                if (f12 < -999.0F)
                {
                    f13 = textureatlassprite.getInterpolatedU(0.0D);
                    f17 = textureatlassprite.getInterpolatedV(0.0D);
                    f14 = f13;
                    f18 = textureatlassprite.getInterpolatedV(16.0D);
                    f15 = textureatlassprite.getInterpolatedU(16.0D);
                    f19 = f18;
                    f16 = f15;
                    f20 = f17;
                }
                else
                {
                    float f21 = MathHelper.sin(f12) * 0.25F;
                    float f22 = MathHelper.cos(f12) * 0.25F;
                    float f23 = 8.0F;
                    f13 = textureatlassprite.getInterpolatedU((double)(8.0F + (-f22 - f21) * 16.0F));
                    f17 = textureatlassprite.getInterpolatedV((double)(8.0F + (-f22 + f21) * 16.0F));
                    f14 = textureatlassprite.getInterpolatedU((double)(8.0F + (-f22 + f21) * 16.0F));
                    f18 = textureatlassprite.getInterpolatedV((double)(8.0F + (f22 + f21) * 16.0F));
                    f15 = textureatlassprite.getInterpolatedU((double)(8.0F + (f22 + f21) * 16.0F));
                    f19 = textureatlassprite.getInterpolatedV((double)(8.0F + (f22 - f21) * 16.0F));
                    f16 = textureatlassprite.getInterpolatedU((double)(8.0F + (f22 - f21) * 16.0F));
                    f20 = textureatlassprite.getInterpolatedV((double)(8.0F + (-f22 - f21) * 16.0F));
                }

                int k2 = blockStateIn.getPackedLightmapCoords(blockAccess, blockPosIn);
                int l2 = k2 >> 16 & 65535;
                int i3 = k2 & 65535;
                float f24 = 1.0F * f;
                float f25 = 1.0F * f1;
                float f26 = 1.0F * f2;
                bufferBuilderIn.pos(d0 + 0.0D, d1 + (double)f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f13, (double)f17).lightmap(l2, i3).endVertex();
                bufferBuilderIn.pos(d0 + 0.0D, d1 + (double)f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f14, (double)f18).lightmap(l2, i3).endVertex();
                bufferBuilderIn.pos(d0 + 1.0D, d1 + (double)f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f15, (double)f19).lightmap(l2, i3).endVertex();
                bufferBuilderIn.pos(d0 + 1.0D, d1 + (double)f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f16, (double)f20).lightmap(l2, i3).endVertex();

                    bufferBuilderIn.pos(d0 + 0.0D, d1 + (double)f7, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f13, (double)f17).lightmap(l2, i3).endVertex();
                    bufferBuilderIn.pos(d0 + 1.0D, d1 + (double)f10, d2 + 0.0D).color(f24, f25, f26, 1.0F).tex((double)f16, (double)f20).lightmap(l2, i3).endVertex();
                    bufferBuilderIn.pos(d0 + 1.0D, d1 + (double)f9, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f15, (double)f19).lightmap(l2, i3).endVertex();
                    bufferBuilderIn.pos(d0 + 0.0D, d1 + (double)f8, d2 + 1.0D).color(f24, f25, f26, 1.0F).tex((double)f14, (double)f18).lightmap(l2, i3).endVertex();
                
            }

            if (flag2)
            {
                float f35 = atextureatlassprite[0].getMinU();
                float f36 = atextureatlassprite[0].getMaxU();
                float f37 = atextureatlassprite[0].getMinV();
                float f38 = atextureatlassprite[0].getMaxV();
                int l1 = blockStateIn.getPackedLightmapCoords(blockAccess, blockPosIn.down());
                int i2 = l1 >> 16 & 65535;
                int j2 = l1 & 65535;
                bufferBuilderIn.pos(d0, d1, d2 + 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).tex((double)f35, (double)f38).lightmap(i2, j2).endVertex();
                bufferBuilderIn.pos(d0, d1, d2).color(0.5F, 0.5F, 0.5F, 1.0F).tex((double)f35, (double)f37).lightmap(i2, j2).endVertex();
                bufferBuilderIn.pos(d0 + 1.0D, d1, d2).color(0.5F, 0.5F, 0.5F, 1.0F).tex((double)f36, (double)f37).lightmap(i2, j2).endVertex();
                bufferBuilderIn.pos(d0 + 1.0D, d1, d2 + 1.0D).color(0.5F, 0.5F, 0.5F, 1.0F).tex((double)f36, (double)f38).lightmap(i2, j2).endVertex();
                flag3 = true;
            }

            for (int i1 = 0; i1 < 4; ++i1)
            {
                int j1 = 0;
                int k1 = 0;

                if (i1 == 0)
                {
                    --k1;
                }

                if (i1 == 1)
                {
                    ++k1;
                }

                if (i1 == 2)
                {
                    --j1;
                }

                if (i1 == 3)
                {
                    ++j1;
                }

                BlockPos blockpos = blockPosIn.add(j1, 0, k1);
                TextureAtlasSprite textureatlassprite1 = atextureatlassprite[1];


                if (aboolean[i1])
                {
                    float f39;
                    float f40;
                    double d3;
                    double d4;
                    double d5;
                    double d6;

                    if (i1 == 0)
                    {
                        f39 = f7;
                        f40 = f10;
                        d3 = d0;
                        d5 = d0 + 1.0D;
                        d4 = d2 + 0.0010000000474974513D;
                        d6 = d2 + 0.0010000000474974513D;
                    }
                    else if (i1 == 1)
                    {
                        f39 = f9;
                        f40 = f8;
                        d3 = d0 + 1.0D;
                        d5 = d0;
                        d4 = d2 + 1.0D - 0.0010000000474974513D;
                        d6 = d2 + 1.0D - 0.0010000000474974513D;
                    }
                    else if (i1 == 2)
                    {
                        f39 = f8;
                        f40 = f7;
                        d3 = d0 + 0.0010000000474974513D;
                        d5 = d0 + 0.0010000000474974513D;
                        d4 = d2 + 1.0D;
                        d6 = d2;
                    }
                    else
                    {
                        f39 = f10;
                        f40 = f9;
                        d3 = d0 + 1.0D - 0.0010000000474974513D;
                        d5 = d0 + 1.0D - 0.0010000000474974513D;
                        d4 = d2;
                        d6 = d2 + 1.0D;
                    }

                    flag3 = true;
                    float f41 = textureatlassprite1.getInterpolatedU(0.0D);
                    float f27 = textureatlassprite1.getInterpolatedU(8.0D);
                    float f28 = textureatlassprite1.getInterpolatedV((double)((1.0F - f39) * 16.0F * 0.5F));
                    float f29 = textureatlassprite1.getInterpolatedV((double)((1.0F - f40) * 16.0F * 0.5F));
                    float f30 = textureatlassprite1.getInterpolatedV(8.0D);
                    int j = blockStateIn.getPackedLightmapCoords(blockAccess, blockpos);
                    int k = j >> 16 & 65535;
                    int l = j & 65535;
                    float f31 = i1 < 2 ? 0.8F : 0.6F;
                    float f32 = 1.0F * f31 * f;
                    float f33 = 1.0F * f31 * f1;
                    float f34 = 1.0F * f31 * f2;
                    bufferBuilderIn.pos(d3, d1 + (double)f39, d4).color(f32, f33, f34, 1.0F).tex((double)f41, (double)f28).lightmap(k, l).endVertex();
                    bufferBuilderIn.pos(d5, d1 + (double)f40, d6).color(f32, f33, f34, 1.0F).tex((double)f27, (double)f29).lightmap(k, l).endVertex();
                    bufferBuilderIn.pos(d5, d1 + 0.0D, d6).color(f32, f33, f34, 1.0F).tex((double)f27, (double)f30).lightmap(k, l).endVertex();
                    bufferBuilderIn.pos(d3, d1 + 0.0D, d4).color(f32, f33, f34, 1.0F).tex((double)f41, (double)f30).lightmap(k, l).endVertex();

                   
                        bufferBuilderIn.pos(d3, d1 + 0.0D, d4).color(f32, f33, f34, 1.0F).tex((double)f41, (double)f30).lightmap(k, l).endVertex();
                        bufferBuilderIn.pos(d5, d1 + 0.0D, d6).color(f32, f33, f34, 1.0F).tex((double)f27, (double)f30).lightmap(k, l).endVertex();
                        bufferBuilderIn.pos(d5, d1 + (double)f40, d6).color(f32, f33, f34, 1.0F).tex((double)f27, (double)f29).lightmap(k, l).endVertex();
                        bufferBuilderIn.pos(d3, d1 + (double)f39, d4).color(f32, f33, f34, 1.0F).tex((double)f41, (double)f28).lightmap(k, l).endVertex();
                    
                }
            }

            return flag3;
        }
    }
    
    
    public static void renderFluid(float fluidLevel, float sizeX, float sizeZ, float maxHeight, float minHeight, FluidStack fluid, boolean renderTop, boolean isTop, boolean renderDown, boolean isDouble) {

        if (fluid == null || minHeight >= maxHeight) return;

        if (isDouble && !isTop)
            maxHeight = 1;

        GlStateManager.pushMatrix();


        FluidStack fluidStack = new FluidStack(fluid, 1000);

        GlStateManager.translate(0.5, 0, 0.5); // minHeight + (fluidLevel) * (maxHeight - minHeight)


        TextureAtlasSprite fluidStillSprite = Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(fluid.getFluid().getStill().toString());

        int fluidColor = fluid.getFluid().getColor(fluidStack);

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        setGLColorFromInt(fluidColor);

        if (fluidLevel > 1) fluidLevel = 1.05f;

        float height = renderTop ? minHeight + (fluidLevel) * (maxHeight - minHeight) : 1;

        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      
        float xMax, zMax, xMin, zMin, yMin = minHeight;
        xMax = sizeX;
        zMax = sizeZ;
        xMin = -sizeX;
        zMin = -sizeZ;

        if (isTop) yMin = 0;
        
        renderCuboid(worldRenderer,xMax, xMin, yMin, height, zMin, zMax, fluidStillSprite, renderTop, renderDown);


        tessellator.draw();

        GlStateManager.popMatrix();

    }

    public static void renderFluid(float fluidLevel, float size, float maxHeight, float minHeight, FluidStack fluid, boolean renderTop, boolean isTop, boolean renderDown, boolean isDouble) {
       // renderFluid(fluidLevel, size, size, maxHeight, minHeight, fluid, renderTop, isTop, renderDown, isDouble);
    }

    private static void setGLColorFromInt(int color) {
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;

        GlStateManager.color(red, green, blue, 1.0F);
    }

    private static void renderCuboid(BufferBuilder worldRenderer, float xMax, float xMin, float yMin, float height, float zMin, float zMax, TextureAtlasSprite textureAtlasSprite, boolean renderTop, boolean renderDown) {
        double uMin = (double) textureAtlasSprite.getMinU();
        double uMax = (double) textureAtlasSprite.getMaxU();
        double vMin = (double) textureAtlasSprite.getMinV();
        double vMax = (double) textureAtlasSprite.getMaxV();

        final double vHeight = vMax - vMin;

        //top
        if (renderTop) {
        	addVertexWithUV(worldRenderer, xMax, height, zMax, uMax, vMin);
        	addVertexWithUV(worldRenderer, xMax, height, zMin, uMin, vMin);
        	addVertexWithUV( worldRenderer, xMin, height, zMin, uMin, vMax);
        	addVertexWithUV(worldRenderer, xMin, height, zMax, uMax, vMax);
        }

        //north
        addVertexWithUV(worldRenderer, xMax, yMin, zMin, uMax, vMin);
        addVertexWithUV(worldRenderer, xMin, yMin, zMin, uMin, vMin);
        addVertexWithUV(worldRenderer, xMin, height, zMin, uMin, vMin + (vHeight * height));
        addVertexWithUV(worldRenderer, xMax, height, zMin, uMax, vMin + (vHeight * height));

        //south
        addVertexWithUV(worldRenderer, xMax, yMin, zMax, uMin, vMin);
        addVertexWithUV(worldRenderer, xMax, height, zMax, uMin, vMin + (vHeight * height));
        addVertexWithUV(worldRenderer, xMin, height, zMax, uMax, vMin + (vHeight * height));
        addVertexWithUV(worldRenderer, xMin, yMin, zMax, uMax, vMin);

        //east
        addVertexWithUV(worldRenderer, xMax, yMin, zMin, uMin, vMin);
        addVertexWithUV(worldRenderer, xMax, height, zMin, uMin, vMin + (vHeight * height));
        addVertexWithUV(worldRenderer, xMax, height, zMax, uMax, vMin + (vHeight * height));
        addVertexWithUV(worldRenderer, xMax, yMin, zMax, uMax, vMin);

        //west
        addVertexWithUV(worldRenderer, xMin, yMin, zMax, uMin, vMin);
        addVertexWithUV(worldRenderer, xMin, height, zMax, uMin, vMin + (vHeight * height));
        addVertexWithUV(worldRenderer, xMin, height, zMin, uMax, vMin + (vHeight * height));
        addVertexWithUV(worldRenderer, xMin, yMin, zMin, uMax, vMin);

        if (renderDown) {
        	addVertexWithUV(worldRenderer, xMax, yMin, zMin, uMax, vMin);
        	addVertexWithUV(worldRenderer, xMax, yMin, zMax, uMin, vMin);
        	addVertexWithUV(worldRenderer, xMin, yMin, zMax, uMin, vMax);
        	addVertexWithUV(worldRenderer, xMin, yMin, zMin, uMax, vMax);
        }

    }

   
    private static void addVertexWithUV(BufferBuilder worldRenderer, float x, float y, float z, double u, double v) {

        worldRenderer.pos(x / 2f, y, z / 2f).tex(u, v).endVertex();

    }
    private static void addVertexWithUVandLightMap(int l2, int i3, BufferBuilder worldRenderer, float x, float y, float z, double u, double v) {

        worldRenderer.pos(x / 2f, y, z / 2f).tex(u, v).endVertex();

    }

    private static void addVertexWithColor(BufferBuilder worldRenderer, float x, float y, float z, float red, float green, float blue, float alpha) {

        worldRenderer.pos(x / 2f, y, z / 2f).color(red, green, blue, alpha).endVertex();

    }

    private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
    private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
    private static final Random RANDOM = new Random(31100L);
    private static final FloatBuffer floatBuffer = GLAllocation.createDirectFloatBuffer(16);

    public static void renderEndPortal(float size, float yMin, float yMax, float oldPx, float oldPy, float oldPz) {
        renderEndPortal(-size, size, yMin, yMax, -size, size, oldPx, oldPy, oldPz);
    }

    public static void renderEndPortal(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax, float oldPx, float oldPy, float oldPz) {
        GlStateManager.disableLighting();
        RANDOM.setSeed(31100L);
        float f3 = 0.75F;

        for (int i = 0; i < 5; ++i) {
            GlStateManager.pushMatrix();
            float f4 = (float) (7.4 - i);
            float f5 = 1F;// 0.0625F;
            float f6 = 1.0F / (f4 + 1.0F - 0.4F);

            if (i == 0) {
                Minecraft.getMinecraft().renderEngine.bindTexture(END_SKY_TEXTURE);
                f6 = 0.1F;
                f4 = 65.0F;
                f5 = 0.125F;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
            }

            if (i >= 1) {
                Minecraft.getMinecraft().renderEngine.bindTexture(END_PORTAL_TEXTURE);
            }

            if (i == 1) {
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(1, 1);
                f5 = 0.5F;
            }

            float f7 = (float) (-(yMin + (double) f3));
            float f8 = f7 + (float) ActiveRenderInfo.getCameraPosition().y;
            float f9 = f7 + f4 + (float) ActiveRenderInfo.getCameraPosition().y;
            float f10 = f8 / f9;
            f10 = (float) (yMin + (double) f3) + f10;
            GlStateManager.translate(oldPx, f10, oldPz);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
            GlStateManager.texGen(GlStateManager.TexGen.S, 9473, fillFloatBuffer(1.0F, 0.0F, 0.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.T, 9473, fillFloatBuffer(0.0F, 0.0F, 1.0F, 0.0F));
            GlStateManager.texGen(GlStateManager.TexGen.R, 9473, fillFloatBuffer(0.0F, 0.0F, 0.0F, 1.0F));
            GlStateManager.texGen(GlStateManager.TexGen.Q, 9474, fillFloatBuffer(0.0F, 1.0F, 0.0F, 0.0F));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, (float) (Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
            GlStateManager.scale(f5, f5, f5);
            GlStateManager.translate(0.5F, 0.5F, 0.0F);
            GlStateManager.rotate((float) (i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-0.5F, -0.5F, 0.0F);
            GlStateManager.translate(-oldPx, -oldPz, -oldPy);
            f8 = f7 + (float) ActiveRenderInfo.getCameraPosition().y;
            GlStateManager.translate((float) ActiveRenderInfo.getCameraPosition().x * f4 / f8, (float) ActiveRenderInfo.getCameraPosition().z * f4 / f8, -oldPy);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder worldrenderer = tessellator.getBuffer();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            float f11 = (RANDOM.nextFloat() * 0.5F + 0.1F) * f6;
            float f12 = (RANDOM.nextFloat() * 0.5F + 0.4F) * f6;
            float f13 = (RANDOM.nextFloat() * 0.5F + 0.5F) * f6;

            if (i == 0) {
                f11 = f12 = f13 = 1.0F * f6;
            }

            /*worldrenderer.pos(x, y + (double)f3, z).color(f11, f12, f13, 1.0F).endVertex();
            worldrenderer.pos(x, y + (double)f3, z + 1.0D).color(f11, f12, f13, 1.0F).endVertex();
            worldrenderer.pos(x + 1.0D, y + (double)f3, z + 1.0D).color(f11, f12, f13, 1.0F).endVertex();
            worldrenderer.pos(x + 1.0D, y + (double)f3, z).color(f11, f12, f13, 1.0F).endVertex();*/

            //north
            addVertexWithColor(worldRenderer, xMax, yMin, zMin, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMin, yMin, zMin, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMin, yMax, zMin, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMax, yMax, zMin, f11, f12, f13, 1.0F);

            //south
            addVertexWithColor(worldRenderer, xMax, yMin, zMax, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMax, yMax, zMax, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMin, yMax, zMax, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMin, yMin, zMax, f11, f12, f13, 1.0F);

            //east
            addVertexWithColor(worldRenderer, xMax, yMin, zMin, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMax, yMax, zMin, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMax, yMax, zMax, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMax, yMin, zMax, f11, f12, f13, 1.0F);

            //west
            addVertexWithColor(worldRenderer, xMin, yMin, zMax, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMin, yMax, zMax, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMin, yMax, zMin, f11, f12, f13, 1.0F);
            addVertexWithColor(worldRenderer, xMin, yMin, zMin, f11, f12, f13, 1.0F);


            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            Minecraft.getMinecraft().renderEngine.bindTexture(END_SKY_TEXTURE);
        }

        GlStateManager.disableBlend();
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
        GlStateManager.enableLighting();
    }

    private static FloatBuffer fillFloatBuffer(float f1, float f2, float f3, float f4) {
        floatBuffer.clear();
        floatBuffer.put(f1).put(f2).put(f3).put(f4);
        floatBuffer.flip();
        return floatBuffer;
}
	
	
	
}
