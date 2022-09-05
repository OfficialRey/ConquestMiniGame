package net.conquest.gamestates;

public abstract class GameState {

    public static final int LOBBY_STATE = 0, INGAME_STATE = 1, ENDGAME_STATE = 2;

    public abstract void start();

    public abstract void stop();

}
