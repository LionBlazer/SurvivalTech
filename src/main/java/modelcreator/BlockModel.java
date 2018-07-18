package modelcreator;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.Collection;
import java.util.function.Function;

public class BlockModel implements IModel {
    final ResourceLocation loc;

    public BlockModel(ResourceLocation loc) {
        this.loc = loc;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //текстурка
        TextureAtlasSprite sprite = bakedTextureGetter.apply(loc);
        //Получаем экземпляр для лучшей читабельности
        ModelBaker baker = ModelBaker.INSTANCE;
        //начинаем создание модели
        baker.begin(state, format);
        //привязываем текстурку
        baker.setTexture(sprite);
        //создаем куб размером 0.5 - это полная величина, т.е. куб будет размером с полноценный блок(1 метр)
        baker.putTexturedCube(0, 0, 0, 0.5f);
        return new BlockBakedModel(/*Испекаем нашу модель*/baker.bake(), bakedTextureGetter.apply(loc), format, state);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Lists.newArrayList(loc);
    }

    @Override
    public IModel retexture(ImmutableMap<String, String> textures) {
        if (textures.isEmpty())
            return this;
        return new BlockModel(loc);
    }
}

