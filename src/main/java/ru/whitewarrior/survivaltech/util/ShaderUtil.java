package ru.whitewarrior.survivaltech.util;

import net.minecraft.client.*;
import org.lwjgl.opengl.ARBShaderObjects;

/**
 * Date: 2018-01-08.
 * Time: 13:02:54.
 *
 * @author WhiteWarrior
 */
public class ShaderUtil {
    public static void useTime(int id) {
        int timeID = ARBShaderObjects.glGetUniformLocationARB(id, "time");
        ARBShaderObjects.glUniform1fARB(timeID, Minecraft.getMinecraft().world.getTotalWorldTime() / 20.0F);
    }

}
