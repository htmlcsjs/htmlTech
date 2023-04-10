package net.htmlcsjs.htmlTech.loaders.recipe;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.loaders.recipe.CraftingComponent;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.common.HTConfig;
import net.htmlcsjs.htmlTech.common.blocks.BlockHTCasing;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLY_LINE_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.blocks.MetaBlocks.METAL_CASING;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static net.htmlcsjs.htmlTech.api.HTValues.mteLength;
import static net.htmlcsjs.htmlTech.api.unification.materials.HTOrePrefix.laser;
import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.HT_CASING;
import static net.htmlcsjs.htmlTech.common.blocks.HTMetaBlocks.LASER_PIPES;
import static net.htmlcsjs.htmlTech.common.item.HTMetaItems.EMPTY_LASER;
import static net.htmlcsjs.htmlTech.common.item.HTMetaItems.LASER_GUN;
import static net.htmlcsjs.htmlTech.common.metatileentity.HTMetaTileEntities.*;

public class LaserEquipmentLoader {

    public static void init() {
        // Vacuum pipes
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(wireFine, BorosilicateGlass, 8)
                .input(foil, Silver, 8)
                .input(foil, RhodiumPlatedPalladium, 2)
                .fluidInputs(Polybenzimidazole.getFluid(L))
                .output(LASER_PIPES[0], 1)
                .EUt(VA[IV])
                .duration(40)
                .buildAndRegister();

        // Naq alloy casing
        ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(16)
                .input(plate, NaquadahAlloy, 6)
                .input(frameGt, Trinium)
                .notConsumable(new IntCircuitIngredient(6))
                .outputs(HT_CASING.getItemVariant(BlockHTCasing.CasingType.NAQ_ALLOY_CASING))
                .duration(50)
                .buildAndRegister();

        // Laser Collecting hatches
        for (int i = HTConfig.lasers.minLaserTier; i < mteLength; i++) {
            ItemStack coil;
            if (CraftingComponent.VOLTAGE_COIL.getIngredient(i) instanceof ItemStack) {
                coil = (ItemStack) CraftingComponent.VOLTAGE_COIL.getIngredient(i);
            } else if (CraftingComponent.VOLTAGE_COIL.getIngredient(i) instanceof MetaItem.MetaValueItem) {
                coil = ((MetaItem<?>.MetaValueItem) CraftingComponent.VOLTAGE_COIL.getIngredient(i)).getStackForm();
            } else {
                HtmlTech.logger.warn(String.format("Cannot add recipe for %s hatch", VN[i]));
                continue;
            }
            try {
                Object sensor = !ConfigHolder.machines.highTierContent && i == UHV ? CraftingComponent.SENSOR.getIngredient(i - 1) : CraftingComponent.SENSOR.getIngredient(i);
                ASSEMBLY_LINE_RECIPES.recipeBuilder()
                        .input(HULL[i])
                        .input(lens, Glass)
                        .inputs((ItemStack) sensor)
                        .inputs(coil)
                        .input(wireGtQuadruple, ((UnificationEntry) CraftingComponent.CABLE_QUAD.getIngredient(i)).material, 4)
                        .input(foil, ((UnificationEntry) CraftingComponent.PLATE.getIngredient(i)).material, 4)
                        .input(circuit, ((UnificationEntry) CraftingComponent.CIRCUIT.getIngredient(i)).material)
                        .fluidInputs(SodiumPotassium.getFluid(1000))
                        .output(LASER_INPUT_HATCHES[i])
                        .EUt(VA[i])
                        .duration(100)
                        .buildAndRegister();
            } catch (NullPointerException ignored) {
                HtmlTech.logger.warn(String.format("Cannot add recipe for %s hatch", VN[i]));
            }
        }

        // Laser Emitter hatch
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .input(HULL[ZPM])
                .input(circuit, MarkerMaterials.Tier.LuV)
                .input(lens, Glass)
                .input(wireFine, IndiumTinBariumTitaniumCuprate, 8)
                .input(foil, Silver, 2)
                .fluidInputs(SodiumPotassium.getFluid(1000))
                .output(LASER_OUTPUT_HATCH)
                .EUt(VA[ZPM])
                .duration(100)
                .buildAndRegister();

        // Laser projector controller
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(HT_CASING.getItemVariant(BlockHTCasing.CasingType.NAQ_ALLOY_CASING))
                .input(wireFine, UraniumRhodiumDinaquadide, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(EMITTER_ZPM, 2)
                .input(plateDouble, Duranium)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .fluidInputs(SodiumPotassium.getFluid(1000))
                .fluidInputs(SolderingAlloy.getFluid(L * 7))
                .output(LASER_PROJECTOR)
                //.reserchItem(LASER_OUTPUT_HATCH)
                .EUt(VA[ZPM])
                .duration(800)
                .buildAndRegister();

        // Laser collector controller
        ASSEMBLY_LINE_RECIPES.recipeBuilder()
                .inputs(METAL_CASING.getItemVariant(BlockMetalCasing.MetalCasingType.HSSE_STURDY))
                .input(spring, UraniumRhodiumDinaquadide, 64)
                .input(ULTRA_HIGH_POWER_INTEGRATED_CIRCUIT, 64)
                .input(SENSOR_ZPM, 2)
                .input(plateDouble, Europium)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .input(circuit, MarkerMaterials.Tier.ZPM)
                .fluidInputs(SodiumPotassium.getFluid(1000))
                .fluidInputs(SolderingAlloy.getFluid(L * 7))
                .output(LASER_COLLECTOR)
                //.reserchItem(LASER_INPUT_HATCHES[LuV])
                .EUt(VA[ZPM])
                .duration(800)
                .buildAndRegister();

        // Empty Laser Recipe
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(GLASS_TUBE)
                .input(lens, Glass)
                .fluidInputs(Silver.getFluid(4 * L))
                .output(EMPTY_LASER)
                .EUt(VA[EV])
                .duration(20)
                .buildAndRegister();

        // Laser Gun Recipe
        ASSEMBLER_RECIPES.recipeBuilder()
                .input(screw, Steel, 4)
                .input(stick, Iron)
                .input(bolt, VanadiumSteel)
                .input(plate, Polyethylene, 4)
                .input(laser, CarbonDioxide)
                .output(LASER_GUN)
                .EUt(VA[HV])
                .duration(200)
                .buildAndRegister();

    }
}
