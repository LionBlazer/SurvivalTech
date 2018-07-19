package ru.whitewarrior.survivaltech.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.Core;
import ru.whitewarrior.survivaltech.handler.client.*;
import ru.whitewarrior.survivaltech.handler.common.BlockGuiHandler;
import ru.whitewarrior.survivaltech.handler.common.EnergyManagerEventHandler;
import ru.whitewarrior.survivaltech.handler.common.MultiBlockEventHandler;
import ru.whitewarrior.survivaltech.handler.common.WorldGenEventHandler;

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
        MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenEventHandler());
        MinecraftForge.ORE_GEN_BUS.register(new WorldGenEventHandler());
	}

	@SideOnly(Side.CLIENT)
	public static void registerClient() {
        MinecraftForge.EVENT_BUS.register(new TickEventHandler());
		MinecraftForge.EVENT_BUS.register(new OverlayEventHandler());
		MinecraftForge.EVENT_BUS.register(new TextureEventHandler());
		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
        MinecraftForge.EVENT_BUS.register(new ModelEventHandler());
	}

}
