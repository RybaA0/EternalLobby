package com.eternalcode.lobby.config.implementation;

import com.eternalcode.lobby.feature.menus.serverselector.ServerSelectorItem;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import panda.utilities.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ServerSelectorConfiguration {

    @Description({ "# Server selector configuration" })
    public Settings settings = new Settings();

    @Contextual
    public static class Settings {
        public FillSettings fill = new FillSettings();

        @Description({ StringUtils.EMPTY })
        public String guiTitle = "Wybór serwera:";

        @Description({ StringUtils.EMPTY })
        public int guiRows = 3;

        @Contextual
        public static class FillSettings {
            @Description({ "# Fill item options" })
            public boolean enableFillItems = true;

            public List<Material> fillItems = Arrays.asList(Material.BLACK_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Description({ StringUtils.EMPTY })
        public Map<Integer, ServerSelectorItem> items = ImmutableMap.of(
            1, new ServerSelectorItem("Survival", 11, Material.GRASS_BLOCK, new ArrayList<>(), "survival", true),
            2, new ServerSelectorItem("Creative", 13, Material.BRICK, new ArrayList<>(), "creative", true),
            3, new ServerSelectorItem("Spectator", 15, Material.DRAGON_EGG, new ArrayList<>(), "War of the Ender", true)
        );
    }
}
