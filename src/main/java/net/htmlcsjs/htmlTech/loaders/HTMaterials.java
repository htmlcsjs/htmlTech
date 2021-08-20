package net.htmlcsjs.htmlTech.loaders;

import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlag;

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
        GENERATE_LASER = (new MaterialFlag.Builder(64, "generate_lasers")).build();
    }

    public static void register() {
        FlOPPa = new Material.Builder(21000, "floppa")
                .ingot().fluid().blastTemp(6900)
                .color(0xac8353).iconSet(SHINY)
                .components(Flerovium, 1, Oxygen, 1, Phosphorus, 1, Protactinium, 1)
                .flags(EXT2_METAL, GENERATE_LASER, DECOMPOSITION_BY_ELECTROLYZING)
                .build();
    }

    @Override
    public void onMaterialsInit() {
        register();
    }
}
