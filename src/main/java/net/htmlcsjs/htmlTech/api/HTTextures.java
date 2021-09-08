package net.htmlcsjs.htmlTech.api;

import gregtech.api.render.SimpleOverlayRenderer;
import net.htmlcsjs.htmlTech.HTValues;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = HTValues.MODID, value = Side.CLIENT)
public class HTTextures {
    public static SimpleOverlayRenderer LASER_INPUT;
    public static SimpleOverlayRenderer LASER_OUTPUT;

    public static void preInit() {
        LASER_INPUT = new SimpleOverlayRenderer("overlay/machine/overlay_laser_in");
        LASER_OUTPUT = new SimpleOverlayRenderer("overlay/machine/overlay_laser_out");
    }
}
