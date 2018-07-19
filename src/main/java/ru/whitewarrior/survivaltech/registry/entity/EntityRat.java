package ru.whitewarrior.survivaltech.registry.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWander;
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
		this.setSize(0.4F, 0.3F);
		this.setHealth(2);
	}

	@Override
	protected void initEntityAI() {
		this.dig = new EntityAIdig(this);
		
		this.tasks.addTask(0, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(1, dig);
		super.initEntityAI();
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	}

}
