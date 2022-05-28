package com.eternalcode.lobby.feature.image;

import com.eternalcode.lobby.LobbyPlugin;
import com.eternalcode.lobby.config.implementation.PluginConfiguration;
import com.eternalcode.lobby.legacy.Legacy;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageController implements Listener {

    private final PluginConfiguration pluginConfiguration;
    private final MiniMessage miniMessage;
    private final LobbyPlugin lobbyPlugin;

    public ImageController(PluginConfiguration pluginConfiguration, MiniMessage miniMessage, LobbyPlugin lobbyPlugin) {
        this.pluginConfiguration = pluginConfiguration;
        this.miniMessage = miniMessage;
        this.lobbyPlugin = lobbyPlugin;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (this.pluginConfiguration.cleanAtJoin) {
            for (int i = 0; i < 255; i++) {
                player.sendMessage(" ");
            }
        }


        if (this.pluginConfiguration.enableWelcomeMessage) {

            if (this.pluginConfiguration.welcomeMessageHeadDisplay) {
                this.lobbyPlugin.getServer().getScheduler().runTaskAsynchronously(lobbyPlugin, () -> {
                    try {
                        URL URL = new URL(this.pluginConfiguration.apiUrl.replace("%uuid%", player.getUniqueId().toString()).replace("%name%", player.getName()));

                        HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
                        BufferedImage BufferedImage = ImageIO.read(connection.getInputStream());


                        new ImageMessage(BufferedImage, 8, ImageChar.BLOCK.getChar())
                            .addText(PlaceholderAPI.setPlaceholders(player, this.pluginConfiguration.messageAfterJoin))
                            .send(player);

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }

            if (!this.pluginConfiguration.welcomeMessageHeadDisplay) {
                this.pluginConfiguration.messageAfterJoin.forEach(message ->
                    player.sendMessage(Legacy.LEGACY_AMPERSAND_SERIALIZER.serialize(miniMessage.deserialize(PlaceholderAPI.setPlaceholders(player, message)))));

            }
        }
    }
}
