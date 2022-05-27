package com.eternalcode.lobby.feature.menus.lobbyswitcher;

import com.eternalcode.lobby.config.implementation.LobbySwitcherConfiguration;
import com.eternalcode.lobby.feature.menus.ConnectionManager;
import com.eternalcode.lobby.legacy.Legacy;
import com.eternalcode.lobby.utils.ChatUtils;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class LobbySwitcherGui {
    private final LobbySwitcherConfiguration config;
    private final ConnectionManager connectionManager;

    public LobbySwitcherGui(LobbySwitcherConfiguration config, ConnectionManager connectionManager) {
        this.config = config;
        this.connectionManager = connectionManager;
    }

    public void openGui(Player player) {
        Gui gui = Gui.gui()
            .rows(this.config.settings.guiRows)
            .title(ChatUtils.component(this.config.settings.guiTitle))
            .disableAllInteractions()
            .create();

        for (LobbySwitcherItem lobbySwitcherItem : this.config.settings.items.values()) {
            ItemStack itemStack = ItemBuilder
                .from(lobbySwitcherItem.material)
                .name(Legacy.RESET_ITEM.append(ChatUtils.component(PlaceholderAPI.setPlaceholders(player, lobbySwitcherItem.name))))
                .lore(PlaceholderAPI.setPlaceholders(player, lobbySwitcherItem.lore).stream()
                    .map(ChatUtils::component)
                    .map(Legacy.RESET_ITEM::append)
                    .collect(Collectors.toList()))
                .glow(lobbySwitcherItem.glowItem)
                .build();

            GuiItem guiItem = new GuiItem(itemStack);

            guiItem.setAction(event -> {
                this.connectionManager.connect(player, lobbySwitcherItem.server);
                gui.close(player);
            });

            gui.setItem(lobbySwitcherItem.slot, guiItem);
        }

        if (!this.config.settings.fill.enableFillItems) {
            return;
        }

        gui.getFiller().fill(this.config.settings.fill.fillItems.stream().map(ItemBuilder::from).map(ItemBuilder::asGuiItem).collect(Collectors.toList()));
        gui.open(player);
    }
}
