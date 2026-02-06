package net.crystalix.teleport.command;

import net.crystalix.teleport.TeleportPlugin;
import net.crystalix.teleport.command.cloud.PaperCommand;
import net.crystalix.teleport.command.cloud.PaperCommandSource;
import net.crystalix.teleport.command.cloud.PaperPlayerCommandSource;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;

import static org.incendo.cloud.bukkit.parser.PlayerParser.playerParser;

public class TeleportCommand extends PaperCommand<TeleportPlugin> {

   public TeleportCommand(TeleportPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerTo(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("tpa")
                .senderType(PaperPlayerCommandSource.class)
                .required("player", playerParser())
                .handler(context -> {
                    final Player player = (Player) context.sender().plattformSender();
                    final Player target = context.getOrDefault("player", null);
                }));
    }
}
