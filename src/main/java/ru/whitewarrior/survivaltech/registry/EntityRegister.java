package ru.whitewarrior.survivaltech.registry;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.whitewarrior.survivaltech.api.common.automatic.entity.BasicEntityEntry;
import ru.whitewarrior.survivaltech.registry.entity.EntityRat;

/**
 * Date: 2018-01-26.
 * Time: 14:59:49.
 * @author WhiteWarrior
 */
public class EntityRegister {
	public static void preInit() {
		ForgeRegistries.ENTITIES.register(new BasicEntityEntry(EntityRat.class, "rat"));
	}

}
