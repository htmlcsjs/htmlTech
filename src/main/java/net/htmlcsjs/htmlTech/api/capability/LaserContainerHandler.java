package net.htmlcsjs.htmlTech.api.capability;

import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraftforge.common.capabilities.Capability;

public class LaserContainerHandler extends EnergyContainerHandler implements ILaserContainer {
    private long diodeVoltage;
    private long diodeAmperage;

    public LaserContainerHandler(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage, long maxInputAmperage, long maxOutputVoltage, long maxOutputAmperage) {
        super(tileEntity, maxCapacity, maxInputVoltage, maxInputAmperage, maxOutputVoltage, maxOutputAmperage);
    }

    public static LaserContainerHandler emitterContainer(MetaTileEntity tileEntity, long maxCapacity, long maxOutputVoltage, long maxOutputAmperage) {
        return new LaserContainerHandler(tileEntity, maxCapacity, 0L, 0L, maxOutputVoltage, maxOutputAmperage);
    }

    public static LaserContainerHandler receiverContainer(MetaTileEntity tileEntity, long maxCapacity, long maxInputVoltage, long maxInputAmperage) {
        return new LaserContainerHandler(tileEntity, maxCapacity, maxInputVoltage, maxInputAmperage, 0L, 0L);
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability.equals(HtmlTechCapabilities.LASER_CONTAINER)) {
            return HtmlTechCapabilities.LASER_CONTAINER.cast(this);
        } else {
            return null;
        }
    }

    @Override
    public long getDiodeAmperage() {
        return diodeAmperage;
    }

    @Override
    public long getDiodeVoltage() {
        return diodeVoltage;
    }

    @Override
    public void setDiodeAmperage(long amperage) {
        diodeAmperage = amperage;
    }

    @Override
    public void setDiodeVoltage(long voltage) {
        diodeVoltage = voltage;
    }
}
