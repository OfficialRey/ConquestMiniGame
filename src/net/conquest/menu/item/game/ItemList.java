package net.conquest.menu.item.game;

import net.conquest.other.ConquestTeam;
import net.conquest.menu.item.ItemType;
import net.conquest.other.SkullCreator;
import net.conquest.other.Util;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ItemList {

    //----General----
    BOW(new ConquestItem("Bow", "A tool made of tendon and wood to shoot arrows.//Used in ranged combat.", Material.BOW, ItemType.BOW, 0, 2, 0)),

    ARROW(new ConquestItem("Arrow", "Ammunition to use for the bow. Can be accelerated to//high speed to cut through flesh and bone.", Material.ARROW, 32, ItemType.AMMO, 0, 1, 0)),

    SHIELD(new ConquestItem("Shield", "A shield to block attacks.//Grants a chance to entirely deny incoming damage.", Material.SHIELD, ItemType.OFFHAND, 15, 1, 5)),

    ELVEN_SWORD(new ConquestItem("Elven Sword", "Forged by the talented elven of the mountains.//Cuts precisely and carefully.", Material.IRON_SWORD, ItemType.WEAPON, 0, 6, 0)),
    SWORD(new ConquestItem("Sword", "A typical sword mass produced for war.//Serves its purpose.", Material.STONE_SWORD, ItemType.WEAPON, 0, 5, 0)),

    CHESTPLATE(new ConquestItem("Chestplate", "An iron forged chestplate.//Offers great protection against stabs.", Material.IRON_CHESTPLATE, ItemType.CHESTPLATE, 25, 0, 8)),

    //--Leather--
    HOOD(new ConquestItem("Hood", "A hood that hides your face and identity.", Material.LEATHER_HELMET, ItemType.HELMET, 7, 3, 1)),

    CLOAK(new ConquestItem("Cloak", "A cloak that holds all your weapons and gear.", Material.LEATHER_CHESTPLATE, ItemType.CHESTPLATE, 13, 0, 2)),

    LEGGINGS(new ConquestItem("Trousers", "To protect your legs.", Material.LEATHER_LEGGINGS, ItemType.LEGGINGS, 4, 0, 1)),

    BOOTS(new ConquestItem("Boots", "Sturdy shoes for battle.//You ain't losin' those.", Material.LEATHER_BOOTS, ItemType.BOOTS, 8, 0, 3)),

    //--Chain--

    CHAIN_HELMET(new ConquestItem("Helmet", "Made of chains.", Material.CHAINMAIL_HELMET, ItemType.HELMET, 12, 2, 2)),
    CHAIN_CHESTPLATE(new ConquestItem("Chestplate", "Made of chains.", Material.CHAINMAIL_CHESTPLATE, ItemType.CHESTPLATE, 15, 0, 5)),
    CHAIN_LEGGINGS(new ConquestItem("Leggings", "Made of chains.", Material.CHAINMAIL_LEGGINGS, ItemType.LEGGINGS, 14, 1, 3)),
    CHAIN_BOOTS(new ConquestItem("Boots", "Made of chains.", Material.CHAINMAIL_BOOTS, ItemType.BOOTS, 6, 1, 2)),

    //--Netherite--
    NETHERITE_SCYTHE(new ConquestItem("Death's Scythe", "Forged in the fires of hell.", Material.NETHERITE_HOE, ItemType.WEAPON, 0, 11, 0)),
    NETHERITE_CHESTPLATE(new ConquestItem("Chestplate", "Made from materials of hell.", Material.NETHERITE_CHESTPLATE, ItemType.CHESTPLATE, 40, 2, 12)),
    NETHERITE_LEGGINGS(new ConquestItem("Leggings", "Made from materials of hell.", Material.NETHERITE_LEGGINGS, ItemType.LEGGINGS, 25, 5, 7)),


    //---Peasant equipment---

    //Farmer
    FARMING_HOE(new ConquestItem("Rake", "Used to plow the field.", Material.IRON_HOE, ItemType.WEAPON, 0, 4, 0)),

    FARMING_SHIRT(new PaintedConquestItem(CLOAK, Color.RED)),

    //-Lumberjack-
    LUMBER_AXE(new ConquestItem("Axe", "Used to gather wood from trees.", Material.IRON_AXE, ItemType.WEAPON, 0, 4, 0)),

    LUMBER_SHIRT(new PaintedConquestItem(CLOAK, Util.Colors.BROWN.COLOR)),

    //-Miner-
    MINING_PICKAXE(new ConquestItem("Pickaxe", "A tool used to mine ore and stone.", Material.IRON_PICKAXE, ItemType.WEAPON, 0, 4, 0)),

    MINING_SHIRT(new PaintedConquestItem(CLOAK, Color.GRAY)),

    //-Digger-
    DIGGING_SHOVEL(new ConquestItem("Shovel", "A tool to dig earth.", Material.IRON_SHOVEL, ItemType.WEAPON, 0, 4, 0)),

    DIGGING_SHIRT(new PaintedConquestItem(CLOAK, Color.YELLOW)),


    //Assassin
    IRON_DAGGER(new ConquestItem("Iron Dagger", "A sharpened weapon to slice someones throat.", Material.IRON_SWORD, ItemType.WEAPON, 0, 7, 0)),

    WHITE_HOOD(new PaintedConquestItem(HOOD, Color.WHITE)),

    RED_CLOAK(new PaintedConquestItem(CLOAK, Color.RED)),

    BROWN_LEGGINGS(new PaintedConquestItem(LEGGINGS, Color.fromRGB(110, 38, 14))),

    BLACK_BOOTS(new PaintedConquestItem(BOOTS, Color.BLACK)),


    //Maniac
    RED_HOOD(new PaintedConquestItem(HOOD, Color.RED)),
    WHITE_CLOAK(new PaintedConquestItem(CLOAK, Color.WHITE)),
    RED_LEGGINGS(new PaintedConquestItem(LEGGINGS.ITEM, Color.RED)),

    //Mechanic
    SAFETY_HELMET(new PaintedConquestItem("Safety Helmet", "Required on every construction site.", Material.LEATHER_HELMET, ItemType.HELMET, 4, 2, 5, Color.YELLOW)),
    BOILER_SUIT_CHEST(new PaintedConquestItem("Boiler Suit Chest Part", "A part of a boiler suit", Material.LEATHER_CHESTPLATE, ItemType.CHESTPLATE, 6, 0, 2, Color.BLUE)),
    BOILER_SUIT_LEGGINGS(new PaintedConquestItem("Boiler Suit Leg Part", "A part of a boiler suit", Material.LEATHER_LEGGINGS, ItemType.LEGGINGS, 4, 0, 2, Color.BLUE)),
    WRENCH(new ConquestItem("Wrench", "Used to repair stuff.//Can also be used to damage things.", Material.STONE_AXE, ItemType.WEAPON, 0, 7, 0)),

    //NONE
    NONE(null);


    public final ConquestItem ITEM;

    ItemList(ConquestItem item) {
        ITEM = item;
    }

    public static final String
            BLUE_PUMPKIN = "b387265d13172847950ea506ba0ff2b04b7999828525a0329057a98985db25fc",
            RED_PUMPKIN = "1b77198b6c70036fe4ac35961ae071dc3cdd0176ea37f0913582a015e0b373c0";

    public static ConquestItem getTeamHelmet(ConquestTeam conquestTeam) {
        return new PaintedConquestItem(ItemList.HOOD.ITEM, conquestTeam.getColor()); //Team-Colored Hood
    }

    public static ConquestItem getTeamChestplate(ConquestTeam conquestTeam) {
        return new PaintedConquestItem(ItemList.CLOAK.ITEM, conquestTeam.getColor()); //Team-Colored Hood
    }

    public static ConquestItem getTeamLeggings(ConquestTeam conquestTeam) {
        return new PaintedConquestItem(ItemList.LEGGINGS.ITEM, conquestTeam.getColor());
    }

    public static ConquestItem getTeamBoots(ConquestTeam conquestTeam) {
        return new PaintedConquestItem(ItemList.BOOTS.ITEM, conquestTeam.getColor());
    }

    public static ConquestItem getPumpkin(ConquestTeam conquestTeam) {
        ItemStack itemStack;
        if (conquestTeam.getColor() == Color.RED) {
            itemStack = SkullCreator.itemFromTexture(RED_PUMPKIN);
        } else if (conquestTeam.getColor() == Color.BLUE) {
            itemStack = SkullCreator.itemFromTexture(BLUE_PUMPKIN);
        } else {
            itemStack = new ItemStack(Material.AIR);
        }
        return new ConquestItem("Soul Prison", "Your soul is bound to this vessel.//" + ChatColor.BLACK + "There is no escape.", itemStack, ItemType.HELMET, -10, 5, 0);
    }

    public static ConquestItem getCompass(ConquestTeam conquestTeam) {
        return new ConquestItem(conquestTeam.getChatColor() + "Compass", "Shows you your next target.", Material.COMPASS, ItemType.OTHER, 0, 0, 0);
    }
}