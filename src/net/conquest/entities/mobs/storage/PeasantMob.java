package net.conquest.entities.mobs.storage;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestMob;
import net.conquest.entities.mobs.MobData;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.menu.item.game.ItemList;
import org.bukkit.Location;

import java.util.Random;

public class PeasantMob extends ConquestMob {

    public PeasantMob(Location location, ConquestTeam conquestTeam) {
        super(location, MobData.PEASANT, conquestTeam);
    }


    private static final int CHESTPLATE = 1, WEAPON = 4;

    @Override
    protected ConquestItem[] getItems(ConquestTeam conquestTeam) {
        PeasantType type = PeasantType.values()[new Random().nextInt(PeasantType.values().length)];
        //4 Armor pieces
        //1 Weapon slot
        ConquestItem[] items = {ItemList.getTeamHelmet(conquestTeam), null, ItemList.LEGGINGS.ITEM, ItemList.BOOTS.ITEM, null};
        switch (type) {
            case FARMER -> {
                items[CHESTPLATE] = ItemList.FARMING_SHIRT.ITEM;
                items[WEAPON] = ItemList.FARMING_HOE.ITEM;
            }
            case MINER -> {
                items[CHESTPLATE] = ItemList.MINING_SHIRT.ITEM;
                items[WEAPON] = ItemList.MINING_PICKAXE.ITEM;
            }
            case LUMBERJACK -> {
                items[CHESTPLATE] = ItemList.LUMBER_SHIRT.ITEM;
                items[WEAPON] = ItemList.LUMBER_AXE.ITEM;
            }
            case DIGGER -> {
                items[CHESTPLATE] = ItemList.DIGGING_SHIRT.ITEM;
                items[WEAPON] = ItemList.DIGGING_SHOVEL.ITEM;
            }
            default -> {
                items[CHESTPLATE] = ItemList.CLOAK.ITEM;
                items[WEAPON] = ItemList.SWORD.ITEM;
            }
        }
        return items;
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

enum PeasantType {
    FARMER,
    LUMBERJACK,
    MINER,
    DIGGER
}