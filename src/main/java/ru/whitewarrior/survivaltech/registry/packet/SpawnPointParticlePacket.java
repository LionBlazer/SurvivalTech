package ru.whitewarrior.survivaltech.registry.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.relauncher.Side;
import ru.whitewarrior.survivaltech.api.common.network.SimplePacket;
import ru.whitewarrior.survivaltech.coremod.packet.NetworkPacket;

@NetworkPacket(Side.CLIENT)
public class SpawnPointParticlePacket extends SimplePacket {
    public SpawnPointParticlePacket(int count, EnumParticleTypes particleType, float x, float y, float z, float xSpeed, float ySpeed, float zSpeed) {
        buf().writeInt(count);
        buf().writeInt(particleType.ordinal());

        buf().writeFloat(x);
        buf().writeFloat(y);
        buf().writeFloat(z);

        buf().writeFloat(xSpeed);
        buf().writeFloat(ySpeed);
        buf().writeFloat(zSpeed);
    }

    public SpawnPointParticlePacket(EnumParticleTypes particleType, float x, float y, float z, float xSpeed, float ySpeed, float zSpeed) {
        this(1, particleType, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public void client(EntityPlayer player) {
        int count = buf().readInt();
        int particleOriginalType = buf().readInt();

        float x = buf().readFloat();
        float y = buf().readFloat();
        float z = buf().readFloat();

        float xSpeed = buf().readFloat();
        float ySpeed = buf().readFloat();
        float zSpeed = buf().readFloat();
        player.world.spawnParticle(EnumParticleTypes.values()[particleOriginalType], x, y, z, xSpeed, ySpeed, zSpeed);
        for (int i = 0; i < count - 1; i++) {
            player.world.spawnParticle(EnumParticleTypes.values()[particleOriginalType], x, y, z, xSpeed + Math.random() / 5f, ySpeed + Math.random() / 5f, zSpeed + Math.random() / 5f);
        }
    }
}
