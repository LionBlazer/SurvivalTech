package ru.whitewarrior.survivaltech.registry.tileentity.ledmachine;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;
import ru.whitewarrior.survivaltech.api.client.automatic.guicontainer.BasicGuiContainer;
import ru.whitewarrior.survivaltech.api.client.gui.element.ButtonGuiElement;
import ru.whitewarrior.survivaltech.api.client.gui.element.EnergybarGuiElement;
import ru.whitewarrior.survivaltech.api.client.gui.element.TooltipGuiElement;
import ru.whitewarrior.survivaltech.api.common.automatic.container.BasicContainer;
import ru.whitewarrior.survivaltech.api.common.container.slot.EnergySlot;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyStandard;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.registry.item.armor.ItemArmorPower;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.NbtUtil;
import ru.whitewarrior.survivaltech.util.NumberUtil;

public class TileEntityLedMachine extends TileEntityEnergyStandard implements ITickable, ITileEntityGui {
    private ElectricEnergyStorage storage = new ElectricEnergyStorage(2000, 16);
    public byte red;
    public byte green;
    public byte blue;
    public TileEntityLedMachine(){
        super("TileEntityLedMachine");
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
        if(!world.isRemote){

            if(getEnergyStoredMod() >= 10 && this.getStackInSlot(1).getItem() instanceof ItemArmorPower) {

                byte r = NbtUtil.getTagCompound(getStackInSlot(1)).getByte("red");
                byte g = NbtUtil.getTagCompound(getStackInSlot(1)).getByte("green");
                byte b = NbtUtil.getTagCompound(getStackInSlot(1)).getByte("blue");
                if (r >= red && g >= green && b >= blue) {
                    return;
                }
                extractEnergy(10, false);
                if (r < red)
                    r++;
                if (g < green)
                    g++;
                if (b < blue)
                    b++;

                if (r > red)
                    r--;
                if (g > green)
                    g--;
                if (b > blue)
                    b--;
                NbtUtil.getTagCompound(getStackInSlot(1)).setByte("red", r);
                NbtUtil.getTagCompound(getStackInSlot(1)).setByte("green", g);
                NbtUtil.getTagCompound(getStackInSlot(1)).setByte("blue", b);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setByte("red", red);
        compound.setByte("green", green);
        compound.setByte("blue", blue);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        red = compound.getByte("red");
        green = compound.getByte("blue");
        blue = compound.getByte("blue");
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasGui(World world, BlockPos pos) {
        return true;
    }

    @Override
    public Object getGuiContainer(EntityPlayer player) {
        return new BasicGuiContainer((Container) this.getContainer(player), this, player){
            private EnergybarGuiElement energyBar;
            private TooltipGuiElement tooltip;
            private ButtonGuiElement buttonRed;
            private ButtonGuiElement buttonGreen;
            private ButtonGuiElement buttonBlue;
            @Override
            public void initGui() {
                super.initGui();
                energyBar = new EnergybarGuiElement(GuiUtil.getCoords(this)[0] + 8, GuiUtil.getCoords(this)[1] + 55, 50);
                tooltip = new TooltipGuiElement(energyBar.getX() - GuiUtil.getCoords(this)[0], energyBar.getY()-GuiUtil.getCoords(this)[1], energyBar.getWidth(), energyBar.getHeight(), "");
                buttonRed = new ButtonGuiElement(0, GuiUtil.getCoords(this)[0]+40, GuiUtil.getCoords(this)[1]+20,30,20, "red");
                buttonGreen = new ButtonGuiElement(1, GuiUtil.getCoords(this)[0]+80, GuiUtil.getCoords(this)[1]+20,30,20, "green");
                buttonBlue = new ButtonGuiElement(2, GuiUtil.getCoords(this)[0]+120, GuiUtil.getCoords(this)[1]+20,30,20, "blue");

                buttonRed.setColor((byte) 127,(byte) 0,(byte) 0);
                buttonGreen.setColor((byte) 0,(byte) 127,(byte) 0);
                buttonBlue.setColor((byte) 0,(byte) 0,(byte) 127);


                buttonList.add(buttonRed);
                buttonList.add(buttonGreen);
                buttonList.add(buttonBlue);
            }

            @Override
            protected void actionPerformed(GuiButton button) {
                switch (button.id){
                    case 0 : {
                        if(GuiContainer.isShiftKeyDown()){
                            ((TileEntityLedMachine)this.inventory).red--;
                        }
                        else
                            ((TileEntityLedMachine)this.inventory).red++;
                    }
                    case 1 : {
                        if(GuiContainer.isShiftKeyDown()){
                            ((TileEntityLedMachine)this.inventory).green--;
                        }
                        else
                            ((TileEntityLedMachine)this.inventory).green++;
                    }
                    case 2 : {
                        if(GuiContainer.isShiftKeyDown()){
                            ((TileEntityLedMachine)this.inventory).blue--;
                        }
                        else
                            ((TileEntityLedMachine)this.inventory).blue++;
                    }
                }
            }

            @Override
            protected void init(int x, int y) {
                addTextureSlots();
                energyBar.draw((int) getEnergyStoredMod(), getMaxEnergyStoredMod());
                tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod())+" SEU");
            }

            @Override
            protected void renderHoveredToolTip(int mouseX, int mouseY) {
                super.renderHoveredToolTip(mouseX, mouseY);
                tooltip.draw(mouseX, mouseY, this.getSlotUnderMouse(), this);
            }
        };
    }

    @Override
    public Object getContainer(EntityPlayer player) {
        return new BasicContainer(player, this) {
            @Override
            protected void initContainer(TileEntityInventory tile) {
                this.addSlotToContainer(new EnergySlot(tile.getHandlerInventory(), 0, 10, 20));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 1, 140, 55));
            }
        };
    }
}
