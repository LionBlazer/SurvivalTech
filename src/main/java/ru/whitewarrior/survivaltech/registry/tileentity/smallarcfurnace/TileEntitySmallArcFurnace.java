package ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace;

import net.minecraft.client.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.api.client.automatic.guicontainer.BasicGuiContainer;
import ru.whitewarrior.survivaltech.api.client.gui.element.EnergybarGuiElement;
import ru.whitewarrior.survivaltech.api.client.gui.element.TooltipGuiElement;
import ru.whitewarrior.survivaltech.api.common.automatic.container.BasicContainer;
import ru.whitewarrior.survivaltech.api.common.container.slot.EnergySlot;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyStandard;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.registry.block.BlockSolidFuelGenerator;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipe;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.NumberUtil;
import ru.whitewarrior.survivaltech.util.TileEntityUtil;

import java.util.HashSet;
import java.util.Set;

public class TileEntitySmallArcFurnace extends TileEntityEnergyStandard implements ITileEntityGui, ITickable {
    ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(32), 64, 64);

    int indexCurrent = -1;
    int timeMax = 0;
    int lasTimeMax = 0;
    int timeCurrent = 0;

    public TileEntitySmallArcFurnace() {
        super("TileEntitySmallArcFurnace");
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (indexCurrent == -1) {
                int matches = 0;
                Pair<ItemStack, Integer>[] slotsMinus = new Pair[0];
                for (int j = 0; j < SmallArcFurnaceRecipe.RECIPES.size(); j++) {
                    SmallArcFurnaceRecipe recipe = SmallArcFurnaceRecipe.RECIPES.get(j);
                    Pair<ItemStack, Integer>[] slots = new Pair[recipe.getInput().size()];
                    int curMatches = 0;
                    int curFree = 0;
                    Set<Integer> usedInputs = new HashSet<>();
                    for (int i = 1; i < 5; i++) {
                        ItemStack stack = getStackInSlot(i);
                        if (stack.isEmpty())
                            continue;
                        for (int k = 0; k < recipe.getInput().size(); k++) {
                            if (usedInputs.contains(k))
                                continue;
                            if (recipe.getInput().get(k) instanceof ItemStack) {
                                ItemStack input = ((ItemStack) recipe.getInput().get(k)).copy();
                                if (ItemStack.areItemsEqual(input, stack) && stack.getCount() >= input.getCount()) {
                                    curMatches++;
                                    slots[k] = new Pair<>(new ItemStack(input.getItem(), stack.getCount() - input.getCount()), i);
                                    usedInputs.add(k);
                                    break;
                                }
                            } else if (recipe.getInput().get(k) instanceof Pair) {
                                int[] ores = OreDictionary.getOreIDs(stack);
                                boolean isReturn = false;
                                if (ores.length > 0) {
                                    String oreName = ((Pair<String, Integer>) recipe.getInput().get(k)).getKey();
                                    for (int i1 = 0; i1 < ores.length; i1++) {
                                        if (OreDictionary.getOreName(ores[i1]).equals(oreName) && stack.getCount() >= ((Pair<String, Integer>) recipe.getInput().get(k)).getValue()) {
                                            curMatches++;
                                            isReturn = true;
                                            usedInputs.add(k);
                                            slots[k] = new Pair<>(new ItemStack(stack.getItem(), stack.getCount() - ((Pair<String, Integer>) recipe.getInput().get(k)).getValue()), i);
                                            break;
                                        }
                                    }
                                }
                                if (isReturn)
                                    break;
                            }

                        }
                    }
                    for (int i = 5; i < 9; i++) {
                        ItemStack stack = getStackInSlot(i);
                        if (stack.isEmpty())
                            curFree++;
                        for (int k = 0; k < recipe.getOutput().size(); k++) {
                            ItemStack input = recipe.getOutput().get(k).copy();
                            if (ItemStack.areItemsEqual(input, stack) && stack.getCount() + input.getCount() <= input.getMaxStackSize()) {
                                curFree++;
                                break;
                            }
                        }
                    }
                    if (curFree >= recipe.getOutput().size() && curMatches >= recipe.getInput().size() && curMatches > matches) {
                        indexCurrent = j;
                        matches = curMatches;
                        timeMax = recipe.getTime();
                        slotsMinus = slots;
                    }
                    if (j >= SmallArcFurnaceRecipe.RECIPES.size() - 1 && indexCurrent != -1) {
                        for (int i = 0; i < slotsMinus.length; i++) {
                            setStackInSlot(slotsMinus[i].getValue(), slotsMinus[i].getKey());
                        }
                        updateState();
                    }
                }
            } else {
                if (timeCurrent < timeMax) {
                    lasTimeMax++;
                    if (getEnergyStoredMod() >= 64) {
                        extractEnergy(64, false);
                        timeCurrent++;
                        lasTimeMax = timeCurrent;
                    }
                    updateVars();
                } else {
                    updateState();
                    timeMax = 0;
                    timeCurrent = 0;
                    lasTimeMax = 0;
                    SmallArcFurnaceRecipe recipe = SmallArcFurnaceRecipe.RECIPES.get(indexCurrent);
                    indexCurrent = -1;

                    for (int k = 0; k < recipe.getOutput().size(); k++) {
                        ItemStack output = recipe.getOutput().get(k).copy();
                        boolean isReturn = false;

                        for (int i = 5; i < 9; i++) {
                            ItemStack stackInSlot = getStackInSlot(i);
                            if (ItemStack.areItemsEqual(stackInSlot, output) && stackInSlot.getCount() + output.getCount() <= output.getMaxStackSize()) {
                                ItemStack newOutput = new ItemStack(output.getItem(), output.getCount() + stackInSlot.getCount());
                                setStackInSlot(i, newOutput);
                                isReturn = true;
                                break;
                            }

                        }
                        if (isReturn)
                            continue;
                        for (int i = 5; i < 9; i++) {
                            ItemStack stackInSlot = getStackInSlot(i);
                            if (stackInSlot.isEmpty()) {
                                setStackInSlot(i, output);
                                break;
                            }

                        }

                    }

                    updateVars();
                }
            }
            //  if(stateUpdate)
            TileEntityUtil.setState(world, pos, BlockSolidFuelGenerator.BURNING, timeCurrent > 0 && Math.abs(timeCurrent - lasTimeMax) < 100);

        }

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        indexCurrent = compound.getInteger("indexCurrent");
        timeMax = compound.getInteger("timeMax");
        timeCurrent = compound.getInteger("timeCurrent");
        lasTimeMax = compound.getInteger("lasTimeMax");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("timeMax", timeMax);
        compound.setInteger("timeCurrent", timeCurrent);
        compound.setInteger("indexCurrent", indexCurrent);
        compound.setInteger("lasTimeMax", lasTimeMax);
        return super.writeToNBT(compound);
    }

    @Override
    public ElectricEnergyStorage getStorage() {
        return storage;
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean hasGui(World world, BlockPos pos) {
        return true;
    }

    @Override
    public Object getGuiContainer(EntityPlayer player) {
        return new BasicGuiContainer((Container) getContainer(player), this, player) {
            private EnergybarGuiElement energyBar;
            private TooltipGuiElement tooltip;
            private TooltipGuiElement tooltipTime;

            @Override
            public void initGui() {
                super.initGui();
                energyBar = new EnergybarGuiElement(GuiUtil.getCoords(this)[0] + xSize - 90, GuiUtil.getCoords(this)[1] + 90, 70);
                tooltip = new TooltipGuiElement(energyBar.getX() - GuiUtil.getCoords(this)[0], energyBar.getY() - GuiUtil.getCoords(this)[1], energyBar.getWidth(), energyBar.getHeight(), "");
                tooltipTime = new TooltipGuiElement(75, 50, 24, 15, "");
                tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod()) + " SEU");
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
                tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod()) + " SEU");
                tooltipTime.getTooltip().set(0, (int) NumberUtil.rounding((float) timeMax == 0 ? 0 : (float) timeCurrent / (float) timeMax * 100) + "%");
                Minecraft.getMinecraft().renderEngine.bindTexture(texture);
                this.drawTexturedModalRect(guiLeft + 75, guiTop + 50, 176, 14, 24, 15);
                this.drawTexturedModalRect(guiLeft + 75, guiTop + 50, 176, 29, (int) (24 * (float) timeCurrent / (float) timeMax), 15);
            }

            @Override
            protected void renderHoveredToolTip(int mouseX, int mouseY) {
                super.renderHoveredToolTip(mouseX, mouseY);
                tooltip.draw(mouseX, mouseY, this.getSlotUnderMouse(), this);
                tooltipTime.draw(mouseX, mouseY, this.getSlotUnderMouse(), this);
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
                this.addSlotToContainer(new EnergySlot(tile.getHandlerInventory(), 0, 10, 20));

                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 1, 30, 40));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 2, 50, 40));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 3, 30, 60));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 4, 50, 60));

                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 5, 110, 40));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 6, 130, 40));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 7, 110, 60));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 8, 130, 60));
            }
        };
    }
}
