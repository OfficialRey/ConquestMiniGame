package net.conquest.entities;

import net.conquest.serialization.Serialization;
import org.bukkit.entity.Player;

public class PlayerStatistic {

    private Player owner;
    private int kills;
    private int deaths;
    private int gold; //Main Currency
    private int gems; //VIP Currency
    private long playtime; //In seconds

    public PlayerStatistic(Player player) {

    }

    public void saveStatistic() {
        Serialization.savePlayerStatistic(this);
    }

    //TODO: Handle players achievements (kills, deaths, gold earned, xp earned etc...)

}