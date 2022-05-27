package com.eternalcode.lobby;

import com.eternalcode.lobby.config.ConfigurationManager;
import com.eternalcode.lobby.config.implementation.LocationConfiguration;
import com.eternalcode.lobby.feature.menus.lobbyswitcher.LobbySwitcherGui;
import com.eternalcode.lobby.feature.menus.serverselector.ServerSelectorGui;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Section(route = "lobby")
@Permission("lobby.command.lobby")
class LobbyCommand {

    private final ConfigurationManager manager;
    private final LocationConfiguration locationsConfig;
    private final Server server;
    private final MiniMessage miniMessage;
    private final AudienceProvider provider;
    private final ServerSelectorGui serverSelectorGui;
    private final LobbySwitcherGui lobbySwitcherGui;

    LobbyCommand(ConfigurationManager manager, LocationConfiguration locationsConfig, Server server, MiniMessage miniMessage, AudienceProvider provider, ServerSelectorGui serverSelectorGui, LobbySwitcherGui lobbySwitcherGui) {
        this.manager = manager;
        this.locationsConfig = locationsConfig;
        this.server = server;
        this.miniMessage = miniMessage;
        this.provider = provider;
        this.serverSelectorGui = serverSelectorGui;
        this.lobbySwitcherGui = lobbySwitcherGui;
    }

    @Execute(route = "reload")
    private void reload(Player player) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        this.manager.loadAndRenderConfigs();

        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.server.getLogger().info("Lobby configs has ben successfully reloaded in " + millis + "ms");
        Component deserialized = this.miniMessage.deserialize("<b><gradient:#29fbff:#38b3ff>EternalMC-Lobby:</gradient></b> <green>Lobby configs has ben successfully reloaded in " + millis + "ms");
        this.provider.player(player.getUniqueId()).sendMessage(deserialized);
    }

    @Execute(route = "setlobby")
    private void setLobby(Player player) {
        this.locationsConfig.lobby = player.getLocation().clone();
        this.manager.render(this.locationsConfig, "locations.yml");

        Component deserialized = this.miniMessage.deserialize("<b><gradient:#29fbff:#38b3ff>EternalMC-Lobby:</gradient></b> <green>Successfully set lobby to your current location!");
        this.provider.player(player.getUniqueId()).sendMessage(deserialized);
    }

    @Execute(route = "server-selector")
    private void serverSelector(Player player) {
        this.serverSelectorGui.openGui(player);
    }

    @Execute(route = "lobby-switcher")
    private void lobbySwitcher(Player player) {
        this.lobbySwitcherGui.openGui(player);
    }
}
