package net.htmlcsjs.htmlTech.loaders;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.OreProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import net.htmlcsjs.htmlTech.htmlTech;

public class HTRecipes {

    public static void registerHandlers() {
        HTOrePrefix.laser.addProcessingHandler(PropertyKey.ORE, HTRecipes::processLaser);
    }

    private static void processLaser(OrePrefix orePrefix, Material material, OreProperty oreProperty) {
        htmlTech.logger.info(material.getUnlocalizedName());
    }
}
