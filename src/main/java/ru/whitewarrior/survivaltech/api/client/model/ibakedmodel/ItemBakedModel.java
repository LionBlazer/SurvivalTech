package ru.whitewarrior.survivaltech.api.client.model.ibakedmodel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBakedModel implements IBakedModel {
    List<BakedQuad> quads;
    TextureAtlasSprite textureAtlasSprite;

    public ItemBakedModel(List<BakedQuad> quads, TextureAtlasSprite textureAtlasSprite) {
        this.quads = quads;
        this.textureAtlasSprite = textureAtlasSprite;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return textureAtlasSprite;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        ItemTransformVec3f thirdPerson = new ItemTransformVec3f(new Vector3f(0f, 0, 0), new Vector3f(0.005f, 0.15f, 0.04f), new Vector3f(0.55f, 0.55f, 0.55f));
        ItemTransformVec3f entity = new ItemTransformVec3f(new Vector3f(0f, 0, 0), new Vector3f(0.005f, 0.15f, 0.04f), new Vector3f(0.55f, 0.55f, 0.55f));
        ItemTransformVec3f firstPerson = new ItemTransformVec3f(new Vector3f(0, -90, 25), new Vector3f(0.03f, 0.23f, 0.104f), new Vector3f(0.68f, 0.68f, 0.68f));
        ItemCameraTransforms itemCameraTransforms = new ItemCameraTransforms(thirdPerson, thirdPerson, firstPerson, firstPerson, ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, entity, firstPerson);
        return itemCameraTransforms;
    }
}