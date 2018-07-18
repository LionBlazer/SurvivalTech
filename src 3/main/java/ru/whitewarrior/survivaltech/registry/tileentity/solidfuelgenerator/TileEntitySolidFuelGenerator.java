package ru.whitewarrior.survivaltech.registry.tileentity.solidfuelgenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.NBTConstants;
import ru.whitewarrior.survivaltech.api.client.automatic.guicontainer.BasicGuiContainer;
import ru.whitewarrior.survivaltech.api.client.gui.element.EnergybarGuiElement;
import ru.whitewarrior.survivaltech.api.client.gui.element.TooltipGuiElement;
import ru.whitewarrior.survivaltech.api.common.automatic.container.BasicContainer;
import ru.whitewarrior.survivaltech.api.common.container.slot.EnergySlot;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyManager;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.registry.MaterialRegister;
import ru.whitewarrior.survivaltech.registry.block.BlockSolidFuelGenerator;
import ru.whitewarrior.survivaltech.util.EnergyUtil;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.NumberUtil;
import ru.whitewarrior.survivaltech.util.TileEntityUtil;

/**
 * Date: 2017-12-29. Time: 14:17:49.
 * 
 * @author WhiteWarrior
 */
public class TileEntitySolidFuelGenerator extends TileEntityEnergyManager implements ITileEntityGui{
	public int burntime[] = new int[2];
	private int reciveEnergy;
	ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(8), 16);
	public TileEntitySolidFuelGenerator() {
		super("TileEntitySolidFuelGenerator");
	}
	

	@Override
	public boolean hasGui(World world, BlockPos pos) {
		return true;
	}

	
	private boolean isBurning() {
		if(burntime[0]>0)
			return true;
		return false;
		
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			stateUpdate=false;
			if (MaterialRegister.fuel.containsKey(.stacks.get(1).getItem()) && !EnergyUtil.isMaxEnergyStorage(.getStorage()) && burntime[0] == 0) {
					burntime[0]=MaterialRegister.fuel.get(.stacks.get(1).getItem());
					burntime[1]=MaterialRegister.fuel.get(.stacks.get(1).getItem());
					.stacks.get(1).shrink(1);
					.updateVars();
					.updateState();
			}
			else if (isBurning()) {
				.getStorage().receiveEnergy(1, false);
				burntime[0]--;
				.updateVars();
				.updateState();
			}
			if(.getStackInSlot(0).getItem() instanceof IItemElectricEnergyStorage) {
				double energyreceive = .getStackInSlot(0).getCapability(CapabilityElectricEnergy.ENERGY, null).receiveEnergy(.getStorage().extractEnergy(.getStorage().getMaxExtract(), true), false);
				.getStorage().extractEnergy(energyreceive, false);
				updateVars();
			}
			if(stateUpdate) {
				if(MaterialRegister.fuel.containsKey(.stacks.get(1).getItem()) && !EnergyUtil.isMaxEnergyStorage(.getStorage()) && burntime[0] == 0)
					TileEntityUtil.setState(world, pos, BlockSolidFuelGenerator.BURNING, true);
				else
					TileEntityUtil.setState(world, pos, BlockSolidFuelGenerator.BURNING, isBurning());
			}
		}
		super.update();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		burntime=compound.getIntArray(NBTConstants.burnName);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setIntArray(NBTConstants.burnName, burntime);
		return compound;
	}
	
	@Override
	public Object getContainer(EntityPlayer player) {
		return new BasicContainer(player, ) {
			@Override
			protected void initContainer(IInventory tile) {
				.addSlotToContainer(new EnergySlot(tile, 0, 10, 20));
				.addSlotToContainer(new Slot(tile, 1, 109, 40));
			}
		};
	}

	@SideOnly(Side.CLIENT)
    @Override
	public Object getGuiContainer(EntityPlayer player) {
		return new BasicGuiContainer((Container) .getContainer(player), , player) {
			
			private TooltipGuiElement tooltip;
			private EnergybarGuiElement energybar;
			private TooltipGuiElement tooltipbar;
			@Override
			public void initGui() {
				super.initGui();
				tooltip = new TooltipGuiElement(109, 22, 14, 14, burntime[0] / 20+"");
				energybar = new EnergybarGuiElement(GuiUtil.getCoords()[0] + 8, GuiUtil.getCoords()[1] + 55 , 50);
				tooltipbar = new TooltipGuiElement(energybar.getX() - GuiUtil.getCoords()[0], energybar.getY()-GuiUtil.getCoords()[1], energybar.getWidth(), energybar.getHeight(), "");
			}

            @Override
            protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
                super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
                .addFire(110, 22, burntime);
                .addTextureSlots();
                energybar.draw((int) getEnergyStored(), getMaxEnergyStored());
                tooltip.getTooltip().set(0, burntime[0] / 20+"");
                tooltipbar.getTooltip().set(0, EnergyHelper.getColorForEnergy((int) getStorage().getEnergyStored(), getStorage().getMaxEnergyStored()).toString()+NumberUtil.rounding(getStorage().getEnergyStored())+" SEU");
            }
			
			@Override
			protected void renderHoveredToolTip(int mouseX, int mouseY) {
				super.renderHoveredToolTip(mouseX, mouseY);
				tooltip.draw(mouseX, mouseY, .getSlotUnderMouse(), );
				tooltipbar.draw(mouseX, mouseY, .getSlotUnderMouse(), );
			}
		};
		
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}


	@Override
	public ElectricEnergyStorage getStorage() {
		return storage;
	}
}
