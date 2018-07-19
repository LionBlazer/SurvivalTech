package ru.whitewarrior.survivaltech.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * Date: 2018-01-10.
 * Time: 13:04:12.
 * @author WhiteWarrior
 */
public class RayTraceResultUtil {
	public static RayTraceResult getRayTraceToBlock(EntityLivingBase entity_base, float partialTicks, double dist, boolean interact) {
		Vec3d vec3 = new Vec3d(entity_base.posX, entity_base.posY + entity_base.getEyeHeight(), entity_base.posZ);
		Vec3d vec31 = entity_base.getLook(partialTicks);
		Vec3d vec32 = vec3.addVector(vec31.x * dist, vec31.y * dist, vec31.z * dist);
	    RayTraceResult mop = entity_base.world.rayTraceBlocks(vec3, vec32, interact);
	    return mop;
	}
}
