package net.htmlcsjs.htmlTech.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class HtmlTechCapabilities {

    @CapabilityInject(ILaserContainer.class)
    public static Capability<ILaserContainer> LASER_CONTAINER = null;
}
