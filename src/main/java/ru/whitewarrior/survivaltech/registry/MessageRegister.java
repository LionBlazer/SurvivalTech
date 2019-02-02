package ru.whitewarrior.survivaltech.registry;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.network.SimplePacket;
import ru.whitewarrior.survivaltech.coremod.packet.NetworkPacket;

import java.lang.annotation.Annotation;

public class MessageRegister {

    private static short id;
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MODID);

    @SuppressWarnings("unchecked")
    public static void preInit(FMLPreInitializationEvent event) {
        for(ASMDataTable.ASMData data : event.getAsmData().getAll(NetworkPacket.class.getName())){
            try {
                Class packetClass = Class.forName(data.getClassName());
                if (!SimplePacket.class.isAssignableFrom(packetClass))
                    throw new ClassCastException("Class must extends SimplePacket!");
                Annotation packet = packetClass.getDeclaredAnnotation(NetworkPacket.class);
                MessageRegister.register(packetClass, ((NetworkPacket)packet).value());
            } catch (Exception exception) {
                 System.err.print("Packet '" + data.getClassName() + "' has error! ("+exception+")\n");
            }
        }
    }

    public static void register(Class<? extends SimplePacket> packet, Side side) {
       // try {
            //NETWORK.registerMessage(packet.newInstance(), packet, id++, side);
       // } catch (InstantiationException | IllegalAccessException e) {
          //  e.printStackTrace();
        //}
    }

}