package net.conquest.entities.abilities.storage.assassin;

import net.conquest.entities.abilities.PassiveAbility;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.entities.abilities.Ability;
import net.conquest.entities.abilities.AbilityList;
import net.conquest.other.Util;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

public class WallRunAbility extends PassiveAbility {
    private static final float
            WALL_RUNNING_FACTOR = 0.07f,
            WALL_RUNNING_ACCELERATION = 1.2f,
            MAX_SPEED = 0.4f,
            MAX_HEIGHT_GRIP = 4;

    private int animationCycle;

    public WallRunAbility() {
        super(AbilityList.WALL_RUN);
    }

    private void wallRun(ConquestPlayer player) {
        if (!player.isOnGround()) {
            if (getHeight(player) < MAX_HEIGHT_GRIP) {
                Location wall = hasNearbyBlock(player);
                horizontalWallRunning(player, wall);
            }
        }
    }

    private void horizontalWallRunning(ConquestPlayer player, Location wall) {
        if (wall != null) {
            animationCycle++;
            if (animationCycle % 5 == 0) {
                Util.playSoundAtAll(Sound.BLOCK_STONE_STEP, player.getOwner().getLocation());
                Util.playStepParticle(player.getOwner().getLocation(), wall.getBlock().getType());
            }
            Vector playerVelocity = player.getOwner().getVelocity();

            playerVelocity.add(new Vector(0, WALL_RUNNING_FACTOR / getHeight(player), 0));

            playerVelocity.setZ(playerVelocity.getZ() * WALL_RUNNING_ACCELERATION);
            playerVelocity.setX(playerVelocity.getX() * WALL_RUNNING_ACCELERATION);

            //Max Speed
            if (playerVelocity.length() > MAX_SPEED) {
                playerVelocity.normalize().multiply(MAX_SPEED);
            }
            player.getOwner().setVelocity(playerVelocity);
        }
    }

    private double getHeight(ConquestPlayer player) {
        return 1D + player.getOwner().getFallDistance();
    }

    private Location hasNearbyBlock(ConquestPlayer player) {
        Location loc0 = player.getOwner().getLocation().add(1, 0, 0);
        Location loc1 = player.getOwner().getLocation().add(-1, 0, 0);
        Location loc2 = player.getOwner().getLocation().add(0, 0, 1);
        Location loc3 = player.getOwner().getLocation().add(0, 0, -1);
        if (isSolid(loc0)) {
            return loc0;
        } else if (isSolid(loc1)) {
            return loc1;
        } else if (isSolid(loc2)) {
            return loc2;
        } else if (isSolid(loc3)) {
            return loc3;
        }
        return null;
    }

    private boolean isSolid(Location location) {
        return location.getBlock().getType().isSolid();
    }

    @Override
    public void onMove(ConquestPlayer player) {
        wallRun(player);
    }

    @Override
    public void onDeath(ConquestPlayer player) {

    }

    @Override
    public void onSpawn(ConquestPlayer player) {

    }

    @Override
    public void onLaunchArrow(ConquestPlayer player, Arrow arrow) {

    }

    @Override
    public void onAttack(ConquestPlayer player) {

    }

    @Override
    public void onDamaged(ConquestPlayer player, DamageCause cause) {

    }

    @Override
    public void onRegenerateHealth(ConquestPlayer player) {

    }

    @Override
    public void onJump(ConquestPlayer player) {

    }

    @Override
    public Ability copy() {
        return new WallRunAbility();
    }
}