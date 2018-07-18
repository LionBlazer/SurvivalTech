package modelcreator;


import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

public class BlockBakedModel implements IBakedModel {
    List<BakedQuad> bakedQuads;
    TextureAtlasSprite textureParticle;
    VertexFormat format;
    IModelState state;

    public BlockBakedModel(List<BakedQuad> bakedQuads, TextureAtlasSprite textureParticle, VertexFormat format, IModelState state) {
        this.bakedQuads = bakedQuads;
        this.textureParticle = textureParticle;
        this.format = format;
        this.state = state;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return bakedQuads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return textureParticle;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        ItemCameraTransforms itemCameraTransforms = ItemCameraTransforms.DEFAULT;
        ItemTransformVec3f itemTransformVec3f = itemCameraTransforms.getTransform(cameraTransformType);
        TRSRTransformation tr = new TRSRTransformation(itemTransformVec3f);
        Matrix4f mat = null;
        if (tr != null) {
            mat = tr.getMatrix();
        }
        return Pair.of(this, mat);
    }
}

