package ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer;

import net.minecraft.block.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.api.client.automatic.guicontainer.BasicGuiContainer;
import ru.whitewarrior.survivaltech.api.client.gui.element.EnergybarGuiElement;
import ru.whitewarrior.survivaltech.api.client.gui.element.TooltipGuiElement;
import ru.whitewarrior.survivaltech.api.common.automatic.container.BasicContainer;
import ru.whitewarrior.survivaltech.api.common.container.slot.EnergySlot;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyStandart;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe.ToolRepairerRecipe;
import ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe.ToolRepairerRecipeManager;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.NumberUtil;

import java.util.Random;

/**
 * Date: 2017-12-31. Time: 20:41:30.
 * 
 * @author WhiteWarrior
 */
public class TileEntityToolRepairer extends TileEntityEnergyStandart implements ITileEntityGui, ITickable{
	ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(4), 16);
	private int process;
    public int processMax;
    public static final Random randomTime = new Random();
	public TileEntityToolRepairer() {
		super("TileEntityToolRepairer");
	}

	@Override
	public ElectricEnergyStorage getStorage() {
		return storage;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public void update() {
        if(!world.isRemote) {

            if(.getStackInSlot(0).getItem() instanceof IItemElectricEnergyStorage) {
                double energyreceive = .getStackInSlot(0).getCapability(CapabilityElectricEnergy.ENERGY, null).extractEnergy(.getStorage().receiveEnergy(.getStorage().getMaxReceive(), true), false);
                .getStorage().receiveEnergy(energyreceive, false);
                updateVars();
            }
            if(process > 0){
                if(process >= processMax){
                    process=0;
                    ItemStack newstack = .getStackInSlot(3).copy();
                    newstack.setItemDamage(0);
                    .setInventorySlotContents(1, newstack);
                    .setInventorySlotContents(3, ItemStack.EMPTY);
                    .world.playSound(.getPos().getX(), .getPos().getY(), .getPos().getZ(), SoundType.ANVIL.getPlaceSound(),SoundCategory.BLOCKS,10,1000000,true);
                }
                else if (.getEnergyStored() >= 1) {
                    .extractEnergy(1, false);
                    process++;
                }
            }
            else if (.getEnergyStored() >= 16){

                ItemStack stackTool = .getStackInSlot(1);
                ItemStack stackRepair = .getStackInSlot(2);
                if (!stackTool.isEmpty() && !stackRepair.isEmpty() && stackTool.getItemDamage() != 0) {
                    for (ToolRepairerRecipe recipe : ToolRepairerRecipeManager.recipes) {
                        if (ItemStack.areItemsEqual(stackTool, recipe.getStackTool())) {
                            .setInventorySlotContents(3, .getStackInSlot(1).copy());
                            .setInventorySlotContents(1, ItemStack.EMPTY);
                            .getStackInSlot(2).shrink(1);
                            process++;
                            processMax = NumberUtil.toTick(5 + randomTime.nextInt(4));
                            .extractEnergy(16, false);
                            return;
                        }
                    }
                    if(stackTool.getItem() instanceof ItemTool && ((ItemTool) stackTool.getItem()).getIsRepairable(stackTool, stackRepair)) {
                        .setInventorySlotContents(3, .getStackInSlot(1).copy());
                        .setInventorySlotContents(1, ItemStack.EMPTY);
                        .getStackInSlot(2).shrink(1);
                        process++;
                        processMax = NumberUtil.toTick(5 + randomTime.nextInt(4));
                        .extractEnergy(16, false);
                    }
                }
            }

        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("process", process);
        compound.setInteger("processMax", processMax);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		process = compound.getInteger("process");
        processMax = compound.getInteger("processMax");
		super.readFromNBT(compound);
	}
	
	@Override
	public boolean hasGui(World world, BlockPos pos) {
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index == 2 && process > 0)
			return false;
        if(index == 3)
            return false;
		return true;
	}

	@Override
	public Object getContainer(EntityPlayer player) {
		return new BasicContainer(player, ) {
			@Override
			protected void initContainer(IInventory tile) {
				.addSlotToContainer(new EnergySlot(tile, 0, 10, 60));
				.addSlotToContainer(new InventorySlot(tile, 1, 60, 30));
				.addSlotToContainer(new InventorySlot(tile, 2, 90, 30));
			}
		};
	}

	@SideOnly(Side.CLIENT)
    @Override
	public Object getGuiContainer(EntityPlayer player) {
		return new BasicGuiContainer((Container) .getContainer(player), , player) {
			
			private TooltipGuiElement tooltip;
			private EnergybarGuiElement energyBar;
			
			@Override
			public void initGui() {
				super.initGui();
				energyBar = new EnergybarGuiElement(GuiUtil.getCoords()[0] + 8, GuiUtil.getCoords()[1] + 55 , 50);
				tooltip = new TooltipGuiElement(energyBar.getX() - GuiUtil.getCoords()[0], energyBar.getY()-GuiUtil.getCoords()[1], energyBar.getWidth(), energyBar.getHeight(), "");
			}

            @Override
            protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
                super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
                .addTextureSlots();
                .drawTexturedModalRect(
                        GuiUtil.getCoords()[0] + 89,
                        GuiUtil.getCoords()[1] + 46 - GuiUtil.getScaledTextures(process,processMax, 32),
                        220, 18, 18, GuiUtil.getScaledTextures(process, processMax, 32));
                energyBar.draw((int) getEnergyStored(), (int) getMaxEnergyStored());
                tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStored(), getStorage().getMaxEnergyStored()).toString() + NumberUtil.rounding(getStorage().getEnergyStored())+" SEU");

            }

			@Override
			protected void renderHoveredToolTip(int mouseX, int mouseY) {
				super.renderHoveredToolTip(mouseX, mouseY);
				tooltip.draw(mouseX, mouseY, .getSlotUnderMouse(), );
			}
		};
		
	}

}
