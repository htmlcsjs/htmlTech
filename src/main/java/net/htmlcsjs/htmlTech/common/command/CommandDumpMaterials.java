package net.htmlcsjs.htmlTech.common.command;

import com.google.gson.Gson;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.recipeproperties.TemperatureProperty;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.*;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTUtility;
import net.htmlcsjs.htmlTech.HtmlTech;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.VN;

public class CommandDumpMaterials extends CommandTreeBase {
    @Override
    public String getName() {
        return "dump_materials";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "htmltech.command.usage";
    }

    // TODO, rewrite this shit
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Map<String, Object> materialsData = new HashMap<>();
        List<Map<String, Object>> materialsArray = new ArrayList<>();
        for (Material material : GregTechAPI.MaterialRegistry.getAllMaterials()) {
            Map<String, Object> materialData = new HashMap<>();
            // dump simple stuff
            materialData.put("unlocalized_name", material.getUnlocalizedName());
            materialData.put("id", material.getId());
            materialData.put("colour", material.getMaterialRGB());
            materialData.put("blast_temp", material.getBlastTemperature());
            materialData.put("formula", material.getChemicalFormula());
            materialData.put("mass", material.getMass());
            materialData.put("icon_set", material.getMaterialIconSet().getName());

            // material properties stuff
            Map<String, Object> propertiesMap = new HashMap<>();
            MaterialProperties properties = material.getProperties();

            if (properties.hasProperty(PropertyKey.WIRE)) {
                WireProperties wireProperties = material.getProperty(PropertyKey.WIRE);
                propertiesMap.put("wire", String.format("Voltage: %s, Amperage: %,d, Loss: %,d",
                        VN[GTUtility.getTierByVoltage(wireProperties.getVoltage())],
                        wireProperties.getAmperage(),
                        wireProperties.getLossPerBlock()));
            }
            if (properties.hasProperty(PropertyKey.TOOL)) {
                ToolProperty toolProperty = material.getProperty(PropertyKey.TOOL);
                propertiesMap.put("tool", String.format("Speed: %,.2f, Durability: %,d, Damage: %,.2f, Harvest Level: %,d",
                        toolProperty.getToolSpeed(),
                        toolProperty.getToolDurability(),
                        toolProperty.getToolAttackDamage(),
                        toolProperty.getToolHarvestLevel()));
            }
            if (properties.hasProperty(PropertyKey.ROTOR)) {
                RotorProperty rotorProperty = material.getProperty(PropertyKey.ROTOR);
                propertiesMap.put("rotor", String.format("Damage: %,.2f, Durability: %,d, Speed: %,.2f,", rotorProperty.getDamage(), rotorProperty.getDurability(), rotorProperty.getSpeed()));
            }
            if (properties.hasProperty(PropertyKey.ORE)) {
                propertiesMap.put("ore", true);
            } else {
                propertiesMap.put("ore", false);
            }
            try {
                if (properties.hasProperty(PropertyKey.BLAST)) {
                    BlastProperty blastProperty = material.getProperty(PropertyKey.BLAST);
                    Method reflectedGetTempTeir = TemperatureProperty.class.getDeclaredMethod("getMinTierForTemperature", Integer.class);
                    reflectedGetTempTeir.setAccessible(true);
                    propertiesMap.put("blast", String.format("Temp: %,dK (%s), Gas Tier: %s",
                            blastProperty.getBlastTemperature(),
                            reflectedGetTempTeir.invoke(TemperatureProperty.getInstance(), blastProperty.getBlastTemperature()),
                            blastProperty.getGasTier().toString()));
                }
            } catch (Exception e) {
                HtmlTech.logger.error(e.getMessage(), Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
            }
            if (properties.hasProperty(PropertyKey.FLUID_PIPE)) {
                FluidPipeProperties fluidPipeProperties = properties.getProperty(PropertyKey.FLUID_PIPE);
                propertiesMap.put("fluid_pipe", String.format("Throughput: %,d, Max Temp: %,dK, Acid proof: %b, Cryo proof: %b, Gas proof: %b, Plasma proof: %b",
                        fluidPipeProperties.getThroughput(),
                        fluidPipeProperties.getMaxFluidTemperature(),
                        fluidPipeProperties.isAcidProof(),
                        fluidPipeProperties.isCryoProof(),
                        fluidPipeProperties.isGasProof(),
                        fluidPipeProperties.isPlasmaProof()));
            }
            if (properties.hasProperty(PropertyKey.ITEM_PIPE)) {
                ItemPipeProperties itemPipeProperties = properties.getProperty(PropertyKey.ITEM_PIPE);
                propertiesMap.put("item_pipe", String.format("Throughput: %f, Priority: %,d", itemPipeProperties.getTransferRate() * 64, itemPipeProperties.getPriority()));
            }
            if (properties.hasProperty(PropertyKey.FLUID)) {
                FluidProperty fluidProperty = properties.getProperty(PropertyKey.FLUID);
                fluidProperty.isGas();
                fluidProperty.getFluidType().getName();
                propertiesMap.put("fluid", String.format("Temp: %,dK, Gas: %b, Type: %s",
                        fluidProperty.getFluidTemperature(),
                        fluidProperty.isGas(),
                        fluidProperty.getFluidType().getName()));
            }
            if (properties.hasProperty(PropertyKey.PLASMA)) {
                PlasmaProperty plasmaProperty = properties.getProperty(PropertyKey.PLASMA);
                propertiesMap.put("plasma_temp", String.format("Plasma Temp: %,dK", plasmaProperty.getPlasma().getTemperature()));
            }

            materialData.put("properties", propertiesMap);
            // Component dumping
            List<Map<String, Object>> componentsList = new ArrayList<>();
            for (MaterialStack component : material.getMaterialComponents()) {
                Map<String, Object> componentData = new HashMap<>();
                componentData.put("name", component.material.getUnlocalizedName());
                componentData.put("amount", component.amount);
                componentsList.add(componentData);
            }
            materialData.put("components", componentsList);
            materialsArray.add(materialData);
        }
        materialsData.put("materials", materialsArray);
        try {
            server.getFile("config/materials.json").createNewFile();
            FileWriter writer = new FileWriter(server.getFile("config/materials.json"));
            Gson gson = new Gson();
            writer.write(gson.toJson(materialsData));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
