package net.conquest.menu.item.menu.storage;

import net.conquest.menu.item.MenuItem;

public enum ItemList {

    KIT_SELECTOR(new KitSelector()),
    PLAYER_STATS(new PlayerStatistics());

    public final MenuItem MENU_ITEM;

    ItemList(MenuItem menuItem) {
        MENU_ITEM = menuItem;
    }
}