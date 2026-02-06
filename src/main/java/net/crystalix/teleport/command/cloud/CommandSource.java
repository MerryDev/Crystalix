package net.crystalix.teleport.command.cloud;

/**
 * Repräsentiert eine plattformunabhängige Quelle, von der ein Befehl ausgeführt wird.
 *
 * @param <C> Der native CommandSender-Typ
 */
public abstract class CommandSource<C> {

    protected final C plattformSender;

    public CommandSource(C plattformSender) {
        this.plattformSender = plattformSender;
    }

    public C plattformSender() {
        return plattformSender;
    }
}
