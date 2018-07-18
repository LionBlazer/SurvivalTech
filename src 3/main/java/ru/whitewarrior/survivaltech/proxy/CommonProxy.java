package ru.whitewarrior.survivaltech.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;
import ru.whitewarrior.survivaltech.handler.Handlers;
import ru.whitewarrior.survivaltech.registry.*;

/**
 * Date: 2017-12-23.
 * Time: 14:04:07.
 * @author WhiteWarrior
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		CapabilityElectricEnergy.register();
		MaterialRegister.preInit();
		BlockRegister.preInit();
		ItemRegister.preInit();
		GameMaterialRegister.preInit();
		Handlers.registerCommon();
		EffectRegister.preInit();
		EntityRegister.preInit();
		MultiblockRegister.preInit();
	}

	public void init(FMLInitializationEvent event) {
		OreDictionaryRegister.init();
	}

	public void postInit(FMLPostInitializationEvent event) {
		TileEntityRegister.postInit();
	}

}
