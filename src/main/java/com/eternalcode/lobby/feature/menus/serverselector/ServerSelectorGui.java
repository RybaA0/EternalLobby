package com.eternalcode.lobby.feature.menus.serverselector;

import com.eternalcode.lobby.config.implementation.ServerSelectorConfiguration;
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

public class ServerSelectorGui {

    private final ServerSelectorConfiguration config;
    private final ConnectionManager connectionManager;

    public ServerSelectorGui(ServerSelectorConfiguration config, ConnectionManager connectionManager) {
        this.config = config;
        this.connectionManager = connectionManager;
    }

    public void openGui(Player player) {

        Gui gui = Gui.gui()
                .rows(this.config.settings.guiRows)
                .title(ChatUtils.component(this.config.settings.guiTitle))
                .disableAllInteractions()
                .create();

        for (ServerSelectorItem serverSelectorItem : this.config.settings.items.values()) {
            ItemStack itemStack = ItemBuilder
                    .from(serverSelectorItem.material)
                    .name(Legacy.RESET_ITEM.append(ChatUtils.component(PlaceholderAPI.setPlaceholders(player, serverSelectorItem.name))))

                    .lore(PlaceholderAPI.setPlaceholders(player, serverSelectorItem.lore).stream()
                            .map(ChatUtils::component)
                            .map(Legacy.RESET_ITEM::append)
                            .collect(Collectors.toList()))
                    .glow(serverSelectorItem.glowItem)
                    .build();

            GuiItem guiItem = new GuiItem(itemStack);

            guiItem.setAction(event -> {
                this.connectionManager.connect(player, serverSelectorItem.server);
                gui.close(player);
            });

            gui.setItem(serverSelectorItem.slot, guiItem);
        }

        if (!this.config.settings.fill.enableFillItems) {
            return;
        }

        gui.getFiller().fill(this.config.settings.fill.fillItems.stream().map(ItemBuilder::from).map(ItemBuilder::asGuiItem).collect(Collectors.toList()));
        gui.open(player);
    }
}
