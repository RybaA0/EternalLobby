package com.eternalcode.lobby.config;

import com.eternalcode.lobby.config.composer.LocationComposer;
import com.eternalcode.lobby.config.implementation.ItemJoinConfiguration;
import com.eternalcode.lobby.config.implementation.LobbySwitcherConfiguration;
import com.eternalcode.lobby.config.implementation.LocationConfiguration;
import com.eternalcode.lobby.config.implementation.PluginConfiguration;
import com.eternalcode.lobby.config.implementation.ServerSelectorConfiguration;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Location;

import java.io.File;

public class ConfigurationManager {

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Location.class, new LocationComposer())
        .build();

    private final PluginConfiguration pluginConfig;
    private final LocationConfiguration locationsConfig;
    private final ServerSelectorConfiguration serverSelectorConfig;
    private final ItemJoinConfiguration itemJoinConfiguration;
    private final LobbySwitcherConfiguration lobbySwitcherConfiguration;

    private final File dataFolder;

    public ConfigurationManager(File dataFolder) {
        this.itemJoinConfiguration = new ItemJoinConfiguration();
        this.pluginConfig = new PluginConfiguration();
        this.locationsConfig = new LocationConfiguration();
        this.serverSelectorConfig = new ServerSelectorConfiguration();
        this.lobbySwitcherConfiguration = new LobbySwitcherConfiguration();
        this.dataFolder = dataFolder;
    }

    public ServerSelectorConfiguration getServerSelectorConfig() {
        return serverSelectorConfig;
    }

    public void loadAndRenderConfigs() {
        this.loadAndRender(this.pluginConfig, "config.yml");
        this.loadAndRender(this.locationsConfig, "locations.yml");
        this.loadAndRender(this.serverSelectorConfig, "server-selector.yml");
        this.loadAndRender(this.itemJoinConfiguration, "item-join.yml");
        this.loadAndRender(this.lobbySwitcherConfiguration, "lobby-switcher.yml");
    }

    public PluginConfiguration getPluginConfig() {
        return this.pluginConfig;
    }

    public LocationConfiguration getLocationsConfig() {
        return this.locationsConfig;
    }

    public LobbySwitcherConfiguration getLobbySwitcherConfiguration() {
        return this.lobbySwitcherConfiguration;
    }

    public ItemJoinConfiguration getItemJoinConfiguration() {
        return this.itemJoinConfiguration;
    }

    public <T> void loadAndRender(T config, String file) {
        Resource resource = Source.of(this.dataFolder, file);

        this.cdn.load(resource, config)
            .orThrow(RuntimeException::new);

        this.cdn.render(config, resource)
            .orThrow(RuntimeException::new);
    }

    public <T> void render(T config, String file) {
        Resource resource = Source.of(this.dataFolder, file);

        this.cdn.render(config, resource)
            .orThrow(RuntimeException::new);
    }
}
