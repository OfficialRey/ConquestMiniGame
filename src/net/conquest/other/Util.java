package net.conquest.other;

import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.menu.item.menu.storage.ItemList;
import net.conquest.plugin.Conquest;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class Util {

    public static final int GAME_SPEED = 1;

    public static final int TICKS_PER_SECOND = 20, DEFAULT_HEALTH = 20, DEFAULT_HUNGER = 20, NULL = 0, MIN_PLAYERS = 1, MAX_PLAYERS = 20, START_COUNTDOWN = 3, END_COUNTDOWN = 20, IDLE_TIME = 15, KNIGHT_HORSE_CHANCE = 10, //10% Chance for a knight to ride a (undead) horse
            DEAD_HORSE_CHANCE = 40, //40% Chance to ride an undead horse
            FOG_DAMAGE = 2, DEFAULT_RESPAWN_TIME = 5, RESPAWN_TIME_CAP = 45, MIN_BLOOD = 10, MAX_BLOOD = 30, MAX_TIME_STUCK = 15, SPAWN_PROTECTION = 3, BATTLE_TIME = 7;

    public static final String PREFIX = ChatColor.GOLD + "♕" + ChatColor.GRAY + " Conquest " + ChatColor.GOLD + "♕ " + ChatColor.WHITE, NO_PERMISSION = ChatColor.RED + "You do not have the permissions to execute that command.", SPECTATOR_CHAT_PREFIX = ChatColor.GRAY + "<" + ChatColor.WHITE + "Spectator" + ChatColor.GRAY + "> " + ChatColor.WHITE, CHAT_MESSAGE_PREFIX = ChatColor.GRAY + ">>" + ChatColor.WHITE;
    public static final float CAPTURE_RADIUS = 12.5f, RANGE = 15f, FOG_RANGE = 2f, BLOOD_RANGE = 0.4f, REGAIN_HEALTH_VALUE = 7f, CIRCLE_HEIGHT = 0.05f, CIRCLE_RADIUS = 1, CIRCLES = 3, SPAWN_FACTOR = 1.5f, //Mob maximum limit. 1 is 100%
            CAPTURE_FACTOR = 2.5f, EXPLOSION_NORMALIZATION = 3f, MAX_EXPLOSION_VELOCITY = 2.2f;

    public static void resetPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setExp(Float.MIN_VALUE);
        player.setLevel(Util.NULL);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setMaxHealth(DEFAULT_HEALTH);
        player.setHealth(DEFAULT_HEALTH);
        player.setFoodLevel(DEFAULT_HUNGER);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public static void addLobbyItems(Player player) {
        resetPlayer(player);
        player.getInventory().addItem(ItemList.KIT_SELECTOR.MENU_ITEM.getMenuItem());
        player.getInventory().addItem(ItemList.PLAYER_STATS.MENU_ITEM.getMenuItem());
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public static void playSoundAtAll(Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, 1, 1);
        }
    }

    public static void playSoundAtAll(Sound sound, Location location) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(location, sound, 1, 1);
        }
    }

    public static void playPitchedSoundAtAll(Sound sound, Location location, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(location, sound, 1, pitch);
        }
    }

    public static void setPlayerLevel(int level) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setLevel(level);
        }
    }

    public static void setPlayerExp(float exp) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setExp(exp);
        }
    }

    public static boolean compareItemStacks(ItemStack base, ItemStack toCompare) {
        if (base != null && toCompare != null) {
            if (base.getType() != Material.AIR && toCompare.getType() != Material.AIR) {
                if (base.getType() == toCompare.getType()) {
                    if (base.getItemMeta() != null && toCompare.getItemMeta() != null) {
                        return base.getItemMeta().getDisplayName().equals(toCompare.getItemMeta().getDisplayName());
                    }
                }
            }
        }
        return false;
    }

    public static void playColoredParticleAtAll(Location location, Color color, int amount) {
        for (int i = 0; i < amount; i++) {
            //RGB
            //Amount = 0 -> Enables Coloring
            //Offset -> RGB
            //Brightness -> 1 (last param)
            location.getWorld().spawnParticle(Particle.SPELL_MOB, location.getX(), location.getY(), location.getZ(), Util.NULL, Float.MIN_VALUE + color.getRed(), Float.MIN_VALUE + color.getGreen(), Float.MIN_VALUE + color.getBlue(), 1);
        }
    }

    public static void playParticleAtAll(Location location, Particle particle, int amount) {
        location.getWorld().spawnParticle(particle, location, amount);
    }

    public static void playParticleAtAll(Location location, Particle particle, int amount, float radius) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < amount; i++) {
                player.spawnParticle(particle, location.clone().add(randomRange(radius), randomRange(radius), randomRange(radius)), 1);
            }
        }
    }

    public static void playParticleAtAll(Location location, Particle particle, int amount, float radius, float velocity) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < amount; i++) {
                player.spawnParticle(particle, location.getX() + randomRange(radius), location.getY() + randomRange(radius), location.getZ() + randomRange(radius), 1, 0, 0, 0, velocity);
            }
        }
    }

    public static void playSpedParticleAtAll(Location location, Particle particle, int amount, float radius, float speed) {
        for (int i = 0; i < amount; i++) {
            location.getWorld().spawnParticle(particle, location.getX() + randomRange(radius), location.getY() + randomRange(radius), location.getZ() + randomRange(radius), 1, 0, 0, 0, speed);
        }
    }


    public static void playBloodParticle(Entity entity) {
        Location loc = entity.getLocation().clone();
        loc.setY(loc.getY() + entity.getHeight() / 1.5f);
        loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 1 + MIN_BLOOD + new Random().nextInt(MAX_BLOOD - MIN_BLOOD), BLOOD_RANGE, BLOOD_RANGE, BLOOD_RANGE, Material.REDSTONE_BLOCK.createBlockData());
    }

    public static void playStepParticle(Location location, Material type) {
        location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 10, BLOOD_RANGE, BLOOD_RANGE, BLOOD_RANGE, type.createBlockData());
    }

    private static float randomRange(float radius) {
        return new Random().nextFloat() * radius - new Random().nextFloat() * radius;
    }

    public static void addBuildItems(Player player) {
        resetPlayer(player);
    }

    public static void main(String[] args) {
        float min = 0;
        float max = 0;
        for (int i = 0; i < 1_000_000; i++) {
            float value = randomRange(2000);
            if (value < min) min = value;
            if (value > max) max = value;
        }
        System.out.println(min);
        System.out.println(max);
    }

    public enum Colors {
        BROWN(Color.fromRGB(139, 69, 19));

        public final Color COLOR;

        Colors(Color color) {
            COLOR = color;
        }
    }

    public static void spectate(Player player) {
        Util.resetPlayer(player);
        hidePlayer(player);
        player.setAllowFlight(true);
    }

    public static void noSpectate(Player player) {
        Util.resetPlayer(player);
        showPlayer(player);
        player.setAllowFlight(false);
    }

    public static void showPlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(all -> all.showPlayer(Conquest.getPlugin(), player));
    }

    public static void hidePlayer(Player player) {
        Bukkit.getOnlinePlayers().forEach(all -> all.hidePlayer(Conquest.getPlugin(), player));
    }

    public static void lose(Player player) {
        sendTitle(player, ChatColor.RED + "ELIMINATED", ChatColor.GRAY + "You have been eliminated from the game.", 0.3f, 5f, 0.3f);
    }

    public static void win(Player player) {
        sendTitle(player, ChatColor.GOLD + "WON", ChatColor.GRAY + "You have won the game.", 0.3f, 5f, 0.3f);
    }

    public static void sendTitle(Player player, String title, String subtitle, float fadeIn, float stay, float fadeOut) {
        player.sendTitle(title, subtitle, (int) (Util.TICKS_PER_SECOND * fadeIn), (int) (Util.TICKS_PER_SECOND * stay), (int) (Util.TICKS_PER_SECOND * fadeOut));
    }

    public static void sendTitleToAll(String title, String subtitle, float fadeIn, float stay, float fadeOut) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitle(player, title, subtitle, fadeIn, stay, fadeOut));
    }

    public static void createSpawnAnimation(Entity entity) {
        if (entity instanceof Player) {
            animatePlayer((Player) entity);
        }
    }

    private static void animatePlayer(Player player) {
        Util.playPitchedSoundAtAll(Sound.ENTITY_WITHER_SPAWN, player.getLocation(), 1.5f);
        for (int theta = 0; theta < 360; theta++) {

            double finalTheta = theta;

            new BukkitRunnable() {

                double x;
                final double y = player.getHeight() / 360 * finalTheta;
                double z;

                @Override
                public void run() {
                    Location loc = player.getLocation();

                    x = Math.cos(finalTheta) * Util.CIRCLE_RADIUS;
                    z = Math.sin(finalTheta) * Util.CIRCLE_RADIUS;

                    loc.add(x, y, z);
                    //loc.getWorld().spawnParticle(Particle.ASH, loc, 1);
                    loc.getWorld().spawnParticle(Particle.SPELL, loc.getX(), loc.getY(), loc.getZ(), 1, 0, 0, 0, 0);
                    loc.subtract(x, y, z);
                }
            }.runTaskLater(Conquest.getPlugin(), (long) finalTheta);
        }
    }

    public static ArmorStand createHelperArmorStand(Location location, boolean onGround) {
        return createHelperArmorStand(location, onGround, null);
    }

    public static ArmorStand createHelperArmorStand(Location location, boolean onGround, Vector offset) {
        if (onGround) {
            getGroundBlock(location);
        }
        if (offset != null) {
            location.add(offset);
        }
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setAI(false);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setBasePlate(false);
        armorStand.setSilent(true);
        armorStand.setArms(false);
        armorStand.setMarker(true);
        return armorStand;
    }

    private static Location getGroundBlock(Location location) {
        location.setY((int) (location.getY() + 0.5));
        while (location.clone().add(0, -1, 0).getBlock().getType() == Material.AIR && location.getY() > 0) {
            location.add(0, -1, 0);
        }
        return location;
    }

    public static void createExplosion(Location location, float radius, ConquestEntity damager, int damage) {
        Util.playParticleAtAll(location, Particle.EXPLOSION_LARGE, (int) (15 * radius), radius);
        Util.playSoundAtAll(Sound.ENTITY_GENERIC_EXPLODE, location);
        for (Entity entity : location.getWorld().getNearbyEntities(location, radius, radius, radius)) {
            double distance = location.distance(entity.getLocation());
            if (distance < radius) {
                ConquestEntity conquestEntity = Conquest.getGame().getConquestEntity(entity.getUniqueId());
                if (conquestEntity != null && damager.getTeam() != conquestEntity.getTeam()) {
                    Vector locToEntity = conquestEntity.getBukkitEntity().getLocation().subtract(location).toVector().normalize();
                    locToEntity.multiply(radius / distance / EXPLOSION_NORMALIZATION);

                    if (locToEntity.length() > MAX_EXPLOSION_VELOCITY) {
                        locToEntity.normalize().multiply(MAX_EXPLOSION_VELOCITY);
                    }

                    conquestEntity.getBukkitEntity().setVelocity(locToEntity);
                    conquestEntity.damage((int) (damage / (distance / 4f)), damager, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
                }
            }
        }
    }
}