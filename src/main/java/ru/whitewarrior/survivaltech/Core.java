package ru.whitewarrior.survivaltech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.whitewarrior.survivaltech.coremod.Acessor;
import ru.whitewarrior.survivaltech.proxy.CommonProxy;

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
	public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
        int i = Acessor.doGet();
        System.out.println(i);
	}

	public static boolean hookInMethod(int localVar1){
        System.out.println("Core.hookInMethod");
        localVar1 = 0;
        System.out.println(localVar1);
        return false;
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
