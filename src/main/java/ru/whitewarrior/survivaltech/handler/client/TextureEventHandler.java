package ru.whitewarrior.survivaltech.handler.client;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.whitewarrior.survivaltech.api.client.model.imodel.OreModel;

/**
 * Date: 2018-01-10.
 * Time: 12:53:03.
 * @author WhiteWarrior
 */
public class TextureEventHandler {
    @SubscribeEvent
    public void event(TextureStitchEvent.Pre event) {
        //event.getMap().registerSprite( new ResourceLocation("blocks/stone"));
       // event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"block/ore/scattered_gold"));

        boolean d = event.getMap().setTextureEntry(OreModel.spriteStone);
        System.out.println("TextureEventHandler.event");
    }

//    BlockPos oldpos;
//
//    @SubscribeEvent
//    public void eventClick(RenderWorldLastEvent event) {
//        Minecraft mc = Minecraft.getMinecraft();
//        if (mc.player.getHeldItemMainhand().getItem() == ItemRegister.battery && RayTraceResultUtil.getRayTraceToBlock(mc.player, event.getPartialTicks(), 5, true) != null && !mc.isGamePaused()) {
//            if (oldpos == null || !oldpos.equals(RayTraceResultUtil.getRayTraceToBlock(mc.player, 1, 5, true).getBlockPos())) {
//                BlockPos pos = RayTraceResultUtil.getRayTraceToBlock(mc.player, 1, 5, true).getBlockPos();
//                oldpos = pos;
//                Block block = mc.world.getBlockState(pos).getBlock();
//
//                RendererHelper.drawBlockTexture(mc.player, mc.getRenderPartialTicks(), pos, mc.world, "double_plant_syringa_top");
//            } else {
//                TextureAtlasSprite[] textureatlassprite = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, Minecraft.getMinecraft().renderGlobal, "destroyBlockIcons");
//
//                RendererHelper.drawBlockTexture(mc.player, mc.getRenderPartialTicks(), oldpos, mc.world, "double_plant_syringa_top");
//            }
//
//        }
//    }

}
