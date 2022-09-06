package net.conquest.events;

import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.gamestates.EndGameState;
import net.conquest.gamestates.GameState;
import net.conquest.gamestates.LobbyState;
import net.conquest.other.DamageFactor;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class BattleListener implements Listener {

    @EventHandler
    public void onNoDamage(EntityDamageEvent e) {
        e.setDamage(0);
        GameState state = Conquest.getGame().getGameStateManager().getCurrentGameState();
        if (state instanceof LobbyState || state instanceof EndGameState) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        e.getDrops().clear();
        e.setDroppedExp(Util.NULL);
    }

    @EventHandler
    public void onMeleeDamage(EntityDamageByEntityEvent e) {
        DamageCause cause = e.getCause();
        if (cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.ENTITY_EXPLOSION || cause == DamageCause.ENTITY_SWEEP_ATTACK) {
            if (e.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity) {
                ConquestEntity attacker = Conquest.getGame().getConquestEntity(e.getDamager().getUniqueId());
                ConquestEntity victim = Conquest.getGame().getConquestEntity(e.getEntity().getUniqueId());

                if (attacker != null && victim != null) {
                    if (attacker.getTeam() != victim.getTeam()) {
                        if (!(attacker instanceof ConquestPlayer && ((ConquestPlayer) attacker).isSpectating())) {
                            if (victim.isVulnerable()) {
                                victim.damage(attacker, DamageCause.ENTITY_ATTACK);
                                return;
                            }
                        }
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDistanceDamage(EntityDamageByEntityEvent e) {
        if (e.getCause() == DamageCause.PROJECTILE) {
            if (e.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) e.getDamager();
                ConquestEntity attacker = Conquest.getGame().getConquestEntity(((Entity)projectile.getShooter()).getUniqueId());
                ConquestEntity victim = Conquest.getGame().getConquestEntity(e.getEntity().getUniqueId());

                if (attacker != null && victim != null) {
                    if (attacker.getTeam() != victim.getTeam()) {
                        if (victim.isVulnerable()) {
                            victim.damage((int) (attacker.getAttack() * projectile.getVelocity().length() * DamageFactor.PROJECTILE), attacker, DamageCause.PROJECTILE);
                            projectile.remove();
                            return;
                        }
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAmbientDamage(EntityDamageEvent e) {
        DamageCause cause = e.getCause();
        if (cause != DamageCause.CUSTOM && cause != DamageCause.ENTITY_ATTACK && cause != DamageCause.ENTITY_EXPLOSION && cause != DamageCause.ENTITY_SWEEP_ATTACK && cause != DamageCause.PROJECTILE) {
            ConquestEntity entity = Conquest.getGame().getConquestEntity(e.getEntity().getUniqueId());
            if (entity != null) {
                if (entity.isVulnerable()) {
                    float damage = (int) e.getDamage() + 1;
                    damage *= switch (e.getCause()) {
                        case HOT_FLOOR -> DamageFactor.HOT_FLOOR;
                        case CONTACT -> DamageFactor.BLOCK;
                        case BLOCK_EXPLOSION -> DamageFactor.EXPLOSION;
                        case WITHER -> DamageFactor.WITHER;
                        case DROWNING -> DamageFactor.DROWNING;
                        case FALL -> DamageFactor.FALL;
                        case LAVA -> DamageFactor.LAVA;
                        case FIRE -> DamageFactor.FIRE;
                        case VOID -> DamageFactor.VOID;

                        default -> 1;
                    };
                    if (damage > 0) {
                        entity.damage((int) damage);
                        return;
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void removeArrows(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            if (e.getEntity().isDead() || e.getEntity().isOnGround() || e.getEntity().getLocation().getBlock().getType() != Material.AIR) {
                e.getEntity().remove();
            }
        }
    }
}