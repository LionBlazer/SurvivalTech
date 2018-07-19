package ru.whitewarrior.survivaltech.handler.common;

import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
		if (event.getWorld().getBlockState(pos).getBlock() == BlockRegister.toolRepairer) {
			Iterable<BlockPos> poss = BlockPos.getAllInBox(pos.down().north().west(), pos.up().east().south());
			for (BlockPos po : poss) {
				if (!po.equals(pos.up()) && !po.equals(pos)) {
					
					if (!getBlock(event.getWorld(), po)) {event.getPlayer().sendMessage(new TextComponentString("Multiblock start broke"));
						return;
					}
				}
				
			}
			poss.forEach(this.poss::add);
			posmain=pos;
			event.getPlayer().sendMessage(new TextComponentString("Multiblock created"));
		}
	}

     @SubscribeEvent
     public void event(PlayerInteractEvent.RightClickBlock event) {
         if(!event.getEntityPlayer().isSneaking()) return;
         if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.SNOW) {
             if(!event.getWorld().isRemote) {
                 IBlockState prevState = Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, 7);
                 event.getWorld().setBlockState(event.getPos(), prevState);
                 event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(Items.SNOWBALL));
             }
             event.setUseItem(Result.DENY);
         } else if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.SNOW_LAYER) {
             if(!event.getWorld().isRemote) {
                 if (event.getWorld().getBlockState(event.getPos()).getValue(BlockSnow.LAYERS) == 1)
                     event.getWorld().destroyBlock(event.getPos(), false);
                 else {
                     int prevValue = event.getWorld().getBlockState(event.getPos()).getValue(BlockSnow.LAYERS);
                     IBlockState state = Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS,  prevValue- 1);
                     event.getWorld().setBlockState(event.getPos(), state);

                 }
                 event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(Items.SNOWBALL));
             }

             event.setCancellationResult(EnumActionResult.SUCCESS);
             event.setCanceled(true);

         }
     }
	private boolean getBlock(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == GameMaterialRegister.copper.getFullBlock();
    }
}
