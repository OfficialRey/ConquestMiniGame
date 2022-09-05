package net.conquest.menu.inventory.storage;

import net.conquest.kits.Kit;
import net.conquest.kits.KitList;
import net.conquest.menu.inventory.MenuInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class KitInventory extends MenuInventory {

    public KitInventory() {
        super(ChatColor.LIGHT_PURPLE + "Choose a kit.", BIG);
    }

    @Override
    protected Inventory getInventory(Player player, Inventory inv) {
        for (Kit kit : KitList.getKits()) {
            inv.addItem(kit.getMenuItem());
        }
        return inv;
    }
}
