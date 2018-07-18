package ru.whitewarrior.survivaltech.api.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.FMLLog;
import ru.whitewarrior.survivaltech.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

public class CombinedTextureAtlasSprite extends TextureAtlasSprite {
    private final ResourceLocation bucket = new ResourceLocation("blocks/stone");
    private final ResourceLocation bucketCoverMask = new ResourceLocation(Constants.MODID, "block/ore/chalcopyrite");
    private final ImmutableList<ResourceLocation> dependencies = ImmutableList.of(bucket, bucketCoverMask);

    @Nullable
    protected static IResource getResource(ResourceLocation resourceLocation) {
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation);
        } catch (IOException ignored) {
            return null;
        }
    }

    public CombinedTextureAtlasSprite(ResourceLocation resourceLocation) {
        super(resourceLocation.toString());
    }

    @Override
    public boolean hasCustomLoader(@Nonnull IResourceManager manager, @Nonnull ResourceLocation location) {
        return false;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return dependencies;
    }


    @Override
    public void loadSprite(PngSizeInfo sizeInfo, boolean p_188538_2_) throws IOException {
        super.loadSprite(sizeInfo, p_188538_2_);
    }

    @Override
    public boolean load(@Nonnull IResourceManager manager, @Nonnull ResourceLocation location, @Nonnull Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        final TextureAtlasSprite sprite = textureGetter.apply(bucket);
        final TextureAtlasSprite alphaMask = textureGetter.apply(bucketCoverMask);
        width = sprite.getIconWidth();
        height = sprite.getIconHeight();
        final int[][] pixels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
        pixels[0] = new int[width * height];

        try (IResource empty = getResource(new ResourceLocation("textures/items/bucket_empty.png")); IResource mask = getResource(new ResourceLocation(ForgeVersion.MOD_ID, "textures/items/vanilla_bucket_cover_mask.png"))) {
            // use the alpha mask if it fits, otherwise leave the cover texture blank
            if (empty != null && mask != null && Objects.equals(empty.getResourcePackName(), mask.getResourcePackName()) && alphaMask.getIconWidth() == width && alphaMask.getIconHeight() == height) {
                final int[][] oldPixels = sprite.getFrameTextureData(0);
                final int[][] alphaPixels = alphaMask.getFrameTextureData(0);

                for (int p = 0; p < width * height; p++) {
                    final int alphaMultiplier = alphaPixels[0][p] >>> 24;
                    final int oldPixel = oldPixels[0][p];
                    final int oldPixelAlpha = oldPixel >>> 24;
                    final int newAlpha = oldPixelAlpha * alphaMultiplier / 0xFF;
                    pixels[0][p] = (oldPixel & 0xFFFFFF) + (newAlpha << 24);
                }
            }
        } catch (IOException e) {
            FMLLog.log.error("Failed to close resource", e);
        }

        this.clearFramesTextureData();
        this.framesTextureData.add(pixels);
        return false;
    }
}