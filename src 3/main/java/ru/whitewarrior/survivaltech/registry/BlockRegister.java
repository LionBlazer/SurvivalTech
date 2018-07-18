package ru.whitewarrior.survivaltech.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import ru.whitewarrior.survivaltech.AdvancedRegistry;
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
	public static BlockSolidFuelGenerator coalgenerator = new BlockSolidFuelGenerator(BlockType.MECHANISM, "solidfuelgenerator");
	public static BlockSmallEnergyStorage smallstorage = new BlockSmallEnergyStorage(BlockType.MECHANISM, "smallenergystorage");
	public static BlockToolRepairer toolrepairer = new BlockToolRepairer(BlockType.MECHANISM, "toolRepairer");
	public static BlockFluidTank fluidtank = new BlockFluidTank(BlockType.GLASS, "fluidTank");
	
	public static void preInit() {
		AdvancedRegistry.register(copperCable);
		AdvancedRegistry.register(tinCable);
        AdvancedRegistry.register(superconductorCable);
		AdvancedRegistry.register(coalgenerator);
		AdvancedRegistry.register(smallstorage);
		AdvancedRegistry.register(toolrepairer);
		AdvancedRegistry.register(fluidtank);
	}
	
	public static void initClient() {
		AdvancedRegistry.registerRender(copperCable);
		AdvancedRegistry.registerRender(tinCable);
		AdvancedRegistry.registerRender(superconductorCable);
		AdvancedRegistry.registerRender(coalgenerator);
		AdvancedRegistry.registerRender(smallstorage);
		AdvancedRegistry.registerRender(toolrepairer);
		AdvancedRegistry.registerRender(fluidtank);
	}
}
