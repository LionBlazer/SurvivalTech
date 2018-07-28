package ru.whitewarrior.survivaltech.registry.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.world.World;

/**
 * Date: 2018-01-26. Time: 15:27:27.
 * 
 * @author WhiteWarrior
 */
public class EntityAIdig extends EntityAIBase {
	
	/** The entity owner of this AITask */
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

		return true;

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.eatingGrassTimer = 10;
		this.entityWorld.setEntityState(this.grassEaterEntity, (byte) 10);
		this.grassEaterEntity.getNavigator().clearPath();
        grassEaterEntity.getNavigator().setPath(new Path(new PathPoint[]{new PathPoint(0,100,0)}), 1);
       // grassEaterEntity.getNavigator().
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
		return false;
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

	}
}