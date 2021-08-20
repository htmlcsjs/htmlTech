package net.htmlcsjs.htmlTech.api.materials;

import gregtech.api.unification.material.IMaterialHandler;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;

import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static net.htmlcsjs.htmlTech.api.materials.HTMaterials.LASER;

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

        laser = new OrePrefix("laser", -1, null, laserIcon, ENABLE_UNIFICATION, material -> material.hasProperty(LASER));

        MetaItems.addOrePrefix(laser);
    }
}
