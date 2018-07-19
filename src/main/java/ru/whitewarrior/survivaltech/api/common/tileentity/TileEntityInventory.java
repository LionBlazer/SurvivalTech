package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Date: 2017-12-29. Time: 14:25:24.
 * 
 * @author WhiteWarrior
 */
public abstract class TileEntityInventory extends TileEntityBlock {
    private ItemStackHandler handlerInventory = new ItemStackHandler(getSlots());

    public ItemStackHandler getHandlerInventory() {
        return handlerInventory;
    }

    public TileEntityInventory() {

    }



    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)handlerInventory : super.getCapability(capability, facing);
    }

    public TileEntityInventory(String name) {
		super(name);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        for (int i = 0; i < getSlots(); ++i)
        {
            ItemStack itemstack = getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }
        worldIn.updateComparatorOutputLevel(pos, worldIn.getBlockState(pos).getBlock());
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
        handlerInventory.deserializeNBT(compound.getCompoundTag("inv"));
	}


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        compound.setTag("inv", handlerInventory.serializeNBT());
		return compound;
	}


    public abstract int getSlots();


    public ItemStack getStackInSlot(int slot){
        return handlerInventory.getStackInSlot(slot);
    }

    public void setStackInSlot(int slot, ItemStack stack){

        handlerInventory.setStackInSlot(slot, stack);

    }

    public String getName() {
	    return name;
    }

    public boolean isUsableByPlayer(EntityPlayer playerIn){
        return true;
    }
}
