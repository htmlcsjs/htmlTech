package net.htmlcsjs.htmlTech.common.command;

import com.google.gson.Gson;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.*;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTUtility;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandDumpMaterials extends CommandTreeBase {
    @Override
    public String getName() {
        return "dump_materials";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "htmltech.command.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Map<String, Object> materialsData = new HashMap<>();
        List<Map<String, Object>> materialsArray = new ArrayList<>();
        for (Material material: GregTechAPI.MaterialRegistry.getAllMaterials()) {
            try {
                Map<String, Object> materialData = new HashMap<>();
                // dump simple stuff
                materialData.put("unlocalized_name", material.getUnlocalizedName());
                materialData.put("id", material.getId());
                materialData.put("colour", material.getMaterialRGB());
                materialData.put("blast_temp", material.getBlastTemperature());

                // material properties stuff
                Map<String, Object> propertiesMap = new HashMap<>();
                MaterialProperties properties = material.getProperties();

                if (properties.hasProperty(PropertyKey.WIRE)) {
                    WireProperties wireProperties = material.getProperty(PropertyKey.WIRE);
                    propertiesMap.put("wire", String.format("Voltage: %s, Amperage: %d, Loss: %d", GTUtility.getTierByVoltage(wireProperties.getVoltage()), wireProperties.getAmperage(), wireProperties.getLossPerBlock()));
                }
                if (properties.hasProperty(PropertyKey.TOOL)) {
                    ToolProperty toolProperty = material.getProperty(PropertyKey.TOOL);
                    propertiesMap.put("tool", String.format("Speed: %f, Durability: %d, Dmg: %f", toolProperty.getToolSpeed(), toolProperty.getToolDurability(), toolProperty.getToolAttackDamage()));
                }
                if (properties.hasProperty(PropertyKey.ORE)) {
                    propertiesMap.put("ore", true);
                } else {
                    propertiesMap.put("ore", false);
                }
                if (properties.hasProperty(PropertyKey.BLAST)) {
                    BlastProperty blastProperty = material.getProperty(PropertyKey.BLAST);
                    propertiesMap.put("blast", String.format("Temp: %d, Gas Tier: %s", blastProperty.getBlastTemperature(), blastProperty.getGasTier().toString()));
                }
                if (properties.hasProperty(PropertyKey.FLUID_PIPE)) {
                    FluidPipeProperties fluidPipeProperties = properties.getProperty(PropertyKey.FLUID_PIPE);
                    propertiesMap.put("blast", String.format("Throughput: %d, Max Temp: %dK, Channels: %d", fluidPipeProperties.getThroughput(), fluidPipeProperties.getMaxFluidTemperature(), fluidPipeProperties.getTanks()));
                }
                if (properties.hasProperty(PropertyKey.ITEM_PIPE)) {
                    ItemPipeProperties itemPipeProperties = properties.getProperty(PropertyKey.ITEM_PIPE);
                    propertiesMap.put("blast", String.format("Throughput: %f, Priority: %d", itemPipeProperties.getTransferRate() * 64, itemPipeProperties.getPriority()));
                }
                if (properties.hasProperty(PropertyKey.FLUID)) {
                    FluidProperty fluidProperty = properties.getProperty(PropertyKey.FLUID);
                    propertiesMap.put("fluid_temp", String.format("Fluid Temp: %dK", fluidProperty.getFluidTemperature()));
                }
                if (properties.hasProperty(PropertyKey.PLASMA)) {
                    PlasmaProperty plasmaProperty = properties.getProperty(PropertyKey.PLASMA);
                    propertiesMap.put("plasma_temp", String.format("Plasma Temp: %dK", plasmaProperty.getPlasma().getTemperature()));
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
            } catch (Exception ignored) {}
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
