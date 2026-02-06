package net.crystalix.teleport.command;

import net.crystalix.teleport.TeleportPlugin;
import net.crystalix.teleport.command.cloud.PaperCommand;
import net.crystalix.teleport.command.cloud.PaperCommandSource;
import net.crystalix.teleport.command.cloud.PaperPlayerCommandSource;
import net.crystalix.teleport.util.IgnoreManager;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.translatable;

public class TeleportIgnoreCommand extends PaperCommand<TeleportPlugin> {

    public TeleportIgnoreCommand(TeleportPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerTo(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("tpignore")
                .senderType(PaperPlayerCommandSource.class)
                .handler(context -> {
                    final Player player = (Player) context.sender().plattformSender();
                    final IgnoreManager ignoreManager = plugin.ignoreManager();

                    if (ignoreManager.isIgnored(player)) {
                        ignoreManager.unignore(player);
                        player.sendMessage(translatable("command.ignore.off"));
                        return;
                    }
                    ignoreManager.ignore(player);
                    player.sendMessage(translatable("command.ignore.on"));
                }));
    }
}
