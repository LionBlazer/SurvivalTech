package ru.whitewarrior.survivaltech.api.common.automatic.potion;

import net.minecraft.potion.Potion;

/**
 * Date: 2018-01-09. Time: 13:25:14.
 * 
 * @author WhiteWarrior
 */
public class BasicPotion extends Potion {

	public BasicPotion(boolean isBadEffectIn, int liquidColorIn, String name) {
		super(isBadEffectIn, liquidColorIn);
        this.setPotionName(name);
        this.setRegistryName(name);
	}

	@Override
	public boolean hasStatusIcon() {
		// TODO Auto-generated method stub
		return false;
	}
}
