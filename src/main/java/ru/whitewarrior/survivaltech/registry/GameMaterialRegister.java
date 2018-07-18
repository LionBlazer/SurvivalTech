package ru.whitewarrior.survivaltech.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.client.model.ModModelLoader;
import ru.whitewarrior.survivaltech.api.client.model.imodel.ItemModel;
import ru.whitewarrior.survivaltech.api.client.model.imodel.ItemToolModel;
import ru.whitewarrior.survivaltech.api.client.model.imodel.OreModel;
import ru.whitewarrior.survivaltech.api.common.automatic.BasicGameMaterial;
import ru.whitewarrior.survivaltech.api.common.automatic.block.BasicTypeBlockCutout;
import ru.whitewarrior.survivaltech.api.common.automatic.item.BasicTypeItem;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.item.IAdvancedItem;
import ru.whitewarrior.survivaltech.api.common.item.ItemType;
import ru.whitewarrior.survivaltech.registry.item.ItemHammer;

/**
 * Date: 2017-12-27. Time: 9:44:39.
 *
 * @author WhiteWarrior
 */
public class GameMaterialRegister {
    public static BasicGameMaterial copper = new BasicGameMaterial("copper", 1, 1);
    public static BasicGameMaterial small_iron = new BasicGameMaterial("iron_small", 0.6f, 1, false, false);
    public static BasicGameMaterial tin = new BasicGameMaterial("tin", 0.6f, 1, true, true);
    public static BasicGameMaterial gold = new BasicGameMaterial("gold", 0.6f, 1, false, false);
    public static BasicGameMaterial redstone = new BasicGameMaterial("redstone", 0.7f, 2, false, false);

    public static void preInit() {
        Item.ToolMaterial copperToolMaterial = EnumHelper.addToolMaterial("copper", 1, 100, 1, 2, 1);
        Item.ToolMaterial tinToolMaterial = EnumHelper.addToolMaterial("tin", 1, 80, 1, 2, 1);
        tin.setToolMaterial(tinToolMaterial);
        tin.getOresBlock().add(new BasicTypeBlockCutout(BlockType.ORE, "cassiterite", 1.4f, 1.2f, 2, 0, 0, "stannite"));
        tin.getOresBlock().add(new BasicTypeBlockCutout(BlockType.ORE, "stannite", 1.0f, 0.9f, 1, 0, 0, "stannite"));
        tin.addChunk(new BasicTypeItem("tin", ItemType.CHUNK, 4, 0));
        tin.addChunk(new BasicTypeItem("tin", ItemType.SMALL_CHUNK, 16, 0));

        copper.setToolMaterial(copperToolMaterial);
        copper.getOresBlock().add(new BasicTypeBlockCutout(BlockType.ORE, "chalcopyrite", 1, 0.8f, 1, 0, 0, "chalcopyrite"));
        copper.getOresBlock().add(new BasicTypeBlockCutout(BlockType.ORE, "malachite", 1.2f, 0.95f, 1, 0, 0, "malachite"));
        copper.setNugget(new BasicTypeItem("copper", ItemType.NUGGET, 64, 0));

        small_iron.getOresBlock().add(new BasicTypeBlockCutout(BlockType.ORE, "iron_small", 0.6f, 0.8f, 1, 0, 0));
        small_iron.getItemsMaterial().add(new BasicTypeItem("slag", ItemType.MISCELLANEA, 32, 0));
        copper.addHammer(new ItemHammer(copper.getToolMaterial(), "copper_hammer", 100));
        copper.addChunk(new BasicTypeItem("copper", ItemType.CHUNK, 4, 0));
        copper.addChunk(new BasicTypeItem("copper", ItemType.SMALL_CHUNK, 16, 0));
        tinToolMaterial.setRepairItem(new ItemStack(tin.getIngot()));
        copperToolMaterial.setRepairItem(new ItemStack(copper.getIngot()));

        gold.getOresBlock().add(new BasicTypeBlockCutout(BlockType.ORE, "small_gold", 0.5f, 0.5f, 2, 0, 0, "gold"));
        gold.getOresBlock().add(new BasicTypeBlockCutout(BlockType.ORE, "scattered_gold", 0.6f, 0.5f, 2, 0, 0, "gold"));


        AdvancedRegistry.register(copper);
        AdvancedRegistry.register(tin);
        AdvancedRegistry.register(small_iron);
        AdvancedRegistry.register(gold);
    }

    public static void initClient() {
        AdvancedRegistry.registerRender(copper);
        AdvancedRegistry.registerRender(tin);
        AdvancedRegistry.registerRender(small_iron);
        AdvancedRegistry.registerRender(gold);
    }

    @SideOnly(Side.CLIENT)
    public static void preInitClient() {
        ModModelLoader.registerModel(copper.getOresBlock().get(0).getRegistryName(), new OreModel(new ResourceLocation(Constants.MODID, "block/ore/chalcopyrite")));
        ModModelLoader.registerModel(copper.getOresBlock().get(1).getRegistryName(), new OreModel(new ResourceLocation(Constants.MODID, "block/ore/malachite")));

        ModModelLoader.registerModel(tin.getOresBlock().get(0).getRegistryName(), new OreModel(new ResourceLocation(Constants.MODID, "block/ore/cassiterite")));
        ModModelLoader.registerModel(tin.getOresBlock().get(1).getRegistryName(), new OreModel(new ResourceLocation(Constants.MODID, "block/ore/stannite")));

        ModModelLoader.registerModel(gold.getOresBlock().get(0).getRegistryName(), new OreModel(new ResourceLocation(Constants.MODID, "block/ore/small_gold")));
        ModModelLoader.registerModel(gold.getOresBlock().get(1).getRegistryName(), new OreModel(new ResourceLocation(Constants.MODID, "block/ore/scattered_gold")));


        GameMaterialRegister.registerBasicItemModel(copper.getNugget());
        GameMaterialRegister.registerBasicItemModel(tin.getIngot());
        GameMaterialRegister.registerItemToolModel(copper.getItemsMaterial().get(0));
        GameMaterialRegister.registerBasicItemModel(copper.getItemsMaterial().get(1));
        GameMaterialRegister.registerBasicItemModel(copper.getItemsMaterial().get(2));

        GameMaterialRegister.registerBasicItemModel(tin.getItemsMaterial().get(0));
        GameMaterialRegister.registerBasicItemModel(tin.getItemsMaterial().get(1));

        GameMaterialRegister.registerBasicItemModel(small_iron.getItemsMaterial().get(0));

        ModModelLoader.registerModel(small_iron.getOresBlock().get(0).getRegistryName(), new OreModel(new ResourceLocation(Constants.MODID, "block/ore/small_iron")));

    }

    @SideOnly(Side.CLIENT)
    public static void registerBasicItemModel(Item item) {
        ModModelLoader.registerModel(item.getRegistryName(), new ItemModel(new ResourceLocation(item.getRegistryName().getResourceDomain(), "item/".concat(((IAdvancedItem) item).getItemType().getPrefix()).concat("/").concat(item.getRegistryName().getResourcePath().substring(0, item.getRegistryName().getResourcePath().length() - ((IAdvancedItem) item).getItemType().getPrefix().length() - 1)))));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemToolModel(Item item) {
        ModModelLoader.registerModel(item.getRegistryName(), new ItemToolModel(new ResourceLocation(item.getRegistryName().getResourceDomain(), "item/".concat(((IAdvancedItem) item).getItemType().getPrefix()).concat("/").concat(item.getRegistryName().getResourcePath().substring(0, item.getRegistryName().getResourcePath().length() - ((IAdvancedItem) item).getItemType().getPrefix().length() - 1)))));
    }
}
