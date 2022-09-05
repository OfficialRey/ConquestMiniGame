package net.conquest.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class CustomNPCListener implements Listener {

    @EventHandler
    public void onTargetEntity(EntityTargetEvent e) {
        e.setCancelled(!e.getReason().equals(EntityTargetEvent.TargetReason.CUSTOM));
    }
}