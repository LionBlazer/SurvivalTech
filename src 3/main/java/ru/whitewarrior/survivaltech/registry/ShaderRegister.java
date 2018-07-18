package ru.whitewarrior.survivaltech.registry;

import ru.whitewarrior.survivaltech.api.client.automatic.shader.BasicShader;

/**
 * Date: 2018-01-04. Time: 17:26:50.
 * 
 * @author WhiteWarrior
 */
public class ShaderRegister {
	public static BasicShader iridescent_opaqueAll;
	public static BasicShader iridescent_transparentAll;
	public static BasicShader iridescent_transparent_opaqueTexture;
	public static void register() {
		iridescent_opaqueAll = new BasicShader("uniform float time;\r\n" + 
				"uniform sampler2D texture;\r\n" + 
				"void main()\r\n" + 
				"{	\r\n" + 
				"		vec2 pos = gl_TexCoord[0].xy;\r\n" + 
				"		vec4 color = texture2D(texture, pos);\r\n" + 
				"		if(color.a <= 0)discard;gl_FragColor = vec4(color.x*sin(time),color.y+0.1*sin(time), color.z+0.5*sin(time), color.a);\r\n" + 
				"}\r\n" + 
				"");
		iridescent_transparentAll = new BasicShader("uniform float time;\r\n" +
				"uniform sampler2D texture;\r\n" + 
				"void main()\r\n" + 
				"{	\r\n" + 
				"		vec2 pos = gl_TexCoord[0].xy;\r\n" + 
				"		vec4 color = texture2D(texture, pos);\r\n" + 
				"		gl_FragColor = vec4(color.x*sin(time),color.y+0.1*sin(time), color.z+0.5*sin(time), color.a);\r\n" +
				"}\r\n" + 
				"");
		iridescent_transparent_opaqueTexture = new BasicShader("uniform float time;\r\n" + 
				"uniform sampler2D texture;\r\n" + 
				"void main()\r\n" + 
				"{	\r\n" + 
				"		vec2 pos = gl_TexCoord[0].xy;\r\n" + 
				"		vec4 color = texture2D(texture, pos);\r\n" + 
				"		if(color.a <= 0)discard;gl_FragColor = vec4(color.x*sin(time),color.y+0.1*sin(time), color.z+0.5*sin(time), 0.5);\r\n" + 
				"}\r\n" + 
				"");
	}
}
	