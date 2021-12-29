package net.htmlcsjs.htmlTech.integration.theoneprobe;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.htmlcsjs.htmlTech.integration.theoneprobe.provider.LaserContainerInfoProvider;

public class HTTOPCompatibility {

    public static void registerCompatibility() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        oneProbe.registerProvider(new LaserContainerInfoProvider());
    }
}
