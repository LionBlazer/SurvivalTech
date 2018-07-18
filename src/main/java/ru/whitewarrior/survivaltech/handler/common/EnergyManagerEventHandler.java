package ru.whitewarrior.survivaltech.handler.common;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import ru.whitewarrior.survivaltech.api.common.energy.IElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkCable;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkProvider;
import ru.whitewarrior.survivaltech.api.common.network.game.NetworkProviderType;
import ru.whitewarrior.survivaltech.util.NetworkUtil;

/**
 * Date: 2017-12-30.
 * Time: 14:57:39.
 *
 * @author WhiteWarrior
 */
public class EnergyManagerEventHandler {
    @SubscribeEvent
    public void tickEvent(WorldTickEvent e) {
        for (TileEntity tile : e.world.tickableTileEntities) {
            if (e.world.getBlockState(tile.getPos()).getBlock() instanceof INetworkProvider) {
                IElectricEnergyStorage tileOr = (IElectricEnergyStorage) e.world.getTileEntity(tile.getPos());
                INetworkProvider tile1 = (INetworkProvider) e.world.getBlockState(tile.getPos()).getBlock();
                if ((tile1.getProviderType(e.world, tile.getPos()).equals(NetworkProviderType.RECEIVER) || tile1.getProviderType(e.world, tile.getPos()).equals(NetworkProviderType.MULTI)) && tileOr.getStorage().getEnergyReceivedPerTick() > 0) {
                    tileOr.getStorage().zeroEnergyReceivedPerTick();
                }
            }
        }
    }

    @SubscribeEvent
    public void placeEvent(BlockEvent.PlaceEvent e) {
        Block block1 = e.getWorld().getBlockState(e.getPos()).getBlock();
        IBlockState state = e.getWorld().getBlockState(e.getPos());

        if (block1 instanceof INetworkCable)
            NetworkUtil.buildNetworkConductor(e.getWorld(), e.getPos());

        NetworkProviderType type = null;
        if (block1 instanceof INetworkProvider)
            type = (((INetworkProvider) block1).getProviderType(e.getWorld(), e.getPos()));
        else
            return;

        if (type == NetworkProviderType.MULTI)
            NetworkUtil.buildNetworkMulti(e.getWorld(), e.getPos());
        else if (type == NetworkProviderType.PRODUCER)
            NetworkUtil.buildNetworkProducer(e.getWorld(), e.getPos());
        else if (type == NetworkProviderType.RECEIVER)
            NetworkUtil.buildNetworkReceiver(e.getWorld(), e.getPos());
    }
}
