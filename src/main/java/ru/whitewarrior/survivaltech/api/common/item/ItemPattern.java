package ru.whitewarrior.survivaltech.api.common.item;

import com.google.gson.annotations.SerializedName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPattern {
    @SerializedName("id")
    private String id;
    @SerializedName("metadata")
    private int metadata;
    @SerializedName("count")
    private int count = 1;

    public ItemPattern(String id, int metadata) {
        this.id = id;
        this.metadata = metadata;
    }

    public ItemPattern(String id, int metadata, int count) {
        this.id = id;
        this.metadata = metadata;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getId() {
        return id;
    }

    public int getMetadata() {
        return metadata;
    }

    public ItemStack getItemStack() {
        return new ItemStack(Item.getByNameOrId(id), count, metadata);
    }

    @Override
    public String toString() {
        return "item[" + id + ":" + metadata + "]";
    }
}

