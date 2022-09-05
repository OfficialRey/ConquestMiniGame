package net.conquest.menu.item.game;

import net.conquest.menu.item.BaseItem;
import net.conquest.menu.item.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ConquestItem extends BaseItem {

    private static final float HEALTH_FACTOR = 0.3f;

    protected final int health;
    protected final int attack;
    protected final int defense;
    protected final int dodgeChance;
    private Rarity rarity;

    protected final ItemType type;

    //TODO: Item abilities
    //private ItemAbility ability;

    public ConquestItem(String title, String description, Material material, int amount, ItemType type, int health, int attack, int defense) {
        super(title, description, material, amount);
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        dodgeChance = 0;
        calculateRarity();
    }

    public ConquestItem(String title, String description, Material material, int amount, ItemType type, int health, int attack, int defense, int dodgeChance) {
        super(title, description, material, amount);
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
        calculateRarity();
    }

    public ConquestItem(String title, String description, Material material, ItemType type, int health, int attack, int defense) {
        super(title, description, material, 1);
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        dodgeChance = 0;
        calculateRarity();
    }

    public ConquestItem(String title, String description, ItemStack itemStack, ItemType type, int health, int attack, int defense) {
        super(title, description, itemStack);
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        dodgeChance = 0;
        calculateRarity();
    }

    @Override
    public ItemStack getMenuItem() {
        ItemStack item = super.getMenuItem();
        ItemMeta im = item.getItemMeta();

        assert im != null;

        im.setDisplayName(rarity.COLOR + im.getDisplayName());

        List<String> lore = im.getLore();

        assert lore != null;

        if (health != 0 || attack != 0 || defense != 0) {
            lore.add("");
            if (health != 0) {
                lore.add(ChatColor.BLUE + "+ " + health + " Health");
            }
            if (attack != 0) {
                lore.add(ChatColor.BLUE + "+ " + attack + " Damage");
            }
            if (defense != 0) {
                lore.add(ChatColor.BLUE + "+ " + defense + " Defense");
            }
            if (dodgeChance != 0) {
                lore.add(ChatColor.BLUE + "+ " + dodgeChance + "% Dodge Chance");
            }
        }

        lore.add("");
        lore.add(ChatColor.WHITE + "Rarity: " + rarity.COLOR + rarity.NAME);

        im.setLore(lore);
        item.setItemMeta(im);

        return item;
    }

    public ItemType getType() {
        return type;
    }

    private void calculateRarity() {

        //TODO: Add ability

        int itemStat = (health != 0 && attack != 0 && defense != 0) ? 1 : 0;

        itemStat += (int) (health * HEALTH_FACTOR + defense + attack);
        if (itemStat > Rarity.LEGENDARY.STAT_MIN) {
            rarity = Rarity.LEGENDARY;
        } else if (itemStat > Rarity.EPIC.STAT_MIN) {
            rarity = Rarity.EPIC;
        } else if (itemStat > Rarity.RARE.STAT_MIN) {
            rarity = Rarity.RARE;
        } else if (itemStat > Rarity.UNCOMMON.STAT_MIN) {
            rarity = Rarity.UNCOMMON;
        } else {
            rarity = Rarity.COMMON;
        }
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public void setAmount(int value) {
        amount = value;
    }

    public boolean reduceAmount() {
        if (amount > 0) {
            amount--;
            return true;
        }
        return false;
    }

    public void addAmount() {
        amount++;
    }

    public ConquestItem copy() {
        return new ConquestItem(title, description, material, amount, type, health, attack, defense);
    }
}

enum Rarity {
    COMMON("Common", ChatColor.GRAY, 0),
    UNCOMMON("Uncommon", ChatColor.GREEN, 6),
    RARE("Rare", ChatColor.AQUA, 7),
    EPIC("Epic", ChatColor.LIGHT_PURPLE, 12),
    LEGENDARY("Legendary", ChatColor.RED, 15);


    public final String NAME;
    public final ChatColor COLOR;
    public final int STAT_MIN;

    Rarity(String name, ChatColor color, int statMinimum) {
        NAME = name;
        COLOR = color;
        STAT_MIN = statMinimum;
    }
}