package net.conquest.entities.mobs;

import net.conquest.entities.abilities.Ability;
import net.conquest.menu.item.ItemType;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.other.ConquestTeam;
import net.conquest.kits.Kit;
import net.conquest.menu.item.game.ItemList;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.logging.Level;

public class ConquestPlayer extends ConquestEntity {

    // Level System
    protected int level;
    protected int experience;
    protected int experienceNeeded;
    private Kit kit;

    private boolean isSpectating;
    private boolean isAlive;
    private int respawnTime;

    //TODO: Choose random kit if kit == null
    public ConquestPlayer(@Nonnull Player player, @Nullable Kit kit, ConquestTeam conquestTeam) {
        super(player, MobData.PLAYER, conquestTeam);
        this.kit = kit != null ? kit.copy() : null;
        isSpectating = true;
        isVulnerable = false;
        isDamageable = false;
    }

    public Player getOwner() {
        return (Player) entity;
    }

    public void setKit(Kit kit) {
        getOwner().sendMessage(Util.PREFIX + "You selected the kit " + kit.getTitle() + "");
        this.kit = kit.copy();
        setItems(kit.getConquestItems());
        ((Player) entity).getInventory().setItem(8, ItemList.getCompass(conquestTeam).getItemStack());
    }

    public void updateCompass() {
        for (ConquestTeam allTeams : Conquest.getGame().getTeams()) {
            if (conquestTeam != allTeams) {
                if (allTeams.getNextZone() != null) {
                    Location loc = allTeams.getNextZone().getLocation();
                    if (loc != null)
                        ((Player) entity).setCompassTarget(loc);
                }
            }
        }
    }

    public boolean isSpectating() {
        return isSpectating;
    }

    public Kit getKit() {
        return kit;
    }

    public ArrayList<Ability> getAbilities() {
        if (kit != null) {
            return kit.getAbilities();
        }
        return new ArrayList<>();
    }

    @Override
    public void run() {
        if (!conquestTeam.isEliminated()) {
            runRespawnMechanic();
        }

        updateAbilities();
        //TODO: Stamina - Regeneration - Ability Cooldowns
    }

    private void runRespawnMechanic() {
        if (respawnTime >= 0) {
            getOwner().setLevel(respawnTime);
            switch (respawnTime) {
                case 60, 45, 30, 20, 15, 10, 5, 4, 3, 2, 1 -> sendRespawnInformation();
                case 0 -> respawn();
            }
            respawnTime--;
        }
    }

    private void sendRespawnInformation() {
        getOwner().sendMessage(Util.PREFIX + "You will be respawning in " + ChatColor.RED + respawnTime + " seconds.");
        Util.playSound(getOwner(), Sound.BLOCK_DISPENSER_DISPENSE);
        getOwner().setLevel(respawnTime);
    }

    @Override
    public void onDeath() {
        respawnTime = Conquest.getGame().getGameStatus().getRespawnTime();
        isDamageable = false;
        Util.playBloodParticle(entity);
        Util.playParticleAtAll(entity.getLocation(), Particle.CLOUD, 100);
        Util.playSoundAtAll(Sound.ENTITY_PLAYER_DEATH, entity.getLocation());
        enterSpectatorMode();
    }

    @Override
    public void onSummon(ConquestTeam conquestTeam) {

    }

    private void enterSpectatorMode() {
        isSpectating = true;
        isAlive = false;
        setVulnerable(false);
        health = getMaxHealth();
        Util.spectate(getOwner());
    }

    public void respawn() {
        isSpectating = false;
        isAlive = true;
        isDamageable = true;
        Util.noSpectate(getOwner());
        health = getMaxHealth();
        updateHealth();
        equipItems();
        equipAbilities();
        entity.teleport(conquestTeam.getZone(0).getLocation());
        Util.createSpawnAnimation(entity);
        setVulnerable(false);

        new BukkitRunnable() {
            @Override
            public void run() {
                setVulnerable(true);
            }
        }.runTaskLater(Conquest.getPlugin(), Util.TICKS_PER_SECOND * Util.SPAWN_PROTECTION);
    }

    private void equipAbilities() {
        for (int i = 0; i < getAbilities().size(); i++) {
            getOwner().getInventory().setItem(8 - i, getAbilities().get(i).getItemStack());
        }
    }

    private void updateAbilities() {
        for (int i = 0; i < getAbilities().size(); i++) {
            if (getAbilities().get(i).isOnCooldown()) {
                getAbilities().get(i).cooldown();
                if (!isSpectating) {
                    getOwner().getInventory().setItem(8 - i, getAbilities().get(i).getItemStack());
                }
            }
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void lose() {
        isAlive = false;
        enterSpectatorMode();
        Util.lose(getOwner());
    }

    public boolean isOnGround() {
        return getOwner().isOnGround();
    }

    @Override
    public String getName() {
        return getOwner().getDisplayName();
    }

    public boolean removeAmmo() {
        for (ConquestItem item : items) {
            if (item.getType() == ItemType.AMMO) {
                if (item.reduceAmount()) {
                    equipItems();
                    return true;
                }
            }
        }
        return false;
    }

    public void addAmmo() {
        for (ConquestItem item : items) {
            if (item.getType() == ItemType.AMMO) {
                item.addAmount();
                equipItems();
            }
        }
    }
}