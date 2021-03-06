package ru.whitewarrior.survivaltech.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.whitewarrior.survivaltech.api.common.config.Conf;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;
import ru.whitewarrior.survivaltech.handler.Handlers;
import ru.whitewarrior.survivaltech.registry.*;
import ru.whitewarrior.survivaltech.registry.tileentity.oreextractor.recipe.OreExtractorRecipe;

import java.io.File;

/**
 * Date: 2017-12-23.
 * Time: 14:04:07.
 * @author WhiteWarrior
 */
public class CommonProxy {
    public static File dirSmallArcFurnaceRecipe;
	public void preInit(FMLPreInitializationEvent event) {
        dirSmallArcFurnaceRecipe = Conf.dirConf("survival tech/small arc furnace/");
		CapabilityElectricEnergy.register();
		BlockRegister.preInit();
		ItemRegister.preInit();
		GameMaterialRegister.preInit();
		Handlers.registerCommon();
		EffectRegister.preInit();
		EntityRegister.preInit();
		MultiblockRegister.preInit();
        MessageRegister.preInit(event);
        WorldGeneratorRegister.preInit();

	}

	public void init(FMLInitializationEvent event) {
		OreDictionaryRegister.init();
        MaterialRegister.init();
        ConfigParserRegister.init(event);
	}

	public void postInit(FMLPostInitializationEvent event) {
		TileEntityRegister.postInit();
        OreExtractorRecipe.bake();
	}

}
