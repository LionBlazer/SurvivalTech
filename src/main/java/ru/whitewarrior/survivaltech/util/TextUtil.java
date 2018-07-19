package ru.whitewarrior.survivaltech.util;

/**
 * Date: 2017-12-29.
 * Time: 11:15:36.
 * @author WhiteWarrior
 */
public class TextUtil {
	public static String firstUpperCase(String word){
		if(word == null || word.isEmpty()) return word;
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}
}
