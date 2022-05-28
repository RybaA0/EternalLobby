package com.eternalcode.lobby;

import com.eternalcode.lobby.config.ConfigurationManager;
import com.eternalcode.lobby.config.implementation.ItemJoinConfiguration;
import com.eternalcode.lobby.config.implementation.LobbySwitcherConfiguration;
import com.eternalcode.lobby.config.implementation.LocationConfiguration;
import com.eternalcode.lobby.config.implementation.PluginConfiguration;
import com.eternalcode.lobby.config.implementation.ServerSelectorConfiguration;
import com.eternalcode.lobby.feature.doublejump.DoubleJumpController;
import com.eternalcode.lobby.feature.hotbar.itemjoin.ItemJoinController;
import com.eternalcode.lobby.feature.image.ImageController;
import com.eternalcode.lobby.feature.menus.ConnectionManager;
import com.eternalcode.lobby.feature.menus.lobbyswitcher.LobbySwitcherGui;
import com.eternalcode.lobby.feature.menus.serverselector.ServerSelectorGui;
import com.eternalcode.lobby.feature.sound.SoundController;
import com.eternalcode.lobby.feature.voidteleport.VoidController;
import com.eternalcode.lobby.legacy.LegacyProcessor;
import com.eternalcode.lobby.listener.PlayerBlockBreakListener;
import com.eternalcode.lobby.listener.PlayerBlockPlaceListener;
import com.eternalcode.lobby.listener.PlayerDamageListener;
import com.eternalcode.lobby.listener.PlayerDeathListener;
import com.eternalcode.lobby.listener.PlayerFoodListener;
import com.eternalcode.lobby.listener.PlayerJoinListener;
import com.eternalcode.lobby.listener.PlayerQuitListener;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class LobbyPlugin extends JavaPlugin {

    private ConfigurationManager configManager;
    private LiteCommands<CommandSender> liteCommands;
    private AudienceProvider audienceProvider;
    private MiniMessage miniMessage;
    private ServerSelectorGui serverSelectorGui;
    private LobbySwitcherGui lobbySwitcherGui;
    private ConnectionManager connectionManager;

    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();

        Server server = this.getServer();

        this.configManager = new ConfigurationManager(this.getDataFolder());
        this.configManager.loadAndRenderConfigs();

        PluginConfiguration pluginConfig = this.configManager.getPluginConfig();
        LocationConfiguration locationsConfig = this.configManager.getLocationsConfig();
        ServerSelectorConfiguration serverSelectorConfig = this.configManager.getServerSelectorConfig();
        ItemJoinConfiguration itemJoinConfig = this.configManager.getItemJoinConfiguration();
        LobbySwitcherConfiguration lobbySwitcherConfig = this.configManager.getLobbySwitcherConfiguration();

        this.connectionManager = new ConnectionManager(this);
        this.serverSelectorGui = new ServerSelectorGui(serverSelectorConfig, this.connectionManager);
        this.lobbySwitcherGui = new LobbySwitcherGui(lobbySwitcherConfig, this.connectionManager);

        this.audienceProvider = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyProcessor())
            .build();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "EternalLobby")
            .typeBind(PluginConfiguration.class,            () -> pluginConfig)
            .typeBind(LocationConfiguration.class,          () -> locationsConfig)
            .typeBind(ServerSelectorConfiguration.class,    () -> serverSelectorConfig)
            .typeBind(ItemJoinConfiguration.class,          () -> itemJoinConfig)
            .typeBind(LobbySwitcherConfiguration.class,     () -> lobbySwitcherConfig)
            .typeBind(ServerSelectorGui.class,              () -> this.serverSelectorGui)
            .typeBind(LobbySwitcherGui.class,               () -> this.lobbySwitcherGui)
            .typeBind(ConfigurationManager.class,           () -> this.configManager)
            .typeBind(MiniMessage.class,                    () -> this.miniMessage)
            .typeBind(AudienceProvider.class,               () -> this.audienceProvider)
            .typeBind(Server.class,                         () -> server)

            .contextualBind(Player.class, new BukkitOnlyPlayerContextual("This command is only for players on this server, don't use from console please."))

            .command(LobbyCommand.class)
            .register();

        Stream.of(
            new PlayerBlockBreakListener(),
            new PlayerBlockPlaceListener(),
            new PlayerDamageListener(),
            new PlayerDeathListener(),
            new PlayerFoodListener(),
            new PlayerJoinListener(locationsConfig, pluginConfiguration),
            new PlayerQuitListener(),
            new ImageController(pluginConfig, this.miniMessage, this),
            new DoubleJumpController(pluginConfig),
            new SoundController(pluginConfig, server),
            new VoidController(locationsConfig, pluginConfig),
            new ItemJoinController(itemJoinConfig, this.miniMessage)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));

        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("Plugin enabled in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);


        if (this.audienceProvider != null) {
            this.audienceProvider.close();
            this.audienceProvider = null;
        }
    }

    public ConfigurationManager getConfigManager() {
        return this.configManager;
    }

    public LiteCommands<CommandSender> getLiteCommands() {
        return this.liteCommands;
    }

    public AudienceProvider getAudienceProvider() {
        return this.audienceProvider;
    }

    public MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    public ServerSelectorGui getServerSelectorGui() {
        return this.serverSelectorGui;
    }

    public LobbySwitcherGui getLobbySwitcherGui() {
        return this.lobbySwitcherGui;
    }

    public ConnectionManager getConnectionManager() {
        return this.connectionManager;
    }
}
