package net.conquest.entities.abilities.storage.assassin;

import net.conquest.entities.abilities.RepeatedActiveAbility;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class ShadowDashAbility extends RepeatedActiveAbility {
    private static final float SPEED = 2.6f, HEIGHT = 0.1f, RANGE = 3f;

    public ShadowDashAbility() {
        super(AbilityList.SHADOW_DASH);
    }

    @Override
    protected void start(ConquestPlayer player) {
        //Hide Player
        Util.hidePlayer(player.getOwner());
        Util.playParticleAtAll(player.getOwner().getLocation().add(0, 1, 0), Particle.SMOKE_NORMAL, 200);
        Util.playSoundAtAll(Sound.ENTITY_ENDERMAN_TELEPORT, player.getOwner().getLocation());

        //Set Velocity
        Vector direction = player.getOwner().getLocation().getDirection();
        direction.setY(direction.getY() + HEIGHT).normalize();
        player.getOwner().teleport(player.getOwner().getLocation().add(0, 1, 0.2f));
        player.getOwner().setGravity(false);
        player.getOwner().setVelocity(direction.multiply(SPEED));
    }

    @Override
    protected void runRepeatedly(ConquestPlayer player) {
        if (player.isOnGround() && player.getOwner().getVelocity().getY() < 0) {
            cancelTask(player);
        }
        //Particles and Damage
        Util.playParticleAtAll(player.getOwner().getLocation().add(0, 1, 0), Particle.SPELL, 5);
        for (ConquestEntity entity : Conquest.getGame().getAllEntities()) {
            if (entity.distance(player) < RANGE) {
                entity.damage(damage, player, EntityDamageEvent.DamageCause.MAGIC);
            }
        }
    }

    @Override
    protected void stop(ConquestPlayer player) {

        //Show player
        if (player.isAlive()) {
            Util.showPlayer(player.getOwner());
            Util.playParticleAtAll(player.getOwner().getLocation().add(0, 1, 0), Particle.SMOKE_NORMAL, 200);
            Util.playSoundAtAll(Sound.ENTITY_ENDERMAN_TELEPORT, player.getOwner().getLocation());
        }

        //Cancel movement
        player.getOwner().setGravity(true);
        if ((int) player.getOwner().getVelocity().length() != 0) {
            player.getOwner().setVelocity(player.getOwner().getVelocity().normalize().multiply(0.5f));
        }
    }

    @Override
    public Ability copy() {
        return new ShadowDashAbility();
    }
}
