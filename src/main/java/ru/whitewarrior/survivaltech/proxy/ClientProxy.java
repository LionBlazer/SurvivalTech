package ru.whitewarrior.survivaltech.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.whitewarrior.survivaltech.handler.Handlers;
import ru.whitewarrior.survivaltech.registry.*;

/**
 * Date: 2017-12-23.
 * Time: 14:04:00.
 * @author WhiteWarrior
 */
public class ClientProxy extends CommonProxy{
	
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		Handlers.registerClient();
		ShaderRegister.register();
        GameMaterialRegister.preInitClient();
        ItemRegister.preInitClient();
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		BlockRegister.initClient();
		ItemRegister.initClient();
		GameMaterialRegister.initClient();
		RenderRegister.postInitClient();
        EntityRegister.initClient();
	}

	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		
	}
	
}
