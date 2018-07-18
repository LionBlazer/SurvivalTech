package ru.whitewarrior.survivaltech.registry.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Date: 2018-01-26. Time: 15:27:27.
 *
 * @author WhiteWarrior
 */
public class EntityAIdig extends EntityAIBase {

    /**
     * The entity owner of this AITask
     */
    private final EntityLiving grassEaterEntity;
    /**
     * The world the grass eater entity is eating from
     */
    private final World entityWorld;
    /**
     * Number of ticks since the entity started to eat grass
     */
    int eatingGrassTimer;

    public EntityAIdig(EntityLiving grassEaterEntityIn) {
        this.grassEaterEntity = grassEaterEntityIn;
        this.entityWorld = grassEaterEntityIn.world;
        this.setMutexBits(7);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);

        return this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.STONE;

    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.eatingGrassTimer = 10;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte) 10);
        this.grassEaterEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by
     * another one
     */
    public void resetTask() {
        this.eatingGrassTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.eatingGrassTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        System.out.println("updateTask()");
        if (this.eatingGrassTimer == 2) {
            BlockPos blockpos = new BlockPos(this.grassEaterEntity.posX, this.grassEaterEntity.posY, this.grassEaterEntity.posZ);
            BlockPos blockpos1 = blockpos.north();
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (facing != EnumFacing.DOWN)
                    if (!this.entityWorld.isAirBlock(blockpos.offset(facing))) {
                        this.entityWorld.playEvent(2001, blockpos.offset(facing), Block.getIdFromBlock(Blocks.STONE));
                        this.entityWorld.setBlockState(blockpos.offset(facing), Blocks.AIR.getDefaultState(), 2);
                        continue;
                    }

            }
            this.grassEaterEntity.eatGrassBonus();
        }
    }
}