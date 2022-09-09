package net.conquest.entities.abilities;

import net.conquest.menu.item.BaseItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Ability extends BaseItem {

    private final int cooldown;
    protected final int damage;
    protected final int executions;
    private int currentCooldown;
    private ItemStack cooldownItem;

    public Ability(AbilityList abilityList) {
        super(ChatColor.GOLD + abilityList.TITLE, abilityList.DESCRIPTION, abilityList.MATERIAL);
        this.cooldown = abilityList.COOLDOWN;
        this.damage = abilityList.DAMAGE;
        this.executions = 1 + abilityList.REPETITIONS;
        this.currentCooldown = 0;
        this.modifiyMenuItem();
        this.cooldownItem = this.createCooldownItem();
    }

    @Override
    public ItemStack getItemStack() {
        if(this.isOnCooldown()) {
            this.updateCooldownItem();

            return this.cooldownItem;
        }

        return super.getItemStack();
    }

    private void modifiyMenuItem()  {
        assert !this.isOnCooldown();

        ItemStack baseItem = this.getItemStack();
        ItemMeta baseItemMeta = baseItem.getItemMeta();

        List<String> lore = baseItemMeta.getLore();

        if (this.cooldown > 0) {
            lore.add(ChatColor.WHITE + "Cooldown: " + ChatColor.GREEN + cooldown);
            lore.add("");
        }

        if (this instanceof ActiveAbility) {
            lore.add(ChatColor.BLUE + "Active Ability");
        } else {
            lore.add(ChatColor.BLUE + "Passive Ability");
        }

        baseItemMeta.setLore(lore);
        baseItem.setItemMeta(baseItemMeta);
    }

    private void updateCooldownItem() {
        if(!this.isOnCooldown()) return;

        ItemMeta cooldownItemMeta = Objects.requireNonNull(this.cooldownItem.getItemMeta());

        List<String> lore = cooldownItemMeta.getLore();
        lore.set(lore.size() - 1, ChatColor.WHITE + "Time left: " + ChatColor.GRAY + currentCooldown + ChatColor.WHITE + " seconds.");
        cooldownItemMeta.setLore(lore);

        this.cooldownItem.setItemMeta(cooldownItemMeta);
        this.cooldownItem.setAmount(this.currentCooldown);
    }

    private ItemStack createCooldownItem() {
        assert !this.isOnCooldown();

        ItemStack cooldownItem = new ItemStack(Material.GRAY_DYE);
        ItemMeta cooldownItemMeta = Objects.requireNonNull(cooldownItem.getItemMeta());

        List<String> lore = Objects.requireNonNull(this.getItemStack().getItemMeta().getLore());
        lore.add("");
        lore.add(ChatColor.WHITE + "This ability is on " + ChatColor.RED + "Cooldown" + ChatColor.WHITE + ":");
        lore.add(ChatColor.WHITE + "Time left: " + ChatColor.GRAY + currentCooldown + ChatColor.WHITE + " seconds.");
        cooldownItemMeta.setLore(lore);

        cooldownItemMeta.setDisplayName(this.getItemStack().getItemMeta().getDisplayName());

        cooldownItem.setItemMeta(cooldownItemMeta);

        return cooldownItem;
    }

    protected void startCooldown() {
        this.currentCooldown = this.cooldown;
    }

    public void cooldown() {
        if (this.isOnCooldown()) {
            this.currentCooldown--;
        }
    }

    public boolean isOnCooldown() {
        return this.currentCooldown > 0;
    }

    public abstract Ability copy();
}