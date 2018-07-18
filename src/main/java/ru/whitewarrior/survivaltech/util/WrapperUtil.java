package ru.whitewarrior.survivaltech.util;

import net.minecraft.tileentity.TileEntity;
import ru.whitewarrior.survivaltech.registry.tileentity.redstonefluxconverter.TileEntityRedStoneFluxAntiConverter;
import ru.whitewarrior.survivaltech.registry.tileentity.redstonefluxconverter.TileEntityRedStoneFluxConverter;

public class WrapperUtil {
    public static TileEntity createTileRfConvertr() {
        return new TileEntityRedStoneFluxConverter();
    }

    public static TileEntity createTileRfAntiConvertr() {
        return new TileEntityRedStoneFluxAntiConverter();
    }
}
