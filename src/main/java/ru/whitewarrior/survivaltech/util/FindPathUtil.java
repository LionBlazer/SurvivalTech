package ru.whitewarrior.survivaltech.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.api.common.energy.IEnergyElectricNetworkManager;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkConductor;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkProvider;
import ru.whitewarrior.survivaltech.api.common.network.game.NetworkProviderType;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Date: 2018-04-20.
 * Time: 12:27:52.
 *
 * @author WhiteWarrior
 */
public class FindPathUtil {
    public static boolean isPathExistsEnergy(BlockPos start, BlockPos end, IBlockAccess world) {
        HashSet<BlockPos> visited = new HashSet<>();
        LinkedList<BlockPos> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            if (pos.equals(end)) {
                return true;
            }
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos child = pos.offset(facing);
                if (!visited.contains(child) && world.getBlockState(child).getBlock() instanceof INetworkProvider) {
                    visited.add(child);
                    if (((INetworkProvider) world.getBlockState(child).getBlock()).getProviderType(world, child) == NetworkProviderType.CONDUCTOR)
                        queue.addLast(child);
                    else {
                        if (child.equals(end)) {
                            return true;
                        }
                    }
                }

            }
        }

        return false;
    }

    public static int distanceEnergy(BlockPos start, BlockPos end, IBlockAccess world) {
        HashMap<BlockPos, Integer> visited = new HashMap<>();
        LinkedList<BlockPos> queue = new LinkedList<>();
        queue.offer(start);
        visited.put(start, 0);
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            if (pos.equals(end)) {
                return visited.get(pos);
            }
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos child = pos.offset(facing);
                if (!visited.containsKey(child) && world.getBlockState(child).getBlock() instanceof INetworkProvider) {
                    visited.put(child, visited.get(pos) + 1);
                    queue.addLast(child);
                }

            }
        }
        return -1;
    }

    public static HashMap<BlockPos, Integer> distanceAllNetworkEnergy(BlockPos start, IBlockAccess world) {
        HashMap<BlockPos, Integer> visited = new HashMap<>();
        HashMap<BlockPos, Integer> vertexes = new HashMap<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.offer(start);
        visited.put(start, 0);
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos child = pos.offset(facing);
                int cond = 0;
                if (world.getBlockState(child).getBlock() instanceof INetworkConductor) {
                    cond = ((INetworkConductor) world.getBlockState(child).getBlock()).getConductivity(world, pos);
                }
                if ((!visited.containsKey(child) && world.getBlockState(child).getBlock() instanceof INetworkProvider) || (visited.containsKey(child) && visited.get(child) > visited.get(pos) + cond)) {

                    NetworkProviderType type = ((INetworkProvider) world.getBlockState(child).getBlock()).getProviderType(world, child);
                    if (type == NetworkProviderType.CONDUCTOR) {
                        queue.addLast(child);
                    } else {
                        vertexes.put(child, visited.get(pos) + cond);
                    }
                    visited.put(child, visited.get(pos) + cond);
                }
            }
        }
        return vertexes;
    }

    public static void buildNetwork(BlockPos element, IBlockAccess world, boolean withStart) {
        Pair<HashMap<BlockPos, NetworkProviderType>, HashSet<BlockPos>> pair = !withStart ? FindPathUtil.findAllNetworkVertexes(element, world) : FindPathUtil.findAllNetworkVertexesWithStart(element, world);
        HashMap<BlockPos, NetworkProviderType> vertexes = pair.getKey();
        HashSet<BlockPos> graph = pair.getValue();
        //input - принимают в себя энергию
        HashMap<BlockPos, Integer> fixedInput = new HashMap<>();
        //output - выдают из себя энергию, они - распределители
        HashSet<BlockPos> fixedOutput = new HashSet<>();
        if (world.getTileEntity(element) != null && world.getTileEntity(element) instanceof IEnergyElectricNetworkManager) {
            IEnergyElectricNetworkManager manager = (IEnergyElectricNetworkManager) world.getTileEntity(element);
            manager.getReceiversPos().clear();
            manager.getActiveReceiversPos().clear();
        }
        for (Entry<BlockPos, NetworkProviderType> entry : vertexes.entrySet()) {
            INetworkProvider provider = (INetworkProvider) world.getBlockState(entry.getKey()).getBlock();
            if (provider.getInputFacing(world, entry.getKey()) != null && provider.getProviderType(world, entry.getKey()) != NetworkProviderType.PRODUCER)
                for (EnumFacing facing : provider.getInputFacing(world, entry.getKey())) {
                    BlockPos pos = entry.getKey().offset(facing);
                    if (graph.contains(pos)) {
                        fixedInput.put(entry.getKey(), -1);
                        break;
                    }
                }
            if (provider.getOutputFacing(world, entry.getKey()) != null && provider.getProviderType(world, entry.getKey()) != NetworkProviderType.RECEIVER)
                for (EnumFacing facing : provider.getOutputFacing(world, entry.getKey())) {
                    BlockPos pos = entry.getKey().offset(facing);
                    if (graph.contains(pos)) {
                        fixedOutput.add(entry.getKey());
                        break;
                    }
                }

        }
        for (BlockPos posI : fixedOutput) {
            HashMap<BlockPos, Integer> network = FindPathUtil.distanceAllNetworkEnergy(posI, world);
            for (Entry<BlockPos, Integer> posO : fixedInput.entrySet()) {
                fixedInput.put(posO.getKey(), network.get(posO.getKey()));
            }
            IEnergyElectricNetworkManager manager = (IEnergyElectricNetworkManager) world.getTileEntity(posI);
            manager.getReceiversPos().clear();
            manager.getActiveReceiversPos().clear();
            manager.getReceiversPos().putAll(fixedInput);
            manager.getActiveReceiversPos().putAll(fixedInput);
        }

    }

    public static Pair<HashMap<BlockPos, NetworkProviderType>, HashSet<BlockPos>> findAllNetworkVertexes(BlockPos start, IBlockAccess world) {
        HashSet<BlockPos> visited = new HashSet<>();
        HashMap<BlockPos, NetworkProviderType> vertexes = new HashMap<>();
        LinkedList<BlockPos> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos child = pos.offset(facing);
                if (!visited.contains(child) && world.getBlockState(child).getBlock() instanceof INetworkProvider) {
                    NetworkProviderType type = ((INetworkProvider) world.getBlockState(child).getBlock()).getProviderType(world, child);
                    if (type == NetworkProviderType.CONDUCTOR) {
                        queue.addLast(child);
                    } else {
                        vertexes.put(child, type);
                    }
                    visited.add(child);
                }
            }
        }
        return new Pair<>(vertexes, visited);
    }

    /**
     * ГОВНОКОД Start*
     */
    public static Pair<HashMap<BlockPos, NetworkProviderType>, HashSet<BlockPos>> findAllNetworkVertexesWithStart(BlockPos start, IBlockAccess world) {
        HashSet<BlockPos> visited = new HashSet<>();
        HashMap<BlockPos, NetworkProviderType> vertexes = new HashMap<>();
        LinkedList<BlockPos> queue = new LinkedList<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            NetworkProviderType types = ((INetworkProvider) world.getBlockState(pos).getBlock()).getProviderType(world, pos);
            if (types == NetworkProviderType.CONDUCTOR) {
            } else {
                vertexes.put(pos, types);
            }

            for (EnumFacing facing : EnumFacing.VALUES) {
                BlockPos child = pos.offset(facing);
                if (!visited.contains(child) && world.getBlockState(child).getBlock() instanceof INetworkProvider) {
                    NetworkProviderType type = ((INetworkProvider) world.getBlockState(child).getBlock()).getProviderType(world, child);
                    if (type == NetworkProviderType.CONDUCTOR) {
                        queue.addLast(child);
                    } else {
                        vertexes.put(child, type);
                    }
                    visited.add(child);
                }
            }
        }
        return new Pair<>(vertexes, visited);
    }
    /** *ГОВНОКОД End* */
}
