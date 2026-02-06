package net.crystalix.teleport.command.cloud;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;

/**
 * Repräsentation einer {@link CommandSource} für die Plattform Paper,
 * welche zusätzlich {@link Audience} von Adventure implementiert.
 */
public class PaperCommandSource extends CommandSource<CommandSender> implements Audience {

    private final CommandSourceStack commandSourceStack;

    public PaperCommandSource(CommandSender plattformSender, CommandSourceStack commandSourceStack) {
        super(plattformSender);
        this.commandSourceStack = commandSourceStack;
    }

    public CommandSourceStack commandSourceStack() {
        return commandSourceStack;
    }
}
