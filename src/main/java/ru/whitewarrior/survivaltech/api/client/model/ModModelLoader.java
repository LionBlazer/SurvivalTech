package ru.whitewarrior.survivaltech.api.client.model;

import net.minecraft.client.resources.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import java.util.HashMap;
import java.util.Map;

public class ModModelLoader implements ICustomModelLoader {
    private static Map<ResourceLocation, IModel> models = new HashMap<>();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        String path = modelLocation.getResourcePath();
        String normalName = path;
        if (normalName.contains("#"))
            normalName = normalName.substring(0, normalName.indexOf("#"));
        else if (normalName.contains("/")) {
            normalName = normalName.substring(normalName.lastIndexOf("/") + 1);
        }
        ResourceLocation resourceLocation = new ResourceLocation(modelLocation.getResourceDomain(), normalName);
        return models.containsKey(resourceLocation);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        String path = modelLocation.getResourcePath();
        String normalName = path;
        if (path.contains("#"))
            normalName = path.substring(0, path.indexOf("#"));
        else if (path.contains("/")) {
            normalName = path.substring(path.lastIndexOf("/" + 1));
        }
        ResourceLocation resourceLocation = new ResourceLocation(modelLocation.getResourceDomain(), normalName);
        IModel model = models.get(resourceLocation);
        return model;
    }

    public static void registerModel(ResourceLocation resourceLocation, IModel model) {
        models.put(resourceLocation, model);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
