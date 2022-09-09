package net.conquest.entities.abilities;

import net.conquest.menu.item.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class Ability extends BaseItem {

    private final int cooldown;
    protected final int damage;
    protected final int executions;
    private int currentCooldown;

    public Ability(AbilityList abilityList) {
        super(ChatColor.GOLD + abilityList.TITLE, abilityList.DESCRIPTION, abilityList.MATERIAL);
        cooldown = abilityList.COOLDOWN;
        damage = abilityList.DAMAGE;
        executions = 1 + abilityList.REPETITIONS;
        currentCooldown = 0;
    }

    @Override
    public ItemStack getMenuItem() {
        ItemStack item = super.getMenuItem();
        ItemMeta im = item.getItemMeta();

        assert im != null;

        List<String> lore = im.getLore();

        assert lore != null;

        lore.add("");

        if (cooldown > 0) {
            lore.add(ChatColor.WHITE + "Cooldown: " + ChatColor.GREEN + cooldown);
            lore.add("");
        }

        if (this instanceof ActiveAbility) {
            lore.add(ChatColor.BLUE + "Active Ability");
        } else {
            lore.add(ChatColor.BLUE + "Passive Ability");
        }


        if (currentCooldown > 0) {
            lore.add("");
            lore.add(ChatColor.WHITE + "This ability is on " + ChatColor.RED + "Cooldown" + ChatColor.WHITE + ":");
            lore.add(ChatColor.WHITE + "Time left: " + ChatColor.GRAY + currentCooldown + ChatColor.WHITE + " seconds.");
            item.setType(Material.GRAY_DYE);
            item.setAmount(currentCooldown);
        }
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }


    protected void startCooldown() {
        currentCooldown = cooldown;
    }

    public void cooldown() {
        if (isOnCooldown()) {
            currentCooldown--;
        }
    }

    public boolean isOnCooldown() {
        return currentCooldown > 0;
    }

    public abstract Ability copy();
}