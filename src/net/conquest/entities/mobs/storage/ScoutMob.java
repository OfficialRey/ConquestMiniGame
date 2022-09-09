package net.conquest.entities.mobs.storage;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestMob;
import net.conquest.entities.mobs.MobData;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.menu.item.game.ItemList;
import net.conquest.other.Util;
import org.bukkit.Location;

public class ScoutMob extends ConquestMob {

    public ScoutMob(Location location, ConquestTeam conquestTeam) {
        super(location, MobData.SCOUT, conquestTeam);
    }

    @Override
    protected ConquestItem[] getItems(ConquestTeam conquestTeam) {
        return new ConquestItem[]{
                ItemList.getTeamHelmet(conquestTeam),
                ItemList.getTeamChestplate(conquestTeam),
                ItemList.getTeamLeggings(conquestTeam),
                ItemList.getTeamBoots(conquestTeam),
                ItemList.SWORD.ITEM
        };
    }

    @Override
    public void onDeath() {

    }

    @Override
    public void advancedAI() {
        if (getTarget() == null || getTarget().getLocation().distance(entity.getLocation()) > Util.RANGE) {
            updateWeapon(ItemList.BOW.ITEM);
        } else {
            updateWeapon(ItemList.SWORD.ITEM);
        }
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
