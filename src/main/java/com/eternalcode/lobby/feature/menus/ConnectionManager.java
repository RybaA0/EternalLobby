package com.eternalcode.lobby.feature.menus;

import com.eternalcode.lobby.LobbyPlugin;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class ConnectionManager {

    private final LobbyPlugin lobbyPlugin;

    public ConnectionManager(LobbyPlugin lobbyPlugin) {
        this.lobbyPlugin = lobbyPlugin;
    }

    public void connect(Player target, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();

        output.writeUTF("Connect");
        output.writeUTF(server);

        target.sendPluginMessage(lobbyPlugin, "BungeeCord", output.toByteArray());
    }
}
