package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

/**
 * Date: 2017-12-29. Time: 14:25:24.
 * 
 * @author WhiteWarrior
 */
public abstract class TileEntityInventory extends TileEntityBlock implements ISidedInventory {
    private ItemStackInventoryHandler handlerInventory = new ItemStackInventoryHandler(getSlots());

    private SidedInvWrapper[] wrappers = new SidedInvWrapper[EnumFacing.values().length];

    public ItemStackHandler getHandlerInventory() {
        return handlerInventory;
    }

    public TileEntityInventory() {

    }

    protected void setInventorySide(EnumFacing facing){
        wrappers[facing.ordinal()] = new SidedInvWrapper(this, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return wrappers[facing.ordinal()] != null;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) wrappers[facing.ordinal()];
        return super.getCapability(capability, facing);
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

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return handlerInventory.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(handlerInventory.getStacks(), index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(handlerInventory.getStacks(), index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        handlerInventory.setStackInSlot(index, stack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
