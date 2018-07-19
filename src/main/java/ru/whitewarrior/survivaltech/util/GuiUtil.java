package ru.whitewarrior.survivaltech.util;

import net.minecraft.client.gui.inventory.*;

/**
 * Date: 2017-12-29.
 * Time: 19:52:22.
 * @author WhiteWarrior
 */
public class GuiUtil {
	public static int[] getCoords(GuiContainer gui) {
		int x = (gui.width - gui.getXSize()) / 2;
        int y = (gui.height - gui.getYSize()) / 2;
        int[] coords=new int[2];
        coords[0]=x;
        coords[1]=y;
		return coords;
		
	}
	
    public static int getScaledTextures(double energy, double maxEnergy, int width){
		return (int) (maxEnergy != 0 && energy != 0 ? energy * (width /2)/ maxEnergy : 0);
    }
}
