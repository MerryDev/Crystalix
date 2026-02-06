package net.crystalix.teleport.util;

import net.crystalix.teleport.TeleportPlugin;
import org.bukkit.entity.Player;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class IgnoreManager {

    private static final Path PATH = Path.of("plugins/config.json");

    private final Set<UUID> ignoredPlayers = ConcurrentHashMap.newKeySet();
    private final TeleportPlugin plugin;

    public IgnoreManager(TeleportPlugin plugin) {
        this.plugin = plugin;
    }

    public void ignore(Player player) {
        ignoredPlayers.add(player.getUniqueId());
    }

    public void unignore(Player player) {
        ignoredPlayers.remove(player.getUniqueId());
    }

    public boolean isIgnored(Player player) {
        return ignoredPlayers.contains(player.getUniqueId());
    }

    public void readConfig() {
        Config config = plugin.jsonMapper().readValue(PATH, Config.class);

        ignoredPlayers.clear();
        ignoredPlayers.addAll(config.uuids());
    }

    public void saveAll() {
        plugin.jsonMapper().writeValue(PATH, new Config(new ArrayList<>(ignoredPlayers)));
    }
}
