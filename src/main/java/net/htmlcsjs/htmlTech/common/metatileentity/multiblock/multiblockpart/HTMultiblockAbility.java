package net.htmlcsjs.htmlTech.common.metatileentity.multiblock.multiblockpart;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import net.htmlcsjs.htmlTech.api.capability.ILaserContainer;

@SuppressWarnings("InstantiationOfUtilityClass")
public class HTMultiblockAbility {
    public static final MultiblockAbility<ILaserContainer> OUTPUT_LASER = new MultiblockAbility<>("htmltech:output_laser");
    public static final MultiblockAbility<ILaserContainer> INPUT_LASER = new MultiblockAbility<>("htmltech:input_laser");
}
