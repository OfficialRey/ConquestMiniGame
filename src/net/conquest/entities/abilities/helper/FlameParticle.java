package net.conquest.entities.abilities.helper;

import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.ConquestTeam;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class FlameParticle {

    private static final int MAX_LIFETIME = (int) (Util.TICKS_PER_SECOND / 2.5f);

    private int lifetime;
    private final BukkitTask task;
    private final ArmorStand particle;
    private final ConquestTeam team;

    public FlameParticle(ConquestPlayer source, int damage) {
        particle = Util.createHelperArmorStand(source.getOwner().getLocation().add(0, 1.5f, 0));
        Vector velocity = source.getOwner().getLocation().getDirection().normalize().multiply(0.6f);
        particle.setGravity(true);

        lifetime = 0;
        team = source.getTeam();

        task = new BukkitRunnable() {
            @Override
            public void run() {
                particle.teleport(particle.getLocation().add(velocity));
                for (ConquestEntity entity : Conquest.getGame().getAllEntities()) {
                    if (entity.getTeam() != team) {
                        if (entity.distance(particle.getLocation().add(0, -1, 0)) < 1.5f) {
                            entity.damage(damage);
                            entity.getBukkitEntity().setFireTicks(entity.getBukkitEntity().getFireTicks() + 3);
                        }
                    }
                }
                if (lifetime > MAX_LIFETIME) {
                    stopTask();
                }
                animate();
                lifetime++;
            }
        }.runTaskTimer(Conquest.getPlugin(), 0, 1);
    }

    private void stopTask() {
        task.cancel();
        particle.remove();
    }

    private void animate() {
        Util.playSpedParticleAtAll(particle.getLocation(), Particle.FLAME, 1, 0.5f, 0);
    }
}