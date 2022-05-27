package com.eternalcode.lobby.config.implementation;

import com.eternalcode.lobby.feature.hotbar.itemjoin.JoinItem;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Map;

public class ItemJoinConfiguration {

    @Description({ "# Item join configuration" })
    public Settings settings = new Settings();

    @Contextual
    public static class Settings {
        public Map<Integer, JoinItem> slots = ImmutableMap.of(
                1, new JoinItem("&bGame selector", 0, Material.GRASS_BLOCK, new ArrayList<>(), "/lobby server-selector"),
                3, new JoinItem("&bLobby selector", 8, Material.DRAGON_EGG, new ArrayList<>(), "/lobby lobby-switcher")
        );
    }
}

