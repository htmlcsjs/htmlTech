package net.htmlcsjs.htmlTech.client;

import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.htmlcsjs.htmlTech.api.HTValues;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

import static gregtech.client.renderer.texture.cube.OrientedOverlayRenderer.OverlayFace.FRONT;

@Mod.EventBusSubscriber(modid = HTValues.MODID, value = Side.CLIENT)
public class HTTextures {
    public static OrientedOverlayRenderer LASER_PROJECTOR_OVERLAY;
    public static OrientedOverlayRenderer LASER_COLLECTOR_OVERLAY;
    public static OrientedOverlayRenderer LASER_REFLECTOR_OVERLAY;
    public static ResourceLocation HTMLTECH_CAPE;
    public static SimpleOverlayRenderer LASER_INPUT;
    public static SimpleOverlayRenderer LASER_OUTPUT;
    public static SimpleOverlayRenderer FLOPPA_CASING;

    public static void preInit() {
        LASER_INPUT = new SimpleOverlayRenderer("overlay/machine/overlay_laser_in");
        LASER_OUTPUT = new SimpleOverlayRenderer("overlay/machine/overlay_laser_out");
        HTMLTECH_CAPE = new ResourceLocation(HtmlTech.MODID, "textures/htmltech_cape.png");
        FLOPPA_CASING = new SimpleOverlayRenderer("casings/floppa_casing");
        LASER_PROJECTOR_OVERLAY = new OrientedOverlayRenderer("overlay/multi/laser_projector",  FRONT);
        LASER_COLLECTOR_OVERLAY = new OrientedOverlayRenderer("overlay/multi/laser_collector",  FRONT);
        LASER_REFLECTOR_OVERLAY = new OrientedOverlayRenderer("overlay/multi/laser_reflector",  FRONT);
    }
}
