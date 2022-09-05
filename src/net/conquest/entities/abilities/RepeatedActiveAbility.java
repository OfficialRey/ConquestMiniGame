package net.conquest.entities.abilities;

import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class RepeatedActiveAbility extends ActiveAbility {

    private BukkitTask task;

    public RepeatedActiveAbility(AbilityList abilityList) {
        super(abilityList);
    }

    @Override
    protected void run(ConquestPlayer player) {
        task = new BukkitRunnable() {
            private int executionTime = 0;

            @Override
            public void run() {
                if (executionTime == Util.NULL) {
                    start(player);
                }
                runRepeatedly(player);
                executionTime++;
                if (executionTime == executions) {
                    RepeatedActiveAbility.this.cancelTask(player);
                }
            }
        }.runTaskTimer(Conquest.getPlugin(), 0, 1);
    }

    public void cancelTask(ConquestPlayer player) {
        if (task != null) {
            stop(player);
            task.cancel();
        }
        task = null;
    }

    protected abstract void start(ConquestPlayer player);

    protected abstract void runRepeatedly(ConquestPlayer player);

    protected abstract void stop(ConquestPlayer player);
}
