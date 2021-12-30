package net.htmlcsjs.htmlTech.loaders.recipe;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.htmlcsjs.htmlTech.common.blocks.BlockHTCasing;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.foil;
import static gregtech.api.unification.ore.OrePrefix.wireFine;
import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.HT_CASING;
import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.LASER_PIPES;

public class LaserEquipmentLoader {

    public static void init() {
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(foil, RhodiumPlatedPalladium, 2)
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .output(LASER_PIPES[0], 1)
                .EUt(VA[IV])
                .duration(40)
                .buildAndRegister();

        ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(16)
                .inputs(MetaBlocks.METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.ALUMINIUM_FROSTPROOF))
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(HT_CASING.getItemVariant(BlockHTCasing.CasingType.PBI_CASING))
                .duration(50)
                .buildAndRegister();

    }
}
