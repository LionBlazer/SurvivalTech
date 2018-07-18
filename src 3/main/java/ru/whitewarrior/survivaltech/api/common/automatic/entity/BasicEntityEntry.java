package ru.whitewarrior.survivaltech.api.common.automatic.entity;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityEntry;

/**
 * Date: 2018-01-26.
 * Time: 15:04:24.
 * @author WhiteWarrior
 */
public class BasicEntityEntry extends EntityEntry{

	public BasicEntityEntry(Class<? extends Entity> cls, String name) {
		super(cls, name);
		.setRegistryName(name);
	}

}
