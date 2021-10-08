package net.htmlcsjs.htmlTech.api.capability;

import gregtech.api.capability.IEnergyContainer;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class LaserContainerList implements ILaserContainer {
    private final List<ILaserContainer> laserContainerList;

    public LaserContainerList(List<ILaserContainer> laserContainerList) {
        this.laserContainerList = laserContainerList;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long amperesUsed = 0L;
        for (IEnergyContainer energyContainer : laserContainerList) {
            amperesUsed += energyContainer.acceptEnergyFromNetwork(null, voltage, amperage);
            if (amperage == amperesUsed) break;
        }
        return amperesUsed;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        long energyAdded = 0L;
        for (IEnergyContainer energyContainer : laserContainerList) {
            energyAdded += energyContainer.changeEnergy(energyToAdd - energyAdded);
            if (energyAdded == energyToAdd) break;
        }
        return energyAdded;
    }

    @Override
    public long getEnergyStored() {
        return laserContainerList.stream()
                .mapToLong(IEnergyContainer::getEnergyStored)
                .sum();
    }

    @Override
    public long getEnergyCapacity() {
        return laserContainerList.stream()
                .mapToLong(IEnergyContainer::getEnergyCapacity)
                .sum();
    }

    @Override
    public long getInputAmperage() {
        return 1L;
    }

    @Override
    public long getOutputAmperage() {
        return 1L;
    }

    @Override
    public long getInputVoltage() {
        return laserContainerList.stream()
                .mapToLong(v -> v.getInputVoltage() * v.getInputAmperage())
                .sum();
    }

    @Override
    public long getOutputVoltage() {
        return laserContainerList.stream()
                .mapToLong(v -> v.getOutputVoltage() * v.getOutputAmperage())
                .sum();
    }

    @Override
    public boolean inputsEnergy(EnumFacing side) {
        return true;
    }

    @Override
    public boolean outputsEnergy(EnumFacing side) {
        return true;
    }
}
