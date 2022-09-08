package net.conquest.minigame;

import net.conquest.other.Util;

public class GameStatus {

    //Expected game time: 20 Minutes
    private int gameTime;

    public GameStatus() {
        gameTime = 0;
    }

    public void start() {
        gameTime = 0;
    }

    public void run() {
        gameTime++;
    }

    public void reset() {
        gameTime = 0;
    }

    public int getGameTime() {
        return gameTime;
    }

    public int getRespawnTime() {
        int respawnTime = Util.DEFAULT_RESPAWN_TIME * (1 + (gameTime / 300));
        if (respawnTime > Util.RESPAWN_TIME_CAP) {
            respawnTime = Util.RESPAWN_TIME_CAP;
        }
        return respawnTime;
    }
}