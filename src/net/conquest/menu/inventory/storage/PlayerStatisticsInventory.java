package net.conquest.menu.inventory.storage;

import net.conquest.menu.inventory.MenuInventory;
import net.conquest.menu.item.MenuItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerStatisticsInventory extends MenuInventory {

    public PlayerStatisticsInventory() {
        super(ChatColor.LIGHT_PURPLE + "Statistics", BIG);
    }

    @Override
    protected Inventory getInventory(Player player, Inventory inv) {
        return inv;
    }
}
