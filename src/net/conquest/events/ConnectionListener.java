package net.conquest.events;

import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.plugin.Conquest;
import net.conquest.serialization.Serialization;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ConquestPlayer player = Conquest.getGame().addPlayer(e.getPlayer());
        player.getOwner().teleport(Serialization.loadLobbyLocation());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Conquest.getGame().removePlayer(e.getPlayer());
    }
}