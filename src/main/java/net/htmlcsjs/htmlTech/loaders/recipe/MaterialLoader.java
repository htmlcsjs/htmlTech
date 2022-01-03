package net.htmlcsjs.htmlTech.loaders.recipe;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.MIXER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static net.htmlcsjs.htmlTech.api.unification.materials.HTMaterials.FlOPPa;
import static net.htmlcsjs.htmlTech.api.unification.materials.HTMaterials.NaquadriaTetratrinite;

public class MaterialLoader {
    public static void init() {
        MIXER_RECIPES.recipeBuilder().EUt(VA[ZPM]).duration(200)
                .input(dust, Flerovium, 1)
                .fluidInputs(Oxygen.getFluid(1000))
                .input(dust, Phosphorus, 1)
                .input(dust, Protactinium, 1)
                .output(dust, FlOPPa, 4)
                .buildAndRegister();

        MIXER_RECIPES.recipeBuilder().EUt(VA[UV]).duration(2000)
                .fluidInputs(Naquadria.getFluid(20), Trinium.getFluid(80))
                .fluidOutputs(NaquadriaTetratrinite.getFluid(100))
                .buildAndRegister();
    }
}
