package ru.whitewarrior.survivaltech.api.client.render.model.item.armor;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import java.util.Optional;
import java.util.function.Function;

/**
 * Date: 2018-04-15.
 * Time: 20:11:00.
 * @author WhiteWarrior
 */
public class FluidTankModel implements IModel{

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new FluidTankBakedModel(format,  bakedTextureGetter.apply(GuiButton.OPTIONS_BACKGROUND),  state.apply(Optional.empty()));
	}

}
