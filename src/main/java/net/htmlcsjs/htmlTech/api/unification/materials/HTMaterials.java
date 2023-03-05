package net.htmlcsjs.htmlTech.api.unification.materials;

import gregtech.api.fluids.fluidType.FluidTypes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.properties.DustProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.SHINY;
import static gregtech.api.unification.material.properties.PropertyKey.DUST;

public class HTMaterials {

    /* Clamed Range 21000 to 21499 */
    // Materials
    public static Material FlOPPa;
    public static Material NaquadriaTetratrinite;

    // Flags
    public static final MaterialFlag GENERATE_LASER;
    static {
        GENERATE_LASER = new MaterialFlag.Builder("generate_lasers").build();
    }

    // Properties
    public static final PropertyKey<LaserProperties> LASER = new PropertyKey<>("laser", LaserProperties.class);

    public static void register() {
        // New Materials

        // Maybe balance?
        FlOPPa = new Material.Builder(21000, "floppa")
                .ingot(32).blastTemp(6900)
                .color(0xac8353).iconSet(SHINY)
                .components(Flerovium, 1, Oxygen, 1, Phosphorus, 1, Protactinium, 1)
                .flags(EXT2_METAL, DECOMPOSITION_BY_ELECTROLYZING)
                //.properties.setProperty(LASER, new LaserProperties((int) GTValues.V[5], 128, 2))
                .toolStats(new ToolProperty(128F, 50.0F, 4390983, 128))
                .rotorStats(128F, 10F, 11888800)
                .build();

        NaquadriaTetratrinite = new Material.Builder(21001, "naquadria_tetratrinite")
                .fluid(FluidTypes.GAS).fluidTemp(32012)
                .components(Naquadria, 1, Trinium, 4)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .color(0x560909)
                .build();


        // Adding Properties to Materials
        CarbonDioxide.setProperty(LASER, new LaserProperties(V[IV], 256));
        Helium.setProperty(LASER, new LaserProperties(V[LuV], 256));
        Neon.setProperty(LASER, new LaserProperties(V[ZPM], 256));
        Argon.setProperty(LASER, new LaserProperties(V[UV], 256));
        Krypton.setProperty(LASER, new LaserProperties(V[IV], 1024));
        Xenon.setProperty(LASER, new LaserProperties(V[LuV], 1024));
        Radon.setProperty(LASER, new LaserProperties(V[ZPM], 1024));
        NaquadriaTetratrinite.setProperty(LASER, new LaserProperties(V[UV], 1024));


        // Adding Flags to Materials
        Protactinium.setProperty(DUST, new DustProperty());
        Flerovium.setProperty(DUST, new DustProperty());
        RhodiumPlatedPalladium.addFlags(GENERATE_FOIL);
        Trinium.addFlags(GENERATE_FRAME);
        Titanium.addFlags(GENERATE_FOIL);
        Darmstadtium.addFlags(GENERATE_FOIL);
        UraniumRhodiumDinaquadide.addFlags(GENERATE_SPRING);

    }

    public static void init() {
        //HtmlTech.logger.info("Register htmltech materials"); WHY TF DOES THIS CRASH
        register();
    }
}
