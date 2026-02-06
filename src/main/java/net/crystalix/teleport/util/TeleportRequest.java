package net.crystalix.teleport.util;

import org.bukkit.entity.Player;

import java.sql.Timestamp;

public record TeleportRequest(Player sender, Player target, Timestamp expiresAt) {
}
