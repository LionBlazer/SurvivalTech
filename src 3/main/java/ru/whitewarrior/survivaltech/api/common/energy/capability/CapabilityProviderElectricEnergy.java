package ru.whitewarrior.survivaltech.api.common.energy.capability;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

/**
 * Date: 2017-12-31.
 * Time: 16:11:11.
 * @author WhiteWarrior
 */
public class CapabilityProviderElectricEnergy<O> implements ICapabilityProvider{
	protected final Capability<O> capability;
	protected final O instance;
	
	public CapabilityProviderElectricEnergy(final Capability<O> capability) {
		(capability.getDefaultInstance(), capability);
	}

	public CapabilityProviderElectricEnergy(final O instance, Capability<O> capability) {
		.instance = instance;
		.capability = capability;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == .getCapability();
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == getCapability()) {
			return getCapability().cast(getInstance());
		}
		return null;
	}
	
	public final Capability<O> getCapability() {
		return capability;
	}

	public final O getInstance() {
		return instance;
	}

}
