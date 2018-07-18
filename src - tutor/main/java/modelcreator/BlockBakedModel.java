package modelcreator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class BlockBakedModel implements IBakedModel {
    private List<BakedQuad> quads;
    private TextureAtlasSprite textureParticle;

    public BlockBakedModel(List<BakedQuad> quads, TextureAtlasSprite textureParticle) {
        this.quads = quads;
        this.textureParticle = textureParticle;
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
    public ItemCameraTransforms getItemCameraTransforms() {
        ItemTransformVec3f firstPerson = new ItemTransformVec3f(new Vector3f(0,45,0), new Vector3f(0, 0, 0), new Vector3f(0.40f, 0.40f, 0.40f));
        ItemCameraTransforms itemCameraTransforms = new ItemCameraTransforms(firstPerson, firstPerson, firstPerson,  firstPerson, firstPerson, firstPerson, firstPerson, firstPerson);
        return itemCameraTransforms;
    }
}

