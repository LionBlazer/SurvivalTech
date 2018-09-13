package ru.whitewarrior.survivaltech.api.client.model.imodel;

import modelcreator.ModelBaker;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import ru.whitewarrior.survivaltech.api.client.model.ibakedmodel.BlockBakedModel;
import ru.whitewarrior.survivaltech.api.client.texture.MultiTextureAtlasSprite;
import ru.whitewarrior.survivaltech.handler.client.TextureEventHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class OreModel implements IModel {
    ResourceLocation texture;
    public static ResourceLocation rockTexture = new ResourceLocation("blocks/stone");
    MultiTextureAtlasSprite spriteOre;
    public OreModel(ResourceLocation texture) {
        this.texture=texture;
        spriteOre = new MultiTextureAtlasSprite(rockTexture, texture);
        TextureEventHandler.registerSprite(spriteOre);
    }

    @Override
    public IModel smoothLighting(boolean value) {
        return new OreModel(texture);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Collections.singletonList(texture);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

        ModelBaker baker = ModelBaker.INSTANCE;
        baker.begin(state, format);
        baker.setTexture(spriteOre);
        baker.putCube(0,0,0,0.5f, spriteOre.getMaxU(), spriteOre.getMinU(), spriteOre.getMinV(), spriteOre.getMaxV());
        return new BlockBakedModel(baker.bake(), bakedTextureGetter.apply(rockTexture));
    }

}