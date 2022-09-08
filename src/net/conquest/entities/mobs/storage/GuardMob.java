package net.conquest.entities.mobs.storage;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestMob;
import net.conquest.entities.mobs.MobData;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.menu.item.game.ItemList;
import org.bukkit.Location;

public class GuardMob extends ConquestMob {

    public GuardMob(Location location, ConquestTeam conquestTeam) {
        super(location, MobData.GUARD, conquestTeam);
    }

    @Override
    protected ConquestItem[] getItems(ConquestTeam conquestTeam) {
        return new ConquestItem[]{
                ItemList.SWORD.ITEM,
                ItemList.SHIELD.ITEM,
                ItemList.getTeamHelmet(conquestTeam),
                ItemList.CHESTPLATE.ITEM,
                ItemList.getTeamLeggings(conquestTeam),
                ItemList.BOOTS.ITEM
        };
    }

    @Override
    public void onMobDeath() {

    }

    @Override
    public void advancedAI() {

    }

    @Override
    public void onDamage() {

    }

    @Override
    public void onAttack() {

    }

    @Override
    public void onSummon(ConquestTeam conquestTeam) {

    }
}
