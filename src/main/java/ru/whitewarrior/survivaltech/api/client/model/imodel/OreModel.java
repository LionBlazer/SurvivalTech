package ru.whitewarrior.survivaltech.api.client.model.imodel;

import modelcreator.ModelBaker;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.client.model.ibakedmodel.OreBakedModel;
import ru.whitewarrior.survivaltech.api.client.texture.MultiTextureAtlasSprite;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class OreModel implements IModel {
    ResourceLocation texture;
    public static ResourceLocation rockTexture = new ResourceLocation("blocks/stone");
    public static MultiTextureAtlasSprite spriteStone = new MultiTextureAtlasSprite(new ResourceLocation(Constants.MODID,"test/stone"));
    public OreModel(ResourceLocation texture) {
        this.texture=texture;
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
        TextureAtlasSprite spriteOre = bakedTextureGetter.apply(texture);
      //  TextureAtlasSprite spriteStone = bakedTextureGetter.apply(rockTexture);

//        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
//        try {
//            spriteStone.loadSprite(PngSizeInfo.makeFromResource(rm.getResource(MultiTextureAtlasSprite.location)), false);
//            spriteStone.loadSpriteFrames(null, 2);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ModelBaker baker = ModelBaker.INSTANCE;
        baker.begin(state, format);

        baker.setTexture(spriteStone);
        baker.putTexturedCube(0,0,0,0.5f);
     //   baker.setTexture(spriteOre);
      //  baker.putCube(0,0,0,0.5f, spriteOre.getMaxU(), spriteOre.getMinU(), spriteOre.getMinV(), spriteOre.getMaxV());
        return new OreBakedModel(baker.bake(), bakedTextureGetter.apply(rockTexture));
    }

}