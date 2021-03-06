package ru.whitewarrior.survivaltech.api.jsoncreator;

import org.apache.commons.io.IOUtils;
import ru.whitewarrior.survivaltech.api.jsoncreator.type.JsonTemplateType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Date: 2017-12-25. 
 * Time: 20:16:36.
 * @author WhiteWarrior
 */

public final class JsonCreator {
	private static String CURRENT_DIRECTORY = new File("").getAbsolutePath() + "/../";
	private static JsonTemplateType currentType;
	private static HashMap<String, String> currentTypeReplaceable = new HashMap<String, String>();
	private static String currentFileName;
	private static boolean enable = true;

	public static class Util {

		public static String read(String fileName) {
			try {
				InputStream is = JsonCreator.class.getResourceAsStream(fileName);
				if (is != null) {
					String text = IOUtils.toString(is, StandardCharsets.UTF_8);
					is.close();
					return text;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Not found file";
		}

		public static void write(String text, String fileName) {
			try {
				File file = new File(JsonCreator.CURRENT_DIRECTORY + fileName);
				if(!file.exists()) {
				OutputStream os = new FileOutputStream(file);
				os.write(text.getBytes(), 0, text.length());
				os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static String replace(String text, HashMap<String, String> replaceable) {
			for (Entry<String, String> entry : replaceable.entrySet()) {
				text = text.replaceAll(entry.getKey(), entry.getValue());
			}
			return text;
		}

	}

	public static void set(JsonTemplateType type, String fileName) {
		if(enable) {
			JsonCreator.currentType = type;
			JsonCreator.currentFileName = fileName;
		}
	}

	public static void replace(String regex, String replacement) {
		if(enable) 
			currentTypeReplaceable.put(regex, replacement);
	}

	public static void end() {
		if(enable) {
			Util.write(Util.replace(Util.read(JsonCreator.currentType.getTemplateFileName()), currentTypeReplaceable), currentFileName);
			currentTypeReplaceable.clear();
		}
	}

}
