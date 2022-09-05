package net.conquest.entities.abilities.storage.maniac;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.ActiveAbility;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class SuicideAbility extends ActiveAbility {
    public SuicideAbility() {
        super(AbilityList.SUICIDE);
    }

    @Override
    public Ability copy() {
        return new SuicideAbility();
    }

    @Override
    protected void run(ConquestPlayer player) {
        player.setEntityWalkSpeed(0);
        Util.playSoundAtAll(Sound.ENTITY_TNT_PRIMED, player.getOwner().getLocation());
        Util.playSoundAtAll(Sound.BLOCK_NOTE_BLOCK_BIT);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isAlive()) {
                    Util.createExplosion(player.getOwner().getLocation(), 10f, player, damage);
                    player.setHealth(0.05f);
                }
                player.setEntityWalkSpeed();
            }
        }.runTaskLater(Conquest.getPlugin(), Util.TICKS_PER_SECOND * 3);
    }
}
