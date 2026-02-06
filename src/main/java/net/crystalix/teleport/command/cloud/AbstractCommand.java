package net.crystalix.teleport.command.cloud;

import org.incendo.cloud.CommandManager;
import org.jetbrains.annotations.NotNull;

/**
 * Repräsentation eines plattformunabhängigen Befehls.
 *
 * @param <C> Der native CommandSender-Typ
 * @param <I> Die Hauptklasse des Plugins
 */
public abstract class AbstractCommand<C, I> {

    protected final I plugin;

    public AbstractCommand(I plugin) {
        this.plugin = plugin;
    }

    /**
     * Registriert den Befehl in den CommandManager.
     *
     * @param commandManager Der CommandManager
     */
    public abstract void registerTo(@NotNull CommandManager<C> commandManager);

}
