package net.conquest.gamestates;

import net.conquest.other.Countdown;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class EndGameCountdown extends Countdown {

    private int countdown;
    private boolean isRunning;
    private final GameStateManager gameStateManager;

    public EndGameCountdown(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        isRunning = false;
    }

    @Override
    public void start() {
        if (!isRunning) {
            isRunning = true;
            countdown = Util.END_COUNTDOWN;
            countdownTask = new BukkitRunnable() {
                @Override
                public void run() {
                    Util.setPlayerLevel(countdown);
                    switch (countdown) {
                        case 20, 15, 10, 5 -> {
                            Bukkit.broadcastMessage(Util.PREFIX + "The game will end in " + ChatColor.GREEN + countdown + ChatColor.WHITE + " seconds.");
                            Util.playSoundAtAll(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        }
                        case 0 -> {
                            Conquest.getGame().resetGame();
                            stop();
                        }
                    }
                    countdown--;
                }
            }.runTaskTimer(Conquest.getPlugin(), 0, Util.TICKS_PER_SECOND);
        }
    }

    @Override
    public void stop() {
        if (isRunning) {
            isRunning = false;
            countdownTask.cancel();
        }
    }
}
