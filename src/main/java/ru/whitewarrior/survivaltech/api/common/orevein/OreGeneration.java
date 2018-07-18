package ru.whitewarrior.survivaltech.api.common.orevein;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.whitewarrior.api.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class OreGeneration {
    private List<Pair<IBlockState, Float>> list = new ArrayList<>();
    private float spawnChance;
    private int ySize;
    private byte oreTypeColor;
    private OreGenerationType generationType;

    public OreGeneration(OreGenerationType generationType, int generationYSize, float spawnChance, Pair... blocks) {
        this.ySize = generationYSize;
        this.spawnChance = spawnChance;
        this.generationType = generationType;
        if (blocks.length > 0)
            list.addAll(Arrays.asList((Pair<IBlockState, Float>[]) blocks));
    }

    public OreGeneration(OreGenerationType generationType, int generationYSize, float spawnChance, byte oreTypeColor, Pair... blocks) {
        this.ySize = generationYSize;
        this.spawnChance = spawnChance;
        this.generationType = generationType;
        this.oreTypeColor = oreTypeColor;
        if (blocks.length > 0)
            list.addAll(Arrays.asList((Pair<IBlockState, Float>[]) blocks));
    }

    public void setOreTypeColor(byte oreTypeColor) {
        this.oreTypeColor = oreTypeColor;
    }

    public float getSpawnChance() {
        return spawnChance;
    }

    public byte getOreTypeColor() {
        return oreTypeColor;
    }

    public List<Pair<IBlockState, Float>> getList() {
        return list;
    }

    public void writeToNbt(NBTTagCompound compound) {
        compound.setInteger("size", list.size());
        for (int i = 0; i < list.size(); i++) {
            compound.setString("pairState_" + i, list.get(i).getKey().getBlock().getRegistryName().toString());
            compound.setFloat("change_" + i, list.get(i).getValue());
        }
        compound.setFloat("changeGeneration", spawnChance);
        compound.setInteger("ySize", ySize);
        compound.setInteger("generationType", generationType.ordinal());
        compound.setByte("oreTypeColor", oreTypeColor);
    }

    public void readFromNbt(NBTTagCompound compound) {
        int size = compound.getInteger("size");
        list.clear();
        for (int i = 0; i < size; i++) {
            String blockRegName = compound.getString("pairState_" + i);
            float change = compound.getFloat("change_" + i);
            ResourceLocation regName = new ResourceLocation(blockRegName);
            if (ForgeRegistries.BLOCKS.containsKey(regName)) {
                IBlockState state = ForgeRegistries.BLOCKS.getValue(regName).getDefaultState();
                list.add(new Pair<>(state, change));
            }
        }
        oreTypeColor = compound.getByte("oreTypeColor");
        spawnChance = compound.getFloat("changeGeneration");
        ySize = compound.getInteger("ySize");
        generationType = OreGenerationType.values()[compound.getInteger("generationType")];
    }

    public int getYSize() {
        return ySize;
    }
}
