package net.conquest.other;

import java.util.Random;

public class DamageFactor {

    public static final float RAND_FACTOR = 0.05f;

    public static final float
            HOT_FLOOR = 5f,
            BLOCK = 2.5f,
            EXPLOSION = 1.5f,
            WITHER = 2f,
            DROWNING = 3f,
            FALL = 1.5f,
            LAVA = 3f,
            FIRE = LAVA,
            PROJECTILE = 0.25f,
            VOID = 100f;

    public static float getDamageFactor() {
        return (1f + RAND_FACTOR) - (RAND_FACTOR * 2f * new Random().nextFloat());
    }
}
