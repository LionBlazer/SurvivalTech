package ru.whitewarrior.survivaltech.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.Loader;
import ru.whitewarrior.survivaltech.AdvancedRegistry;
import ru.whitewarrior.survivaltech.api.client.model.ModModelLoader;
import ru.whitewarrior.survivaltech.api.client.model.imodel.LaunchingPadModel;
import ru.whitewarrior.survivaltech.api.common.automatic.block.network.conductor.BasicBlockCable;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.registry.block.*;

/**
 * Date: 2017-12-23. 
 * Time: 23:45:39.
 * @author WhiteWarrior
 */
public class BlockRegister {
	
	public static Block copperCable = new BasicBlockCable(Material.ROCK, 1, "copper_cable");
	public static Block tinCable = new BasicBlockCable(Material.ROCK, 2, "tin_cable");
	public static Block superconductorCable = new BasicBlockCable(Material.GLASS, 0, "superconductor_cable");
	public static Block coalGenerator = new BlockSolidFuelGenerator(BlockType.MECHANISM, "solid_fuel_generator");
	public static Block smallStorage = new BlockSmallEnergyStorage(BlockType.MECHANISM, "small_energy_storage");
	public static Block toolRepairer = new BlockToolRepairer(BlockType.MECHANISM, "tool_repairer");
	public static Block fluidTank = new BlockFluidTank(BlockType.GLASS, "fluid_tank");
    public static Block ledMachine = new BlockLedMachine(BlockType.MECHANISM, "led_machine");
    public static Block smallArcFurnace = new BlockSmallArcFurnace(BlockType.MECHANISM, "small_arc_furnace");

    public static Block launchingPad = new BlockLaunchingPad(BlockType.MECHANISM, "launching_pad");

    public static Block energyConverter;
    public static Block energyAntiConverter;

	public static void preInit() {
		AdvancedRegistry.register(copperCable);
		AdvancedRegistry.register(tinCable);
        AdvancedRegistry.register(superconductorCable);
		AdvancedRegistry.register(coalGenerator);
		AdvancedRegistry.register(smallStorage);
		AdvancedRegistry.register(toolRepairer);
		AdvancedRegistry.register(fluidTank);
        AdvancedRegistry.register(ledMachine);
        AdvancedRegistry.register(smallArcFurnace);
        AdvancedRegistry.register(launchingPad);

        if(Loader.isModLoaded("redstoneflux")) {
            energyConverter = new BlockRedStoneFluxConverter(BlockType.MECHANISM, "energy_converter");
            energyAntiConverter = new BlockRedStoneFluxAntiConverter(BlockType.MECHANISM, "energy_anti_converter");
            AdvancedRegistry.register(energyConverter);
            AdvancedRegistry.register(energyAntiConverter);
        }
	}

    public static void preInitClient() {
        ModModelLoader.registerModel(launchingPad.getRegistryName(), new LaunchingPadModel());
    }
	
	public static void initClient() {
		AdvancedRegistry.registerRender(copperCable);
		AdvancedRegistry.registerRender(tinCable);
		AdvancedRegistry.registerRender(superconductorCable);
		AdvancedRegistry.registerRender(coalGenerator);
		AdvancedRegistry.registerRender(smallStorage);
		AdvancedRegistry.registerRender(toolRepairer);
		AdvancedRegistry.registerRender(fluidTank);
        AdvancedRegistry.registerRender(ledMachine);
        AdvancedRegistry.registerRender(smallArcFurnace);
        AdvancedRegistry.registerRender(energyConverter);
        AdvancedRegistry.registerRender(energyAntiConverter);
        AdvancedRegistry.registerRender(launchingPad);
	}
}
