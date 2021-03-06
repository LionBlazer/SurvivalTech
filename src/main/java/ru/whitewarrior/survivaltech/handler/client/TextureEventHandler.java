package ru.whitewarrior.survivaltech.handler.client;

import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2018-01-10.
 * Time: 12:53:03.
 * @author WhiteWarrior
 */
public class TextureEventHandler {
    private static List<TextureAtlasSprite> textureAtlasSprites = new ArrayList<>();
    @SubscribeEvent
    public void event(TextureStitchEvent.Pre event) {
        for (TextureAtlasSprite sprite : textureAtlasSprites)
            event.getMap().setTextureEntry(sprite);
    }

    public static void registerSprite(TextureAtlasSprite textureAtlasSprite){
        textureAtlasSprites.add(textureAtlasSprite);
    }

}
