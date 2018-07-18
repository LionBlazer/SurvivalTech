package ru.whitewarrior.survivaltech.api.common.automatic.block.network.conductor;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.whitewarrior.survivaltech.api.common.block.BlockRenderCable;
import ru.whitewarrior.survivaltech.api.common.network.INetworkCable;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;

/**
 * Date: 2017-12-24. 
 * Time: 0:01:43.
 * @author WhiteWarrior
 */
public class BasicBlockCable extends BlockRenderCable implements INetworkCable{
	int conductivity;

	public BasicBlockCable(Material materialIn, int conductivity, String name) {
		super(materialIn);
		.conductivity = conductivity;
		.setRegistryName(name);
		.setUnlocalizedName(name);
		.setHardness(0.3f);
		.setCreativeTab(CreativeTabRegister.MECHANISM);
	}

	@Override
	public int getConductivity(IBlockAccess world, BlockPos pos) {
		return conductivity;
	}

}
