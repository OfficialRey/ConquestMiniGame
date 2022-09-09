package net.conquest.other;

import net.conquest.buildings.captureable.Captureable;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestMob;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.plugin.Conquest;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ConquestTeam {
    private final Color color;
    private final BarColor barColor;
    private final ChatColor chatColor;
    private final String name;
    private final int id;

    private ArrayList<ConquestEntity> entities;
    private ArrayList<Captureable> zones;

    public ConquestTeam(BarColor barColor, ChatColor chatColor, Color color, String name, int id) {
        this.color = color;
        this.barColor = barColor;
        this.chatColor = chatColor;
        this.name = name;
        this.id = id;
        entities = new ArrayList<>();
        zones = new ArrayList<>();
    }

    public void addEntity(ConquestEntity entity) {
        if (!entities.contains(entity))
            entities.add(entity);
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
        for (ConquestEntity entity : entities) {
            if (entity instanceof ConquestPlayer) {
                players.add((ConquestPlayer) entity);
            }
        }
        return players;
    }

    public ArrayList<ConquestEntity> getEntities() {
        return entities;
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

        for (ConquestEntity entity : entities) {
            entity.regenerateHealth();
            entity.run();
        }
    }

    public Captureable getBase() {
        return zones.get(Util.NULL);
    }

    public Captureable getNextZone() {
        if (zones.size() > 0) {
            return zones.get(zones.size() - 1);
        }
        return null;
    }

    public void addZone(Captureable zone) {
        zones.add(zone);
    }

    public void endGame(boolean wonGame) {
        getPlayers().forEach(player -> player.endGame(wonGame));
        deleteEntities();
    }

    public void deleteEntities() {
        getEntities().forEach(conquestEntity -> {
            if (conquestEntity instanceof ConquestMob) {
                conquestEntity.onDeath(null);
            }
        });
    }

    //Remove dead or unused entities to save memory
    public void cleanUp() {
        ArrayList<ConquestEntity> newEntities = new ArrayList<>();

        for (ConquestEntity entity : entities) {
            if (!(entity.getBukkitEntity() == null || entity.getBukkitEntity().isDead())) {
                newEntities.add(entity);
            }
        }
        entities = newEntities;
    }

    public boolean isEliminated() {
        return zones.size() < 1;
    }
}