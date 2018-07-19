package ru.whitewarrior.survivaltech.util;

public class StringUtil {
    public static int indexOf(String string, String str, int number) {
        String mainString = string;
        for(int i = 0; i < number -1; i++) {
            mainString = mainString.substring(mainString.indexOf(str)+ str.length());
        }
        return string.substring(0, string.indexOf(mainString)).length()+mainString.indexOf(str);
    }
}
