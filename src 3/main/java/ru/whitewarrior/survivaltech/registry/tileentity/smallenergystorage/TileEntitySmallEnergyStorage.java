package ru.whitewarrior.survivaltech.registry.tileentity.smallenergystorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
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
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyManager;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.NumberUtil;

/**
 * Date: 2017-12-30.
 * Time: 22:00:35.
 * @author WhiteWarrior
 */
public class TileEntitySmallEnergyStorage extends TileEntityEnergyManager implements ITileEntityGui{
	ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(32), 16);

	public TileEntitySmallEnergyStorage() {
		super("TileEntitySmallEnergyStorage");
	}
	
	@Override
	public ElectricEnergyStorage getStorage() {
		return storage;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public void update() {
		super.update();
		if(!world.isRemote) {
			if(.getStackInSlot(0).getItem() instanceof IItemElectricEnergyStorage) {
				double energyreceive = .getStackInSlot(0).getCapability(CapabilityElectricEnergy.ENERGY, null).receiveEnergy(.getStorage().extractEnergy(.getStorage().getMaxExtract(), true), false);
				.getStorage().extractEnergy(energyreceive, false);
				updateVars();
			}
			if(.getStackInSlot(1).getItem() instanceof IItemElectricEnergyStorage) {
				double energyreceive = .getStackInSlot(1).getCapability(CapabilityElectricEnergy.ENERGY, null).extractEnergy(.getStorage().receiveEnergy(.getStorage().getMaxReceive(), true), false);
				.getStorage().receiveEnergy(energyreceive, false);
				updateVars();
			}
		}
	}

	@Override
	public boolean hasGui(World world, BlockPos pos) {
		return true;
	}

	@Override
	public Object getContainer(EntityPlayer player) {
		return new BasicContainer(player, ) {
			@Override
			protected void initContainer(IInventory tile) {
				.addSlotToContainer(new EnergySlot(tile, 0, 10, 20));
				.addSlotToContainer(new Slot(tile, 1, 30, 20));
			}
		};
	}

	@SideOnly(Side.CLIENT)
    @Override
	public Object getGuiContainer(EntityPlayer player) {
		return new BasicGuiContainer((Container) .getContainer(player), , player) {
			
			private TooltipGuiElement tooltip;
			private EnergybarGuiElement energybar;
			
			@Override
			public void initGui() {
				super.initGui();
				energybar = new EnergybarGuiElement(GuiUtil.getCoords()[0] + 8, GuiUtil.getCoords()[1] + 55 , 50);
				tooltip = new TooltipGuiElement(energybar.getX() - GuiUtil.getCoords()[0], energybar.getY()-GuiUtil.getCoords()[1], energybar.getWidth(), energybar.getHeight(), "");
			}

            @Override
            protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
                super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
                .addTextureSlots();
                energybar.draw((int) getEnergyStored(), (int) getMaxEnergyStored());
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
