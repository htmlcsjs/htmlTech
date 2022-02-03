package net.htmlcsjs.htmlTech.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.command.CommandTreeBase;

public class HtmlTechCommand extends CommandTreeBase {
    public HtmlTechCommand() {
        addSubcommand(new CommandDumpMaterials());
    }

    @Override
    public String getName() {
        return "htmltech";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "htmltech.command.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentString("sus"));
        super.execute(server, sender, args);
    }
}
