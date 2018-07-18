package modelcreator;


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

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public class TestItemModel implements IModel {
    final ResourceLocation texture;

    public TestItemModel(ResourceLocation texture){
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
        return new TestItemModel(texture);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //получаем TextureAtlasSprite из ResourceLocation
        TextureAtlasSprite textureAtlasSprite = bakedTextureGetter.apply(texture);
        //Получаем список квадратов из текструки, для этого уже есть удобный метод.
        ImmutableList<BakedQuad> quads = ItemLayerModel.getQuadsForSprite(0, textureAtlasSprite, format, state.apply(Optional.empty()));
        return new TestItemBakedModel(quads, textureAtlasSprite);
    }
}

