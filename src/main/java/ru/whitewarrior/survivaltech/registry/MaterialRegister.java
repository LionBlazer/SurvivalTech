package ru.whitewarrior.survivaltech.registry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import ru.whitewarrior.survivaltech.Constants;
import ru.whitewarrior.survivaltech.api.common.container.slot.SlotSolidFuel;
import ru.whitewarrior.survivaltech.registry.item.ItemOreMap;
import ru.whitewarrior.survivaltech.registry.recipe.HammerRecipes;
import ru.whitewarrior.survivaltech.registry.tileentity.smallarcfurnace.recipe.SmallArcFurnaceRecipe;
import ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.recipe.ToolRepairerRecipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Date: 2017-12-29. Time: 19:18:16.
 * 
 * @author WhiteWarrior
 */
public class MaterialRegister {
	public static HashMap<Item, Integer> fuel = new HashMap<Item, Integer>();
	public static final Material MECHANISM = new Material(MapColor.IRON);
	public static final ToolMaterial WRENCH = EnumHelper.addToolMaterial("wrench", 0, 100, 1, 0, 0);
	public static final ToolMaterial ELECTRIC = EnumHelper.addToolMaterial("electric", 2, 100, 1, 0, 0);
	public static void init() {
     //   CraftingManager.REGISTRY.register(CraftingManager.REGISTRY.getKeys().size(), new ResourceLocation("hammers"), new HammerRecipes());
     //   CraftingManager.
        HammerRecipes cableCopper = new HammerRecipes(new ItemStack(GameMaterialRegister.copper.getIngot()), new ItemStack(BlockRegister.copperCable));
        cableCopper.setRegistryName("cableCopper");
        ForgeRegistries.RECIPES.register(cableCopper);

        HammerRecipes cableTin = new HammerRecipes(new ItemStack(GameMaterialRegister.tin.getIngot()), new ItemStack(BlockRegister.tinCable));
        cableTin.setRegistryName("cableTin");
        ForgeRegistries.RECIPES.register(cableTin);
        //FMLCommonHandler.instance().BU

        HammerRecipes crushedGold = new HammerRecipes(new ItemStack(GameMaterialRegister.gold.getOresBlock().get(1)), new ItemStack(GameMaterialRegister.gold.getCrushedOre()));
        crushedGold.setRegistryName("crushedGold");
        ForgeRegistries.RECIPES.register(crushedGold);

        HammerRecipes copperIngot = new HammerRecipes(new ItemStack(GameMaterialRegister.copper.getItemsMaterial().get(1)), new ItemStack(GameMaterialRegister.copper.getIngot()));
        copperIngot.setRegistryName("copperIngot");
        ForgeRegistries.RECIPES.register(copperIngot);

        HammerRecipes tinIngot = new HammerRecipes(new ItemStack(GameMaterialRegister.tin.getItemsMaterial().get(0)), new ItemStack(GameMaterialRegister.tin.getIngot()));
        tinIngot.setRegistryName("tinIngot");
        ForgeRegistries.RECIPES.register(tinIngot);

		fuel.put(Items.COAL, 100);
		fuel.put(Item.getItemFromBlock(Blocks.COAL_BLOCK), fuel.get(Items.COAL)*9);
		SlotSolidFuel.itemsValidSet.add(Items.COAL);
		SlotSolidFuel.itemsValidSet.add(Item.getItemFromBlock(Blocks.COAL_BLOCK));
		SlotSolidFuel.itemsValidSet.add(Item.getItemFromBlock(Blocks.PLANKS));
		SlotSolidFuel.itemsValidSet.add(Item.getItemFromBlock(Blocks.LOG));
		SlotSolidFuel.itemsValidSet.add(Item.getItemFromBlock(Blocks.LOG2));
		ToolRepairerRecipes.registerRecipe(Items.IRON_PICKAXE, Items.IRON_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.IRON_AXE, Items.IRON_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.IRON_HOE, Items.IRON_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.IRON_SHOVEL, Items.IRON_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.IRON_SWORD, Items.IRON_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.GOLDEN_PICKAXE, Items.GOLD_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.GOLDEN_AXE, Items.GOLD_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.GOLDEN_HOE, Items.GOLD_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.GOLDEN_SHOVEL, Items.GOLD_INGOT);
		ToolRepairerRecipes.registerRecipe(Items.GOLDEN_SWORD, Items.GOLD_INGOT);


        GameRegistry.addSmelting(GameMaterialRegister.small_iron.getOresBlock().get(0), new ItemStack(Items.IRON_NUGGET, 3), 0.1f);
        GameRegistry.addSmelting(GameMaterialRegister.copper.getItemsMaterial().get(1), new ItemStack(GameMaterialRegister.copper.getNugget(), 4), 0.1f);
        GameRegistry.addSmelting(GameMaterialRegister.copper.getOresBlock().get(1), new ItemStack(GameMaterialRegister.copper.getItemsMaterial().get(2), 3), 0.1f);

        GameRegistry.addSmelting(GameMaterialRegister.gold.getOresBlock().get(0), new ItemStack(Items.GOLD_NUGGET, 7), 0.1f);
        GameRegistry.addSmelting(GameMaterialRegister.redstone.getOresBlock().get(0), new ItemStack(Items.REDSTONE, 8), 0.1f);


        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"copperIngot1"), null, new ItemStack(GameMaterialRegister.copper.getIngot()), "TTT", "TTT", "TTT", 'T', "nuggetCopper");
        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"copperBlock1"), null, new ItemStack(GameMaterialRegister.copper.getFullBlock()), "TTT", "TTT", "TTT", 'T', "ingotCopper");

        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"hammerCopper"), null, new ItemStack(GameMaterialRegister.copper.getItemsMaterial().get(0)), "TTT", "TTT", " S ", 'T', "ingotCopper", 'S', Items.STICK);
        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"hammerIron"), null, new ItemStack(ItemRegister.ironHammer), "TTT", "TTT", " S ", 'T', "ingotIron", 'S', Items.STICK);
        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"chunkCopper"), null, new ItemStack(GameMaterialRegister.copper.getItemsMaterial().get(1)), "TTT", "T T", "TTT", 'T', "smallChunkCopper");


        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"coalGenerator"), null, new ItemStack(BlockRegister.coalGenerator), "  Z", "CBP", "III", 'Z', Blocks.COBBLESTONE_WALL, 'C', "ingotCopper", 'B', Blocks.STONE, 'P', Blocks.FURNACE, 'I', "ingotIron");
        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"smallStorage"), null, new ItemStack(BlockRegister.smallStorage), "SWS", "AAA", "SWS", 'A', new ItemStack(ItemRegister.battery), 'S', new ItemStack(Blocks.STONE_SLAB, 1,3), 'W', BlockRegister.copperCable);
        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"battery"), null, new ItemStack(ItemRegister.battery), "FRF", "WCW", "WCW", 'F', BlockRegister.copperCable, 'C', "ingotCopper", 'W', Blocks.WOOL, 'R', Items.REDSTONE);
        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"smallArcFurnace"), null, new ItemStack(BlockRegister.smallArcFurnace), "IWI", "CSC", "WCW", 'C', Blocks.COBBLESTONE, 'W', BlockRegister.copperCable, 'S', BlockRegister.smallStorage, 'I', Blocks.IRON_BLOCK);
        GameRegistry.addShapedRecipe(new ResourceLocation(Constants.MODID,"toolRepairer"), null, new ItemStack(BlockRegister.toolRepairer), "Z Z", "SNS", "ZCZ", 'Z', Blocks.COBBLESTONE_WALL, 'C', BlockRegister.copperCable, 'S', new ItemStack(Blocks.STONE_SLAB, 1,3), 'N', Blocks.ANVIL);

        VillagerRegistry.VillagerCareer cartographer = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:librarian")).getCareer(1);
        cartographer.addTrade(1, new EntityVillager.ITradeList() {
            @Override
            public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {

                recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 20),ItemOreMap.setupNewMap(merchant.getWorld(), random.nextInt(1000) - 500, random.nextInt(1000) - 500,(byte)2,true,true)));
            }
        });
        GameRegistry.addShapelessRecipe(new ResourceLocation(Constants.MODID,"copperIngot2"), null,new ItemStack(GameMaterialRegister.copper.getIngot(),9), Ingredient.fromItem(Item.getItemFromBlock(GameMaterialRegister.copper.getFullBlock())));

        SmallArcFurnaceRecipe.RECIPES.add(new SmallArcFurnaceRecipe(
                        Arrays.asList(
                                new ItemStack(GameMaterialRegister.small_iron.getItemsMaterial().get(0))
                        ),
                        Arrays.asList(
                            new ItemStack(Items.IRON_NUGGET, 8)
                        ), 2000));
        SmallArcFurnaceRecipe.RECIPES.add(new SmallArcFurnaceRecipe(
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.copper.getItemsMaterial().get(2), 4)
                ),
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.copper.getItemsMaterial().get(1), 1)
                ), 200));
        SmallArcFurnaceRecipe.RECIPES.add(new SmallArcFurnaceRecipe(
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.tin.getItemsMaterial().get(1), 4)
                ),
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.tin.getItemsMaterial().get(0), 1)
                ), 200));
        SmallArcFurnaceRecipe.RECIPES.add(new SmallArcFurnaceRecipe(
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.tin.getOresBlock().get(0))
                ),
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.tin.getItemsMaterial().get(0), 2)
                ), 300));

        SmallArcFurnaceRecipe.RECIPES.add(new SmallArcFurnaceRecipe(
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.tin.getOresBlock().get(1))
                ),
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.tin.getItemsMaterial().get(1), 3)
                ), 3000));

        SmallArcFurnaceRecipe.RECIPES.add(new SmallArcFurnaceRecipe(
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.gold.getCrushedOre())
                ),
                Arrays.asList(
                        new ItemStack(Items.GOLD_INGOT, 1)
                ), 500));


        SmallArcFurnaceRecipe.RECIPES.add(new SmallArcFurnaceRecipe(
                Arrays.asList(

                        new ItemStack(Blocks.SAND),
                        new ItemStack(GameMaterialRegister.copper.getOresBlock().get(0))
                ),
                Arrays.asList(
                        new ItemStack(GameMaterialRegister.copper.getItemsMaterial().get(2)),
                        new ItemStack(GameMaterialRegister.small_iron.getItemsMaterial().get(0))
                ), 200));

    }
	


}
