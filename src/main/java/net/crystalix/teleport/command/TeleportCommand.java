package net.crystalix.teleport.command;

import net.crystalix.teleport.TeleportPlugin;
import net.crystalix.teleport.command.cloud.PaperCommand;
import net.crystalix.teleport.command.cloud.PaperCommandSource;
import net.crystalix.teleport.command.cloud.PaperPlayerCommandSource;
import net.crystalix.teleport.util.RequestManager;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;

import static org.incendo.cloud.bukkit.parser.PlayerParser.playerParser;

public class TeleportCommand extends PaperCommand<TeleportPlugin> {

    private final RequestManager requestManager;

    public TeleportCommand(TeleportPlugin plugin) {
        super(plugin);
        this.requestManager = new RequestManager(plugin);
    }

    @Override
    public void registerTo(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("tpa")
                .senderType(PaperPlayerCommandSource.class)
                .required("player", playerParser())
                .handler(context -> {
                    final Player player = (Player) context.sender().plattformSender();
                    final Player target = context.getOrDefault("player", null);

                    requestManager.createRequest(player, target);
                    player.sendMessage("<green>Deine Teleportanfrage wurde erfolgreich gesendet.");
                    target.sendMessage("<green>Du hast eine Teleportanfrage von " + player.getName() + " erhalten.");
                }));
    }
}
