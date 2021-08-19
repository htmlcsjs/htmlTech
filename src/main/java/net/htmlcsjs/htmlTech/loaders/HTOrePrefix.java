package net.htmlcsjs.htmlTech.loaders;

import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlag;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_FRAME;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_GEAR;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasDustProperty;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasGemProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static net.htmlcsjs.htmlTech.loaders.HTMaterials.GENERATE_LASER;

@IMaterialHandler.RegisterMaterialHandler
public class HTOrePrefix implements IMaterialHandler {
    public static MaterialIconType laserIcon;
    public static OrePrefix laser;

    @Override
    public void onMaterialsInit() {
        initLasers();
    }

    private void initLasers() {
        laserIcon = new MaterialIconType("laser");

        laser = new OrePrefix("laser", -1, null, laserIcon, ENABLE_UNIFICATION, material -> material.hasFlag(GENERATE_LASER));
        laser.setAlternativeOreName(laser.name());

        MetaItems.addOrePrefix(laser);

        Materials.Diamond.addFlag(GENERATE_LASER);
    }
}
