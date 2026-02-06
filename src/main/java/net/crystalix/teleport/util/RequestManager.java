package net.crystalix.teleport.util;

import net.crystalix.teleport.TeleportPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RequestManager {

    private static final long TIMEOUT = 60_000L; // 1 Minute
    private final Map<Player, TeleportRequest> requests = new ConcurrentHashMap<>();

    private final TeleportPlugin plugin;

    public RequestManager(TeleportPlugin plugin) {
        this.plugin = plugin;
    }

    public void createRequest(Player sender, Player target) {
        final TeleportRequest request = new TeleportRequest(sender, target, Timestamp.from(Instant.now().plusMillis(TIMEOUT)));
        requests.put(sender, request);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportRequest current = requests.getOrDefault(sender, null);

            if (current == null) return;
            if (current == request) {
                cancelRequest(sender);

                if (sender.isOnline()) sender.sendMessage("<red>Deine Teleportanfrage ist abgelaufen.");
                if (target.isOnline()) target.sendMessage("<red>Die Teleportanfrage von " + sender.getName() + " ist abgelaufen.");
            }
        }, 20L * 5);
    }

    public void ignoreRequest(Player sender) {
        final TeleportRequest request = requests.getOrDefault(sender, null);
        if (request == null) return;

        Player target = request.target();
        cancelRequest(sender);

        sender.sendMessage(target.getName() + " hat deine Anfrage abgelehnt.");
        target.sendMessage("Du hast die Anfrage von " + sender.getName() + " abgelehnt.");
    }

    public void cancelRequest(Player sender) {
        requests.remove(sender);
    }
}
