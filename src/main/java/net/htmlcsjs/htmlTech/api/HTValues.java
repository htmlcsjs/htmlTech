package net.htmlcsjs.htmlTech.api;

import gregtech.api.GTValues;
import gregtech.common.ConfigHolder;

public class HTValues {

    /**
     *  List of Mod IDs that is needed for proper interigratuion with mods
     */
    public static final String MODID = "htmltech",
            MODID_TOP = "theoneprobe",
            MODID_GT = "gregtech";

    /**
     * Full length mod name
     */
    public static final String MOD_NAME = "htmlcsjs's Tech Mod";

    /**
     * Version of htmltech
     * SHOULD ALWAYS BE "@VERSION@" IN REPO
     */
    public static final String VERSION = "@VERSION@";

    /**
     * Length of MTE arrys depending on if GTValues.HT is set
     */
    public static final int mteLength = ConfigHolder.machines.highTierContent ? GTValues.V.length - 1 : Math.min(GTValues.V.length - 1, GTValues.UV + 1);
}
