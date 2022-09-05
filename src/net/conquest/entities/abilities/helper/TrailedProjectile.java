package net.conquest.entities.abilities.helper;

import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Particle;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TrailedProjectile {

    private static final int MAX_LIFETIME = 100;
    private int lifetime;
    private final Projectile projectile;

    private BukkitTask task;

    public TrailedProjectile(Projectile projectile) {
        this.projectile = projectile;
        lifetime = 0;
        startTask();
    }

    private void startTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                Util.playParticleAtAll(projectile.getLocation(), Particle.SPELL, 5);
                if (projectile.isDead() || projectile.isOnGround() || lifetime >= MAX_LIFETIME) {
                    stopTask();
                }
                lifetime++;
            }
        }.runTaskTimer(Conquest.getPlugin(), 0, 2);
    }

    private void stopTask() {
        task.cancel();
        if (projectile != null) {
            projectile.remove();
        }
    }
}