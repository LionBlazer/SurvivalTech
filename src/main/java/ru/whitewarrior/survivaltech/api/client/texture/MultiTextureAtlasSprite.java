package ru.whitewarrior.survivaltech.api.client.texture;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author WhiteWarrior
 * */
public class MultiTextureAtlasSprite extends TextureAtlasSprite
{
    private ResourceLocation texture1;
    private ResourceLocation texture2;
    private ImmutableList<ResourceLocation> dependencies;

    public MultiTextureAtlasSprite(ResourceLocation texture1, ResourceLocation texture2) {
        super(texture1.toString().concat("_".concat(texture2.toString())));
        this.texture1 = texture1;
        this.texture2 = texture2;
        dependencies = ImmutableList.of(this.texture1, this.texture2);
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return dependencies;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        //�������� �������� �� �����������
        TextureAtlasSprite sprite = textureGetter.apply(texture1);
        TextureAtlasSprite mappingSprite = textureGetter.apply(texture2);
        width = sprite.getIconWidth();
        height = sprite.getIconHeight();
        int[][] pixels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
        //���-�� �������� � ����� ��������
        pixels[0] = new int[width * height];

        int[][] oldPixels = sprite.getFrameTextureData(0);
        //�������� ������� 2 ��������
        int[][] alphaPixels = mappingSprite.getFrameTextureData(0);

        for (int p = 0; p < width * height; p++) {
            //������� ����� �� �������� �������(������ ���� ������������ 8 ������)
            int oldPixelAlpha = alphaPixels[0][p] >>> 24;
            //���������, ������ �� �������?
            if (oldPixelAlpha > 0)
                //���� �� ������, �� ������ ������� 2 ��������� � �����
                pixels[0][p] = alphaPixels[0][p];
            else
                //���� ������, �� ������ ������� 1 ��������� � �����
                pixels[0][p] = oldPixels[0][p];
        }
        //������� ���������� �������, ������� ����� �������� ��� ������������ �������
        this.clearFramesTextureData();
        //��������� ����
        this.framesTextureData.add(pixels);
        //���������� false, ����� ��� TextureAtlasSprite �� ���������� ������� ��������
        return false;
    }
}