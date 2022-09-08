package net.conquest.entities.mobs.storage;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestMob;
import net.conquest.entities.mobs.MobData;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.menu.item.game.ItemList;
import org.bukkit.Location;

public class ScarecrowMob extends ConquestMob {

    public ScarecrowMob(Location location, ConquestTeam conquestTeam) {
        super(location, MobData.SCARECROW, conquestTeam);
    }

    @Override
    protected ConquestItem[] getItems(ConquestTeam conquestTeam) {
        return new ConquestItem[]{
                ItemList.getPumpkin(conquestTeam), //helmet
                ItemList.NETHERITE_CHESTPLATE.ITEM,
                ItemList.NETHERITE_LEGGINGS.ITEM,
                ItemList.BOOTS.ITEM,
                ItemList.NETHERITE_SCYTHE.ITEM
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
