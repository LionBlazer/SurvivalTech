package ru.whitewarrior.survivaltech.registry.entity;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityRocket extends EntityMob {
    public EntityRocket(World worldIn) {
        super(worldIn);
        this.setSize(1F, 5F);
        setPosition( posX,posY,posZ);
    }



    public EntityRocket(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }



}
