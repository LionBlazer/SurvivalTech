package ru.whitewarrior.survivaltech.registry.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.whitewarrior.survivaltech.api.common.block.BlockEnergy;
import ru.whitewarrior.survivaltech.api.common.block.BlockType;
import ru.whitewarrior.survivaltech.api.common.network.game.NetworkProviderType;
import ru.whitewarrior.survivaltech.api.common.network.game.NetworkType;
import ru.whitewarrior.survivaltech.api.common.tileentity.TileEntityBlock;
import ru.whitewarrior.survivaltech.registry.CreativeTabRegister;
import ru.whitewarrior.survivaltech.registry.tileentity.toolrepairer.TileEntityToolRepairer;

/**
 * Date: 2017-12-31.
 * Time: 20:28:32.
 *
 * @author WhiteWarrior
 */
public class BlockToolRepairer extends BlockEnergy {

    public BlockToolRepairer(BlockType type, String name) {
        super(type);
        name = name.toLowerCase();
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabRegister.MECHANISM);
        this.setHardness(0.5f);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public NetworkProviderType getProviderType(IBlockAccess world, BlockPos pos) {
        // TODO Auto-generated method stub
        return NetworkProviderType.RECEIVER;
    }

    @Override
    public EnumFacing[] getOutputFacing(IBlockAccess world, BlockPos pos) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EnumFacing[] getInputFacing(IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return new EnumFacing[]{EnumFacing.DOWN};
    }

    @Override
    public boolean allOutputInputFacing(IBlockAccess world, BlockPos pos) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public NetworkType getNetworkType() {
        // TODO Auto-generated method stub
        return NetworkType.ELECTRICITY;
    }


    @Override
    public TileEntityBlock createTileEntity(World worldIn, IBlockState state) {
        // TODO Auto-generated method stub
        return new TileEntityToolRepairer();
    }

}
