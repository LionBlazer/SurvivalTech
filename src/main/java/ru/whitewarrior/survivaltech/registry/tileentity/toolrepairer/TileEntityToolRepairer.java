package ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
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
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyStandard;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityInventory;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;
import ru.whitewarrior.survivaltech.helper.EnergyHelper;
import ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe.ToolRepairerRecipes;
import ru.whitewarrior.survivaltech.util.GuiUtil;
import ru.whitewarrior.survivaltech.util.NumberUtil;

/**
 * Date: 2017-12-31. Time: 20:41:30.
 *
 * @author WhiteWarrior
 */
public class TileEntityToolRepairer extends TileEntityEnergyStandard implements ITileEntityGui, ITickable {
    ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(4), 16);
    private int process;

    public TileEntityToolRepairer() {
        super("TileEntityToolRepairer");
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
    public void update() {
        if (!world.isRemote) {
            if (this.getStackInSlot(0).getItem() instanceof IItemElectricEnergyStorage) {
                double energyReceive = this.getStackInSlot(0).getCapability(CapabilityElectricEnergy.ENERGY, null).extractEnergy(this.getStorage().receiveEnergy(this.getStorage().getMaxReceive(), true), false);
                this.getStorage().receiveEnergy(energyReceive, false);
                updateVars();
            }
            Item item = this.getStackInSlot(1).getItem();
            Item item2 = this.getStackInSlot(2).getItem();
            if (process == 0 && this.getStorage().getEnergyStoredMod() >= 15 && ToolRepairerRecipes.recipes.containsKey(item2) && ToolRepairerRecipes.recipes.get(item2).equals(item) && this.getStackInSlot(2).isItemDamaged()) {
                this.setStackInSlot(2, this.getStackInSlot(2).copy());
                this.getStackInSlot(1).shrink(1);
                process++;
                this.getStackInSlot(2).shrink(1);
                updateVars();
            }
            if (process > 0 && this.getStackInSlot(2) != null) {
                if (this.getStorage().getEnergyStoredMod() >= 15) {
                    this.getStorage().extractEnergy(15, false);
                    updateVars();
                    process++;
                }
            } else {
                this.setStackInSlot(2, ItemStack.EMPTY);
                process = 0;
            }
            if (process >= NumberUtil.toTick(10)) {
                process = 0;
                if (!this.getStackInSlot(2).isEmpty())
                    world.spawnEntity(new EntityItem(world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.getStackInSlot(2)));
                this.setStackInSlot(2, this.getStackInSlot(2).copy());
                this.getStackInSlot(1).setItemDamage(0);
                this.setStackInSlot(2, ItemStack.EMPTY);
                this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("process", process);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        process = compound.getInteger("process");
        super.readFromNBT(compound);
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
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 1, 60, 30));
                this.addSlotToContainer(new SlotItemHandler(tile.getHandlerInventory(), 2, 90, 30));
            }
        };
    }

    @Override
    public Object getGuiContainer(EntityPlayer player) {
        return new BasicGuiContainer((Container) this.getContainer(player), this, player) {

            private TooltipGuiElement tooltip;
            private EnergybarGuiElement energybar;

            @Override
            public void initGui() {
                super.initGui();
                energybar = new EnergybarGuiElement(GuiUtil.getCoords(this)[0] + 8, GuiUtil.getCoords(this)[1] + 55, 50);
                tooltip = new TooltipGuiElement(energybar.getX() - GuiUtil.getCoords(this)[0], energybar.getY() - GuiUtil.getCoords(this)[1], energybar.getWidth(), energybar.getHeight(), "");
            }

            @Override
            protected void init(int x, int y) {
                this.addTextureSlots();
                this.drawTexturedModalRect(GuiUtil.getCoords(this)[0] + 89, GuiUtil.getCoords(this)[1] + 46 - GuiUtil.getScaledTextures(process, NumberUtil.toTick(10), 16), 220, 18, 18, GuiUtil.getScaledTextures(process, NumberUtil.toTick(10), 16));
                energybar.draw((int) getEnergyStoredMod(), (int) getMaxEnergyStoredMod());
                tooltip.getTooltip().set(0, EnergyHelper.getColorForEnergy(getStorage().getEnergyStoredMod(), getStorage().getMaxEnergyStoredMod()).toString() + NumberUtil.rounding(getStorage().getEnergyStoredMod()) + " SEU");
            }

            @Override
            protected void renderHoveredToolTip(int mouseX, int mouseY) {
                super.renderHoveredToolTip(mouseX, mouseY);
                tooltip.draw(mouseX, mouseY, this.getSlotUnderMouse(), this);
            }
        };

    }

}
