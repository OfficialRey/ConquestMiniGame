package net.conquest.gamestates;

import net.conquest.other.Countdown;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class LobbyCountdown extends Countdown {

    private int seconds;
    private BukkitTask idleTask;
    private final GameStateManager gameStateManager;
    private boolean isIdling, isRunning;


    public LobbyCountdown(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void start() {
        if (isRunning) return;
        stopIdleTask();
        seconds = Util.START_COUNTDOWN;
        isRunning = true;
        countdownTask = new BukkitRunnable() {
            @Override
            public void run() {
                switch (seconds) {
                    case 20, 10, 5, 3, 2 -> {
                        Bukkit.broadcastMessage(Util.PREFIX + "The game starts in " + ChatColor.GREEN + seconds + ChatColor.WHITE + " seconds.");
                        Util.playSoundAtAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    }
                    case 1 -> {
                        Bukkit.broadcastMessage(Util.PREFIX + "The game starts in " + ChatColor.GREEN + seconds + ChatColor.WHITE + " second.");
                        Util.playSoundAtAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                    }
                    case 0 -> {
                        gameStateManager.setGameState(GameState.INGAME_STATE);
                        Util.playSoundAtAll(Sound.ENTITY_PLAYER_LEVELUP);
                    }
                    default -> {
                    }
                }
                Util.setPlayerLevel(seconds);
                Util.setPlayerExp(1f - (float) seconds / (float) Util.START_COUNTDOWN);
                seconds--;
            }
        }.runTaskTimer(Conquest.getPlugin(), Util.NULL, Util.TICKS_PER_SECOND);
    }

    @Override
    public void stop() {
        if (isRunning) {
            countdownTask.cancel();
            isRunning = false;
        }
    }

    public void startIdleTask() {
        if (isIdling) return;
        stop();
        isIdling = true;
        idleTask = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(Util.PREFIX + ChatColor.GRAY + "Waiting for players...");
            }
        }.runTaskTimer(Conquest.getPlugin(), Util.NULL, Util.TICKS_PER_SECOND * Util.IDLE_TIME);
    }

    public void stopIdleTask() {
        if (isIdling) {
            idleTask.cancel();
            isIdling = false;
        }
    }
}
