package net.conquest.events;

import net.conquest.entities.abilities.PassiveAbility;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.entities.abilities.ActiveAbility;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AbilityListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        ConquestPlayer player = Conquest.getGame().getConquestPlayer(e.getPlayer().getUniqueId());
        if (player != null && player.getKit() != null) {
            if (!player.isSpectating()) {
                player.getAbilities().forEach(ability -> {
                    if (ability instanceof PassiveAbility) {
                        ((PassiveAbility) ability).triggerMove(player);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onLaunchArrow(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Arrow) {
            if (e.getEntity().getShooter() instanceof Entity) {
                ConquestEntity entity = Conquest.getGame().getConquestEntity(((Entity) e.getEntity().getShooter()).getUniqueId());
                if (entity instanceof ConquestPlayer) {
                    ConquestPlayer player = (ConquestPlayer) entity;
                    if (player.getKit() != null) {
                        if (!player.isSpectating()) {
                            if (player.removeAmmo()) {
                                player.getAbilities().forEach(ability -> {
                                    if (ability instanceof PassiveAbility) {
                                        ((PassiveAbility) ability).triggerLaunchArrow(player, (Arrow) e.getEntity());
                                    }
                                });
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void onActivateAbility(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ConquestPlayer player = Conquest.getGame().getConquestPlayer(e.getPlayer().getUniqueId());
            if (player != null && player.getKit() != null) {
                if (!player.isSpectating()) {
                    player.getAbilities().forEach(ability -> {
                        if (ability instanceof ActiveAbility) {
                            if (Util.compareItemStacks(e.getItem(), ability.getMenuItem())) {
                                ((ActiveAbility) ability).execute(player);
                            }
                        }
                    });
                }
            }
        }
    }
}