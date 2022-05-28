package com.eternalcode.lobby.listener;

import com.eternalcode.lobby.config.implementation.LocationConfiguration;
import com.eternalcode.lobby.config.implementation.PluginConfiguration;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final LocationConfiguration locationsConfig;
    private final PluginConfiguration pluginConfiguration;

    public PlayerJoinListener(LocationConfiguration locationsConfig, PluginConfiguration pluginConfiguration) {
        this.locationsConfig = locationsConfig;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {

    }
}
