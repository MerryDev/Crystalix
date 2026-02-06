package net.crystalix.teleport.util;

import net.crystalix.teleport.TeleportPlugin;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.kyori.adventure.text.Component.translatable;

public final class RequestManager {

    private final Map<Player, TeleportRequest> requests = new ConcurrentHashMap<>();
    private final TeleportPlugin plugin;

    public RequestManager(TeleportPlugin plugin) {
        this.plugin = plugin;
    }

    public void createRequest(Player sender, Player target) {
        final TeleportRequest request = new TeleportRequest(sender, target);
        requests.put(sender, request);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            TeleportRequest current = requests.getOrDefault(sender, null);

            if (current == null) return;
            if (current == request) {
                cancelRequest(sender);

                if (sender.isOnline()) sender.sendMessage(translatable("command.request.timeout.out"));
                if (target.isOnline()) target.sendMessage(translatable("command.request.timeout.in", Argument.component("name", sender.name())));
            }
        }, 20L * 60);
    }

    public void acceptRequest(Player sender) {
        final TeleportRequest request = requests.getOrDefault(sender, null);
        if (request == null) return;

        Player target = request.target();
        if (!target.isOnline()) return;

        sender.teleport(target);
        cancelRequest(sender);

        sender.sendMessage(translatable("command.request.accepted.out", Argument.component("name", target.name())));
        target.sendMessage(translatable("command.request.accepted.in"));
    }

    public void ignoreRequest(Player sender) {
        final TeleportRequest request = requests.getOrDefault(sender, null);
        if (request == null) return;

        Player target = request.target();
        cancelRequest(sender);

        sender.sendMessage(translatable("command.request.denied.out", Argument.component("name", target.name())));
        target.sendMessage(translatable("command.request.denied.in"));
    }

    public void cancelRequest(Player sender) {
        requests.remove(sender);
    }
}
