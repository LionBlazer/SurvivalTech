package ru.whitewarrior.survivaltech.registry.tileentity.solidfuelgenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;
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
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;
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
public class TileEntitySolidFuelGenerator extends TileEntityEnergyManager implements ITileEntityGui {
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
        return burntime[0] > 0;

    }

    @Override
    public void update() {
        if (!world.isRemote) {
            stateUpdate = false;
            if (MaterialRegister.fuel.containsKey(this.getStackInSlot(1).getItem()) && !EnergyUtil.isMaxEnergyStorage(this.getStorage()) && (burntime[0] == 0 || burntime[0] == 1)) {
                burntime[0] = MaterialRegister.fuel.get(this.getStackInSlot(1).getItem());
                burntime[1] = MaterialRegister.fuel.get(this.getStackInSlot(1).getItem());
                this.getStackInSlot(1).shrink(1);
                this.updateVars();
                this.updateState();
            } else if (isBurning()) {
                this.getStorage().receiveEnergy(2, false);
                burntime[0]--;
                this.updateVars();
                this.updateState();
            }
            if (this.getStackInSlot(0).getItem() instanceof IItemElectricEnergyStorage) {
                double energyReceive = this.getStackInSlot(0).getCapability(CapabilityElectricEnergy.ENERGY, null).receiveEnergy(this.getStorage().extractEnergy(this.getStorage().getMaxExtract(), true), false);
                this.getStorage().extractEnergy(energyReceive, false);
                updateVars();
            }
            if (stateUpdate)
                TileEntityUtil.setState(world, pos, BlockSolidFuelGenerator.BURNING, isBurning());
        }
        super.update();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        burntime = compound.getIntArray(NBTConstants.burnName);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setIntArray(NBTConstants.burnName, burntime);
        return compound;
    }

    @Override
    public Object getContainer(EntityPlayer player) {
        return new BasicContainer(player, this) {
            @Override
            protected void initContainer(TileEntityInventory tile) {
                this.addSlotToContainer(new EnergySlot(tile.getHandlerInventory(), 0, 10, 20));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 1, 109, 40));
            }
        };
    }

    @Override
    public Object getGuiContainer(EntityPlayer player) {
        return new BasicGuiContainer((Container) this.getContainer(player), this, player) {

            private TooltipGuiElement tooltip;
            private EnergybarGuiElement energyBar;
            private TooltipGuiElement tooltipBar;

            @Override
            public void initGui() {
                super.initGui();
                tooltip = new TooltipGuiElement(109, 22, 14, 14, burntime[0] / 20 + "");
                energyBar = new EnergybarGuiElement(GuiUtil.getCoords(this)[0] + 8, GuiUtil.getCoords(this)[1] + 55, 50);
                tooltipBar = new TooltipGuiElement(energyBar.getX() - GuiUtil.getCoords(this)[0], energyBar.getY() - GuiUtil.getCoords(this)[1], energyBar.getWidth(), energyBar.getHeight(), "");
            }

            @Override
            protected void init(int x, int y) {
                this.addFire(110, 22, burntime);
                this.addTextureSlots();
                energyBar.draw((int) getEnergyStoredMod(), getMaxEnergyStoredMod());
                tooltip.getTooltip().set(0, burntime[0] / 20 + "");
                tooltipBar.getTooltip().set(0, EnergyHelper.getColorForEnergy((int) getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod()) + " SEU");
            }

            @Override
            protected void renderHoveredToolTip(int mouseX, int mouseY) {
                super.renderHoveredToolTip(mouseX, mouseY);
                tooltip.draw(mouseX, mouseY, this.getSlotUnderMouse(), this);
                tooltipBar.draw(mouseX, mouseY, this.getSlotUnderMouse(), this);
            }
        };

    }

    @Override
    public int getSlots() {
        return 2;
    }


    @Override
    public ElectricEnergyStorage getStorage() {
        return storage;
    }
}
