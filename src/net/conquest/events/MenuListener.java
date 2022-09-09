package net.conquest.events;

import net.conquest.commands.BuildCommand;
import net.conquest.kits.Kit;
import net.conquest.kits.KitList;
import net.conquest.menu.item.menu.storage.ItemList;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            for (ItemList item : ItemList.values()) {
                if (Util.compareItemStacks(e.getItem(), item.MENU_ITEM.getMenuItem())) {
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
                    item.MENU_ITEM.onInteract(e);
                    e.setCancelled(!BuildCommand.canBuild(e.getPlayer()));
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onSelectKit(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            e.setCancelled(!BuildCommand.canBuild((Player) e.getWhoClicked()));
            if (e.getClick().isLeftClick()) {
                for (Kit kit : KitList.getKits()) {
                    if (Util.compareItemStacks(e.getCurrentItem(), kit.getMenuItem())) {
                        Conquest.getGame().setPlayerKit((Player) e.getWhoClicked(), kit);
                        e.getWhoClicked().closeInventory();
                        ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            e.setCancelled(!BuildCommand.canBuild((Player) e.getWhoClicked()));
            if (e.getClick().isLeftClick()) {
                for (ItemList item : ItemList.values()) {
                    if (Util.compareItemStacks(e.getCurrentItem(), item.MENU_ITEM.getMenuItem())) {
                        ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
                        e.getWhoClicked().closeInventory();
                        item.MENU_ITEM.onInventoryClick(e);
                        return;
                    }
                }
            }
        }
    }
}