package ru.whitewarrior.survivaltech.registry;

import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.api.common.automatic.entity.BasicEntityEntry;
import ru.whitewarrior.survivaltech.registry.entity.EntityRat;
import ru.whitewarrior.survivaltech.registry.entity.EntityRocket;
import ru.whitewarrior.survivaltech.registry.render.entity.RenderSpaceship;

/**
 * Date: 2018-01-26.
 * Time: 14:59:49.
 * @author WhiteWarrior
 */
public class EntityRegister {
	public static void preInit() {
		ForgeRegistries.ENTITIES.register(new BasicEntityEntry(EntityRat.class, "rat"));
		ForgeRegistries.ENTITIES.register(new BasicEntityEntry(EntityRocket.class, "spaceship"));

	}

	@SideOnly(Side.CLIENT)
    public static void initClient(){
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityRat.class, new RenderMiner(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityRocket.class, new RenderSpaceship());
    }

    @SideOnly(Side.CLIENT)
    public static class RenderMiner extends RenderBiped<EntityRat>
    {
        private static final ResourceLocation STRAY_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/stray.png");

        public RenderMiner(RenderManager p_i47191_1_)
        {
            super(p_i47191_1_, new ModelZombie(), 0.5F);
            LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
            {
                protected void initArmor()
                {
                    this.modelLeggings = new ModelZombie(0.5F, true);
                    this.modelArmor = new ModelZombie(1.0F, true);
                }
            };
            this.addLayer(layerbipedarmor);
        }

        /**
         * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
         */
        @Override
        protected ResourceLocation getEntityTexture(EntityRat entity)
        {
            return STRAY_SKELETON_TEXTURES;
        }
    }
}
