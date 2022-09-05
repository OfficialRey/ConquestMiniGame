package net.conquest.entities.abilities.storage.maniac;

import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.entities.abilities.PassiveAbility;
import net.conquest.entities.mobs.ConquestPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

public class IrresponsibleAbility extends PassiveAbility {

    public IrresponsibleAbility() {
        super(AbilityList.IRRESPONSIBLE);
    }

    @Override
    public Ability copy() {
        return new IrresponsibleAbility();
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

    }

    @Override
    public void onAttack(ConquestPlayer player) {

    }

    @Override
    public void onDamaged(ConquestPlayer player, DamageCause cause) {
        if (cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.ENTITY_EXPLOSION) {
            player.getOwner().setVelocity(player.getOwner().getVelocity().multiply(0.5f));
        }
    }

    @Override
    public void onRegenerateHealth(ConquestPlayer player) {

    }

    @Override
    public void onJump(ConquestPlayer player) {

    }
}
