package net.conquest.other;

import net.conquest.buildings.captureable.Captureable;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.plugin.Conquest;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class ConquestTeam {
    private final Color color;
    private final BarColor barColor;
    private final ChatColor chatColor;
    private final String name;
    private final int id;

    private HashMap<UUID, ConquestEntity> entities;
    private ArrayList<Captureable> zones;

    public ConquestTeam(BarColor barColor, ChatColor chatColor, Color color, String name, int id) {
        this.color = color;
        this.barColor = barColor;
        this.chatColor = chatColor;
        this.name = name;
        this.id = id;
        entities = new HashMap<>();
        zones = new ArrayList<>();
    }

    public void addEntity(ConquestEntity entity) {
        if (!entities.containsKey(entity.getBukkitEntity().getUniqueId()))
            entities.put(entity.getBukkitEntity().getUniqueId(), entity);
        if (entity instanceof ConquestPlayer) {
            Player p = ((ConquestPlayer) entity).getOwner();
            p.setPlayerListName(chatColor + p.getName());
            p.setDisplayName(chatColor + p.getName());
            p.sendMessage(Util.PREFIX + "You have joined Team " + chatColor + name + ChatColor.WHITE + ".");
        }
    }

    public void removeEntity(ConquestEntity entity) {
        entities.remove(entity);
        if (entity instanceof ConquestPlayer) {
            Player p = ((ConquestPlayer) entity).getOwner();
            p.setPlayerListName(p.getName());
            p.setDisplayName(p.getName());
            sendRemoveMessage(p);
        }
    }

    private void sendRemoveMessage(Player p) {
        p.sendMessage(Util.PREFIX + "You have left Team " + chatColor + name + ChatColor.WHITE + ".");
    }

    public ArrayList<ConquestPlayer> getPlayers() {
        ArrayList<ConquestPlayer> players = new ArrayList<>();
        for (ConquestEntity entity : entities.values()) {
            if (entity instanceof ConquestPlayer) {
                players.add((ConquestPlayer) entity);
            }
        }
        return players;
    }

    public Collection<ConquestEntity> getEntities() {
        return entities.values();
    }

    public ConquestEntity getEntity(UUID entityID) {
        return this.entities.get(entityID);
    }

    public int getSize() {
        return entities.size();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public BarColor getBarColor() {
        return barColor;
    }

    public static ConquestTeam fromId(int id) {
        return Conquest.getGame().getTeams().get(id);
    }

    public ArrayList<Captureable> getZones() {
        return zones;
    }


    public void run() {
        ArrayList<Captureable> newZones = new ArrayList<>();
        ArrayList<Captureable> update = new ArrayList<>();

        for (Captureable captureable : zones) {
            captureable.run();
            if (captureable.getCurrentTeam() == this) {
                newZones.add(captureable);
            } else {
                update.add(captureable);
            }

            cleanUp();
        }

        zones = newZones;
        //Update compass values
        Conquest.getGame().getAllPlayers().forEach(ConquestPlayer::updateCompass);

        for (Captureable zone : update) {
            zone.getCurrentTeam().addZone(zone);
        }

        for (ConquestEntity entity : entities.values()) {
            entity.regenerateHealth();
            entity.run();
        }
    }

    public Captureable getNextZone() {
        for (Captureable captureable : zones) {
            if (captureable.canBeCaptured()) {
                return captureable;
            }
        }
        return null;
    }

    public Captureable getZone(int id) {
        for (Captureable zone : zones) {
            if (zone.getPriority() == id) {
                return zone;
            }
        }
        return null;
    }

    public void addZone(Captureable zone) {
        zones.add(zone);
    }

    public void delete() {
        getEntities().forEach(ConquestEntity::killEntity);
        getZones().forEach(Captureable::remove);
    }

    public void lose() {
        getPlayers().forEach(ConquestPlayer::lose);
    }

    //Remove dead or unused entities to save memory
    public void cleanUp() {
        this.entities.entrySet().removeIf(entry -> entry.getValue().getBukkitEntity() == null || entry.getValue().getBukkitEntity().isDead());
    }

    public boolean isEliminated() {
        return zones.size() < 1;
    }
}