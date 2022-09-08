package net.conquest.economy;

import net.conquest.other.Util;

public class PlayerGameStatistics {

    private int playerKills;
    private int npcKills;
    private int deaths;

    private int captures;
    private int damageDealt;
    private int damageReceived;
    private int playTime;

    public PlayerGameStatistics() {
        playerKills = Util.NULL;
        deaths = Util.NULL;
        damageDealt = Util.NULL;
        damageReceived = Util.NULL;
        playTime = Util.NULL;
    }

    public void addPlayerKill() {
        playerKills++;
    }

    public void addNPCKill() {
        npcKills++;
    }

    public void addDeath() {
        deaths++;
    }

    public void addCapture() {
        captures++;
    }

    public void addDamageDealt(int amount) {
        damageDealt += amount;
    }

    public void addDamageReceived(int amount) {
        damageReceived += amount;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public int getNpcKills() {
        return npcKills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getCaptures() {
        return captures;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public int getDamageReceived() {
        return damageReceived;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public int getPlayTime() {
        return playTime;
    }

    public GameReward getGameReward(boolean gameWin) {
        int gold = 0;
        int exp = 0;

        gold += PlayerReward.PLAYER_KILL.GOLD_EARNED * playerKills;
        exp += PlayerReward.PLAYER_KILL.EXP_EARNED * playerKills;

        gold += PlayerReward.NPC_KILL.GOLD_EARNED * npcKills;
        exp += PlayerReward.NPC_KILL.EXP_EARNED * npcKills;

        gold += PlayerReward.DAMAGE_DEALT.GOLD_EARNED * (damageDealt / PlayerReward.DAMAGE_REQUIRED);
        exp += PlayerReward.DAMAGE_DEALT.EXP_EARNED * (damageDealt / PlayerReward.DAMAGE_REQUIRED);

        gold += PlayerReward.DAMAGE_RECEIVED.GOLD_EARNED * (damageReceived / PlayerReward.DAMAGE_REQUIRED);
        exp += PlayerReward.DAMAGE_RECEIVED.EXP_EARNED * (damageReceived / PlayerReward.DAMAGE_REQUIRED);

        gold += PlayerReward.POINT_CAPTURED.GOLD_EARNED * captures;
        exp += PlayerReward.POINT_CAPTURED.EXP_EARNED * captures;

        gold += PlayerReward.TIME_REWARD.GOLD_EARNED * (playTime / PlayerReward.TIME_REQUIRED);
        exp += PlayerReward.TIME_REWARD.EXP_EARNED * (playTime / PlayerReward.TIME_REQUIRED);

        if (gameWin) {
            gold += PlayerReward.GAME_WIN.GOLD_EARNED;
        } else {
            gold += PlayerReward.GAME_LOSE.GOLD_EARNED;
        }
        return new GameReward(gold, exp);
    }

    public static class GameReward {

        int goldReward;
        int expReward;

        public GameReward(int goldReward, int expReward) {
            this.goldReward = goldReward;
            this.expReward = expReward;
        }

        public int getGoldReward() {
            return goldReward;
        }

        public int getExpReward() {
            return expReward;
        }
    }
}