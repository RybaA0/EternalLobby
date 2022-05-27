package com.eternalcode.lobby.feature.doublejump;

import com.eternalcode.lobby.config.implementation.PluginConfiguration;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJumpController implements Listener {

    private final PluginConfiguration pluginConfig;

    public DoubleJumpController(PluginConfiguration pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @EventHandler
    private void onPlayerFlightToggle(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (this.pluginConfig.doubleJumpEnabled) {
            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR || !event.isFlying()) {
                return;
            }

            event.setCancelled(true);

            player.setVelocity(player.getLocation().getDirection().multiply(this.pluginConfig.doubleJumpPower).setY(this.pluginConfig.doubleJumpPowerY));
            player.playSound(player.getLocation(), this.pluginConfig.doubleJumpSound, this.pluginConfig.doubleJumpVolume, this.pluginConfig.doubleJumpPitch);
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!this.pluginConfig.doubleJumpEnabled) {
            return;
        }

        player.setAllowFlight(true);
    }


    @EventHandler
    private void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if (!this.pluginConfig.doubleJumpEnabled) {
            return;
        }

        if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
            player.setAllowFlight(true);
        }
    }

}
