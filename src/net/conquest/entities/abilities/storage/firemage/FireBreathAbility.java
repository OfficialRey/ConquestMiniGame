package net.conquest.entities.abilities.storage.firemage;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.RepeatedActiveAbility;
import net.conquest.entities.abilities.helper.FlameParticle;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.other.Util;
import org.bukkit.Sound;

import java.util.Random;

public class FireBreathAbility extends RepeatedActiveAbility {

    public FireBreathAbility() {
        super(AbilityList.FIRE_BREATH);
    }

    @Override
    public Ability copy() {
        return new FireBreathAbility();
    }

    @Override
    protected void start(ConquestPlayer player) {
        Util.playSoundAtAll(Sound.ENTITY_BLAZE_SHOOT, player.getOwner().getLocation());
    }

    @Override
    protected void runRepeatedly(ConquestPlayer player) {
        if (new Random().nextInt(100) < 50) {
            Util.playSoundAtAll(Sound.ENTITY_BLAZE_SHOOT, player.getOwner().getLocation());
        }
        new FlameParticle(player, damage);
    }

    @Override
    protected void stop(ConquestPlayer player) {

    }
}
