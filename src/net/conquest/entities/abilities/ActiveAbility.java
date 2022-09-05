package net.conquest.entities.abilities;

import net.conquest.entities.mobs.ConquestPlayer;

public abstract class ActiveAbility extends Ability {

    public ActiveAbility(AbilityList abilityList) {
        super(abilityList);
    }

    public void execute(ConquestPlayer player) {
        if (!isOnCooldown()) {
            startCooldown();
            run(player);
        }
    }

    protected abstract void run(ConquestPlayer player);
}
