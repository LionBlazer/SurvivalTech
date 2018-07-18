package ru.whitewarrior.survivaltech.api.client.render.model;

import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelFluid;
import ru.whitewarrior.survivaltech.api.client.render.model.item.armor.FluidTankModel;

/**
 * Date: 2018-02-24.
 * Time: 13:38:27.
 * @author WhiteWarrior
 */
public class AdvancedModelLoader implements ICustomModelLoader {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation.getResourcePath().contains("fluidTank");
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		return new FluidTankModel();
	}

}
