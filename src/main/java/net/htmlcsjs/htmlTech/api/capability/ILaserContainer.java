package net.htmlcsjs.htmlTech.api.capability;

import gregtech.api.capability.IEnergyContainer;

public interface ILaserContainer extends IEnergyContainer {
    long getDiodeAmperage();

    long getDiodeVoltage();

    void setDiodeAmperage(long amperage);

    void setDiodeVoltage(long voltage);
}
