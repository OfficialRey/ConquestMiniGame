package net.conquest.entities.mobs;

import net.conquest.economy.PlayerGameStatistics;
import net.conquest.entities.PlayerStatistic;
import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.PassiveAbility;
import net.conquest.menu.item.ItemType;
import net.conquest.menu.item.game.ConquestItem;
import net.conquest.other.ConquestTeam;
import net.conquest.kits.Kit;
import net.conquest.menu.item.game.ItemList;
import net.conquest.other.DeathMessage;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import net.conquest.serialization.Serialization;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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

    private final PlayerGameStatistics playerGameStatistics;
    private final PlayerStatistic playerStatistic;
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
        playerGameStatistics = new PlayerGameStatistics();
        playerStatistic = Serialization.loadPlayerStatistic(player);
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
    public void onDeath(ConquestEntity killer) {
        playerGameStatistics.addDeath();
        DeathMessage.createDeathMessage(this, killer);

        if (killer instanceof ConquestPlayer) {
            ((ConquestPlayer) killer).getPlayerGameStatistics().addPlayerKill();
        }

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

    public void enterSpectatorMode() {
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
        entity.teleport(conquestTeam.getBase().getLocation());
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

    public void endGame(boolean wonGame) {
        isAlive = false;
        playerGameStatistics.setPlayTime(Conquest.getGame().getGameStatus().getGameTime());
        playerStatistic.addStatistics(playerGameStatistics, wonGame);
        enterSpectatorMode();
        if (wonGame) {
            Util.win(getOwner());
        } else {
            Util.lose(getOwner());
        }
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
    @Override
    public void damageTrue(int damage, ConquestEntity damager, DamageCause cause) {
        super.damageTrue(damage, damager, cause);
        onAttacked(damager, cause);
        playerGameStatistics.addDamageReceived(damage);
    }

    public PlayerGameStatistics getPlayerGameStatistics() {
        return playerGameStatistics;
    }


    public void onAttacked(ConquestEntity attacker, DamageCause cause) {
        kit.getAbilities().forEach(ability -> {
                    if (ability instanceof PassiveAbility) {
                        ((PassiveAbility) ability).triggerDamaged(this, attacker, cause);
                    }
                }
        );
    }

    public void onAttack(ConquestEntity toAttack) {
        kit.getAbilities().forEach(ability -> {
                    if (ability instanceof PassiveAbility) {
                        ((PassiveAbility) ability).triggerAttack(this, toAttack);
                    }
                }
        );
    }

    public void onGameEnd(boolean wonGame) {
        playerStatistic.addStatistics(playerGameStatistics, wonGame);
    }
}