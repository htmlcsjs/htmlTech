package net.htmlcsjs.htmlTech.loaders.recipe.handlers;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import net.htmlcsjs.htmlTech.api.unification.materials.HTMaterials;
import net.htmlcsjs.htmlTech.api.unification.materials.LaserProperties;
import net.htmlcsjs.htmlTech.common.item.HTMetaItems;

import static gregtech.api.GTValues.VA;
import static gregtech.api.recipes.RecipeMaps.CANNER_RECIPES;
import static net.htmlcsjs.htmlTech.api.unification.materials.HTOrePrefix.laser;

public class LaserHandler {

    public static void register() {
        laser.addProcessingHandler(HTMaterials.LASER, LaserHandler::generateLaserCanningRecipe);
    }

    private static void generateLaserCanningRecipe (OrePrefix orePrefix, Material material, LaserProperties properties) {
        CANNER_RECIPES.recipeBuilder()
                .input(HTMetaItems.EMPTY_LASER)
                .fluidInputs(material.getFluid(1000))
                .output(orePrefix, material)
                .duration(20000)
                .EUt(VA[GTUtility.getTierByVoltage(properties.voltage)])
                .buildAndRegister();
    }
}
