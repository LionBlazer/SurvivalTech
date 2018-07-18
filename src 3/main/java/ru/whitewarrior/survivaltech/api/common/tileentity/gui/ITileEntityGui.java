package ru.whitewarrior.survivaltech.api.common.tileentity.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Date: 2017-12-29. 
 * Time: 13:28:20.
 * @author WhiteWarrior
 */
public interface ITileEntityGui {
	boolean hasGui(World world, BlockPos pos);

	@SideOnly(Side.CLIENT)
	Object getGuiContainer(EntityPlayer player);
	Object getContainer(EntityPlayer player);
}
