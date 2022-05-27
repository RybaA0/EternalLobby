package com.eternalcode.lobby.feature.voidteleport;

import com.eternalcode.lobby.config.implementation.LocationConfiguration;
import com.eternalcode.lobby.config.implementation.PluginConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidController implements Listener {

    private final LocationConfiguration locationsConfig;
    private final PluginConfiguration pluginConfig;

    public VoidController(LocationConfiguration locationsConfig, PluginConfiguration pluginConfig) {
        this.locationsConfig = locationsConfig;
        this.pluginConfig = pluginConfig;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getTo().getBlockY() > this.pluginConfig.voidTeleportHeight) {
            return;
        }

        player.teleport(this.locationsConfig.lobby);
        player.setFallDistance(0.0f);
    }
}
