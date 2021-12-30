package net.htmlcsjs.htmlTech.api.materials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.PropertyKey;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DECOMPOSITION_BY_ELECTROLYZING;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_FOIL;
import static gregtech.api.unification.material.info.MaterialIconSet.SHINY;

public class HTMaterials {

    /* Clamed Range 21000 to 21499 */
    // Materials
    public static Material FlOPPa;

    // Flags
    public static final MaterialFlag GENERATE_LASER;
    static {
        GENERATE_LASER = new MaterialFlag.Builder("generate_lasers").build();
    }

    // Properties
    public static final PropertyKey<LaserProperties> LASER = new PropertyKey<>("laser", LaserProperties.class);

    public static void register() {
        // New Materials
        FlOPPa = new Material.Builder(21000, "floppa")
                .ingot(32).blastTemp(6900)
                .color(0xac8353).iconSet(SHINY)
                .components(Flerovium, 1, Oxygen, 1, Phosphorus, 1, Protactinium, 1)
                .flags(EXT2_METAL, DECOMPOSITION_BY_ELECTROLYZING)
                //.properties.setProperty(LASER, new LaserProperties((int) GTValues.V[5], 128, 2))
                .toolStats(128, 50, 2621440, 128)
                .build();

        // Adding Flags to Materials
        Diamond.setProperty(LASER, new LaserProperties((int) GTValues.V[5], 128, 2));
        Ruby.setProperty(LASER, new LaserProperties((int) GTValues.V[6], 256, 2));

        Protactinium.setHidden(false);
        Flerovium.setHidden(false);

        RhodiumPlatedPalladium.addFlags(GENERATE_FOIL);
    }

    public static void init() {
        //HtmlTech.logger.info("Register htmltech materials"); WHY TF DOES THIS CRASH
        register();
    }
}
