package ru.whitewarrior.survivaltech.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ru.whitewarrior.survivaltech.Core;
import ru.whitewarrior.survivaltech.handler.client.BlockEventHandler;
import ru.whitewarrior.survivaltech.handler.client.OverlayEventHandler;
import ru.whitewarrior.survivaltech.handler.client.TextureEventHandler;
import ru.whitewarrior.survivaltech.handler.common.BlockGuiHandler;
import ru.whitewarrior.survivaltech.handler.common.EnergyManagerEventHandler;
import ru.whitewarrior.survivaltech.handler.common.MultiBlockEventHandler;

/**
 * Date: 2018-01-10.
 * Time: 12:51:05.
 * @author WhiteWarrior
 */
public class Handlers {

	public static void registerCommon() {
		MinecraftForge.EVENT_BUS.register(new EnergyManagerEventHandler());
		MinecraftForge.EVENT_BUS.register(new MultiBlockEventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(Core.INSTANCE, new BlockGuiHandler());
	}
	
	public static void registerClient() {
		MinecraftForge.EVENT_BUS.register(new OverlayEventHandler());
		MinecraftForge.EVENT_BUS.register(new TextureEventHandler());
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
	}

}
