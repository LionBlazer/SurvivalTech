package ru.whitewarrior.survivaltech.coremod.packet;

import net.minecraftforge.fml.relauncher.Side;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unused")
public @interface NetworkPacket {
    /**
     * @return The side to which this packet will be sent
     */
    Side value();
}
