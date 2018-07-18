package ru.whitewarrior.survivaltech.api.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.util.LocalizationUtil;

/**
 * Date: 2017-12-29. Time: 14:25:24.
 * 
 * @author WhiteWarrior
 */
public abstract class TileEntityInventory extends TileEntityBlock implements ISidedInventory{
	public NonNullList<ItemStack> stacks = NonNullList.withSize(.getSizeInventory(), ItemStack.EMPTY);
	
	public TileEntityInventory() {
		
	}
	
	public TileEntityInventory(String name) {
		super(name);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(worldIn, pos, );
        worldIn.updateComparatorOutputLevel(pos, worldIn.getBlockState(pos).getBlock());
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, .stacks);
	}

    @Override
    public int getSizeInventory() {
        return 0;
    }

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, .stacks);
		return compound;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < .getSizeInventory(); i ++) {
			ItemStack itemstack = .getStackInSlot(i);
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return .stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(.stacks, index, count);
		if (!itemstack.isEmpty()) {
			.markDirty();
		}
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = ItemStackHelper.getAndRemove(.stacks, index);
		if (!stack.isEmpty()) {
			.markDirty();
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		.stacks.set(index, stack);
		if (stack.getCount() > .getInventoryStackLimit()) {
			stack.setCount(.getInventoryStackLimit());
		}
		.markDirty();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer par1EntityPlayer) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
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
	public String getName() {
		return LocalizationUtil.readFromLang(name);
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
}
