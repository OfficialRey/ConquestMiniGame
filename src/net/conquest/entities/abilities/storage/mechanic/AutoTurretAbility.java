package net.conquest.entities.abilities.storage.mechanic;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.ActiveAbility;
import net.conquest.entities.abilities.helper.HomingProjectile;
import net.conquest.entities.abilities.helper.TimedObject;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AutoTurretAbility extends ActiveAbility {

    private static final int MAX_LIFETIME = Util.TICKS_PER_SECOND * 15;

    public AutoTurretAbility() {
        super(AbilityList.AUTO_TURRET);
    }

    @Override
    public Ability copy() {
        return new AutoTurretAbility();
    }

    @Override
    protected void run(ConquestPlayer player) {
        new AutomaticTurret(player);
    }

    static class AutomaticTurret extends TimedObject {

        private final ConquestPlayer owner;
        private final ArmorStand turret;
        private ConquestEntity target;

        public AutomaticTurret(ConquestPlayer player) {
            super(Util.TICKS_PER_SECOND * 20, Util.TICKS_PER_SECOND * 2);
            owner = player;
            turret = Util.createHelperArmorStand(player.getOwner().getLocation(), true);
            turret.getEquipment().setHelmet(new ItemStack(Material.DISPENSER));
            turret.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        }

        private Location getTurretLocation() {
            return turret.getLocation().add(0, turret.getHeight(), 0);
        }

        private void clearTarget() {
            if (target != null) {
                if (!target.isVulnerable() || target.getBukkitEntity().isDead()) {
                    target = null;
                }
            }
        }

        private void findClosestTarget() {
            for (ConquestEntity entity : Conquest.getGame().getAllEntities()) {
                if (entity.getTeam() != owner.getTeam()) {
                    if (target == null || target.distance(getTurretLocation()) > entity.distance(getTurretLocation())) {
                        target = entity;
                    }
                }
            }
        }

        private void shootTarget() {
            if (target != null) {
                if (target.distance(turret) < Util.RANGE) {
                    Vector turretToTarget = target.getBukkitEntity().getLocation().subtract(getTurretLocation()).toVector();
                    Location turretLoc = turret.getEyeLocation().setDirection(turretToTarget);
                    turret.teleport(turretLoc);
                    new HomingProjectile(60, getTurretLocation(), Material.MAGMA_BLOCK, owner, target, 8, 0.6f);
                    Util.playSoundAtAll(Sound.BLOCK_DISPENSER_LAUNCH, getTurretLocation());
                }
            }
        }

        @Override
        public void start() {
            Util.playSoundAtAll(Sound.BLOCK_ANVIL_PLACE);
        }

        @Override
        public void execute() {
            clearTarget();
            findClosestTarget();
            shootTarget();
        }

        @Override
        public void stop() {
            Util.createExplosion(turret.getLocation(), 4f, owner, 5);
            turret.remove();
        }
    }
}