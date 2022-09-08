package net.conquest.entities.mobs.storage;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestMob;
import net.conquest.entities.mobs.MobData;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.menu.item.game.ItemList;
import net.conquest.other.Util;
import org.bukkit.Location;

import java.util.Random;

public class KnightMob extends ConquestMob {

    public KnightMob(Location location, ConquestTeam conquestTeam) {
        super(location, MobData.KNIGHT, conquestTeam);
    }

    @Override
    protected ConquestItem[] getItems(ConquestTeam conquestTeam) {
        return new ConquestItem[]{
                ItemList.getTeamHelmet(conquestTeam),
                ItemList.CHAIN_CHESTPLATE.ITEM,
                ItemList.CHAIN_LEGGINGS.ITEM,
                ItemList.CHAIN_BOOTS.ITEM,
                ItemList.SWORD.ITEM,
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
        if (new Random().nextInt(100) < Util.KNIGHT_HORSE_CHANCE) {
            HorseMob horse;
            if (new Random().nextInt(100) < Util.DEAD_HORSE_CHANCE) {
                horse = new HorseMob(entity.getLocation(), MobData.HORSE, conquestTeam);
            } else {
                horse = new HorseMob(entity.getLocation(), MobData.DEAD_HORSE, conquestTeam);
            }
            horse.setRider(this);
        }
    }

    //Knights riding dead and living horses
}