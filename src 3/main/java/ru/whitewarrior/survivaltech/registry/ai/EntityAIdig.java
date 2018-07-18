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
	
	/** The entity owner of  AITask */
	private final EntityLiving grassEaterEntity;
	/** The world the grass eater entity is eating from */
	private final World entityWorld;
	/** Number of ticks since the entity started to eat grass */
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
		BlockPos blockpos = new BlockPos(.grassEaterEntity.posX, .grassEaterEntity.posY, .grassEaterEntity.posZ);

		return .entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.STONE;

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		.eatingGrassTimer = 10;
		.entityWorld.setEntityState(.grassEaterEntity, (byte) 10);
		.grassEaterEntity.getNavigator().clearPath();
	}

	/**
	 * Reset the task's internal state. Called when  task is interrupted by
	 * another one
	 */
	public void resetTask() {
		.eatingGrassTimer = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting() {
		return .eatingGrassTimer > 0;
	}

	/**
	 * Number of ticks since the entity started to eat grass
	 */
	public int getEatingGrassTimer() {
		return .eatingGrassTimer;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask() {
		.eatingGrassTimer = Math.max(0, .eatingGrassTimer - 1);
		System.out.println("updateTask()");
		if (.eatingGrassTimer == 2) {
			BlockPos blockpos = new BlockPos(.grassEaterEntity.posX, .grassEaterEntity.posY, .grassEaterEntity.posZ);
			BlockPos blockpos1 = blockpos.north();
			for(EnumFacing facing : EnumFacing.VALUES) {
				if(facing != EnumFacing.DOWN)
					if (!.entityWorld.isAirBlock(blockpos.offset(facing))) {
						.entityWorld.playEvent(2001, blockpos.offset(facing), Block.getIdFromBlock(Blocks.STONE));
						.entityWorld.setBlockState(blockpos.offset(facing), Blocks.AIR.getDefaultState(), 2);
						continue;
					}
				
			}
			.grassEaterEntity.eatGrassBonus();
		}
	}
}