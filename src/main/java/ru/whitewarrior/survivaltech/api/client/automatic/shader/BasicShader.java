package ru.whitewarrior.survivaltech.api.client.automatic.shader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;

/**
 * Date: 2018-01-08. Time: 12:55:39.
 *
 * @author WhiteWarrior
 */
public class BasicShader {
    int id;

    public BasicShader(String code) {
        int shaderID = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        ARBShaderObjects.glShaderSourceARB(shaderID, code);
        ARBShaderObjects.glCompileShaderARB(shaderID);
        int programID = ARBShaderObjects.glCreateProgramObjectARB();
        ARBShaderObjects.glAttachObjectARB(programID, shaderID);
        ARBShaderObjects.glLinkProgramARB(programID);
        id = programID;
    }

    public int getId() {
        return id;
    }

    public void preRender() {
        ARBShaderObjects.glUseProgramObjectARB(id);
    }

    public void postRender() {
        ARBShaderObjects.glUseProgramObjectARB(0);
    }
}
