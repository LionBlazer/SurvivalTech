package ru.whitewarrior.survivaltech.handler.client;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.whitewarrior.survivaltech.api.client.model.ModModelLoader;

public class ModelEventHandler{

    @SubscribeEvent
    public void registerModelLoaders(ModelRegistryEvent event){
        ModelLoaderRegistry.registerLoader(new ModModelLoader());
    }


}
