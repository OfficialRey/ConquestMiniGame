package net.conquest.menu.item;

import net.conquest.menu.inventory.MenuInventory;
import net.conquest.menu.item.BaseItem;
import net.conquest.menu.item.ItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public abstract class MenuItem extends BaseItem {

    public MenuItem(String title, List<String> description, Material material) {
        super(title, description, material);
    }

    public abstract void onInventoryClick(InventoryClickEvent e);

    public abstract void onInteract(PlayerInteractEvent e);
}
