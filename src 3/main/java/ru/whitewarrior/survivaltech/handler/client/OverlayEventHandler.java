package ru.whitewarrior.survivaltech.handler.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;
import ru.whitewarrior.survivaltech.api.client.item.IOverlayItem;
import ru.whitewarrior.survivaltech.helper.RendererHelper;
import ru.whitewarrior.survivaltech.registry.EffectRegister;
import ru.whitewarrior.survivaltech.registry.ItemRegister;
import ru.whitewarrior.survivaltech.registry.ShaderRegister;
import ru.whitewarrior.survivaltech.util.ShaderUtil;

/**
 * Date: 2017-12-29.
 * Time: 11:51:03.
 * @author WhiteWarrior
 */
public class OverlayEventHandler {
	@SubscribeEvent
	public void tooltip(ItemTooltipEvent event) {
		if(!event.getItemStack().isEmpty()) {
			int[] ores =OreDictionary.getOreIDs(event.getItemStack());
			if(ores.length>0) {
				event.getToolTip().add("");
				event.getToolTip().add(ChatFormatting.WHITE+"OreDictionary:");
				for(int i = 0; i<ores.length; i++) {
					event.getToolTip().add(OreDictionary.getOreName(ores[i]));
				}
			}
		}
	}

    @SubscribeEvent
	public void over1(RenderGameOverlayEvent.Pre event){


    }

    @SubscribeEvent
    public void over2(RenderGameOverlayEvent.Post event){
        for(int i = 0; i <= 3; i++)
            if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && Minecraft.getMinecraft().player.inventory.armorInventory.get(i).getItem() instanceof IOverlayItem) {


                double rtime = Math.sin(Minecraft.getMinecraft().world.getTotalWorldTime() / 20.0D);

                if(((IOverlayItem) Minecraft.getMinecraft().player.inventory.armorInventory.get(i).getItem()).getOverlay(Minecraft.getMinecraft().player.inventory.armorInventory.get(i), Minecraft.getMinecraft().player,(byte)i) != null) {
                    GL11.glPushMatrix();
                    GL11.glColor4f(((float)rtime)+0.1f,0.25f-((float)(rtime*0.1f)) ,((float)rtime*0.5f) -0.1f,0.1f);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    RendererHelper.renderOverlay(((IOverlayItem) Minecraft.getMinecraft().player.inventory.armorInventory.get(i).getItem()).getOverlay(Minecraft.getMinecraft().player.inventory.armorInventory.get(i), Minecraft.getMinecraft().player, (byte) i));
                    GL11.glPopMatrix();
                    GL11.glColor4f(1,1,1,1f);

                }


            }
    }
	/*
	@SubscribeEvent
	public void worldrender(RenderPlayerEvent.Pre event) {
      GL11.glRotated(180, 0, 0, 1);
      GL11.glRotated(190, 0, 1, 0);
      if(!event.getEntityPlayer().isSneaking())
    	  GL11.glTranslatef(0, -1.8f, 0);
      else
    	  GL11.glTranslatef(0, -1.55f, 0);
      event.getEntityPlayer().eyeHeight=1.62F;
	}
	
	
	@SubscribeEvent
	public void worldrender(EntityViewRenderEvent.CameraSetup event) {
       event.setRoll(180);
     //  event.setYaw(event.getYaw()-10);
       //event.setPitch(event.getPitch() +100);
      // event.setPitch(0);
	}
	

	
	@SubscribeEvent
	public void worldrender(RenderPlayerEvent.Post event) {
		 GL11.glRotated(180, 0, 0, 1);
		 GL11.glRotated(-190, 0, 1, 0);
		 if(!event.getEntityPlayer().isSneaking())
			 GL11.glTranslatef(0, 1.8f, 0);
		 else
	    	  GL11.glTranslatef(0, 1.55f, 0);
	}
	*/
	
	@SubscribeEvent
	public void potionbllaaaaa(EntityViewRenderEvent.CameraSetup event) {
		if (event.getEntity() instanceof EntityLivingBase && ((EntityLivingBase)event.getEntity()).isPotionActive(EffectRegister.potion)) {
			event.setRoll((float) (Minecraft.getMinecraft().getRenderPartialTicks() * 5));
			event.setYaw((float) (Minecraft.getMinecraft().getRenderPartialTicks() * 5) + event.getYaw());
		}
	}
	
	
	
}
