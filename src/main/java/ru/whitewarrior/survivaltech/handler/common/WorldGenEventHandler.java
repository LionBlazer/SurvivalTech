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
            Pair<Integer, Integer> pair = new Pair(event.getChunkX()/getVeinX(), event.getChunkZ()/getVeinY());
            if (data.getListVeinOre().containsKey(pair)){
                generation = data.getListVeinOre().get(pair);
            }


            if (generation != null) {
                if(generation.getValue() == -1)
                    return;
                yPos = generation.getValue();
            }
            else {
                OreGeneration generationOre = WorldGeneratorRegister.getListGen().get(event.getRand().nextInt(WorldGeneratorRegister.getListGen().size()));
                yPos = generationOre.getyMin() + event.getRand().nextInt(generationOre.getyMax() - generationOre.getyMin());
                generation = new Pair<>(generationOre, yPos);
                if(event.getRand().nextFloat()/WorldGeneratorRegister.getListGen().size() > generation.getKey().getSpawnChance())
                {
                    data.getListVeinOre().put(new Pair<>(event.getChunkX()/getVeinX(), event.getChunkZ()/getVeinY()), new Pair<>(generation.getKey(), -1));
                    data.markDirty();
                    return;
                }
            }
            long start = System.currentTimeMillis();
            int ySize = generation.getKey().getYSize();
            data.getListVeinOre().put(new Pair<>(event.getChunkX()/getVeinX(), event.getChunkZ()/getVeinY()), generation);
            data.markDirty();
            Chunk chunk = event.getWorld().getChunkFromChunkCoords(event.getChunkX(), event.getChunkZ());

            boolean isRotateX = Math.abs(((event.getChunkX() < 0 ? getVeinX() - 1 : 0) + event.getChunkX()) % getVeinX()) == 0;
            boolean isRotateX2 = Math.abs(((event.getChunkX() < 0 ? 1 : 0) + event.getChunkX()) % getVeinX()) == getVeinX() - 1;
            boolean isRotateZ = Math.abs(((event.getChunkZ() < 0 ? 0 : 1) + event.getChunkZ()) % getVeinY()) == 0;
            boolean isRotateZ2 = Math.abs(((event.getChunkZ() < 0 ? 0 : getVeinY() - 1) + event.getChunkZ()) % getVeinY()) == getVeinY() - 1;
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < ySize; y++) {
                        float changeSpawnOre = (y < ySize / 2 ? (y / (ySize / 2f)) : ((ySize / 2f) / y));
                        float changeSpawnOreX = x/32f;
                        float changeSpawnOreX2 = x == 0 ? 1f : 1f/x;
                        float changeSpawnOreZ = z == 0 ? 1f : 1f/z;
                        float changeSpawnOreZ2 = z/32f;
                        float newChange = changeSpawnOre * changeSpawnOre * changeSpawnOre * changeSpawnOre * (isRotateX ? changeSpawnOreX : 1) * (isRotateZ ? changeSpawnOreZ : 1) * (isRotateX2 ? changeSpawnOreX2 : 1) * (isRotateZ2 ? changeSpawnOreZ2 : 1);
                        if ((event.getRand().nextFloat()) < newChange) {
                            Pair<IBlockState, Float> pairToGenerate = generation.getKey().getList().get(event.getRand().nextInt(generation.getKey().getList().size()));
                            if (event.getRand().nextFloat() < pairToGenerate.getValue() * 10)
                            {
                                int i = (x ) & 15;
                                int j = y; //Math.max(yPos + y, 0);
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

        }

    }

    public static int getVeinX(){
        return 3;
    }

    public static int getVeinY(){
        return 3;
    }


    @SubscribeEvent
    public void event2(OreGenEvent.GenerateMinable event){
        if(event.getType() == OreGenEvent.GenerateMinable.EventType.IRON ||
                event.getType() == OreGenEvent.GenerateMinable.EventType.DIAMOND
                ||event.getType() == OreGenEvent.GenerateMinable.EventType.GOLD
                ||event.getType() == OreGenEvent.GenerateMinable.EventType.REDSTONE){
            event.setResult(Event.Result.DENY);
        }
    }
}
