package net.htmlcsjs.htmlTech.api.materials;

import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.items.MetaItems;
import net.htmlcsjs.htmlTech.htmlTech;

import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static net.htmlcsjs.htmlTech.api.materials.HTMaterials.LASER;

public class HTOrePrefix {
    public static MaterialIconType laserIcon;
    public static OrePrefix laser;

    public static void init() {
        //htmlTech.logger.info("Register Htmltech ore prefixes");
        initLasers();
    }

    private static void initLasers() {
        laserIcon = new MaterialIconType("laser");

        laser = new OrePrefix("laser", -1, null, laserIcon, ENABLE_UNIFICATION, material -> material.hasProperty(LASER));

        MetaItems.addOrePrefix(laser);
    }
}
