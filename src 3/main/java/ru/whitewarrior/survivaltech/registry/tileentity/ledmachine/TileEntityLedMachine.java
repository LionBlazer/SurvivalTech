package ru.whitewarrior.survivaltech.registry.tileentity.ledmachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.api.client.automatic.guicontainer.BasicGuiContainer;
import ru.whitewarrior.survivaltech.api.client.gui.element.EnergybarGuiElement;
import ru.whitewarrior.survivaltech.api.common.automatic.container.BasicContainer;
import ru.whitewarrior.survivaltech.api.common.container.slot.EnergySlot;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyStandart;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.util.GuiUtil;

public class TileEntityLedMachine extends TileEntityEnergyStandart implements ITileEntityGui {
    ElectricEnergyStorage storage;

    public TileEntityLedMachine() {
        super("TileEntityLedMachine");
    }

    @Override
    public ElectricEnergyStorage getStorage() {
        return storage;
    }

    @Override
    public boolean hasGui(World world, BlockPos pos) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Object getGuiContainer(EntityPlayer player) {
        return new BasicGuiContainer((Container).getContainer(player), , player){
            private EnergybarGuiElement energybar;
            @Override
            public void initGui() {
                super.initGui();
                energybar = new EnergybarGuiElement(GuiUtil.getCoords()[0] + 8, GuiUtil.getCoords()[1] + 55 , 50);
            }

            @Override
            protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
                super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
                .addTextureSlots();
                energybar.draw((int) getEnergyStored(), getMaxEnergyStored());
            }
        };
    }

    @Override
    public Object getContainer(EntityPlayer player) {
        return new BasicContainer(player, ) {
            @Override
            protected void initContainer(IInventory tile) {
                .addSlotToContainer(new EnergySlot(tile, 0, 10, 20));
                .addSlotToContainer(new Slot(tile, 1, 5, 5));
            }
        };
    }
}
