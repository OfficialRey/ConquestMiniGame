package net.conquest.economy;

public enum PlayerReward {

    TIME_REWARD(0, 10),
    DAMAGE_DEALT(2, 0),
    DAMAGE_RECEIVED(1, 0),
    PLAYER_KILL(20, 10),
    NPC_KILL(2, 1),
    POINT_CAPTURED(5, 10),
    GAME_WIN(100, 50),
    GAME_LOSE(25, 15);


    public final static int DAMAGE_REQUIRED = 1000, TIME_REQUIRED = 60;

    public final int GOLD_EARNED;
    public final int EXP_EARNED;

    PlayerReward(int GOLD_EARNED, int EXP_EARNED) {
        this.GOLD_EARNED = GOLD_EARNED;
        this.EXP_EARNED = EXP_EARNED;
    }
}