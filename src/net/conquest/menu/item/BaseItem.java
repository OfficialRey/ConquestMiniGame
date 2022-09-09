package net.conquest.menu.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BaseItem {
    private ItemStack menuItems;

    public BaseItem(String title, List<String> description, ItemStack baseItem) {
        this.menuItems = this.createMenuItem(title, description, baseItem);
    }

    public BaseItem(String title, List<String> description, Material material, int amount) {
        this(title, description, new ItemStack(material, amount));
    }

    public BaseItem(String title, List<String> description, Material material) {
        this(title, description, new ItemStack(material));
    }

    public ItemStack getItemStack() {
        return this.menuItems;
    }

    private ItemStack createMenuItem(String title, List<String> description, ItemStack baseItem) {
        ItemStack menuItem = Objects.requireNonNull(baseItem).clone();
        ItemMeta menuItemMeta = Objects.requireNonNull(menuItem.getItemMeta());

        menuItemMeta.setUnbreakable(true);
        menuItemMeta.setDisplayName(title);
        menuItemMeta.setLore(description.stream().map(line -> ChatColor.GRAY + line).collect(Collectors.toList()));
        menuItemMeta.addItemFlags(ItemFlag.values());

        menuItem.setItemMeta(menuItemMeta);

        return menuItem;
    }

    public String getTitle() {
        return this.getItemStack().getItemMeta().getDisplayName();
    }

    public List<String> getDescription() {
        return this.getItemStack().getItemMeta().getLore();
    }

    public Material getMaterial() {
        return this.getItemStack().getType();
    }

    public int getAmount() {
        return this.getItemStack().getAmount();
    }
}