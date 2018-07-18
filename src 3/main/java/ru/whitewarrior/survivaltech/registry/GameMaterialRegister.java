package ru.whitewarrior.survivaltech.registry;

import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.api.common.automatic.BasicGameMaterial;

/**
 * Date: 2017-12-27. Time: 9:44:39.
 * 
 * @author WhiteWarrior
 */
public class GameMaterialRegister {
	public static BasicGameMaterial copper = new BasicGameMaterial("copper", 1, 1);

	public static void preInit() {
		AdvancedRegistry.register(copper);
	}

	public static void initClient() {
		AdvancedRegistry.registerRender(copper);
	}
}
