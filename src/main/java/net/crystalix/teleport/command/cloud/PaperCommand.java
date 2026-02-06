package net.crystalix.teleport.command.cloud;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Repräsentation eines Paper-spezifischen Befehls.
 * <p>
 * Diese Klasse dient als Basisimplementierung für Befehle in einer Paper-Serverumgebung.
 * Sie erbt von dem abstrakten {@link AbstractCommand}, welcher eine generische Variante
 * eines Befehls darstellt und {@link PaperCommandSource} als nativen Uhrheber eines Befehls
 * verwendet.
 *
 * @param <I> Die Hauptklasse des Plugins
 */
public abstract class PaperCommand<I extends JavaPlugin> extends AbstractCommand<PaperCommandSource, I> {

    public PaperCommand(I plugin) {
        super(plugin);
    }
}
