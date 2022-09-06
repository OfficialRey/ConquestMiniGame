package net.conquest.entities.mobs;

import net.conquest.other.Util;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public enum MobData {

    PLAYER(EntityType.PLAYER, null, "", 40, 3, 3, 0.1f, 0, 0),
    GUARD(EntityType.WITHER_SKELETON, Sound.ENTITY_ENDER_DRAGON_FLAP, "Royal Guardian", 60, 9, 13, 0.2f, 2, 45), //Throne Room
    KNIGHT(EntityType.ZOMBIE, Sound.ENTITY_BAT_TAKEOFF, "Knight", 30, 5, 3, 0.24f, 2, 30),
    HORSE(EntityType.HORSE, null, "Horse", 40, 0, 15, 0.3f, 1, 0), //For knights
    DEAD_HORSE(EntityType.SKELETON_HORSE, null, "Horse", 80, 0, 20, 0.4f, 1, 0), //For knights
    PEASANT(EntityType.HUSK, Sound.ENTITY_PILLAGER_AMBIENT, "Peasant", 14, 2, 2, 0.28f, 2, 30), //Village
    SCARECROW(EntityType.WITHER_SKELETON, Sound.ENTITY_SKELETON_AMBIENT, "Scarecrow", 12, 2, 3, 0.24f, 2, 20),
    SCOUT(EntityType.SKELETON, Sound.ENTITY_WITHER_SKELETON_AMBIENT, "Scout", 6, 2, 4, 0.3f, 4, 30);

    public final EntityType TYPE;
    public final Sound SPAWN_SOUND;
    public final String NAME;
    public final int HEALTH;
    public final int ATTACK;
    public final int DEFENSE;
    public final float WALK_SPEED;
    public final int GROUP_SIZE;
    public final int SPAWN_TIME;


    MobData(EntityType type, Sound spawnSound, String name, int health, int attack, int defense, float walkSpeed, int groupSize, int spawnTime) {
        TYPE = type;
        SPAWN_SOUND = spawnSound;
        NAME = name;
        HEALTH = health;
        ATTACK = attack;
        DEFENSE = defense;
        WALK_SPEED = walkSpeed;
        GROUP_SIZE = groupSize;
        SPAWN_TIME = spawnTime;
    }
}