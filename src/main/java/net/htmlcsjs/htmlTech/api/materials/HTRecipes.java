package net.htmlcsjs.htmlTech.api.materials;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static net.htmlcsjs.htmlTech.api.materials.HTMaterials.*;

public class HTRecipes {

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
