package ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe;

import com.google.gson.annotations.SerializedName;
import net.minecraft.item.ItemStack;
import ru.whitewarrior.survivaltech.api.common.item.ItemPattern;

import java.util.ArrayList;
import java.util.List;

public class SmallArcFurnaceRecipeConf {
    @SerializedName("input")
    private List<ItemPattern> input;
    @SerializedName("output")
    private List<ItemPattern> output;
    @SerializedName("time")
    private int time;

    public SmallArcFurnaceRecipeConf(List<ItemPattern> input, List<ItemPattern> output, int time) {
        this.input = input;
        this.output = output;
        this.time = time;
    }

    public SmallArcFurnaceRecipeConf() {
    }

    public SmallArcFurnaceRecipe recipe(){
        List<ItemStack> stacksIn = new ArrayList<>();
        for (ItemPattern pattern : input){
            stacksIn.add(pattern.getItemStack());
        }

        List<ItemStack> stacksOut = new ArrayList<>();
        for (ItemPattern pattern : output){
            stacksOut.add(pattern.getItemStack());
        }

        return new SmallArcFurnaceRecipe(stacksIn, stacksOut, time);
    }

    public List<ItemPattern> getInput() {
        return input;
    }

    public void setInput(List<ItemPattern> input) {
        this.input = input;
    }

    public List<ItemPattern> getOutput() {
        return output;
    }

    public void setOutput(List<ItemPattern> output) {
        this.output = output;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
