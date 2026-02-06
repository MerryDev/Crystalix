package net.crystalix.teleport.util;

import org.bukkit.entity.Player;

public record TeleportRequest(Player sender, Player target) {
}
