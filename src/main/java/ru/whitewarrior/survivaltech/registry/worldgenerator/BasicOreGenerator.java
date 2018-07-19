package ru.whitewarrior.survivaltech.registry.worldgenerator;

import net.minecraft.init.Blocks;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class BasicOreGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.getWorldType().getId() == DimensionType.OVERWORLD.getId()) {
            Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int i = x & 15;
                    int j = 100;
                    int k = z & 15;



                    ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[j >> 4];


                    if (extendedblockstorage == Chunk.NULL_BLOCK_STORAGE) {
                        extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, world.provider.hasSkyLight());
                        chunk.getBlockStorageArray()[j >> 4] = extendedblockstorage;
                    }

                    extendedblockstorage.set(i, j & 15, k, Blocks.DIAMOND_BLOCK.getDefaultState());


                }
            }
           // chunk.generateSkylightMap();

        }
    }
}