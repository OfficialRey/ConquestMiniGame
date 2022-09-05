package net.conquest.menu.inventory.storage;

import net.conquest.menu.inventory.MenuInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StructureInventory extends MenuInventory {
    public StructureInventory() {
        super(ChatColor.GRAY + "Create a capture point ", BIG);
    }

    @Override
    protected Inventory getInventory(Player player, Inventory inv) {
        return inv;
    }
}
