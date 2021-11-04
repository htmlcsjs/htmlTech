package net.htmlcsjs.htmlTech.api;

import gregtech.api.render.OrientedOverlayRenderer;
import gregtech.api.render.SimpleCubeRenderer;
import gregtech.api.render.SimpleOverlayRenderer;
import net.htmlcsjs.htmlTech.HTValues;
import net.htmlcsjs.htmlTech.htmlTech;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = HTValues.MODID, value = Side.CLIENT)
public class HTTextures {
    public static  OrientedOverlayRenderer LASER_PROJECTOR_OVERLAY;
    public static  OrientedOverlayRenderer LASER_COLLECTOR_OVERLAY;
    public static  OrientedOverlayRenderer LASER_REFLECTOR_OVERLAY;
    public static ResourceLocation HTMLTECH_CAPE;
    public static SimpleOverlayRenderer LASER_INPUT;
    public static SimpleOverlayRenderer LASER_OUTPUT;
    public static SimpleCubeRenderer FLOPPA_CASING;

    public static void preInit() {
        LASER_INPUT = new SimpleOverlayRenderer("overlay/machine/overlay_laser_in");
        LASER_OUTPUT = new SimpleOverlayRenderer("overlay/machine/overlay_laser_out");
        HTMLTECH_CAPE = new ResourceLocation(htmlTech.MODID, "textures/htmltech_cape.png");
        FLOPPA_CASING = new SimpleCubeRenderer("casings/floppa_casing");
        LASER_PROJECTOR_OVERLAY = new OrientedOverlayRenderer("overlay/multi/laser_projector", false, OrientedOverlayRenderer.OverlayFace.FRONT);
        LASER_COLLECTOR_OVERLAY = new OrientedOverlayRenderer("overlay/multi/laser_collector", false, OrientedOverlayRenderer.OverlayFace.FRONT);
        LASER_REFLECTOR_OVERLAY = new OrientedOverlayRenderer("overlay/multi/laser_reflector", false, OrientedOverlayRenderer.OverlayFace.FRONT);
    }
}
