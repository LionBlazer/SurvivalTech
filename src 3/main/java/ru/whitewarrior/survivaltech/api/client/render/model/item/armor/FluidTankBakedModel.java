package ru.whitewarrior.survivaltech.api.client.render.model.item.armor;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.vecmath.Vector4f;
import java.util.List;
import java.util.Optional;

/**
 * Date: 2018-04-15.
 * Time: 20:18:59.
 * @author WhiteWarrior
 */
public class FluidTankBakedModel implements IBakedModel{
    TextureAtlasSprite atlasSprite;
    VertexFormat f;
    Optional<TRSRTransformation> transform;
    ImmutableList.Builder<BakedQuad> builder;
    ImmutableList<BakedQuad> quads;
    public FluidTankBakedModel(VertexFormat format, TextureAtlasSprite atlasSprite, Optional<TRSRTransformation> transform){
        this.f=format;
        this.atlasSprite=atlasSprite;
        this.transform=transform;
        builder = ImmutableList.builder();
        // front
        builder.add(buildQuad(format, transform, EnumFacing.NORTH, atlasSprite, 0,
                0, 0, 7.5f / 16f, atlasSprite.getMinU(), atlasSprite.getMaxV(),
                0, 1, 7.5f / 16f, atlasSprite.getMinU(), atlasSprite.getMinV(),
                1, 1, 7.5f / 16f, atlasSprite.getMaxU(), atlasSprite.getMinV(),
                1, 0, 7.5f / 16f, atlasSprite.getMaxU(), atlasSprite.getMaxV()
        ));
        // back
        builder.add(buildQuad(format, transform, EnumFacing.SOUTH, atlasSprite, 0,
                0, 0, 8.5f / 16f, atlasSprite.getMinU(), atlasSprite.getMaxV(),
                1, 0, 8.5f / 16f, atlasSprite.getMaxU(), atlasSprite.getMaxV(),
                1, 1, 8.5f / 16f, atlasSprite.getMaxU(), atlasSprite.getMinV(),
                0, 1, 8.5f / 16f, atlasSprite.getMinU(), atlasSprite.getMinV()
        ));
        quads=builder.build();

    }

    private static BakedQuad buildQuad(
            VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing side, TextureAtlasSprite sprite, int tint,
            float x0, float y0, float z0, float u0, float v0,
            float x1, float y1, float z1, float u1, float v1,
            float x2, float y2, float z2, float u2, float v2,
            float x3, float y3, float z3, float u3, float v3)
    {
        UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
        builder.setQuadTint(tint);
        builder.setQuadOrientation(side);
        builder.setTexture(sprite);
        putVertex(builder, format, transform, side, x0, y0, z0, u0, v0);
        putVertex(builder, format, transform, side, x1, y1, z1, u1, v1);
        putVertex(builder, format, transform, side, x2, y2, z2, u2, v2);
        putVertex(builder, format, transform, side, x3, y3, z3, u3, v3);
        return builder.build();
    }

    private static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat format, Optional<TRSRTransformation> transform, EnumFacing side, float x, float y, float z, float u, float v)
    {
        Vector4f vec = new Vector4f();
        for(int e = 0; e < format.getElementCount(); e++)
        {
            switch(format.getElement(e).getUsage())
            {
                case POSITION:
                    if(transform.isPresent())
                    {
                        vec.x = x;
                        vec.y = y;
                        vec.z = z;
                        vec.w = 1;
                        transform.get().getMatrix().transform(vec);
                        builder.put(e, vec.x, vec.y, vec.z, vec.w);
                    }
                    else
                    {
                        builder.put(e, x, y, z, 1);
                    }
                    break;
                case COLOR:
                    builder.put(e, 1f, 1f, 1f, 1f);
                    break;
                case UV: if(format.getElement(e).getIndex() == 0)
                {
                    builder.put(e, u, v, 0f, 1f);
                    break;
                }
                case NORMAL:
                    builder.put(e, (float)side.getFrontOffsetX(), (float)side.getFrontOffsetY(), (float)side.getFrontOffsetZ(), 0f);
                    break;
                default:
                    builder.put(e);
                    break;
            }
        }
    }


	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGui3d() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		// TODO Auto-generated method stub
		return atlasSprite;
	}

	@Override
	public ItemOverrideList getOverrides() {
		// TODO Auto-generated method stub
		return ItemOverrideList.NONE;
	}

}
