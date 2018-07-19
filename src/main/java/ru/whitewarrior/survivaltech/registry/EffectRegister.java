package ru.whitewarrior.survivaltech.registry;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.whitewarrior.survivaltech.api.common.automatic.potion.BasicPotion;

/**
 * Date: 2018-01-09. Time: 13:22:32.
 * 
 * @author WhiteWarrior
 */
public class EffectRegister {
	public static Potion potion = new BasicPotion(true, 134, "potionbleeeeee");
	public static void preInit() {
		ForgeRegistries.POTIONS.register(potion);
	}
}
