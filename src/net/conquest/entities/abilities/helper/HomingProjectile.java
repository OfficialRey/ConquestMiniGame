package net.conquest.entities.abilities.helper;

import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.other.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HomingProjectile extends TimedObject {

    private final ConquestEntity owner;
    private final ConquestEntity target;
    private final ArmorStand projectile;
    private final int damage;
    private final float speed;

    public HomingProjectile(int maxTimeLived, Location location, Material head, ConquestEntity owner, ConquestEntity target, int damage, float speed) {
        super(maxTimeLived, 1);
        this.owner = owner;
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        projectile = Util.createHelperArmorStand(location, false);
        projectile.setSmall(true);
        projectile.getEquipment().setHelmet(new ItemStack(head));
    }

    private Location getProjectileLocation() {
        return projectile.getLocation().add(0, projectile.getHeight(), 0);
    }

    private Location getTargetLocation() {
        return target.getBukkitEntity().getLocation().add(0, target.getBukkitEntity().getHeight() / 3, 0);
    }

    @Override
    public void start() {

    }

    @Override
    public void execute() {
        if (target.isVulnerable() && !target.getBukkitEntity().isDead()) {
            if (getProjectileLocation().add(0, 1, 0).getBlock().getType().isSolid()) {
                Util.createExplosion(getProjectileLocation(), 1f, owner, damage);
                stopTask();
                return;
            }
            Vector projectileToEnemy = getTargetLocation().subtract(getProjectileLocation()).toVector().normalize();
            Location newLoc = getProjectileLocation().add(projectileToEnemy.multiply(speed));
            projectile.teleport(newLoc);
            if (getProjectileLocation().distance(getTargetLocation()) < 0.4f) {
                Util.createExplosion(getProjectileLocation(), 1f, owner, damage);
                stopTask();
            }
        } else {
            stopTask();
        }
    }

    @Override
    public void stop() {
        projectile.remove();
    }
}