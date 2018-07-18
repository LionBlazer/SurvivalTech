package ru.whitewarrior.survivaltech.api.common.energy.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import ru.whitewarrior.survivaltech.api.common.energy.ElectricEnergyStorage;
import ru.whitewarrior.survivaltech.api.common.energy.IEnergyElectric;

/**
 * Date: 2017-12-31.
 * Time: 17:10:07.
 * @author WhiteWarrior
 */
public class CapabilityElectricEnergy
{
    @CapabilityInject(IEnergyElectric.class)
    public static Capability<IEnergyElectric> ENERGY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IEnergyElectric.class, new IStorage<IEnergyElectric>()
        {
            @Override
            public NBTBase writeNBT(Capability<IEnergyElectric> capability, IEnergyElectric instance, EnumFacing side)
            {
                return new NBTTagDouble(instance.getEnergyStored());
            }

            @Override
            public void readNBT(Capability<IEnergyElectric> capability, IEnergyElectric instance, EnumFacing side, NBTBase nbt)
            {
                if (!(instance instanceof ElectricEnergyStorage))
                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
                ((ElectricEnergyStorage)instance).setEnergy(((NBTTagDouble)nbt).getDouble());
            }
        },
        () -> new ElectricEnergyStorage(1000));
    }
}