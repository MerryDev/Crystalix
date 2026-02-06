package net.crystalix.teleport.command;

import net.crystalix.teleport.TeleportPlugin;
import net.crystalix.teleport.command.cloud.PaperCommand;
import net.crystalix.teleport.command.cloud.PaperCommandSource;
import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;

public class TeleportIgnoreCommand extends PaperCommand<TeleportPlugin> {

    public TeleportIgnoreCommand(TeleportPlugin plugin) {
        super(plugin);
    }

    @Override
    public void registerTo(@NotNull CommandManager<PaperCommandSource> commandManager) {

    }
}
