package net.conquest.entities;

import net.conquest.economy.PlayerGameStatistics;
import net.conquest.other.Util;
import net.conquest.serialization.Serialization;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerStatistic {

    private final Player owner;
    private int level;
    private int exp;
    private int kills;
    private int deaths;
    private int gold; //Main Currency
    private int gems; //VIP Currency
    private long playtime; //In seconds

    public PlayerStatistic(Player owner, int gold, int gems, int kills, int deaths, int playtime) {
        this.owner = owner;
        this.gold = gold;
        this.gems = gems;
        this.kills = kills;
        this.deaths = deaths;
        this.playtime = playtime;
    }

    public PlayerStatistic(Player owner) {
        this.owner = owner;
        gold = 0;
        gems = 0;
        kills = 0;
        deaths = 0;
        playtime = 0;
    }

    public void addStatistics(PlayerGameStatistics statistics, boolean wonGame) {
        //TODO: Exp and Level
        PlayerGameStatistics.GameReward reward = statistics.getGameReward(wonGame);
        owner.sendMessage(Util.PREFIX + "You have received " + ChatColor.GOLD + reward.getGoldReward() + " Gold " + ChatColor.WHITE + " for this round.");

        gold += reward.getGoldReward();
        int exp = reward.getExpReward();
        kills += statistics.getPlayerKills();
        deaths += statistics.getDeaths();
        playtime += statistics.getPlayTime();
        saveStatistic();
    }

    public void saveStatistic() {
        Serialization.savePlayerStatistic(this);
    }

    public Player getOwner() {
        return owner;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getGems() {
        return gems;
    }

    public int getGold() {
        return gold;
    }

    public long getPlaytime() {
        return playtime;
    }

    public int getKills() {
        return kills;
    }
    //TODO: Handle players achievements (kills, deaths, gold earned, xp earned etc...)
}