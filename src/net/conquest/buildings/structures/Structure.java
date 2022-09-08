package net.conquest.buildings.structures;

import net.conquest.other.ConquestTeam;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class Structure {

    protected final Location location;
    private final int executionTime; //seconds
    protected ConquestTeam team;
    private BukkitTask task;


    private int exeTime;

    public Structure(Location location, int executionTime) {
        this.location = location;
        this.executionTime = executionTime;
    }

    public void startTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                execute();
            }
        }.runTaskTimer(Conquest.getPlugin(), 0, executionTime / Util.GAME_SPEED);
    }

    public void stopTask() {
        if (task != null) {
            task.cancel();
        }
    }

    protected abstract void execute();

    public void reset() {
        stopTask();
    }
}