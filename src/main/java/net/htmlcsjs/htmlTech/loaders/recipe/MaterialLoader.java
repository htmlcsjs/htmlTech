package net.htmlcsjs.htmlTech.loaders.recipe;

import static gregtech.api.recipes.RecipeMaps.MIXER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static net.htmlcsjs.htmlTech.api.unification.materials.HTMaterials.FlOPPa;

public class MaterialLoader {
    public static void init() {
        MIXER_RECIPES.recipeBuilder().EUt(122880).duration(200)
                .input(dust, Flerovium, 1)
                .fluidInputs(Oxygen.getFluid(1000))
                .input(dust, Phosphorus, 1)
                .input(dust, Protactinium, 1)
                .output(dust, FlOPPa, 4)
                .buildAndRegister();
    }
}
