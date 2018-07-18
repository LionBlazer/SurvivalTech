package ru.whitewarrior.survivaltech.registry.tileentity.redstonefluxconverter;


import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.api.IEnergyStorage;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityEnergyStandard;
import ru.whitewarrior.survivaltech.util.NumberUtil;

/**
 * se -> rf
 */
public class TileEntityRedStoneFluxConverter extends TileEntityEnergyStandard implements IEnergyProvider, IEnergyStorage, ITickable {
    ElectricEnergyStorage storage = new ElectricEnergyStorage(NumberUtil.toK(4), 8, 8);

    public TileEntityRedStoneFluxConverter() {
        super("TileEntityRedStoneFluxConverter");
    }

    @Override
    public void onBlockActivated(IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            playerIn.sendMessage(new TextComponentString(storage.getEnergyStoredMod() + " energy stored"));
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            EnumFacing outputRF = world.getBlockState(pos).getValue(BlockHorizontal.FACING);
            if (world.getTileEntity(pos.offset(outputRF)) instanceof IEnergyReceiver) {
                IEnergyReceiver receiverRF = (IEnergyReceiver) world.getTileEntity(pos.offset(outputRF));
                if (receiverRF == null)
                    return;
                int canExtract = (int) getStorage().extractEnergy(8, true);
                int amountRf = receiverRF.receiveEnergy(outputRF.getOpposite(), canExtract, true);
                getStorage().extractEnergy(amountRf, false);
                receiverRF.receiveEnergy(outputRF.getOpposite(), amountRf, false);
            }
        }
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
    public int extractEnergy(EnumFacing enumFacing, int i, boolean b) {
        return (int) super.extractEnergy(i, b);
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


}
