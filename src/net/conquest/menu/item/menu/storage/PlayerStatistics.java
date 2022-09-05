package net.conquest.menu.item.menu.storage;

import net.conquest.menu.inventory.storage.PlayerStatisticsInventory;
import net.conquest.menu.item.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerStatistics extends MenuItem {

    public PlayerStatistics() {
        super(ChatColor.GOLD + "Statistics", "Show your achievements and progress.", Material.GOLD_INGOT);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {

    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.getPlayer().openInventory(new PlayerStatisticsInventory().getBukkitInventory(e.getPlayer()));
    }
}