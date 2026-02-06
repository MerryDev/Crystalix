package net.crystalix.teleport.command;

import net.crystalix.teleport.TeleportPlugin;
import net.crystalix.teleport.command.cloud.PaperCommand;
import net.crystalix.teleport.command.cloud.PaperCommandSource;
import net.crystalix.teleport.command.cloud.PaperPlayerCommandSource;
import net.crystalix.teleport.util.IgnoreManager;
import net.crystalix.teleport.util.RequestManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.bukkit.entity.Player;
import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.translatable;
import static org.incendo.cloud.bukkit.parser.PlayerParser.playerParser;

public class TeleportCommand extends PaperCommand<TeleportPlugin> {

    private final RequestManager requestManager;
    private final IgnoreManager ignoreManager;

    public TeleportCommand(TeleportPlugin plugin) {
        super(plugin);
        this.requestManager = new RequestManager(plugin);
        this.ignoreManager = plugin.ignoreManager();
    }

    @Override
    public void registerTo(@NotNull CommandManager<PaperCommandSource> commandManager) {
        commandManager.command(commandManager.commandBuilder("tpa")
                .senderType(PaperPlayerCommandSource.class)
                .required("player", playerParser())
                .handler(context -> {
                    final Player player = (Player) context.sender().plattformSender();
                    final Player target = context.getOrDefault("player", null);

                    if (player.equals(target)) {
                        player.sendMessage(translatable("command.request.error.self"));
                        return;
                    }
                    if (ignoreManager.isIgnored(target)) {
                        player.sendMessage(translatable("command.request.error.ignored"));
                        return;
                    }

                    requestManager.createRequest(player, target);

                    Component acceptButton = translatable("command.request.button.accept")
                            .clickEvent(ClickEvent.callback(_ -> requestManager.acceptRequest(player)));
                    Component denyButton = translatable("command.request.button.deny")
                            .clickEvent(ClickEvent.callback(_ -> requestManager.ignoreRequest(player)));

                    Component message = translatable("command.request.in",
                            Argument.component("name", player.name()),
                            Argument.component("accept", acceptButton),
                            Argument.component("deny", denyButton));

                    player.sendMessage(translatable("command.request.out"));
                    target.sendMessage(message);
                }));
    }
}
