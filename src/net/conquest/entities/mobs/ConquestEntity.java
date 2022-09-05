package net.conquest.entities.mobs;

import net.conquest.other.ConquestTeam;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.menu.item.game.ItemList;
import net.conquest.other.DeathMessage;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class ConquestEntity {

    protected Entity entity;

    //Stats
    protected float maxHealth;
    protected float health;
    protected int defense;
    protected int attack;
    protected float walkSpeed;
    protected boolean isVulnerable;
    protected boolean isDamageable;
    private final String name;
    protected ArrayList<ConquestItem> items = new ArrayList<>();

    //Team system
    protected final ConquestTeam conquestTeam;

    //Battle System (Regen)
    private boolean isInBattle;
    private int battleTimer = 0;

    public ConquestEntity(Entity entity, MobData data, ConquestTeam conquestTeam) {
        this.entity = entity;
        maxHealth = data.HEALTH;
        health = maxHealth;
        defense = data.DEFENSE;
        attack = data.ATTACK;
        walkSpeed = data.WALK_SPEED;
        this.conquestTeam = conquestTeam;
        name = data.NAME;
        isVulnerable = true;
        isDamageable = true;

        conquestTeam.addEntity(this);
        onSummon(conquestTeam);
    }

    public void damage(ConquestEntity attacker, DamageCause cause) {
        damage(attacker.getAttack(), attacker, cause);
    }

    public void damage(int damage, ConquestEntity damager, DamageCause cause) {
        damageTrue(damage, damager, cause);
    }

    public void damage(int damage) {
        damageTrue(calculateDamage(damage), null, DamageCause.CUSTOM);
    }

    private int calculateDamage(int damage) {
        return (int) (damage * (100D / (100D + (double) defense)));
    }

    public void damageTrue(int damage, ConquestEntity damager, DamageCause cause) {

        if (isDamageable && isVulnerable) {
            if (damager == null || conquestTeam != damager.getTeam()) {
                isDamageable = false;
                startBattle();
                Util.playBloodParticle(entity);
                health = health - damage;
                ((Damageable) entity).damage(Util.NULL);
                if ((int) health <= Util.NULL) {
                    health = Util.NULL;
                    if (this instanceof ConquestPlayer) {
                        DeathMessage.createDeathMessage((ConquestPlayer) this, damager);
                        onDeath();
                    }
                    killEntity();
                    return;
                }
                updateHealth();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        isDamageable = true;
                    }
                }.runTaskLater(Conquest.getPlugin(), 4);
            }
        }
    }

    public int getMaxHealth() {
        int stat = (int) maxHealth;
        for (ConquestItem item : items) {
            stat += item.getHealth();
        }
        return stat;
    }

    public int getAttack() {
        int stat = attack;
        for (ConquestItem item : items) {
            stat += item.getAttack();
        }
        return stat;
    }

    public int getDefense() {
        int stat = defense;
        for (ConquestItem item : items) {
            stat += item.getDefense();
        }
        return stat;
    }

    public int getDodgeChance() {
        int stat = 0;
        for (ConquestItem item : items) {
            stat += item.getDodgeChance();
        }
        return stat;
    }

    public Entity getBukkitEntity() {
        return entity;
    }

    public abstract void run();

    protected void resetItems() {
        for (ConquestItem item : items) {
            for (ItemList list : ItemList.values()) {
                if (list.ITEM != null) {
                    if (Util.compareItemStacks(item.getMenuItem(), list.ITEM.getMenuItem())) {
                        item = list.ITEM.copy();

                    }
                }
            }
        }
    }

    public void equipItems() {
        resetItems();
        if (entity instanceof LivingEntity) {
            items.forEach(this::equipItem);
        }

        setEntityWalkSpeed();
    }

    //equip the items
    private void equipItem(ConquestItem item) {

        //Player specific
        if (((LivingEntity) entity).getEquipment() != null) {
            if (entity instanceof Player) {
                switch (item.getType()) {
                    case WEAPON -> ((Player) entity).getInventory().setItem(0, item.getMenuItem());
                    case BOW -> ((Player) entity).getInventory().setItem(1, item.getMenuItem());
                    case AMMO -> ((Player) entity).getInventory().setItem(2, item.getMenuItem());
                }
                //Mob specific
            } else if (entity instanceof LivingEntity) {
                switch (item.getType()) {
                    case WEAPON, BOW -> ((Mob) entity).getEquipment().setItemInMainHand(item.getMenuItem());
                }
            }
            //Default Items
            switch (item.getType()) {
                case OFFHAND -> ((LivingEntity) entity).getEquipment().setItemInOffHand(item.getMenuItem());
                case HELMET -> ((LivingEntity) entity).getEquipment().setHelmet(item.getMenuItem());
                case CHESTPLATE -> ((LivingEntity) entity).getEquipment().setChestplate(item.getMenuItem());
                case LEGGINGS -> ((LivingEntity) entity).getEquipment().setLeggings(item.getMenuItem());
                case BOOTS -> ((LivingEntity) entity).getEquipment().setBoots(item.getMenuItem());
            }
        }
    }

    public void setItems(ArrayList<ConquestItem> items) {
        this.items = items;
    }

    public void setItems(ConquestItem[] items) {
        this.items = new ArrayList<>();
        this.items.addAll(List.of(items));
    }

    private double getEntityMaxHealth() {
        return Objects.requireNonNull(((Attributable) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();
    }

    public void setEntityWalkSpeed() {
        Objects.requireNonNull(((Attributable) entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(walkSpeed);
    }

    public void setEntityWalkSpeed(float value) {
        Objects.requireNonNull(((Attributable) entity).getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(value);
    }

    public ConquestTeam getTeam() {
        return conquestTeam;
    }

    public void killEntity() {
        if (!(this instanceof ConquestPlayer)) {
            ((Damageable) entity).setHealth(Util.NULL);
        }
        onDeath();
    }

    public void updateHealth() {
        ((Damageable) entity).setHealth(health / getMaxHealth() * getEntityMaxHealth());
    }

    public abstract void onDeath();

    public abstract void onSummon(ConquestTeam conquestTeam);

    protected static Entity createEntity(Location location, EntityType type) {
        Entity entity = Objects.requireNonNull(location.getWorld()).spawnEntity(location, type);
        if (entity instanceof Ageable) ((Ageable) entity).setAdult();

        return entity;
    }

    public double distance(ConquestEntity other) {
        return entity.getLocation().distance(other.getBukkitEntity().getLocation());
    }

    public double distance(Entity other) {
        return entity.getLocation().distance(other.getLocation());
    }

    public double distance(Location location) {
        return entity.getLocation().distance(location);
    }

    public void setHealth(float value) { //0-1
        if (value < 0) value = 0;
        if (value > 1) value = 1;
        health = value * getMaxHealth();
        updateHealth();
    }

    public void regainHealth(float amount) {
        if (health > 0) {
            if (health < getMaxHealth()) {
                health += amount;
                Util.playSpedParticleAtAll(entity.getLocation().add(0, 0.5, 0), Particle.HEART, 8, 1, 0);
                if (health > getMaxHealth()) {
                    health = getMaxHealth();
                }
                updateHealth();
            }
        }
    }

    public void regenerateHealth() {
        if (!isInBattle) {
            regainHealth(getMaxHealth() / 100f * Util.REGAIN_HEALTH_VALUE);
        } else {
            battleTimer--;
            if (battleTimer <= 0) {
                isInBattle = false;
            }
        }
    }

    private void startBattle() {
        isInBattle = true;
        battleTimer = Util.BATTLE_TIME;
    }

    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
    }

    public boolean isVulnerable() {
        return isVulnerable;
    }

    public String getName() {
        return name;
    }
}