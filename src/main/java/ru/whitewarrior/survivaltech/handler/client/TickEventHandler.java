package ru.whitewarrior.survivaltech.handler.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TickEventHandler {
    public static long TICKER = -1;

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        TICKER++;
    }
}
