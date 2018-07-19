package ru.whitewarrior.survivaltech.api.client.model.imodel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.model.IModelState;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.client.model.ibakedmodel.ItemToolBakedModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class ItemCrushedOreModel implements IModel {
    final ResourceLocation texture;
    ResourceLocation textureRock = new ResourceLocation(Constants.MODID, "block/ore/copper");
    public ItemCrushedOreModel(ResourceLocation texture){
        this.texture = texture;
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Lists.newArrayList(texture);
    }

    @Override
    public IModel retexture(ImmutableMap<String, String> textures) {
        if (textures.isEmpty())
            return this;
        return new ItemModel(texture);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        TextureAtlasSprite texture = bakedTextureGetter.apply(this.texture);
        TextureAtlasSprite textureStick = bakedTextureGetter.apply(this.textureRock);
        List<BakedQuad> quads = new ArrayList<>();
        quads.addAll(new ItemLayerModel(ImmutableList.of( this.textureRock, this.texture)).bake(state, format, bakedTextureGetter).getQuads(null, null, 0));
        return new ItemToolBakedModel(quads, texture);
    }
}