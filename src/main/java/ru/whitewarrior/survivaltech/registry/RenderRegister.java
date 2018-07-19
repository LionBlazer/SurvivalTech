package ru.whitewarrior.survivaltech.registry;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import ru.whitewarrior.survivaltech.registry.render.FluidTankTESR;
import ru.whitewarrior.survivaltech.registry.tileentity.fluidtank.TileEntityFluidTank;

/**
 * Date: 2018-04-06.
 * Time: 16:14:16.
 * @author WhiteWarrior
 */
public class RenderRegister {
	public static void postInitClient() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluidTank.class, new FluidTankTESR());
	}
}
