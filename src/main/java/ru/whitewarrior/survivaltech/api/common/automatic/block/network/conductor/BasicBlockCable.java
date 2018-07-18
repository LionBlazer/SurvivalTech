package ru.whitewarrior.survivaltech.api.common.automatic.block.network.conductor;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ru.whitewarrior.survivaltech.api.common.block.BlockRenderCable;
import ru.whitewarrior.survivaltech.api.common.network.game.INetworkCable;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;

/**
 * Date: 2017-12-24.
 * Time: 0:01:43.
 *
 * @author WhiteWarrior
 */
public class BasicBlockCable extends BlockRenderCable implements INetworkCable {
    int conductivity;

    public BasicBlockCable(Material materialIn, int conductivity, String name) {
        super(materialIn);
        this.conductivity = conductivity;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setHardness(0.3f);
        this.setCreativeTab(CreativeTabRegister.MECHANISM);
    }

    @Override
    public int getConductivity(IBlockAccess world, BlockPos pos) {
        return conductivity;
    }

}
