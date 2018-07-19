package ru.whitewarrior.survivaltech.registry.tileentity.redstonefluxconverter;


import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.api.IEnergyStorage;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyManager;
import ru.whitewarrior.survivaltech.util.NumberUtil;

/**
 * rf -> se
 */
public class TileEntityRedStoneFluxAntiConverter extends TileEntityEnergyManager implements IEnergyReceiver, IEnergyStorage {
    ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(4), 8,8);
    public TileEntityRedStoneFluxAntiConverter() {
        super("TileEntityRedStoneFluxAntiConverter");
    }
    @Override
    public void onBlockActivated(IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!world.isRemote)
            playerIn.sendMessage(new TextComponentString(storage.getEnergyStoredMod()+" energy stored"));
    }
    @Override
    public void update() {
        super.update();
    }

    @Override
    public ElectricEnergyStorage getStorage() {
        return storage;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Override
    public int getEnergyStored(EnumFacing enumFacing) {
        return (int) getEnergyStoredMod();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing enumFacing) {
        return getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        EnumFacing valid = world.getBlockState(pos).getValue(BlockHorizontal.FACING);
        return enumFacing.equals(valid);
    }

    @Override
    public int receiveEnergy(int i, boolean b) {
        return (int) super.receiveEnergy(i, b);
    }

    @Override
    public int extractEnergy(int i, boolean b) {
        return (int) super.extractEnergy(i, b);
    }

    @Override
    public int getEnergyStored() {
        return (int) super.getEnergyStoredMod();
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) getMaxEnergyStoredMod();
    }

    @Override
    public int receiveEnergy(EnumFacing enumFacing, int i, boolean b) {
        return (int) super.receiveEnergy(i, b);
    }
}