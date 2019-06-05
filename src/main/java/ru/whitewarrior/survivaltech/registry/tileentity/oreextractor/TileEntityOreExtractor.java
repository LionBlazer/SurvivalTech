package ru.whitewarrior.survivaltech.registry.tileentity.oreextractor;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import ru.whitewarrior.survivaltech.api.client.automatic.guicontainer.BasicGuiContainer;
import ru.whitewarrior.survivaltech.api.client.gui.element.EnergybarGuiElement;
import ru.whitewarrior.survivaltech.api.client.gui.element.TooltipGuiElement;
import ru.whitewarrior.survivaltech.api.common.automatic.container.BasicContainer;
import ru.whitewarrior.survivaltech.api.common.container.slot.EnergySlot;
import ru.whitewarrior.survivaltech.api.common.container.slot.SlotFinalHandler;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyStandard;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.registry.block.BlockSolidFuelGenerator;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.NumberUtil;
import ru.whitewarrior.survivaltech.util.TileEntityUtil;

import java.util.Random;

import static ru.whitewarrior.survivaltech.registry.tileentity.oreextractor.recipe.OreExtractorRecipe.BAKED_RECIPES;

public class TileEntityOreExtractor extends TileEntityEnergyStandard implements ITileEntityGui, ITickable {
    private static Random random = new Random();
    private ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(10), 64, 64);
    private int activeTime = -1;


    public TileEntityOreExtractor() {
        super("TileEntityOreExtractor");
        setInventorySide(EnumFacing.DOWN);
        setInventorySide(EnumFacing.UP);
    }


    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index == 1 && !stack.isEmpty() && direction == EnumFacing.DOWN;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return index == 0 && Block.getBlockFromItem(itemStackIn.getItem()) == Blocks.COBBLESTONE && direction == EnumFacing.UP;
    }

    @Override
    public void update() {
        TileEntityUtil.setState(world, pos, BlockSolidFuelGenerator.BURNING, activeTime > 0);
        if (!world.isRemote) {
            ItemStack result = getStackInSlot(1);
            if(!result.isEmpty()) {
                if(activeTime == 1){
                    activeTime = 0;
                    updateVars();
                }
                return;
            }
            if (activeTime <= 1) {
                ItemStack stack = getStackInSlot(0);
                if (!stack.isEmpty()) {
                    Block block = Block.getBlockFromItem(stack.getItem());
                    if (block == Blocks.COBBLESTONE) {
                        stack.setCount(stack.getCount() - 1);
                        setStackInSlot(0, stack);
                        activeTime = (int) (100 * Math.min(1f, random.nextFloat() + 0.5f));
                    }
                }
                activeTime--;
            } else {
                if(getEnergyStoredMod() >= 4) {
                    extractEnergy(4, false);
                    activeTime--;
                    updateVars();
                    if (activeTime == 1 && random.nextFloat() < 0.1f)
                        setStackInSlot(1, WeightedRandom.getRandomItem(random, BAKED_RECIPES).getStack().copy());
                }
            }
            extractEnergyFromSlot(2);
        }
    }

    @Override
    public ElectricEnergyStorage getStorage() {
        return storage;
    }

    @Override
    public int getSlots() {
        return 3;
    }

    @Override
    public boolean hasGui(World world, BlockPos pos) {
        return true;
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        activeTime = compound.getInteger("activeTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("activeTime", activeTime);
        return super.writeToNBT(compound);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Object getGuiContainer(EntityPlayer player) {
        return new BasicGuiContainer((Container) getContainer(player), this, player){
            private EnergybarGuiElement energyBar;
            private TooltipGuiElement tooltip;
            private TooltipGuiElement tooltipTime;

            @Override
            public void initGui() {
                super.initGui();
                energyBar = new EnergybarGuiElement(GuiUtil.getCoords(this)[0] + xSize - 90, GuiUtil.getCoords(this)[1] + 90 , 70);
                tooltip = new TooltipGuiElement(energyBar.getX() - GuiUtil.getCoords(this)[0], energyBar.getY()-GuiUtil.getCoords(this)[1], energyBar.getWidth(), energyBar.getHeight(), "");
                tooltipTime = new TooltipGuiElement(75,50, 24, 15, "");
                tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod())+" SEU");
                tooltipTime.getTooltip().set(0, "");

            }

            @Override
            public int sizeY() {
                return 201;
            }

            @Override
            protected void init(int x, int y) {
                addTextureSlots();
                energyBar.draw((int) getEnergyStoredMod(), (int) getMaxEnergyStoredMod());
                tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod())+" SEU");
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                this.drawTexturedModalRect(guiLeft + 75,guiTop + 50, 176,14, 24, 15);
                if(activeTime >= 1)
                    this.drawTexturedModalRect(guiLeft + 75,guiTop + 50, 176,29, 24, 15);

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
            protected int guiSizeY() {
                return 201 - 166;
            }

            @Override
            protected void initContainer(TileEntityInventory tile) {
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 0, 45, 49));
                this.addSlotToContainer(new SlotFinalHandler(tile.getHandlerInventory(), 1, 110, 49));

                this.addSlotToContainer(new EnergySlot(tile.getHandlerInventory(), 2, 10, 20));
            }
        };
    }
}
