package net.htmlcsjs.htmlTech.api.unification.materials;

import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;

import java.util.Objects;

public class LaserProperties implements IMaterialProperty<LaserProperties> {

    public final int voltage;
    public final int amperage;
    public final double efficiency;

    public LaserProperties(int voltage, int amperage, double efficiency) {
        this.voltage = voltage;
        this.amperage = amperage;
        this.efficiency = efficiency;
    }

    /**
     * Default values constructor
     */
    public LaserProperties() { this(32, 128, 0.2); }

    @Override
    public void verifyProperty(MaterialProperties materialProperties) {
        materialProperties.ensureSet(PropertyKey.GEM);
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
