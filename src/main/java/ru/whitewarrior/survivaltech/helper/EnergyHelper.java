package ru.whitewarrior.survivaltech.helper;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Date: 2017-12-29.
 * Time: 21:39:47.
 * @author WhiteWarrior
 */
public class EnergyHelper {
	@SideOnly(Side.CLIENT)
	public static ChatFormatting getColorForEnergy(double currentEnergy, double maxEnergy){
		if(maxEnergy != 0)
			if(currentEnergy* 100/maxEnergy >= 75)
				return ChatFormatting.GREEN;
			else if(currentEnergy* 100/maxEnergy >= 50)
				return ChatFormatting.DARK_GREEN;
			else if(currentEnergy* 100/maxEnergy>= 25)
				return ChatFormatting.RED;
			else
				return ChatFormatting.DARK_RED;
		return ChatFormatting.DARK_AQUA;
	}
}
