package ru.whitewarrior.survivaltech.registry.item;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.block.multiblock.MultiBlock;
import ru.whitewarrior.survivaltech.api.common.item.IWrench;
import ru.whitewarrior.survivaltech.api.jsoncreator.PatternLoader;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;
import ru.whitewarrior.survivaltech.registry.MaterialRegister;
import ru.whitewarrior.survivaltech.util.RayTraceResultUtil;

import java.util.HashSet;

/**
 * Date: 2018-01-25.
 * Time: 15:11:38.
 * @author WhiteWarrior
 */
public class ItemToolWrench extends ItemTool implements IWrench{
public static final HashSet set = new HashSet();
	public ItemToolWrench(String name) {
		super(MaterialRegister.WRENCH, set);
		PatternLoader.createItemHandheld(name, Constants.MODID, "item/tool/"+name);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabRegister.TOOL);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        return 1600000;
    }

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!worldIn.isRemote) {
			
			RayTraceResult rtr = RayTraceResultUtil.getRayTraceToBlock(entityLiving, 1, 5f, true);
			if(rtr != null && rtr.typeOfHit ==Type.BLOCK ) {
				Block block = worldIn.getBlockState(rtr.getBlockPos()).getBlock();
				MultiBlock isvalid = null;
				for(MultiBlock multiblock : AdvancedRegistry.multiblocks) {
					if(multiblock.getUniqueblocks().contains(block)) {
						isvalid=multiblock;
						continue;
					}
				}
				
				if(isvalid==null)
					return;
				BlockPos posblockclick = rtr.getBlockPos();
				for(Block[][] blockm : isvalid.getBlockList()) {
					for(int i =0; i < blockm.length; i++) {
						for(int i2 =0; i2 < blockm[i].length; i2++) {
						//	((EntityPlayer)entityLiving).inventory.addItemStackToInventory(new ItemStack(blockm[i][i2]));
							if(worldIn.getBlockState(posblockclick).getBlock() == blockm[i][i2]){
								entityLiving.sendMessage(new TextComponentString("Found "+new ItemStack(blockm[i][i2]).getDisplayName()+" in POS: x="+(posblockclick.getX())+", y="+(posblockclick.getY())+", z="+(posblockclick.getZ())));
								
								//worldIn.setBlockState(posblockclick, blockm[i][i2].getDefaultState());
							}
							else {
								return;
							}
							posblockclick = new BlockPos(posblockclick.getX()+1, posblockclick.getY(), posblockclick.getZ());
						}
						posblockclick = new BlockPos(rtr.getBlockPos().getX(), posblockclick.getY(), posblockclick.getZ()+1);
					}
					posblockclick = new BlockPos(rtr.getBlockPos().getX(), posblockclick.getY()+1, rtr.getBlockPos().getZ());
				}
				
				entityLiving.sendMessage(new TextComponentString("created:"+isvalid));
				
				
				//((EntityPlayer)entityLiving).sendMessage(new TextComponentString("-------"));
				//((EntityPlayer)entityLiving).sendMessage(new TextComponentString(worldIn.getBlockState(rtr.getBlockPos()).getBlock()+""));
				//((EntityPlayer)entityLiving).sendMessage(new TextComponentString("Time left: "+(this.getMaxItemUseDuration(stack)- timeLeft)+""));
			}
		}
	}
	/*
	 
	 @Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!worldIn.isRemote) {
			
			RayTraceResult rtr = RayTraceResultUtil.getRayTraceToBlock(entityLiving, 1, 5f, true);
			if(rtr != null && rtr.typeOfHit ==Type.BLOCK ) {
				Block block = worldIn.getBlockState(rtr.getBlockPos()).getBlock();
				MultiBlock isvalid = null;
				for(MultiBlock multiblock : AdvancedRegistry.multiblocks) {
					if(multiblock.getUniqueblocks().contains(block)) {
						isvalid=multiblock;
						continue;
					}
				}
				
				if(isvalid==null)
					return;
				BlockPos posblockclick = rtr.getBlockPos();
				BlockPos posblockclick2 = rtr.getBlockPos();
				boolean isDone = true;
				int index =0;
				boolean issuperdone=false;
				while(index < 5) {
					if(index > -1) {
						posblockclick=posblockclick.offset(EnumFacing.values()[index], 2);
						posblockclick2=posblockclick2.offset(EnumFacing.values()[index], 2);
					}
					for(Block[][] blockm : isvalid.getBlockList()) {
						for(int i =0; i < blockm.length; i++) {
							for(int i2 =0; i2 < blockm[i].length; i2++) {
							//	((EntityPlayer)entityLiving).inventory.addItemStackToInventory(new ItemStack(blockm[i][i2]));
								if(worldIn.getBlockState(posblockclick).getBlock() == blockm[i][i2]){
									((EntityPlayer)entityLiving).sendMessage(new TextComponentString("Found "+new ItemStack(blockm[i][i2]).getDisplayName()+" in POS: x="+(posblockclick.getX())+", y="+(posblockclick.getY())+", z="+(posblockclick.getZ())));
									//worldIn.destroyBlock(posblockclick, true);
									//worldIn.setBlockState(posblockclick, blockm[i][i2].getDefaultState());
								}
								else {
									issuperdone=false;
									isDone=false;
									continue;
								}
								if(!isDone)
									continue;
								posblockclick = new BlockPos(posblockclick.getX()+1, posblockclick.getY(), posblockclick.getZ());
							}
							if(!isDone)
								continue;
							posblockclick = new BlockPos(posblockclick2.getX(), posblockclick.getY(), posblockclick.getZ()+1);
						}
						if(!isDone)
							continue;
						posblockclick = new BlockPos(posblockclick2.getX(), posblockclick.getY()+1, posblockclick2.getZ());
					}
					if(index>-1) {
						posblockclick=posblockclick.offset(EnumFacing.values()[index].getOpposite(), 2);
						posblockclick2=posblockclick2.offset(EnumFacing.values()[index].getOpposite(), 2);
					}
					if(isDone==true) {
						issuperdone=true;
						continue;
					}
					else {
						isDone=true;
					}
					index++;
				}
				if(issuperdone)
					((EntityPlayer)entityLiving).sendMessage(new TextComponentString("created:"+isvalid));
				
				
				//((EntityPlayer)entityLiving).sendMessage(new TextComponentString("-------"));
				//((EntityPlayer)entityLiving).sendMessage(new TextComponentString(worldIn.getBlockState(rtr.getBlockPos()).getBlock()+""));
				//((EntityPlayer)entityLiving).sendMessage(new TextComponentString("Time left: "+(this.getMaxItemUseDuration(stack)- timeLeft)+""));
			}
		}
	}
	 
	 */
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
	}

}
