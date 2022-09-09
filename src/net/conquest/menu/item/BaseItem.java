package net.conquest.menu.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BaseItem {

    protected final String title;
    protected final String description;
    protected final Material material;

    protected int amount;

    private final ItemStack itemStack;

    public BaseItem(String title, String description, Material material, int amount) {
        this.title = title;
        this.description = description;
        this.material = material;
        this.amount = amount;
        itemStack = null;
    }

    public BaseItem(String title, String description, Material material) {
        this.title = title;
        this.description = description;
        this.material = material;
        this.amount = 1;
        itemStack = null;
    }

    public BaseItem(String title, String description, ItemStack itemStack) {
        this.title = title;
        this.description = description;
        this.itemStack = itemStack;
        this.material = Material.AIR;
        this.amount = 1;
    }

    public ItemStack getMenuItem() {
        ItemStack item = itemStack != null ? itemStack : new ItemStack(material, amount);
        ItemMeta im = item.getItemMeta();

        assert im != null;

        im.setUnbreakable(true);
        im.setDisplayName(title);
        ArrayList<String> lore = new ArrayList<>();
        for (String line : description.split("//")) {
            lore.add(ChatColor.GRAY + line);
        }
        im.setLore(lore);
        im.addItemFlags(ItemFlag.values());
        item.setItemMeta(im);
        return item;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }
}