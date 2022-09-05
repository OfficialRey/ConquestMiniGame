package net.conquest.other;

import org.bukkit.scheduler.BukkitTask;

public abstract class Countdown {

    protected BukkitTask countdownTask;

    public abstract void start();

    public abstract void stop();

}