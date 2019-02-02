package ru.whitewarrior.survivaltech.api.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;
import ru.whitewarrior.survivaltech.util.FileUtil;

import java.io.File;

public class Conf {
    private static Gson defaultGson = new GsonBuilder().setPrettyPrinting().create();

    public static Gson gson() {
        return defaultGson;
    }

    public static File dirConf(String name){
        return new File(Loader.instance().getConfigDir(), name + "/");
    }

    public static <T> String serObj(T obj){
        return gson().toJson(obj);
    }

    public static <T> void serObj(T obj, File file){
        FileUtil.writeFile(file, gson().toJson(obj));
    }

    public static <T> T deserObj(String code, Class<T> clazz){
        return gson().fromJson(code, clazz);
    }

    public static <T> T deserObj(File code, Class<T> clazz){
        return gson().fromJson(FileUtil.getFileContent(code), clazz);
    }
}
