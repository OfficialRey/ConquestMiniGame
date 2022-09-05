package net.conquest.entities.abilities.storage.assassin;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.PassiveAbility;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.entities.abilities.helper.TrailedProjectile;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageEvent;

public class SharpShooterAbility extends PassiveAbility {

    public SharpShooterAbility() {
        super(AbilityList.SHARPSHOOTER);
    }

    @Override
    public Ability copy() {
        return new SharpShooterAbility();
    }

    @Override
    public void onMove(ConquestPlayer player) {

    }

    @Override
    public void onDeath(ConquestPlayer player) {

    }

    @Override
    public void onSpawn(ConquestPlayer player) {

    }

    @Override
    public void onLaunchArrow(ConquestPlayer player, Arrow arrow) {
        startCooldown();
        arrow.setGravity(false);
        arrow.setVelocity(arrow.getVelocity().multiply(2.5f));
        new TrailedProjectile(arrow);
        player.addAmmo();
        startCooldown();
    }

    @Override
    public void onAttack(ConquestPlayer player) {

    }

    @Override
    public void onDamaged(ConquestPlayer player, EntityDamageEvent.DamageCause cause) {

    }

    @Override
    public void onRegenerateHealth(ConquestPlayer player) {

    }

    @Override
    public void onJump(ConquestPlayer player) {

    }
}
