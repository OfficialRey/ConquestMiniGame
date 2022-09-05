package net.conquest.menu.inventory.storage;

import net.conquest.menu.inventory.MenuInventory;

import java.util.ArrayList;

public class Inventories {

    private static final ArrayList<MenuInventory> inventories;

    static {
        inventories = new ArrayList<>();
        inventories.add(new KitInventory());
        inventories.add(new PlayerStatisticsInventory());
    }

    public static ArrayList<MenuInventory> getInventories() {
        return inventories;
    }
}