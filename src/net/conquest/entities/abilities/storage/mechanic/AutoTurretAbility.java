package net.conquest.entities.abilities.storage.mechanic;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.ActiveAbility;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

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

    class AutomaticTurret {

        private final ConquestPlayer owner;
        private final ArmorStand turret;
        private ConquestEntity target;

        public AutomaticTurret(ConquestPlayer player) {
            owner = player;
            turret = Util.createHelperArmorStand(player.getOwner().getLocation());
            new BukkitRunnable() {
                @Override
                public void run() {
                    findClosestTarget();

                }
            }.runTaskTimer(Conquest.getPlugin(), 0, 1);
        }

        private void findClosestTarget() {
            for (ConquestEntity entity : Conquest.getGame().getAllEntities()) {
                if (entity.getTeam() != owner.getTeam()) {
                    if (target == null || target.distance(turret) > entity.distance(turret)) {
                        target = entity;
                    }
                }
            }
        }
    }
}