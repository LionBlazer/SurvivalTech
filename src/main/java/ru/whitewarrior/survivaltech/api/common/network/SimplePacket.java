package ru.whitewarrior.survivaltech.api.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.registry.MessageRegister;


public class SimplePacket implements IMessage, IMessageHandler<SimplePacket, SimplePacket> {
    private ByteBuf buf;

    @Override
    public final SimplePacket onMessage(SimplePacket sp, MessageContext ctx) {
        if (ctx.side.isServer())
            sp.server(ctx.getServerHandler().player);
        else
            sp.client(clientPlayer());
        return null;
    }

    protected final ByteBuf buf() {
        return buf != null ? buf : (buf = Unpooled.buffer());
    }

    public void client(EntityPlayer player) {
    }

    public void server(EntityPlayerMP player) {
    }

    @Override
    public final void fromBytes(ByteBuf buf) {
        this.buf = buf;
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        if (buf != null)
            buf.writeBytes(this.buf);
    }

    @SideOnly(Side.CLIENT)
    private final EntityPlayer clientPlayer() {
        return Minecraft.getMinecraft().player;
    }


    //send
    public final void sendToAll() {
        getNetwork().sendToAll(this);
    }

    public final void sendTo(EntityPlayer player) {
        getNetwork().sendTo(this, (EntityPlayerMP) player);
    }

    public final void sendToAllAround(NetworkRegistry.TargetPoint targetPoint) {
        getNetwork().sendToAllAround(this, targetPoint);
    }

    public final void sendToAllTracking(NetworkRegistry.TargetPoint targetPoint) {
        getNetwork().sendToAllTracking(this, targetPoint);
    }

    public final void sendToAllTracking(Entity entity) {
        getNetwork().sendToAllTracking(this, entity);
    }

    public final void sendToServer() {
        getNetwork().sendToServer(this);
    }

    protected SimpleNetworkWrapper getNetwork() {
        return MessageRegister.NETWORK;
    }
}