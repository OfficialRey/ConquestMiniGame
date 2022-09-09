package net.conquest.menu.item.menu.storage;

import net.conquest.menu.inventory.storage.KitInventory;
import net.conquest.menu.item.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitSelector extends MenuItem {

    public KitSelector() {
        super(ChatColor.LIGHT_PURPLE + "Kit Selection", "Choose a kit to play with.", Material.IRON_CHESTPLATE);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {

    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.getPlayer().openInventory(new KitInventory().getBukkitInventory(e.getPlayer()));
    }
}
