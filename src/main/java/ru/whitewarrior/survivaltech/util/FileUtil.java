package ru.whitewarrior.survivaltech.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    public static String getFileContent(InputStream fis) {
        try {
            StringBuilder sb = new StringBuilder();
            Reader r = new InputStreamReader(fis, StandardCharsets.UTF_8);
            char[] buf = new char[1024];
            int amt = r.read(buf);
            while (amt > 0) {
                sb.append(buf, 0, amt);
                amt = r.read(buf);
            }
            r.close();
            fis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void writeFile(File file, String context) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            fileOutputStream.write(context.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getFileContent(File file) {
        try {
            return getFileContent(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
