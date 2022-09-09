package net.conquest.menu.item.game;

import net.conquest.menu.item.ItemType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PaintedConquestItem extends ConquestItem {

    private final Color color;

    public PaintedConquestItem(ConquestItem item, Color color) {
        super(item.getTitle(), item.getDescription(), item.getMaterial(), item.getAmount(), item.getType(), item.getHealth(), item.getAttack(), item.getDefense());
        this.color = color;
    }

    public PaintedConquestItem(ItemList item, Color color) {
        super(item.ITEM.getTitle(), item.ITEM.getDescription(), item.ITEM.getMaterial(), item.ITEM.getAmount(), item.ITEM.getType(), item.ITEM.getHealth(), item.ITEM.getAttack(), item.ITEM.getDefense());
        this.color = color;
    }

    public PaintedConquestItem(String title, String description, Material material, int amount, ItemType type, int health, int attack, int defense, Color color) {
        super(title, description, material, amount, type, health, attack, defense);
        this.color = color;
    }

    public PaintedConquestItem(String title, String description, Material material, ItemType type, int health, int attack, int defense, Color color) {
        super(title, description, material, 1, type, health, attack, defense);
        this.color = color;
    }

    @Override
    public ItemStack getMenuItem() {
        ItemStack item = super.getMenuItem();
        LeatherArmorMeta lm = (LeatherArmorMeta) item.getItemMeta();

        assert lm != null;

        lm.setColor(color);
        item.setItemMeta(lm);
        return item;
    }

    @Override
    public ConquestItem copy() {
        return new PaintedConquestItem(title, description, material, amount, type, health, attack, defense, color);
    }
}