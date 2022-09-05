package net.conquest.events;

import net.conquest.commands.BuildCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GriefListener implements Listener {

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        e.setCancelled(!BuildCommand.canBuild(e.getPlayer()));
    }

    @EventHandler
    public void onBreakFarmLand(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        e.setCancelled(!BuildCommand.canBuild(e.getPlayer()));
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        e.setCancelled(!BuildCommand.canBuild(e.getPlayer()));
    }
}