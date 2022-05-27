package com.eternalcode.lobby.listener;

import com.eternalcode.lobby.config.implementation.LocationConfiguration;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final LocationConfiguration locationsConfig;

    public PlayerJoinListener(LocationConfiguration locationsConfig) {
        this.locationsConfig = locationsConfig;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(null);

        if (player.hasPermission("lobby.join.fly") || player.isOp()) {
            player.setAllowFlight(true);
        }

        player.teleport(locationsConfig.lobby);
        player.setGameMode(GameMode.ADVENTURE);
    }
}
