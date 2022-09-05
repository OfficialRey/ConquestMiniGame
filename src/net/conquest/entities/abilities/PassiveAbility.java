package net.conquest.entities.abilities;

import net.conquest.entities.mobs.ConquestPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public abstract class PassiveAbility extends Ability {

    public PassiveAbility(AbilityList abilityList) {
        super(abilityList);
    }

    public void triggerMove(ConquestPlayer player) {
        if (!isOnCooldown()) {
            onMove(player);
        }
    }

    public void triggerDeath(ConquestPlayer player) {
        if (!isOnCooldown()) {
            onDeath(player);
        }
    }

    public void triggerSpawn(ConquestPlayer player) {
        if (!isOnCooldown()) {
            onSpawn(player);
        }
    }

    public void triggerLaunchArrow(ConquestPlayer player, Arrow arrow) {
        if (!isOnCooldown()) {
            onLaunchArrow(player, arrow);
        }
    }

    public void triggerAttack(ConquestPlayer player) {
        if (!isOnCooldown()) {
            onAttack(player);
        }
    }

    public void triggerDamaged(ConquestPlayer player, DamageCause cause) {
        if (!isOnCooldown()) {
            onDamaged(player, cause);
        }
    }

    public void triggerRegenerateHealth(ConquestPlayer player) {
        if (!isOnCooldown()) {
            onRegenerateHealth(player);
        }
    }

    public void triggerJump(ConquestPlayer player) {
        if (!isOnCooldown()) {
            onJump(player);
        }
    }

    protected abstract void onMove(ConquestPlayer player);

    protected abstract void onDeath(ConquestPlayer player);

    protected abstract void onSpawn(ConquestPlayer player);

    protected abstract void onLaunchArrow(ConquestPlayer player, Arrow arrow);

    protected abstract void onAttack(ConquestPlayer player);

    protected abstract void onDamaged(ConquestPlayer player, DamageCause cause);

    protected abstract void onRegenerateHealth(ConquestPlayer player);

    protected abstract void onJump(ConquestPlayer player);
}
