package ru.whitewarrior.survivaltech.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.whitewarrior.survivaltech.registry.tileentity.fluidtank.TileEntityFluidTank;
import ru.whitewarrior.survivaltech.registry.tileentity.smallenergystorage.TileEntitySmallEnergyStorage;
import ru.whitewarrior.survivaltech.registry.tileentity.solidfuelgenerator.TileEntitySolidFuelGenerator;
import ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.TileEntityToolRepairer;

/**
 * Date: 2017-12-29.
 * Time: 16:07:44.
 * @author WhiteWarrior
 */
public class TileEntityRegister {

	public static void postInit() {
		GameRegistry.registerTileEntity(TileEntitySolidFuelGenerator.class, "TileEntitySolidFuelGenerator");
		GameRegistry.registerTileEntity(TileEntitySmallEnergyStorage.class, "TileEntitySmallEnergyStorage");
		GameRegistry.registerTileEntity(TileEntityToolRepairer.class, "TileEntityToolRepairer");
		GameRegistry.registerTileEntity(TileEntityFluidTank.class, "TileEntityFluidTank");
	}

}
