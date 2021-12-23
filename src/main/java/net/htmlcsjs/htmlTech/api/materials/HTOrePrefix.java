package net.htmlcsjs.htmlTech.api.materials;

import gregtech.api.GTValues;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

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

        laser = new OrePrefix("laser", -1, null, laserIcon, ENABLE_UNIFICATION, material -> material.hasProperty(LASER),  material -> {
            List<String> textList = new ArrayList<String>();

            int voltage = material.getProperty(LASER).voltage;
            int amperage = material.getProperty(LASER).amperage;

            textList.add(I18n.format("htmltech.laser.voltage", voltage, GTValues.VNF[(GTUtility.getTierByVoltage(voltage))]));
            textList.add(I18n.format("htmltech.laser.amperage", amperage));
            return textList;
        });

        MetaItems.addOrePrefix(laser);
    }
}
