package ru.whitewarrior.survivaltech.util;

import net.minecraft.util.text.translation.I18n;

/**
 * Date: 2017-12-23.
 * Time: 17:14:45.
 *
 * @author WhiteWarrior
 */
public class LocalizationUtil {
    public static String readFromLang(String word) {
        return I18n.translateToLocal(word);
    }
}
