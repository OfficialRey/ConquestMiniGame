package net.conquest.events;

import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.ConquestTeam;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {


    //TODO: Fix NullPointerException after game end (ConquestPlayer = null)
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        if (Conquest.getGame().getSpectators().contains(e.getPlayer())) {
            Conquest.getGame().getSpectators().forEach(player -> player.sendMessage(Util.SPECTATOR_CHAT_PREFIX + ChatColor.GOLD + e.getPlayer().getDisplayName() + ChatColor.GRAY + " >> " + ChatColor.WHITE + e.getMessage()));
        } else {
            ConquestPlayer player = Conquest.getGame().getConquestPlayer(e.getPlayer().getUniqueId());
            if (player != null) {
                ConquestTeam conquestTeam = player.getTeam();
                Bukkit.broadcastMessage(conquestTeam.getChatColor() + e.getPlayer().getDisplayName() + Util.CHAT_MESSAGE_PREFIX + e.getMessage());

            }
        }
    }
}