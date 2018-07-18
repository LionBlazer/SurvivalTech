package ru.whitewarrior.survivaltech.handler.client;

import net.minecraft.block.Block;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.api.client.render.model.AdvancedModelLoader;
import ru.whitewarrior.survivaltech.helper.RendererHelper;
import ru.whitewarrior.survivaltech.registry.ItemRegister;
import ru.whitewarrior.survivaltech.util.RayTraceResultUtil;

/**
 * Date: 2018-01-10.
 * Time: 12:53:03.
 * @author WhiteWarrior
 */
public class TextureEventHandler {
	BlockPos oldpos;
	@SubscribeEvent
	public void eventClick(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.player.getHeldItemMainhand().getItem() == ItemRegister.battery && RayTraceResultUtil.getRayTraceToBlock(mc.player, event.getPartialTicks(), 5, true) != null && !mc.isGamePaused()) {
			if(oldpos == null || !oldpos.equals(RayTraceResultUtil.getRayTraceToBlock(mc.player, 1, 5, true).getBlockPos())) {
				BlockPos pos = RayTraceResultUtil.getRayTraceToBlock(mc.player, 1, 5, true).getBlockPos();
				oldpos=pos;
				Block block = mc.world.getBlockState(pos).getBlock();
				
				RendererHelper.drawBlockTexture(mc.player, mc.getRenderPartialTicks(), pos, mc.world, "double_plant_syringa_top");
			}
			else {
				TextureAtlasSprite[] textureatlassprite = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, Minecraft.getMinecraft().renderGlobal, "destroyBlockIcons");
				
				RendererHelper.drawBlockTexture(mc.player, mc.getRenderPartialTicks(), oldpos, mc.world, "double_plant_syringa_top");
			}
				
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void modelRegistry(ModelRegistryEvent event) {
		ModelLoaderRegistry.registerLoader(new AdvancedModelLoader());
		
	}

}
