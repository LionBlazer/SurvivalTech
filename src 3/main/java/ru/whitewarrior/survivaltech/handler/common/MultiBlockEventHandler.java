package ru.whitewarrior.survivaltech.handler.common;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.whitewarrior.survivaltech.api.common.item.IWrench;
import ru.whitewarrior.survivaltech.registry.BlockRegister;
import ru.whitewarrior.survivaltech.registry.GameMaterialRegister;

import java.util.ArrayList;

/**
 * Date: 2018-01-14. Time: 14:15:12.
 * 
 * @author WhiteWarrior
 */
public class MultiBlockEventHandler
 {
	BlockPos posmain;
	ArrayList<BlockPos> poss = new ArrayList<BlockPos>();
	@SubscribeEvent
	public void event(BlockEvent.PlaceEvent event) {
		BlockPos pos = event.getPos();
		if (event.getWorld().getBlockState(pos).getBlock() == BlockRegister.toolrepairer) {	
			Iterable<BlockPos> poss = BlockPos.getAllInBox(pos.down().north().west(), pos.up().east().south());
			for (BlockPos po : poss) {
				if (!po.equals(pos.up()) && !po.equals(pos)) {
					
					if (!getBlock(event.getWorld(), po)) {event.getPlayer().sendMessage(new TextComponentString("Multiblock start broke"));
						return;
					}
				}
				
			}
			poss.forEach(.poss::add);
			posmain=pos;
			event.getPlayer().sendMessage(new TextComponentString("Multiblock created"));
		}
	}
	
	@SubscribeEvent
	public void event(PlayerInteractEvent.RightClickBlock event) {
		
		if(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof IWrench) {
		event.setUseBlock(Result.DENY);
		event.setResult(Result.DENY);
		event.setUseItem(Result.DENY);


		}
	}
	private boolean getBlock(IBlockAccess world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock() == GameMaterialRegister.copper.getFullBlock())
			return true;
		return false;
	}
}
