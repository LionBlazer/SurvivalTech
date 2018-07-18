package ru.whitewarrior.survivaltech.util;

import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class WorldSavedDataUtil {
    @SuppressWarnings("all")
    public static <T extends WorldSavedData> T get(World world, String name, Class<T> clazzSavedData) {
        T instance = (T) world.getMapStorage().getOrLoadData(clazzSavedData, name);

        if (instance == null) {
            try {
                instance = clazzSavedData.getConstructor(String.class).newInstance(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance.markDirty();
            world.getMapStorage().setData(name, instance);
        }
        return instance;
    }
}
