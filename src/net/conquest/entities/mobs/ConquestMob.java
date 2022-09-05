package net.conquest.entities.mobs;

import net.conquest.buildings.captureable.Captureable;
import net.conquest.entities.mobs.storage.ScarecrowMob;
import net.conquest.menu.item.ItemType;
import net.conquest.other.ConquestTeam;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;

import javax.annotation.Nullable;

public abstract class ConquestMob extends ConquestEntity {
    private int stuckTime;
    private Location oldLocation;

    public ConquestMob(Location location, MobData data, ConquestTeam conquestTeam) {
        super(createEntity(location, data.TYPE), data, conquestTeam);
        oldLocation = entity.getLocation();

        setItems(getItems(conquestTeam));
        equipItems();
        health = getMaxHealth();
    }

    public void run() {
        advancedAI();
        loadChunks();

        defaultAI();
        checkIsDead();
    }

    private void checkIsDead() {
        if (entity.isDead() || (int) health <= Util.NULL) {
            killEntity();
        }
    }

    private boolean foundTarget;

    private void defaultAI() {
        if (!conquestTeam.isEliminated()) {
            foundTarget = false;
            targetOpponent();
            targetAlliedZone();
            targetHostileZone();
            clearTarget();
        }
    }

    private void clearTarget() {
        if (getTarget() != null) {
            if (getTarget() instanceof Player) {
                ConquestPlayer player = (ConquestPlayer) Conquest.getGame().getConquestEntity(getTarget());
                if (player != null && player.isSpectating()) {
                    setTarget(null);
                }
            } else if (getTarget() instanceof ArmorStand) {
                if (distance(getTarget()) < Util.CAPTURE_RADIUS / 3) {
                    setTarget(null);
                }
            }
        }
    }

    private void targetOpponent() {
        ConquestEntity opponent = null;
        for (ConquestTeam allTeams : Conquest.getGame().getTeams()) {
            if (conquestTeam != allTeams) {
                for (ConquestEntity allOpponents : allTeams.getEntities()) {
                    if (distance(allOpponents) < Util.CAPTURE_RADIUS) {
                        if (opponent == null || distance(allOpponents) < distance(opponent)) {
                            if (allOpponents.isVulnerable()) {
                                opponent = allOpponents;
                            }
                        }
                    }
                }
            }
        }
        if (opponent != null) {
            setTarget((LivingEntity) opponent.getBukkitEntity());
        }
    }

    private void targetHostileZone() {
        for (ConquestTeam allTeams : Conquest.getGame().getTeams()) {
            if (conquestTeam != allTeams) {
                Captureable captureable = allTeams.getNextZone();
                if (captureable != null) {
                    setTarget(captureable.getMarker());
                    return;
                }
            }
        }
    }

    private void targetAlliedZone() {
        Captureable zone = conquestTeam.getNextZone();
        if (zone.isThreatened()) {
            setTarget(zone.getMarker());
        }
    }

    public void loadChunks() {
        for (Chunk chunk : getChunksToLoad(entity.getLocation().getChunk())) {
            if (!chunk.isLoaded()) {
                chunk.load();
            }
        }
    }

    public Chunk[] getChunksToLoad(Chunk chunk) {
        int x = chunk.getX();
        int z = chunk.getZ();
        World w = chunk.getWorld();
        return new Chunk[]{chunk, w.getChunkAt(x + 1, z), w.getChunkAt(x - 1, z),

                w.getChunkAt(x, z + 1), w.getChunkAt(x, z - 1),

                w.getChunkAt(x + 1, z + 1), w.getChunkAt(x + 1, z - 1),

                w.getChunkAt(x - 1, z + 1), w.getChunkAt(x - 1, z - 1),};
    }

    private void setTarget(@Nullable LivingEntity target) {
        if (!foundTarget || target == null) {
            foundTarget = target != null;
            ((Mob) entity).setTarget(target);
        }
    }

    protected Entity getTarget() {
        return ((Mob) entity).getTarget();
    }

    protected void updateWeapon(ConquestItem item) {
        if (!items.contains(item)) {
            for (int i = 0; i < items.size(); i++) {
                ItemType type = items.get(i).getType();
                if (type == ItemType.WEAPON || type == ItemType.BOW) {
                    items.set(i, item);
                    equipItems();
                    return;
                }
            }
            items.add(item);
            equipItems();
        }
    }

    public void unStuck() {
        if (oldLocation.distance(entity.getLocation()) < 0.5) {
            stuckTime++;
            if (stuckTime > Util.MAX_TIME_STUCK) {
                killEntity();
            }
        } else {
            stuckTime = 0;
        }
        oldLocation = entity.getLocation();
    }

    protected abstract ConquestItem[] getItems(ConquestTeam conquestTeam);

    public abstract void onDeath();

    public abstract void advancedAI();

    public abstract void onDamage();

    public abstract void onAttack();

    public abstract void onSummon(ConquestTeam conquestTeam);

}