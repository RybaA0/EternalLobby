package com.eternalcode.lobby.feature.hotbar.itemjoin;

import com.eternalcode.lobby.config.implementation.ItemJoinConfiguration;
import com.eternalcode.lobby.legacy.Legacy;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Collections;

public class ItemJoinController implements Listener {

    private final ItemJoinConfiguration itemJoinConfig;
    private final MiniMessage miniMessage;

    public ItemJoinController(ItemJoinConfiguration itemJoinConfig, MiniMessage miniMessage) {
        this.itemJoinConfig = itemJoinConfig;
        this.miniMessage = miniMessage;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();

        inventory.clear();

        for (JoinItem joinItem : this.itemJoinConfig.settings.slots.values()) {
            ItemStack itemStacks = new ItemStack(joinItem.material, 1);
            ItemMeta itemMetas = itemStacks.getItemMeta();

            itemMetas.setDisplayName(Legacy.LEGACY_SERIALIZER.serialize(Legacy.RESET_ITEM.append(this.miniMessage.deserialize(joinItem.name))));
            itemMetas.setLore(Collections.singletonList(Legacy.LEGACY_SERIALIZER.serialize(this.miniMessage.deserialize(joinItem.name))));
            itemStacks.setItemMeta(itemMetas);

            inventory.setItem(joinItem.slot, itemStacks);
        }
    }

    @EventHandler
    private void onInventoryClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        for (JoinItem joinItem : this.itemJoinConfig.settings.slots.values()) {
            if (event.getItem() != null && event.getItem().getType() == joinItem.material) {
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    event.setCancelled(true);
                    player.performCommand(joinItem.command);
                }
            }
        }

    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        for (JoinItem joinItem : this.itemJoinConfig.settings.slots.values()) {
            if (event.getItemDrop().getItemStack().getType() == joinItem.material) {
                event.setCancelled(true);
            }
        }

    }


    @EventHandler
    private void onPlayerInventoryClickEvent(InventoryClickEvent event) {
        for (JoinItem joinItem : this.itemJoinConfig.settings.slots.values()) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == joinItem.material && event.getSlot() == joinItem.slot) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();

        inventory.clear();

        for (JoinItem joinItem : this.itemJoinConfig.settings.slots.values()) {
            ItemStack itemStacks = new ItemStack(joinItem.material, 1);
            ItemMeta itemMetas = itemStacks.getItemMeta();

            itemMetas.setDisplayName(joinItem.name);
            itemMetas.setLore(joinItem.lore);
            itemStacks.setItemMeta(itemMetas);

            inventory.setItem(joinItem.slot, itemStacks);
        }

    }

    @EventHandler
    private void onPLayerDeath(PlayerDeathEvent event) {
        event.getDrops().clear();
    }
}
