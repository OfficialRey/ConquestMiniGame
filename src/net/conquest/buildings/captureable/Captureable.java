package net.conquest.buildings.captureable;

import net.conquest.buildings.structures.Barracks;
import net.conquest.buildings.structures.Structure;
import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.entities.mobs.MobData;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public abstract class Captureable {

    private final Zone zone;
    protected final Location location;
    private int attackTime = 0;
    private int oldAttackTime;
    protected ConquestTeam currentConquestTeam;
    private final BossBar bar;
    private final ArrayList<Structure> structures;
    private final LivingEntity marker;

    public Captureable(Zone zone, Location location, ConquestTeam defaultConquestTeam, MobData data) {
        this.zone = zone;
        this.location = location;
        this.currentConquestTeam = defaultConquestTeam;
        structures = new ArrayList<>();
        bar = Bukkit.createBossBar(currentConquestTeam.getChatColor() + zone.NAME, currentConquestTeam.getBarColor(), BarStyle.SEGMENTED_10);
        bar.setVisible(true);
        marker = Util.createHelperArmorStand(location);
        addStructure(new Barracks(location, data));
    }

    public static Captureable fromName(String cfg, Location location, ConquestTeam defaultConquestTeam) {

        if (location != null) {
            if (cfg.equals(Zone.THRONE_ROOM.CFG)) {
                return new ThroneRoom(location, defaultConquestTeam);
            } else if (cfg.equals(Zone.YARD.CFG)) {
                return new Courtyard(location, defaultConquestTeam);
            } else if (cfg.equals(Zone.VILLAGE.CFG)) {
                return new Village(location, defaultConquestTeam);
            } else if (cfg.equals(Zone.FARMLAND.CFG)) {
                return new Farmland(location, defaultConquestTeam);
            } else if (cfg.equals(Zone.FOREST.CFG)) {
                return new Forest(location, defaultConquestTeam);
            }
        }
        return null;
    }

    public void run() {
        runCapture();
    }

    private void runCapture() {
        CaptureAttack attack = getAttackingTeam();
        oldAttackTime = attackTime;
        if (attack.ConquestTEAM == currentConquestTeam || !canBeCaptured()) {
            if (attackTime > 0) {
                attackTime--;
            }
        } else {
            attackTime += 1 + (attack.ATTACKERS - getDefenders()) / Util.CAPTURE_FACTOR;
            if (attackTime >= zone.CAPTURE_TIME) {
                capture(attack.ConquestTEAM);
            }
        }
        updateBossBar();
    }

    private int getUnits(ConquestTeam conquestTeam) {
        int units = 0;
        for (ConquestEntity entity : conquestTeam.getEntities()) {
            if (entity.getBukkitEntity().getLocation().distance(location) < Util.CAPTURE_RADIUS) {
                units++;
            }
        }
        return units;
    }

    private int getDefenders() {
        return getUnits(currentConquestTeam);
    }

    private CaptureAttack getAttackingTeam() {
        ArrayList<ConquestTeam> conquestTeams = Conquest.getGame().getTeams();
        int[] attackers = new int[conquestTeams.size()];
        conquestTeams.forEach(team -> attackers[team.getId()] = getUnits(team));

        ConquestTeam attacker = currentConquestTeam;
        for (int i = Util.NULL; i < attackers.length; i++) {
            if (attackers[i] > attackers[attacker.getId()]) {
                attacker = conquestTeams.get(i);
            }
        }

        return new CaptureAttack(attacker, attackers[attacker.getId()]);
    }

    private void updateBossBar() {
        bar.setProgress(1 - (double) attackTime / (double) zone.CAPTURE_TIME);
        for (ConquestPlayer player : Conquest.getGame().getAllPlayers()) {
            if (player.getOwner().getLocation().distance(location) < Util.CAPTURE_RADIUS) {
                if (!bar.getPlayers().contains(((Player) player.getBukkitEntity()))) {
                    bar.addPlayer((Player) player.getBukkitEntity());
                }
            } else {
                bar.removePlayer((Player) player.getBukkitEntity());
            }
        }
        if (oldAttackTime > attackTime) {
            flashBossBar(BarColor.WHITE);
        } else if (oldAttackTime < attackTime) {
            flashBossBar(getAttackingTeam().ConquestTEAM.getBarColor());
        }
    }

    private void flashBossBar(BarColor flashColor) {
        for (int i = 0; i < 4; i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (bar.getColor() == currentConquestTeam.getBarColor()) {
                        bar.setColor(flashColor);
                    } else {
                        bar.setColor(currentConquestTeam.getBarColor());
                    }
                }
            }.runTaskLater(Conquest.getPlugin(), i);
        }
    }

    private void capture(ConquestTeam conquestTeam) {
        currentConquestTeam = conquestTeam;
        attackTime = 0;
        bar.setTitle(currentConquestTeam.getChatColor() + zone.NAME);
        bar.setColor(currentConquestTeam.getBarColor());
        for (ConquestEntity entity : Conquest.getGame().getAllEntities()) {
            if (entity instanceof ConquestPlayer) {
                sendCaptureMessage((ConquestPlayer) entity);
            }
        }
        initStructures();
    }

    private void initStructures() {
        for (Structure structure : structures) {
            structure.reset();
        }
    }

    private void sendCaptureMessage(ConquestPlayer player) {
        Util.playSoundAtAll(Sound.ENTITY_WITHER_DEATH);
        player.getBukkitEntity().sendMessage(Util.PREFIX + ChatColor.WHITE + "Team " + currentConquestTeam.getChatColor() + currentConquestTeam.getName() + ChatColor.WHITE + " has captured " + ChatColor.GOLD + zone.NAME);
        if (player.getTeam() == currentConquestTeam) {
            ((Player) player.getBukkitEntity()).sendTitle(currentConquestTeam.getChatColor() + "CAPTURED", ChatColor.GREEN + zone.NAME + ChatColor.WHITE + " has been captured", Util.TICKS_PER_SECOND, Util.TICKS_PER_SECOND * 3, Util.TICKS_PER_SECOND);
        } else {
            ((Player) player.getBukkitEntity()).sendTitle(currentConquestTeam.getChatColor() + "LOST", ChatColor.RED + zone.NAME + ChatColor.WHITE + " has been lost", Util.TICKS_PER_SECOND, Util.TICKS_PER_SECOND * 3, Util.TICKS_PER_SECOND);
        }
    }

    public String getTitle() {
        return zone.NAME;
    }

    public String getConfigName() {
        return zone.CFG;
    }

    public Location getLocation() {
        return location;
    }

    public int getCaptureTime() {
        return zone.CAPTURE_TIME;
    }

    public ConquestTeam getCurrentTeam() {
        return currentConquestTeam;
    }

    public int getPriority() {
        return zone.ID;
    }

    public boolean canBeCaptured() {
        for (Captureable captureable : currentConquestTeam.getZones()) {
            if (captureable.getPriority() > getPriority()) {
                return false;
            }
        }
        return true;
    }

    protected void addStructure(Structure structure) {
        structures.add(structure);
    }

    public boolean isThreatened() {
        return (float) attackTime / (float) zone.CAPTURE_TIME > 0.33f;
    }

    public LivingEntity getMarker() {
        return marker;
    }

    public void remove() {
        bar.getPlayers().forEach(bar::removePlayer);
        marker.remove();
    }
}