package net.htmlcsjs.htmlTech.api.capability;

import net.htmlcsjs.htmlTech.htmlTech;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class LaserContainerList implements ILaserContainer {
    private final List<ILaserContainer> laserContainerList;
    private long diodeVoltage;
    private long diodeAmperage;

    public LaserContainerList(List<ILaserContainer> laserContainerList) {
        this.laserContainerList = laserContainerList;
    }

    @Override
    public long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
        long amperesUsed = 0L;
        for (ILaserContainer laserContainer : laserContainerList) {
            amperesUsed += laserContainer.acceptEnergyFromNetwork(null, voltage, amperage);
            if (amperage == amperesUsed) break;
        }
        return amperesUsed;
    }

    @Override
    public long changeEnergy(long energyToAdd) {
        long energyAdded = 0L;
        for (ILaserContainer laserContainer : laserContainerList) {
            energyAdded += laserContainer.changeEnergy(energyToAdd - energyAdded);
            if (energyAdded == energyToAdd) break;
        }
        return energyAdded;
    }

    @Override
    public long getEnergyStored() {
        return laserContainerList.stream()
                .mapToLong(ILaserContainer::getEnergyStored)
                .sum();
    }

    @Override
    public long getEnergyCapacity() {
        return laserContainerList.stream()
                .mapToLong(ILaserContainer::getEnergyCapacity)
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

    @Override
    public long getDiodeAmperage() {
        /*try {
            ILaserContainer firstDiode = laserContainerList.get(0);
            return firstDiode.getDiodeAmperage();
        } catch (IndexOutOfBoundsException e) {
            htmlTech.logger.warn(laserContainerList.size());
            return 0;
        }*/
        htmlTech.logger.warn("list size:" + laserContainerList.size());
        return laserContainerList.stream()
                .mapToLong(ILaserContainer::getDiodeAmperage)
                .sum();
    }

    @Override
    public long getDiodeVoltage() {
        /*try {
            ILaserContainer firstDiode = laserContainerList.get(0);
            return firstDiode.getDiodeVoltage();
        } catch (IndexOutOfBoundsException e) {
            htmlTech.logger.warn(laserContainerList.size());
            return 0;
        }*/
        long voltage = laserContainerList.stream()
                .mapToLong(ILaserContainer::getDiodeVoltage)
                .sum();
        htmlTech.logger.info(voltage);
        return voltage;
    }

    @Override
    public void setDiodeAmperage(long amperage) {
        try {
            ILaserContainer firstDiode = laserContainerList.get(0);
            firstDiode.setDiodeAmperage(amperage);
        } catch (IndexOutOfBoundsException e) {
            htmlTech.logger.warn(laserContainerList.size());
        }
    }

    @Override
    public void setDiodeVoltage(long voltage) {
        try {
            ILaserContainer firstDiode = laserContainerList.get(0);
            firstDiode.setDiodeVoltage(voltage);
        } catch (IndexOutOfBoundsException e) {
            htmlTech.logger.warn(laserContainerList.size());
        }
    }
}
