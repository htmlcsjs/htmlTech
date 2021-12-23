package net.htmlcsjs.htmlTech.api.metatileentity.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.TraceabilityPredicate;
import net.htmlcsjs.htmlTech.common.metatileentity.multiblock.multiblockpart.HTMultiblockAbility;
import net.minecraft.util.ResourceLocation;

public abstract class HTMultiblockWithDisplayBase extends MultiblockWithDisplayBase {

    public HTMultiblockWithDisplayBase(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public TraceabilityPredicate autoAbilities() {
        return autoAbilities(true, false, true, true, true, true);
    }


    public TraceabilityPredicate autoAbilities(boolean checkMaintenance, boolean checkMuffler, boolean checkEnergyIn, boolean checkEnergyOut, boolean checkLaserIn, boolean checkLaserOut) {
        TraceabilityPredicate predicate = super.autoAbilities(checkMaintenance, checkMuffler);
        if (checkEnergyIn) {
            predicate = predicate.or(abilities(MultiblockAbility.INPUT_ENERGY).setMaxGlobalLimited(3).setMinGlobalLimited(1).setPreviewCount(1));
        }
        if (checkEnergyOut) {
            predicate = predicate.or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMaxGlobalLimited(3).setMinGlobalLimited(1).setPreviewCount(1));
        }
        if (checkLaserIn) {
            predicate = predicate.or(abilities(HTMultiblockAbility.INPUT_LASER).setMaxGlobalLimited(3).setMinGlobalLimited(1).setPreviewCount(1));
        }
        if (checkLaserOut) {
            predicate = predicate.or(abilities(HTMultiblockAbility.OUTPUT_LASER).setMaxGlobalLimited(3).setMinGlobalLimited(1).setPreviewCount(1));
        }
        return predicate;
    }
}
