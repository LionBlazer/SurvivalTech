package ru.whitewarrior.survivaltech.registry;

import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.registry.multiblock.MultiBlockCrusher;

/**
 * Date: 2018-02-03.
 * Time: 16:22:49.
 * @author WhiteWarrior
 */
public class MultiblockRegister {
	public static MultiBlockCrusher crusher = new MultiBlockCrusher();
	public static void preInit() {
		AdvancedRegistry.register(crusher);
	}
}
