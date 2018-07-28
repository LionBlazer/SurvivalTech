package ru.whitewarrior.survivaltech.registry.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.registry.ai.EntityAIdig;

/**
 * Date: 2018-01-26.
 * Time: 15:13:15.
 * @author WhiteWarrior
 */
public class EntityRat extends EntityMob{
	EntityAIdig dig;
	public EntityRat(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 2F);
		this.setHealth(2);
	}

	@Override
	protected void initEntityAI() {
		this.dig = new EntityAIdig(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, dig);
        //this.tasks.addTask(4, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
		super.initEntityAI();
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.43000000417232513D);
	}

}
