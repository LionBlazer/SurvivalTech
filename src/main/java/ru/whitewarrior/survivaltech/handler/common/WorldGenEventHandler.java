package ru.whitewarrior.survivaltech.handler.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.DimensionType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.api.common.orevein.OreGeneration;
import ru.whitewarrior.survivaltech.registry.WorldGeneratorRegister;
import ru.whitewarrior.survivaltech.registry.worldsaveddata.OreGenSavedData;
import ru.whitewarrior.survivaltech.util.WorldSavedDataUtil;

public class WorldGenEventHandler {
    @SubscribeEvent
    public void event1(PopulateChunkEvent.Populate event) {
        int chunkX = event.getChunkX();
        int chunkZ = event.getChunkZ();

        if (event.getWorld().getWorldType().getId() == DimensionType.OVERWORLD.getId()) {


            if (WorldGeneratorRegister.getListGen().size() == 0)
                return;
            OreGenSavedData data = WorldSavedDataUtil.get(event.getWorld(), "ore_gen_data", OreGenSavedData.class);
            Pair<OreGeneration, Integer> generation = null;
            int yPos = 0;
            Pair<Integer, Integer> pair = new Pair(event.getChunkX() / 4, event.getChunkZ() / 4);
            if (data.getListVeinOre().containsKey(pair)) {
                generation = data.getListVeinOre().get(pair);
            }


            if (generation != null) {
                if (generation.getValue() == -1)
                    return;
                yPos = generation.getValue();
            } else {
                OreGeneration generationOre = WorldGeneratorRegister.getListGen().get(event.getRand().nextInt(WorldGeneratorRegister.getListGen().size()));
                yPos = event.getRand().nextInt(30);
                generation = new Pair<>(generationOre, yPos);
                if (event.getRand().nextFloat() / WorldGeneratorRegister.getListGen().size() > generation.getKey().getSpawnChance()) {
                    data.getListVeinOre().put(new Pair<>(event.getChunkX() / 4, event.getChunkZ() / 4), new Pair<>(generation.getKey(), -1));
                    data.markDirty();
                    return;
                }
            }
            long start = System.currentTimeMillis();
            int ySize = generation.getKey().getYSize();
            data.getListVeinOre().put(new Pair<>(event.getChunkX() / 4, event.getChunkZ() / 4), generation);
            data.markDirty();
            Chunk chunk = event.getWorld().getChunkFromChunkCoords(event.getChunkX(), event.getChunkZ());

            boolean isRotateX = event.getChunkX() >= 0 ? Math.abs(event.getChunkX() % 3) == 1 : Math.abs(event.getChunkX() % 3) == 2;
            boolean isRotateZ = event.getChunkZ() >= 0 ? Math.abs(event.getChunkZ() % 3) == 1 : Math.abs(event.getChunkZ() % 3) == 2;

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < ySize; y++) {
                        float changeSpawnOre = (y < ySize / 2 ? (y / (ySize / 2f)) : ((ySize / 2f) / y));
                        float newChange = changeSpawnOre * changeSpawnOre;
                        if (event.getRand().nextFloat() < newChange) {
                            Pair<IBlockState, Float> pairToGenerate = generation.getKey().getList().get(event.getRand().nextInt(generation.getKey().getList().size()));
                            if (event.getRand().nextFloat() < pairToGenerate.getValue()) {
                                int i = x & 15;
                                int j = Math.max(50 - yPos - y, 0);
                                int k = z & 15;
                                ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[j >> 4];
                                if (extendedblockstorage == Chunk.NULL_BLOCK_STORAGE) {
                                    extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, event.getWorld().provider.hasSkyLight());
                                    chunk.getBlockStorageArray()[j >> 4] = extendedblockstorage;
                                }
                                if (chunk.getBlockStorageArray()[j >> 4].get(i, j & 15, k) == Blocks.STONE.getDefaultState())
                                    extendedblockstorage.set(i, j & 15, k, pairToGenerate.getKey());
                            }
                        }
                    }

                }
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }

    }

    @SubscribeEvent
    public void event2(OreGenEvent.GenerateMinable event) {
        if (event.getType() == OreGenEvent.GenerateMinable.EventType.IRON) {
            event.setResult(Event.Result.DENY);
        }
    }
}
