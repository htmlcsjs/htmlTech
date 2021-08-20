package net.htmlcsjs.htmlTech.api.materials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.*;

@IMaterialHandler.RegisterMaterialHandler
public class HTMaterials implements IMaterialHandler {

    /* Clamed Range 21000 to 21499 */
    // Materials
    public static Material FlOPPa;

    // Flags
    public static final MaterialFlag GENERATE_LASER;
    static {
        GENERATE_LASER = new MaterialFlag.Builder(64, "generate_lasers").build();
    }

    // Properties
    public static final PropertyKey<LaserProperties> LASER = new PropertyKey<>("laser", LaserProperties.class);

    public static void register() {
        // New Materials
        FlOPPa = new Material.Builder(21000, "floppa")
                .fluid().blastTemp(6900)
                .color(0xac8353).iconSet(SHINY)
                .components(Flerovium, 1, Oxygen, 1, Phosphorus, 1, Protactinium, 1)
                .flags(EXT2_METAL, DECOMPOSITION_BY_ELECTROLYZING)
                //.properties.setProperty(LASER, new LaserProperties((int) GTValues.V[5], 128, 2))
                .build();

        // Adding Flags to Materials
        Diamond.setProperty(LASER, new LaserProperties((int) GTValues.V[5], 128, 2));
        Ruby.setProperty(LASER, new LaserProperties((int) GTValues.V[6], 256, 2));
    }

    @Override
    public void onMaterialsInit() {
        register();
    }

    // Properties Builders
/*
    public Material.Builder laserProperties(long voltage, int amperage, double efficiency) {
        properties.setProperty(LASER, new LaserProperties((int) voltage, amperage, efficiency));
        return this;
    }*/
}
