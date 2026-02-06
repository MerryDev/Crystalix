package net.crystalix.teleport.util;

import net.crystalix.teleport.TeleportPlugin;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class IgnoreManager {

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

    public void readConfig() throws IOException{
        Path path = createOrGetPath();
        Config config = plugin.jsonMapper().readValue(path.toFile(), Config.class);

        ignoredPlayers.clear();
        ignoredPlayers.addAll(config.uuids());
    }

    public void saveAll() throws IOException {
        Path path = createOrGetPath();
        plugin.jsonMapper().writeValue(path, new Config(new ArrayList<>(ignoredPlayers)));
    }

    private Path createOrGetPath() throws IOException {
        final Path directory = plugin.getDataFolder().toPath();

        if (Files.notExists(directory)) {
            Files.createDirectory(directory);
        }
        final Path configPath = directory.resolve("config.json");
        if (Files.notExists(configPath)) {
            Files.createFile(configPath);
        }

        return configPath;
    }
}
