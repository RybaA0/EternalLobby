package com.eternalcode.lobby.config.implementation;

import com.eternalcode.lobby.feature.menus.lobbyswitcher.LobbySwitcherItem;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import panda.utilities.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LobbySwitcherConfiguration {


    @Description({ "# LobbySwitcher configuration" })
    public Settings settings = new Settings();


    @Contextual
    public static class Settings {
        public FillSettings fill = new FillSettings();

        @Description({ StringUtils.EMPTY })
        public String guiTitle = "Wybór lobby:";

        @Description({ StringUtils.EMPTY })
        public int guiRows = 3;

        @Contextual
        public static class FillSettings {
            @Description({ "# Fill item options" })
            public boolean enableFillItems = true;
            public List<Material> fillItems = Arrays.asList(Material.BLACK_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE);
        }

        @Description({ StringUtils.EMPTY })
        public Map<Integer, LobbySwitcherItem> items = ImmutableMap.of(
            1, new LobbySwitcherItem("Lobby #0", 2, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby0", true),
            2, new LobbySwitcherItem("Lobby #1", 3, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby1", true),
            3, new LobbySwitcherItem("Lobby #2", 4, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby2", true),
            4, new LobbySwitcherItem("Lobby #3", 5, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby3", true),
            5, new LobbySwitcherItem("Lobby #4", 6, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby4", true),
            6, new LobbySwitcherItem("Lobby #5", 7, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby5", true),
            7, new LobbySwitcherItem("Lobby #6", 8, Material.QUARTZ_BLOCK, new ArrayList<>(), "lobby6", true)
        );
    }
}
