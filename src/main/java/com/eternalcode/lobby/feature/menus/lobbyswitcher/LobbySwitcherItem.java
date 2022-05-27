package com.eternalcode.lobby.feature.menus.lobbyswitcher;

import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

@Contextual
public class LobbySwitcherItem {
    public String name = "none";

    public int slot = 1;

    public Material material = Material.BARRIER;

    public List<String> lore = Collections.singletonList("lore");

    public String server = "none";

    public Boolean glowItem;

    public LobbySwitcherItem(String name, int slot, Material material, List<String> lore, String server, Boolean glowItem) {
        this.name = name;
        this.slot = slot;
        this.material = material;
        this.lore = lore;
        this.server = server;
        this.glowItem = glowItem;
    }

    public LobbySwitcherItem() {
    }
}
