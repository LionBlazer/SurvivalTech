package ru.whitewarrior.survivaltech.registry.tileentity.fluidtank;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;

import java.util.ArrayList;

/**
 * Date: 2018-03-24. Time: 22:15:11.
 * 
 * @author WhiteWarrior
 */
public class TileEntityFluidTank extends TileEntityBlock {
private ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();
public int allammount;

	public ArrayList<FluidStack> getFluidStacks() {
		return fluids;
	}
	

	public TileEntityFluidTank() {
		super("TileEntityFluidTank");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		int i = 0;
		for(FluidStack fl : fluids) {
			compound.setTag("fluid"+i, fl.writeToNBT(new NBTTagCompound()));
			i++;
		}
		compound.setInteger("keys", i);
		compound.setInteger("allammount", allammount);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		fluids.clear();
		for(int i = 0; i < compound.getInteger("keys"); i++) {
			fluids.add(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluid"+i)));
		}
		allammount= compound.getInteger("allammount");
		super.readFromNBT(compound);
	}

	@Override
	public void onBlockActivated(IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote && playerIn.getHeldItem(hand) != ItemStack.EMPTY && FluidUtil.getFluidContained(playerIn.getHeldItem(hand)) != null){
			FluidStack stackf = (FluidUtil.getFluidContained(playerIn.getHeldItem(hand)));
			if(stackf.getFluid().getTemperature() >= 800) {
				playerIn.sendStatusMessage(new TextComponentTranslation("tile.cannotfilltank.bytemperature"), true);
				return;
			}
			System.out.println(stackf.getFluid().getTemperature()+"tx");
			IFluidHandlerItem fhi = (FluidUtil.getFluidHandler(playerIn.getHeldItem(hand)));
			if(allammount + stackf.amount > 10_000)
				return;
			
			int id = -1;
			for(int i = 0; i < fluids.size(); i++) {
				if(fluids.get(i).isFluidEqual(stackf) ) {
					id = i;
					break;
				}
			}
			System.out.println("id:"+id);
			System.out.println(this.getFluidStacks());
			if(id == -1) {
				int drain = Math.min(stackf.amount, 10_000);
				if(fhi.drain(drain, !playerIn.isCreative()) != null) {
					playerIn.setHeldItem(hand, fhi.getContainer());
					fluids.add(new FluidStack(stackf.getFluid(), drain));
				}
				allammount+=drain;
			}else {
				int drain = Math.min(stackf.amount, 10_000 - fluids.get(id).amount);
				if(fhi.drain(drain, !playerIn.isCreative()) != null) {
					playerIn.setHeldItem(hand, fhi.getContainer());
					fluids.get(id).amount+=drain;
				}
				allammount+=drain;
			}
			
			System.out.println(allammount);
			updateVars();
		}
		else if(!world.isRemote && playerIn.getHeldItem(hand) != ItemStack.EMPTY && FluidUtil.getFluidHandler(playerIn.getHeldItem(hand)) != null && FluidUtil.getFluidContained(playerIn.getHeldItem(hand)) == null){
			IFluidHandlerItem fhi = (FluidUtil.getFluidHandler(playerIn.getHeldItem(hand)));
			/*
			if (fluid == null) {
				return;
			}
			int fill = fhi.fill(fluid, true);
			fluid.amount -= fill;
			playerIn.setHeldItem(hand, fhi.getContainer());
			System.out.println(fluid.amount);
			updateVars();
			*/
		}
		
	}

}
