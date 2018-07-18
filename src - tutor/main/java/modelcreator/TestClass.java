package modelcreator;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = "testmod")
public class TestClass {
    static Item testItem;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        testItem = new Item();
        testItem.setRegistryName("testitem");

        ForgeRegistries.ITEMS.register(testItem);
        MinecraftForge.EVENT_BUS.register(new TestClass());
    }
    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(testItem,
                0, new ModelResourceLocation(testItem.getRegistryName(),
                        "inventory"));

    }

    @SubscribeEvent
    public void eventRegisterModel(ModelRegistryEvent event){
        ModelLoaderRegistry.registerLoader(new TestCustomModelLoader());
    }


}
