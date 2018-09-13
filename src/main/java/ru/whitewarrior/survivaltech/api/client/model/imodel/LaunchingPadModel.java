package ru.whitewarrior.survivaltech.api.client.model.imodel;

import modelcreator.BlockBakedModel;
import modelcreator.ModelBaker;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class LaunchingPadModel implements IModel {

    public LaunchingPadModel() {

    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Collections.singletonList(new ResourceLocation("blocks/iron_block"));
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ModelBaker baker = ModelBaker.INSTANCE;
        TextureAtlasSprite sprite = bakedTextureGetter.apply(new ResourceLocation("blocks/iron_block"));
        baker.begin(state, format);
        baker.setTexture(sprite);
        baker.putCube(0,-0.4f, 0f, 0.5f,0.1f, 0.5f, sprite.getMinU(), sprite.getMaxU(),sprite.getMinV(), sprite.getMaxV());
        return new BlockBakedModel(baker.bake(), sprite, format, state);
    }


}
