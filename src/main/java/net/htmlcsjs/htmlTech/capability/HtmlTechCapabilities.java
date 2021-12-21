package net.htmlcsjs.htmlTech.capability;

import net.htmlcsjs.htmlTech.HTValues;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HTValues.MODID)
public class HtmlTechCapabilities {

    @CapabilityInject(ILaserContainer.class)
    public static Capability<ILaserContainer> LASER_CONTAINER = null;
}
