package net.conquest.entities.abilities.helper;

import net.conquest.plugin.Conquest;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class TimedObject {

    private int timeLived; //ticks
    private final int maxTimeLived;//ticks
    private final int period;
    private BukkitTask task;

    public TimedObject(int maxTimeLived, int period) {
        this.maxTimeLived = maxTimeLived;
        this.period = period;
        startTask();
    }

    private void startTask() {
        start();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                execute();
                timeLived += period;
                if (timeLived >= maxTimeLived) {
                    stopTask();
                }
            }
        }.runTaskTimer(Conquest.getPlugin(), 0, period);
    }

    protected void stopTask() {
        task.cancel();
        stop();
    }

    public abstract void start();

    public abstract void execute();

    public abstract void stop();
}