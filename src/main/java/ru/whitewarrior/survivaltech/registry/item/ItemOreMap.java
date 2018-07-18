package ru.whitewarrior.survivaltech.registry.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import ru.whitewarrior.api.Pair;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.api.common.orevein.OreGeneration;
import ru.whitewarrior.survivaltech.registry.ItemRegister;
import ru.whitewarrior.survivaltech.registry.worldsaveddata.OreGenSavedData;
import ru.whitewarrior.survivaltech.util.WorldSavedDataUtil;

import javax.annotation.Nullable;
import java.util.List;

public class ItemOreMap extends ItemMap implements IAdvancedItem {
    public ItemOreMap(String name) {
        setRegistryName(name + "_" + getItemType().getPrefix());
        setUnlocalizedName(name + "_" + getItemType().getPrefix());
        this.setCreativeTab(getItemType().getCreativeTab());
        setHasSubtypes(false);
    }

    public static ItemStack setupNewMap(World worldIn, double worldX, double worldZ, byte scale, boolean trackingPosition, boolean unlimitedTracking) {
        ItemStack itemstack = new ItemStack(ItemRegister.oreMap, 1, worldIn.getUniqueDataId("map"));
        String s = "map_" + itemstack.getMetadata();
        MapData mapdata = new MapData(s);
        worldIn.setData(s, mapdata);
        mapdata.scale = scale;
        mapdata.calculateMapCenter(worldX, worldZ, mapdata.scale);
        mapdata.dimension = worldIn.provider.getDimension();
        mapdata.trackingPosition = trackingPosition;
        mapdata.unlimitedTracking = unlimitedTracking;
        mapdata.markDirty();
        return itemstack;
    }

    @Nullable
    @Override
    public MapData getMapData(ItemStack stack, World worldIn) {
        return super.getMapData(stack, worldIn);
    }

    @Override
    public void updateMapData(World worldIn, Entity viewer, MapData data) {
        if (worldIn.provider.getDimension() == data.dimension && viewer instanceof EntityPlayer) {
            int i = 1 << data.scale;
            int j = data.xCenter;
            int k = data.zCenter;
            int l = MathHelper.floor(viewer.posX - (double) j) / i + 64;
            int i1 = MathHelper.floor(viewer.posZ - (double) k) / i + 64;
            int j1 = 128 / i;

            if (worldIn.provider.isNether()) {
                j1 /= 2;
            }

            MapData.MapInfo mapdata$mapinfo = data.getMapInfo((EntityPlayer) viewer);
            ++mapdata$mapinfo.step;
            boolean flag = false;
            OreGenSavedData savedData = WorldSavedDataUtil.get(worldIn, "ore_gen_data", OreGenSavedData.class);

            for (int k1 = l - j1 + 1; k1 < l + j1; ++k1) {
                if ((k1 & 15) == (mapdata$mapinfo.step & 15) || flag) {
                    flag = false;
                    double d0 = 0.0D;

                    for (int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1) {
                        if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
                            int i2 = k1 - l;
                            int j2 = l1 - i1;
                            boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
                            int k2 = (j / i + k1 - 64) * i;
                            int l2 = (k / i + l1 - 64) * i;
                            Multiset<MapColor> multiset = HashMultiset.create();
                            Chunk chunk = worldIn.getChunkFromBlockCoords(new BlockPos(k2, 0, l2));

                            if (!chunk.isEmpty()) {
                                int i3 = k2 & 15;
                                int j3 = l2 & 15;
                                int k3 = 0;
                                double d1 = 0.0D;

                                Pair<Integer, Integer> pairCords = new Pair<>(k2 / 16 / 4, l2 / 16 / 4);
                                if (savedData.getListVeinOre().containsKey(pairCords) && savedData.getListVeinOre().get(pairCords).getValue() != -1) {
                                    Pair<OreGeneration, Integer> generationIntegerPair = savedData.getListVeinOre().get(pairCords);
                                    OreGeneration generation = generationIntegerPair.getKey();
                                    if (generation.getOreTypeColor() == 0)
                                        multiset.add(MapColor.IRON, 100);
                                    else if (generation.getOreTypeColor() == 1)
                                        multiset.add(MapColor.BROWN, 100);
                                    else if (generation.getOreTypeColor() == 2)
                                        multiset.add(MapColor.DIAMOND, 100);
                                    else if (generation.getOreTypeColor() == 3)
                                        multiset.add(MapColor.RED, 100);
                                    else if (generation.getOreTypeColor() == 4)
                                        multiset.add(MapColor.BLACK, 100);
                                    else if (generation.getOreTypeColor() == 5)
                                        multiset.add(MapColor.GOLD, 100);
                                    else if (generation.getOreTypeColor() == 6)
                                        multiset.add(MapColor.STONE, 100);
                                    else if (generation.getOreTypeColor() == 7)
                                        multiset.add(MapColor.CYAN, 100);
                                    else
                                        multiset.add(MapColor.WHITE_STAINED_HARDENED_CLAY);

                                }
                                {
                                    if (worldIn.provider.isNether()) {
                                        int l3 = k2 + l2 * 231871;
                                        l3 = l3 * l3 * 31287121 + l3 * 11;

                                        if ((l3 >> 20 & 1) == 0) {
                                            multiset.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT).getMapColor(worldIn, BlockPos.ORIGIN), 10);
                                        } else {
                                            multiset.add(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE).getMapColor(worldIn, BlockPos.ORIGIN), 100);
                                        }

                                        d1 = 100.0D;
                                    } else {
                                        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                                        for (int i4 = 0; i4 < i; ++i4) {
                                            for (int j4 = 0; j4 < i; ++j4) {
                                                int k4 = chunk.getHeightValue(i4 + i3, j4 + j3) + 1;
                                                IBlockState iblockstate = Blocks.AIR.getDefaultState();

                                                if (k4 <= 1) {
                                                    iblockstate = Blocks.BEDROCK.getDefaultState();
                                                } else {
                                                    label175:
                                                    {
                                                        while (true) {
                                                            --k4;
                                                            iblockstate = chunk.getBlockState(i4 + i3, k4, j4 + j3);
                                                            blockpos$mutableblockpos.setPos((chunk.x << 4) + i4 + i3, k4, (chunk.z << 4) + j4 + j3);

                                                            if (iblockstate.getMapColor(worldIn, blockpos$mutableblockpos) != MapColor.AIR || k4 <= 0) {
                                                                break;
                                                            }
                                                        }

                                                        if (k4 > 0 && iblockstate.getMaterial().isLiquid()) {
                                                            int l4 = k4 - 1;

                                                            while (true) {
                                                                IBlockState iblockstate1 = chunk.getBlockState(i4 + i3, l4--, j4 + j3);
                                                                ++k3;

                                                                if (l4 <= 0 || !iblockstate1.getMaterial().isLiquid()) {
                                                                    break label175;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                d1 += (double) k4 / (double) (i * i);
                                                multiset.add(iblockstate.getMapColor(worldIn, blockpos$mutableblockpos));
                                            }
                                        }
                                    }
                                }
                                k3 = k3 / (i * i);
                                double d2 = (d1 - d0) * 4.0D / (double) (i + 4) + ((double) (k1 + l1 & 1) - 0.5D) * 0.4D;
                                int i5 = 1;

                                if (d2 > 0.6D) {
                                    i5 = 2;
                                }

                                if (d2 < -0.6D) {
                                    i5 = 0;
                                }

                                MapColor mapcolor = Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MapColor.AIR);

                                if (mapcolor == MapColor.WATER) {
                                    d2 = (double) k3 * 0.1D + (double) (k1 + l1 & 1) * 0.2D;
                                    i5 = 1;

                                    if (d2 < 0.5D) {
                                        i5 = 2;
                                    }

                                    if (d2 > 0.9D) {
                                        i5 = 0;
                                    }
                                }

                                d0 = d1;


                                if (l1 >= 0 && i2 * i2 + j2 * j2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0)) {
                                    byte b0 = data.colors[k1 + l1 * 128];
                                    byte b1 = (byte) (mapcolor.colorIndex * 4 + i5);

                                    if (b0 != b1) {
                                        data.colors[k1 + l1 * 128] = b1;
                                        data.updateMapData(k1, l1);
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Nullable
    @Override
    public Packet<?> createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
        return super.createMapDataPacket(stack, worldIn, player);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.MISCELLANEA;
    }
}
