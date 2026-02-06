package net.crystalix.teleport.command.cloud;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;

/**
 * Repräsentation einer {@link PaperCommandSource} Implementierung speziell für Spieler.
 */
public class PaperPlayerCommandSource extends PaperCommandSource {

    public PaperPlayerCommandSource(CommandSender plattformSender, CommandSourceStack commandSourceStack) {
        super(plattformSender, commandSourceStack);
    }
}
