package ru.whitewarrior.survivaltech.registry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.api.common.orevein.OreGeneration;
import ru.whitewarrior.survivaltech.api.common.orevein.OreGenerationType;

import java.util.ArrayList;
import java.util.List;

public class WorldGeneratorRegister {
private static final List<OreGeneration> listGen = new ArrayList<>();

    public static void preInit(){
        WorldGeneratorRegister.registerGeneration(OreGenerationType.LARGE, 10,(byte) 1,0.1f,
                new Pair<>(GameMaterialRegister.copper.getOresBlock().get(0).getDefaultState(), 0.15f),
                new Pair<>(GameMaterialRegister.small_iron.getOresBlock().get(0).getDefaultState(), 0.015f)
        );

        WorldGeneratorRegister.registerGeneration(OreGenerationType.LARGE, 10,(byte) 1,0.04f,
                new Pair<>(GameMaterialRegister.copper.getOresBlock().get(1).getDefaultState(), 0.075f)
        );

        WorldGeneratorRegister.registerGeneration(OreGenerationType.LARGE, 15,(byte) 0,0.02f,
                new Pair<>(GameMaterialRegister.tin.getOresBlock().get(0).getDefaultState(), 0.07f),
                new Pair<>(GameMaterialRegister.small_iron.getOresBlock().get(0).getDefaultState(), 0.012f)
        );

        WorldGeneratorRegister.registerGeneration(OreGenerationType.LARGE, 15,(byte) 0,0.04f,
                new Pair<>(GameMaterialRegister.tin.getOresBlock().get(0).getDefaultState(), 0.07f),
                new Pair<>(GameMaterialRegister.tin.getOresBlock().get(1).getDefaultState(), 0.02f),
                new Pair<>(GameMaterialRegister.small_iron.getOresBlock().get(0).getDefaultState(), 0.012f)
        );

        WorldGeneratorRegister.registerGeneration(OreGenerationType.LARGE, 15,(byte) 6,0.04f,
                new Pair<>(GameMaterialRegister.tin.getOresBlock().get(1).getDefaultState(), 0.04f),
                new Pair<>(GameMaterialRegister.copper.getOresBlock().get(0).getDefaultState(), 0.02f)
        );

        WorldGeneratorRegister.registerGeneration(OreGenerationType.LARGE, 20,(byte) 7,0.06f,
                new Pair<>(Blocks.IRON_ORE.getDefaultState(), 0.05f)
        );
    }

    /**
     * Adds ore generation
     *
     * @param type generation size type
     * @param generationYSize the height of a vein of ore
     * @param blocks pairs of block ore and the chance of its appearance in vein
     * @param mapColor vein color on map
     * @param spawnChance the chance of generating ore, if the chance is equal to 1,
     *                    then each core ore will be generated exactly when it's her turn
     * */
    public static void registerGeneration(OreGenerationType type, int generationYSize, byte mapColor, float spawnChance, Pair<IBlockState, Float>... blocks){
        listGen.add(new OreGeneration(type, generationYSize, spawnChance, mapColor, blocks));
    }

    public static List<OreGeneration> getListGen() {
        return listGen;
    }
}
