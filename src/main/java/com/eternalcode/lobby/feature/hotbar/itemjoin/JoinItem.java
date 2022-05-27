package com.eternalcode.lobby.feature.hotbar.itemjoin;

import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

@Contextual
public class JoinItem {

    public String name = "none";

    public int slot = 1;

    public Material material = Material.BARRIER;

    public List<String> lore = Collections.singletonList("lore");

    public String command = "none";

    public JoinItem(String name, int slot, Material material, List<String> lore, String command) {
        this.name = name;
        this.slot = slot;
        this.material = material;
        this.lore = lore;
        this.command = command;
    }

    public JoinItem() {}
}
