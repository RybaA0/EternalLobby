package com.eternalcode.lobby.feature.sound;

import com.eternalcode.lobby.config.implementation.PluginConfiguration;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class SoundController implements Listener {

    private final PluginConfiguration pluginConfig;
    private final Server server;

    public SoundController(PluginConfiguration pluginConfig, Server server) {
        this.pluginConfig = pluginConfig;
        this.server = server;
    }

    @EventHandler
    private void onPlayerHeldItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (this.pluginConfig.playerHeldItemEnabled) {
            player.playSound(player.getLocation(), this.pluginConfig.playerHeldItemSound, this.pluginConfig.playerHeldItemVolume, this.pluginConfig.playerHeldItemPitch);
        }
    }

    @EventHandler
    private void onPlayerChat(AsyncPlayerChatEvent event) {
        if (this.pluginConfig.playerChatSoundEnabled) {
            for (Player players : server.getOnlinePlayers()) {
                players.playSound(players.getLocation(), this.pluginConfig.playerChatSound, this.pluginConfig.playerChatVolume, this.pluginConfig.playerChatPitch);
            }
        }
    }
}
