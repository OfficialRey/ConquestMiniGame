package net.conquest.buildings.structures;

import net.conquest.entities.mobs.ConquestMob;
import net.conquest.entities.mobs.MobData;
import net.conquest.entities.mobs.storage.*;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Barracks extends Structure {

    private final MobData data;

    private int exeTime;
    private final ArrayList<ConquestMob> mobsSpawned;

    public Barracks(Location location, MobData data) {
        super(location, StructureData.BARRACKS.EXE_TIME);
        this.data = data;
        exeTime = data.SPAWN_TIME;
        mobsSpawned = new ArrayList<>();
    }

    @Override
    protected void execute() {
        exeTime++;
        if (exeTime >= data.SPAWN_TIME) {
            cleanUp();
            exeTime = 0;
            for (int i = 0; i < data.GROUP_SIZE; i++) {
                if (mobsSpawned.size() < data.GROUP_SIZE * Util.SPAWN_FACTOR) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            createMob();
                        }
                    }.runTaskLater(Conquest.getPlugin(), (i * 15L) / Util.GAME_SPEED);
                }
            }
        }
    }

    private void cleanUp() {
        mobsSpawned.removeIf(mob -> mob == null || mob.getBukkitEntity().isDead());
    }

    private void createMob() {
        mobsSpawned.add(spawnMob());
    }

    private ConquestMob spawnMob() {
        Util.playSoundAtAll(data.SPAWN_SOUND, location);
        Util.playParticleAtAll(location, Particle.ASH, 200);
        Util.playParticleAtAll(location, Particle.SPELL, 200);
        return switch (data) {
            case GUARD -> new GuardMob(location, team);
            case KNIGHT -> new KnightMob(location, team);
            case SCOUT -> new ScoutMob(location, team);
            case SCARECROW -> new ScarecrowMob(location, team);

            default -> new PeasantMob(location, team);
        };
    }
}
