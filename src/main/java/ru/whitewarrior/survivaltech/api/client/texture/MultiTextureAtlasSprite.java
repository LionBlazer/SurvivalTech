package ru.whitewarrior.survivaltech.api.client.texture;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import ru.whitewarrior.survivaltech.Constants;

import java.util.Collection;
import java.util.function.Function;

public class MultiTextureAtlasSprite extends TextureAtlasSprite
{
    private final ResourceLocation bucket = new ResourceLocation("blocks/stone");
    private final ResourceLocation bucketCoverMask = new ResourceLocation(Constants.MODID,"block/ore/scattered_gold");
    private final ImmutableList<ResourceLocation> dependencies = ImmutableList.of(bucket, bucketCoverMask);

    public MultiTextureAtlasSprite(ResourceLocation resourceLocation)
    {
        super(resourceLocation.toString());
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public Collection<ResourceLocation> getDependencies()
    {
        return dependencies;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        TextureAtlasSprite sprite = textureGetter.apply(bucket);
        TextureAtlasSprite alphaMask = textureGetter.apply(bucketCoverMask);
        width = sprite.getIconWidth();
        height = sprite.getIconHeight();
        int[][] pixels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
        pixels[0] = new int[width * height];

        int[][] oldPixels = sprite.getFrameTextureData(0);
        int[][] alphaPixels = alphaMask.getFrameTextureData(0);

        for (int p = 0; p < width * height; p++) {
            int oldPixelAlpha = alphaPixels[0][p] >>> 24;
            if (oldPixelAlpha > 0)
                pixels[0][p] = alphaPixels[0][p];
            else
                pixels[0][p] = oldPixels[0][p];
        }

        this.clearFramesTextureData();
        this.framesTextureData.add(pixels);
        return false;
    }
}