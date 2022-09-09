package net.conquest.menu.item.game;

import net.conquest.menu.item.BaseItem;
import net.conquest.menu.item.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConquestItem extends BaseItem {

    private static final float HEALTH_FACTOR = 0.3f;

    protected final int health;
    protected final int attack;
    protected final int defense;
    private Rarity rarity;

    protected final ItemType type;

    //TODO: Item abilities
    //private ItemAbility ability;


    public ConquestItem(String title, List<String> description, ItemStack itemStack, ItemType type, int health, int attack, int defense) {
        super(title, description, itemStack);
        this.type = type;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.calculateRarity();
        this.modifiyMenuItem();
    }
    public ConquestItem(String title, List<String> description, Material material, int amount, ItemType type, int health, int attack, int defense) {
        this(title, description, new ItemStack(material, amount), type, health, attack, defense);
    }

    public ConquestItem(String title, List<String> description, Material material, ItemType type, int health, int attack, int defense) {
        this(title, description, material, 1, type, health, attack, defense);
    }

    private void modifiyMenuItem() {
        ItemStack baseMenuItem = this.getItemStack();
        ItemMeta baseMenuItemMeta = Objects.requireNonNull(baseMenuItem.getItemMeta());

        baseMenuItemMeta.setDisplayName(this.rarity.COLOR + this.getTitle());

        List<String> lore = this.getDescription();

        if (this.health != 0 || this.attack != 0 || this.defense != 0) {
            lore.add("");
            if (this.health != 0) {
                lore.add(ChatColor.BLUE + "+ " + this.health + " Health");
            }
            if (this.attack != 0) {
                lore.add(ChatColor.BLUE + "+ " + this.attack + " Damage");
            }
            if (this.defense != 0) {
                lore.add(ChatColor.BLUE + "+ " + this.defense + " Defense");
            }
        }

        lore.add("");
        lore.add(ChatColor.WHITE + "Rarity: " + this.rarity.COLOR + this.rarity.NAME);

        baseMenuItemMeta.setLore(lore);

        baseMenuItem.setItemMeta(baseMenuItemMeta);
    }

    public ItemType getType() {
        return this.type;
    }

    private void calculateRarity() {

        //TODO: Add ability

        int itemStat = (this.health != 0 && this.attack != 0 && this.defense != 0) ? 1 : 0;

        itemStat += (int) (this.health * ConquestItem.HEALTH_FACTOR + this.defense + this.attack);
        if (itemStat > Rarity.LEGENDARY.STAT_MIN) {
            this.rarity = Rarity.LEGENDARY;
        } else if (itemStat > Rarity.EPIC.STAT_MIN) {
            this.rarity = Rarity.EPIC;
        } else if (itemStat > Rarity.RARE.STAT_MIN) {
            this.rarity = Rarity.RARE;
        } else if (itemStat > Rarity.UNCOMMON.STAT_MIN) {
            this.rarity = Rarity.UNCOMMON;
        } else {
            this.rarity = Rarity.COMMON;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }
    
    public void setAmount(int amount) {
        this.getItemStack().setAmount(amount);
    }

    public boolean reduceAmount() {
        if(this.getAmount() > 0) {
            this.setAmount(this.getAmount() - 1);

            return true;
        }

        return false;
    }

    public void addAmount() {
        this.setAmount(this.getAmount() + 1);
    }

    public ConquestItem copy() {
        return new ConquestItem(this.getTitle(), this.getDescription(), this.getMaterial(), this.getAmount(), this.type, this.health, this.attack, this.defense);
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