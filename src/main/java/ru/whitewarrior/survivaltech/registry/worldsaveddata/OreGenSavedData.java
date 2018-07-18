package ru.whitewarrior.survivaltech.registry.worldsaveddata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.api.common.orevein.OreGeneration;
import ru.whitewarrior.survivaltech.api.common.orevein.OreGenerationType;

import java.util.HashMap;
import java.util.Map;

public class OreGenSavedData extends WorldSavedData {
    private HashMap<Pair<Integer, Integer>, Pair<OreGeneration, Integer>> listVeinOre = new HashMap<>();

    public OreGenSavedData(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        int mapSize = nbt.getInteger("mapSize");
        listVeinOre.clear();
        for (int i = 0; i < mapSize; i++) {
            NBTTagCompound entryComp = nbt.getCompoundTag("entry_" + i);
            int posX = entryComp.getInteger("posX");
            int posZ = entryComp.getInteger("posZ");
            int posY = entryComp.getInteger("posY");
            OreGeneration generation = new OreGeneration(OreGenerationType.MEDIUM, 0, -1f);
            generation.readFromNbt(entryComp);
            listVeinOre.put(new Pair<>(posX, posZ), new Pair<>(generation, posY));
        }
    }

    public HashMap<Pair<Integer, Integer>, Pair<OreGeneration, Integer>> getListVeinOre() {
        return listVeinOre;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        int i = 0;
        for (Map.Entry<Pair<Integer, Integer>, Pair<OreGeneration, Integer>> entry : listVeinOre.entrySet()) {
            NBTTagCompound entryComp = new NBTTagCompound();
            entry.getValue().getKey().writeToNbt(entryComp);
            entryComp.setInteger("posX", entry.getKey().getKey());
            entryComp.setInteger("posZ", entry.getKey().getValue());
            entryComp.setInteger("posY", entry.getValue().getValue());
            compound.setTag("entry_" + i, entryComp);
            i++;
        }
        compound.setInteger("mapSize", i);
        return compound;
    }

}
