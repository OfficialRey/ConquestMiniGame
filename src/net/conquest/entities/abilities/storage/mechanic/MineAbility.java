package net.conquest.entities.abilities.storage.mechanic;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.ActiveAbility;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class MineAbility extends ActiveAbility {

    private static final float MAX_MINES = 5, RANGE = 1.25f, EXPLOSION_RANGE = 5;

    private final ArrayList<Mine> mines = new ArrayList<>();

    public MineAbility() {
        super(AbilityList.MINE);
    }

    @Override
    public Ability copy() {
        return new MineAbility();
    }

    @Override
    protected void run(ConquestPlayer player) {
        addMine(new Mine(player));
        removeLastMine();
    }

    private void removeLastMine() {
        if (mines.size() > MAX_MINES) {
            mines.get(Util.NULL).stopTask();
            mines.remove(Util.NULL);
        }
    }

    private void addMine(Mine mine) {
        if (!mines.contains(mine)) {
            mines.add(mine);
        }
    }

    class Mine {
        private final ArmorStand mine;
        private final BukkitTask task;
        private final ConquestPlayer owner;

        public Mine(ConquestPlayer owner) {
            this.owner = owner;
            mine = Util.createHelperArmorStand(getGroundBlock(owner.getOwner().getLocation().add(0, 0.5, 0)));
            mine.getEquipment().setHelmet(new ItemStack(Material.STONE_PRESSURE_PLATE));
            Util.playSoundAtAll(Sound.BLOCK_STONE_PLACE, owner.getOwner().getLocation());
            task = run();
        }

        private Location getGroundBlock(Location location) {
            location.setY((int) location.getY());
            while (location.getBlock().getType() == Material.AIR && location.getY() > 0) {
                location.add(0, -1, 0);
            }
            if (!location.getBlock().getType().isSolid()) {
                location.add(0, -0.5, 0);
            }
            return location.add(0, -0.38, 0);
        }

        private int beeper;

        public BukkitTask run() {
            return new BukkitRunnable() {
                @Override
                public void run() {
                    beeper++;
                    if (beeper % 8 == 0) {
                        Util.playPitchedSoundAtAll(Sound.BLOCK_NOTE_BLOCK_BIT, mine.getLocation(), 2);
                    }
                    Util.playSpedParticleAtAll(getPlateLocation(), Particle.SMOKE_NORMAL, 1, 0.3f, 0);
                    for (ConquestEntity entity : Conquest.getGame().getAllEntities()) {
                        if (owner.getTeam() != entity.getTeam()) {
                            if (getPlateLocation().distance(entity.getBukkitEntity().getLocation()) < RANGE) {
                                blowUp();
                                stopTask();
                                return;
                            }
                        }
                    }
                }
            }.runTaskTimer(Conquest.getPlugin(), 0, (long) (Util.TICKS_PER_SECOND / 4f));
        }

        private Location getPlateLocation() {
            return mine.getLocation().add(0, 1.2f, 0);
        }

        private void blowUp() {
            Util.createExplosion(getPlateLocation(), EXPLOSION_RANGE, owner, damage);
        }

        private void stopTask() {
            task.cancel();
            mine.remove();
        }
    }
}