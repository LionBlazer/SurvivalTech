package ru.whitewarrior.survivaltech.registry.tileentity.smallenergystorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;
import ru.whitewarrior.survivaltech.api.client.automatic.guicontainer.BasicGuiContainer;
import ru.whitewarrior.survivaltech.api.client.gui.element.EnergybarGuiElement;
import ru.whitewarrior.survivaltech.api.client.gui.element.TooltipGuiElement;
import ru.whitewarrior.survivaltech.api.common.automatic.container.BasicContainer;
import ru.whitewarrior.survivaltech.api.common.container.slot.EnergySlot;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.IItemElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.capability.CapabilityElectricEnergy;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyManager;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;
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
    public int getSlots() {
        return 2;
    }

	@Override
	public void update() {
		super.update();
		if(!world.isRemote) {
			if(this.getStackInSlot(0).getItem() instanceof IItemElectricEnergyStorage) {
				double energyReceive = this.getStackInSlot(0).getCapability(CapabilityElectricEnergy.ENERGY, null).receiveEnergy(this.getStorage().extractEnergy(this.getStorage().getMaxExtract(), true), false);
				this.getStorage().extractEnergy(energyReceive, false);
				updateVars();
				markDirty();
			}
			if(this.getStackInSlot(1).getItem() instanceof IItemElectricEnergyStorage) {
				double energyReceive = this.getStackInSlot(1).getCapability(CapabilityElectricEnergy.ENERGY, null).extractEnergy(this.getStorage().receiveEnergy(this.getStorage().getMaxReceive(), true), false);
				this.getStorage().receiveEnergy(energyReceive, false);
				updateVars();
                markDirty();
			}
		}
	}

	@Override
	public boolean hasGui(World world, BlockPos pos) {
		return true;
	}

	@Override
	public Object getContainer(EntityPlayer player) {
		return new BasicContainer(player, this) {
			@Override
			protected void initContainer(TileEntityInventory tile) {
				this.addSlotToContainer(new EnergySlot(tile.getHandlerInventory(), 0, 10, 20));
				this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 1, 30, 20));
			}
		};
	}

	@Override
	public Object getGuiContainer(EntityPlayer player) {
		return new BasicGuiContainer((Container) this.getContainer(player), this, player) {
			
			private TooltipGuiElement tooltip;
			private EnergybarGuiElement energyBar;
			
			@Override
			public void initGui() {
				super.initGui();
				energyBar = new EnergybarGuiElement(GuiUtil.getCoords(this)[0] + 8, GuiUtil.getCoords(this)[1] + 55 , 50);
				tooltip = new TooltipGuiElement(energyBar.getX() - GuiUtil.getCoords(this)[0], energyBar.getY()-GuiUtil.getCoords(this)[1], energyBar.getWidth(), energyBar.getHeight(), "");
			}
			
			@Override
			protected void init(int x, int y) {
				this.addTextureSlots();
				energyBar.draw((int) getEnergyStoredMod(), (int) getMaxEnergyStoredMod());
				tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod())+" SEU");
			}
			
			@Override
			protected void renderHoveredToolTip(int mouseX, int mouseY) {
				super.renderHoveredToolTip(mouseX, mouseY);
				tooltip.draw(mouseX, mouseY, this.getSlotUnderMouse(), this);
			}
		};
		
	}

}
