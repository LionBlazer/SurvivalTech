package ru.whitewarrior.survivaltech.handler.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ru.whitewarrior.survivaltech.api.common.tileentity.gui.ITileEntityGui;

/**
 * Date: 2017-12-29. Time: 13:36:50.
 * 
 * @author WhiteWarrior
 */
public class BlockGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ITileEntityGui && ((ITileEntityGui)tile).hasGui(world, pos)) {
			ITileEntityGui tileGui = (ITileEntityGui) tile;
			return tileGui.getContainer(player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof ITileEntityGui && ((ITileEntityGui)tile).hasGui(world, pos)) {
			ITileEntityGui tileGui = (ITileEntityGui) tile;
			return tileGui.getGuiContainer(player);
		}
		return null;
	}

}
