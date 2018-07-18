package ru.whitewarrior.survivaltech.registry.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import ru.whitewarrior.survivaltech.api.common.network.SimplePacket;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;
import ru.whitewarrior.survivaltech.coremod.packet.NetworkPacket;

@NetworkPacket(Side.SERVER)
public class BlockChangeTileEntityPacket extends SimplePacket {
    public BlockChangeTileEntityPacket(BlockPos blockPos, int fieldIndex, Object value) {
        buf().writeInt(blockPos.getX());
        buf().writeInt(blockPos.getY());
        buf().writeInt(blockPos.getZ());
        buf().writeInt(fieldIndex);

        if (value instanceof Integer) {
            buf().writeShort(0); // id
            buf().writeInt((Integer) value);
        } else if (value instanceof Boolean) {
            buf().writeShort(1); // id
            buf().writeBoolean((Boolean) value);
        } else if (value instanceof Byte) {
            buf().writeShort(2); // id
            buf().writeByte((Byte) value);
        } else if (value instanceof String) {
            buf().writeShort(3); // id
            buf().writeInt(((String) value).getBytes().length);
            buf().writeBytes(((String) value).getBytes());
        } else {
            buf().writeShort(-1);
        }
    }

    @Override
    public void server(EntityPlayerMP player) {
        int blockX = buf().readInt();
        int blockY = buf().readInt();
        int blockZ = buf().readInt();
        int fieldIndex = buf().readInt();
        short id = buf().readShort();
        if (id == -1)
            return;
        Object value = null;

        switch (id) {
            case 0: {
                value = buf().readInt();
                break;
            }
            case 1: {
                value = buf().readBoolean();
                break;
            }
            case 2: {
                value = buf().readByte();
                break;
            }
            case 3: {
                int length = buf().readInt();
                value = new String(buf().readBytes(length).array());
                break;
            }
        }
        if (value == null)
            return;

        if (player.getEntityWorld().getTileEntity(new BlockPos(blockX, blockY, blockZ)) == null || !(player.getEntityWorld().getTileEntity(new BlockPos(blockX, blockY, blockZ)) instanceof TileEntityBlock))
            return;
        TileEntityBlock blockTile = (TileEntityBlock) player.getEntityWorld().getTileEntity(new BlockPos(blockX, blockY, blockZ));

        if (!blockTile.canClientModifyField(id))
            return;
        try {
            blockTile.getClass().getDeclaredFields()[id].set(blockTile, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
