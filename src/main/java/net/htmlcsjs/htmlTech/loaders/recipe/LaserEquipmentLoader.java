package net.htmlcsjs.htmlTech.loaders.recipe;

import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.loaders.recipe.CraftingComponent;
import net.htmlcsjs.htmlTech.common.HTConfig;
import net.htmlcsjs.htmlTech.common.blocks.BlockHTCasing;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static net.htmlcsjs.htmlTech.api.HTValues.mteLength;
import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.HT_CASING;
import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.LASER_PIPES;
import static net.htmlcsjs.htmlTech.common.metatileentity.HTMetaTileEntities.LASER_INPUT_HATCHES;

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
                .input(plate, NaquadahAlloy, 6)
                .input(frameGt, Trinium)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(HT_CASING.getItemVariant(BlockHTCasing.CasingType.NAQ_ALLOY_CASING))
                .duration(50)
                .buildAndRegister();

        for (int i = HTConfig.lasers.minLaserTier; i < mteLength; i++) {
            ASSEMBLER_RECIPES.recipeBuilder()
                    .input(HULL[i])
                    .input(lens, Glass)
                    .inputs((ItemStack) CraftingComponent.SENSOR.getIngredient(i))
                    .input(wireGtQuadruple, ((UnificationEntry) CraftingComponent.CABLE_QUAD.getIngredient(i)).material, 4)
                    .input(foil, ((UnificationEntry) CraftingComponent.PLATE.getIngredient(i)).material, 4)
                    .input(circuit, ((UnificationEntry) CraftingComponent.CIRCUIT.getIngredient(i)).material)
                    .output(LASER_INPUT_HATCHES[i])
                    .EUt((int) V[i-1]/2)
                    .duration(100)
                    .buildAndRegister();
        }

    }
}
