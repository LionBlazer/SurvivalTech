package ru.whitewarrior.survivaltech;

import net.minecraft.client.*;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.whitewarrior.survivaltech.proxy.CommonProxy;
import ru.whitewarrior.survivaltech.registry.GameMaterialRegister;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Date: 2017-12-23.
 * Time: 14:02:45.
 * @author WhiteWarrior
 */

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION)
public class Core {
	@Mod.Instance
	public static Core INSTANCE;

	@SidedProxy(clientSide = Constants.clientSide, serverSide = Constants.serverSide)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
	
}
