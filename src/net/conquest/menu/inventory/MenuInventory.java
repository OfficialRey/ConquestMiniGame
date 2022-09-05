package net.conquest.menu.inventory;

import net.conquest.menu.item.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MenuInventory {

    private static final int ROW_SIZE = 9;
    public static final int SMALL = ROW_SIZE * 3, MEDIUM = ROW_SIZE * 4, BIG = ROW_SIZE * 5;
    private final String title;
    private final int size;

    public MenuInventory(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public Inventory getBukkitInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, size, title);
        inv = getInventory(player, inv);
        //Beautify inventory
        return inv;
    }

    protected abstract Inventory getInventory(Player player, Inventory inv);

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }
}