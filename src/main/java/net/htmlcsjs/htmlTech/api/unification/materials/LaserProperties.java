package net.htmlcsjs.htmlTech.api.unification.materials;

import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;

import java.util.Objects;

public class LaserProperties implements IMaterialProperty<LaserProperties> {

    public final long voltage;
    public final long amperage;
    public final double efficiency;

    public LaserProperties(long voltage, long amperage, double efficiency) {
        this.voltage = voltage;
        this.amperage = amperage;
        this.efficiency = efficiency;
    }

    public LaserProperties(long voltage, long amperage) {
        this(voltage, amperage, 1.0);
    }

    /**
     * Default values constructor
     */
    public LaserProperties() { this(32, 128, 0.2); }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {
        materialProperties.ensureSet(PropertyKey.FLUID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaserProperties)) return false;
        LaserProperties that = (LaserProperties) o;
        return voltage == that.voltage &&
                amperage == that.amperage &&
                efficiency == that.efficiency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voltage, amperage, efficiency);
    }
}
