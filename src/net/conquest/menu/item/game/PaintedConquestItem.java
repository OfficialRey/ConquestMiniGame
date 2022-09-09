package net.conquest.menu.item.game;

import net.conquest.menu.item.ItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PaintedConquestItem extends ConquestItem {

    private final Color color;

    public PaintedConquestItem(String title, List<String> description, Material material, int amount, ItemType type, int health, int attack, int defense, Color color) {
        super(title, description, material, amount, type, health, attack, defense);
        this.color = color;
        this.modifiyMenuItem();
    }

    public PaintedConquestItem(ConquestItem item, Color color) {
        this(item.getTitle(), item.getDescription(), item.getMaterial(), item.getAmount(), item.getType(), item.getHealth(), item.getAttack(), item.getDefense(), color);
    }

    public PaintedConquestItem(ItemList item, Color color) {
        this(item.ITEM, color);
    }

    public PaintedConquestItem(String title, List<String> description, Material material, ItemType type, int health, int attack, int defense, Color color) {
        this(title, description, material, 1, type, health, attack, defense, color);
    }

    private void modifiyMenuItem() {
        ItemStack conquestItem = this.getItemStack();
        LeatherArmorMeta conquestItemMeta = Objects.requireNonNull((LeatherArmorMeta) conquestItem.getItemMeta());

        conquestItemMeta.setColor(this.color);

        conquestItem.setItemMeta(conquestItemMeta);
    }

    @Override
    public ConquestItem copy() {
        return new PaintedConquestItem(this.getTitle(), this.getDescription(), this.getMaterial(), this.getAmount(), type, health, attack, defense, color);
    }
}