package net.htmlcsjs.htmlTech.capability;

import net.minecraft.util.EnumFacing;

public interface ILaserContainer {
    long getDiodeAmperage();

    long getDiodeVoltage();

    void setDiodeAmperage(long amperage);

    void setDiodeVoltage(long voltage);

    long acceptEnergyFromNetwork(EnumFacing var1, long var2, long var4);

    boolean inputsEnergy(EnumFacing var1);

    default boolean outputsEnergy(EnumFacing side) {
        return false;
    }

    long changeEnergy(long var1);

    default long addEnergy(long energyToAdd) {
        return this.changeEnergy(energyToAdd);
    }

    default long removeEnergy(long energyToRemove) {
        return this.changeEnergy(-energyToRemove);
    }

    default long getEnergyCanBeInserted() {
        return this.getEnergyCapacity() - this.getEnergyStored();
    }

    long getEnergyStored();

    long getEnergyCapacity();

    default long getOutputAmperage() {
        return 0L;
    }

    default long getOutputVoltage() {
        return 0L;
    }

    long getInputAmperage();

    long getInputVoltage();

    default boolean isOneProbeHidden() {
        return false;
    }
}
