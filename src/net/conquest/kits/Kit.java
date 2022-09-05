package net.conquest.kits;

import net.conquest.menu.item.game.ConquestItem;
import net.conquest.menu.item.game.ItemList;
import net.conquest.entities.abilities.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Kit {

    private final String title;
    private final String description;
    private final Material material;
    private final int price = 0;
    private ArrayList<Ability> abilities = new ArrayList<>();

    private ArrayList<ConquestItem> conquestItems = new ArrayList<>();

    public Kit(String title, String description, Material material, ItemList... conquestItems) {
        this.title = ChatColor.GOLD + title;
        this.description = description;
        this.material = material;
        setConquestItems(conquestItems);
    }

    public Kit(String title, String description, Material material, ArrayList<ConquestItem> conquestItems, ArrayList<Ability> abilities) {
        this.title = ChatColor.GOLD + title;
        this.description = description;
        this.material = material;
        this.conquestItems = conquestItems;
        this.abilities = abilities;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Ability... newAbilities) {
        abilities.clear();
        for (Ability ability : newAbilities) {
            abilities.add(ability.copy());
        }
    }

    public ItemStack getMenuItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta im = item.getItemMeta();

        assert im != null;

        im.setDisplayName(title);
        ArrayList<String> lore = new ArrayList<>();
        for (String line : description.split("//")) {
            lore.add(ChatColor.GRAY + line);
        }

        //TODO: Serious player money
        int player_money = -10;
        if (price > 0) {
            lore.add("");
            ChatColor color = price > player_money ? ChatColor.WHITE : ChatColor.RED;
            lore.add(ChatColor.GREEN + "Price: " + color + price + ChatColor.GOLD + " Gold");
        }

        im.setLore(lore);
        item.setItemMeta(im);

        return item;
    }

    public ArrayList<ConquestItem> getConquestItems() {
        return conquestItems;
    }

    public void setConquestItems(ItemList... conquestItems) {
        for (ItemList items : conquestItems) {
            this.conquestItems.add(items.ITEM);
        }
    }

    public String getTitle() {
        return title;
    }

    public Kit copy() {
        ArrayList<ConquestItem> newItems = new ArrayList<>();
        ArrayList<Ability> newAbilities = new ArrayList<>();

        for (ConquestItem item : conquestItems) {
            newItems.add(item.copy());
        }

        for (Ability ability : abilities) {
            newAbilities.add(ability.copy());
        }
        return new Kit(title, description, material, newItems, newAbilities);
    }
}