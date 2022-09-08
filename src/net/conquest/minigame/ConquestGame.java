package net.conquest.minigame;

import net.conquest.buildings.captureable.Captureable;
import net.conquest.buildings.captureable.Zone;
import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import net.conquest.gamestates.GameState;
import net.conquest.gamestates.GameStateManager;
import net.conquest.gamestates.LobbyState;
import net.conquest.kits.Kit;
import net.conquest.kits.KitList;
import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import net.conquest.serialization.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;

public class ConquestGame {

    //TODO: Implement multi-world support
    private final GameStateManager gameStateManager;

    private final ArrayList<ConquestTeam> conquestTeams;
    private final ArrayList<Player> spectators;
    private final GameStatus gameStatus;

    public ConquestGame() {
        gameStateManager = new GameStateManager();
        gameStatus = new GameStatus();
        conquestTeams = new ArrayList<>();
        spectators = new ArrayList<>();
        initialise();
        initialiseZones();
    }

    private void initialise() {
        gameStateManager.setGameState(GameState.LOBBY_STATE);
        conquestTeams.add(new ConquestTeam(BarColor.BLUE, ChatColor.BLUE, Color.BLUE, "Blue", 0));
        conquestTeams.add(new ConquestTeam(BarColor.RED, ChatColor.RED, Color.RED, "Red", 1));
    }

    private void initialiseZones() {
        for (ConquestTeam conquestTeam : conquestTeams) {
            for (Zone zone : Zone.values()) {
                Captureable captureable = Serialization.loadCaptureable(conquestTeam, zone.CFG);
                if (captureable != null) {
                    conquestTeam.addZone(captureable);
                }
            }
        }
    }

    public ConquestPlayer addPlayer(Player player) {
        if (!containsPlayer(player)) {
            ConquestPlayer conquestPlayer = new ConquestPlayer(player, null, getWeakestTeam());

            //Handle game start
            if (gameStateManager.getCurrentGameState() instanceof LobbyState) {
                Util.addLobbyItems(player);
                if (getAllPlayers().size() >= Util.MIN_PLAYERS) {
                    LobbyState state = (LobbyState) gameStateManager.getCurrentGameState();
                    state.getCountdown().start();
                }
            }
            return conquestPlayer;
        }
        return null;
    }

    public void removePlayer(Player player) {
        removePlayerByBalance(player);
        if (gameStateManager.getCurrentGameState() instanceof LobbyState) {
            if (getAllPlayers().size() < Util.MIN_PLAYERS) {
                LobbyState state = (LobbyState) gameStateManager.getCurrentGameState();
                state.getCountdown().startIdleTask();
            }
        }
    }

    public void removePlayerByBalance(Player player) {
        if (containsPlayer(player)) {
            for (ConquestTeam conquestTeam : conquestTeams) {
                conquestTeam.removeEntity(getConquestPlayer(player.getUniqueId()));
            }
        }
    }

    private void balanceTeams() {
        boolean balanced = false;
        while (!areTeamsBalanced()) {
            ConquestPlayer player = getStrongestTeam().getPlayers().get(Util.NULL);
            removePlayerByBalance(player.getOwner());
            addPlayer(player.getOwner());
            balanced = true;
        }
        if (balanced) {
            Bukkit.broadcastMessage(Util.PREFIX + "The teams have been balanced.");
        }
    }

    private boolean areTeamsBalanced() {
        return getWeakestTeam().getSize() + (getTeams().size() - 1) >= getStrongestTeam().getSize();
    }

    public void addSpectator(Player player) {
        if (!spectators.contains(player)) {
            spectators.add(player);
            Util.spectate(player);
        }
    }

    public void removeSpectator(Player player) {
        spectators.remove(player);
    }

    public ConquestPlayer getConquestPlayer(UUID playerID) {
        ConquestEntity player = this.getConquestEntity(playerID);

        return (player instanceof ConquestPlayer) ? (ConquestPlayer)player : null;
    }

    public ConquestEntity getConquestEntity(UUID entityID) {
        for (ConquestTeam conquestTeam : conquestTeams) {
            ConquestEntity entity = conquestTeam.getEntity(entityID);

            if(entity != null) {
                return entity;
            }
        }
        return null;
    }

    private boolean containsPlayer(Player player) {
        return getConquestPlayer(player.getUniqueId()) != null;
    }

    public void setPlayerKit(Player player, Kit kit) {
        ConquestPlayer conquestPlayer = getConquestPlayer(player.getUniqueId());
        conquestPlayer.setKit(kit);
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public ArrayList<ConquestTeam> getTeams() {
        return conquestTeams;
    }

    private ConquestTeam getStrongestTeam() {
        ConquestTeam team = conquestTeams.get(Util.NULL);
        for (ConquestTeam conquestTeam : conquestTeams) {
            if (conquestTeam.getSize() > team.getSize()) {
                team = conquestTeam;
            }
        }
        return team;
    }

    private ConquestTeam getWeakestTeam() {
        ConquestTeam team = conquestTeams.get(Util.NULL);
        for (ConquestTeam conquestTeam : conquestTeams) {
            if (conquestTeam.getSize() < team.getSize()) {
                team = conquestTeam;
            }
        }
        return team;
    }

    public void removeTeam(ConquestTeam conquestTeam) {
        conquestTeam.delete();
        Bukkit.broadcastMessage(Util.PREFIX + "Team " + conquestTeam.getChatColor() + conquestTeam.getName() + ChatColor.WHITE + " has been " + ChatColor.GRAY + "eliminated" + ChatColor.WHITE + ".");
        conquestTeams.remove(conquestTeam);

        if (conquestTeams.size() < 2) {
            Bukkit.broadcastMessage(Util.PREFIX + ChatColor.WHITE + "Team " + conquestTeams.get(Util.NULL).getChatColor() + conquestTeams.get(Util.NULL).getName() + ChatColor.WHITE + " has won!");
            conquestTeams.get(Util.NULL).delete();
            conquestTeams.remove(conquestTeams.get(Util.NULL));

            gameStateManager.setGameState(GameState.ENDGAME_STATE);
        }
    }


    public ArrayList<ConquestPlayer> getAllPlayers() {
        ArrayList<ConquestPlayer> players = new ArrayList<>();
        for (ConquestTeam conquestTeam : conquestTeams) {
            players.addAll(conquestTeam.getPlayers());
        }
        return players;
    }

    public ArrayList<ConquestEntity> getAllEntities() {
        ArrayList<ConquestEntity> entities = new ArrayList<>();
        for (ConquestTeam conquestTeam : conquestTeams) {
            entities.addAll(conquestTeam.getEntities());
        }
        return entities;
    }

    private BukkitTask task;


    //Initialise and start Game
    public void startGame() {
        //Equip Players
        balanceTeams();
        gameStatus.start();
        for (ConquestTeam conquestTeam : conquestTeams) {
            for (ConquestPlayer player : conquestTeam.getPlayers()) {
                if (player.getKit() == null) {
                    player.setKit(KitList.getKits().get(0));
                }
                player.respawn();
            }
        }
        startTask();
    }

    public void endGame() {
        task.cancel();
    }

    private void startTask() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                runTeams();
                gameStatus.run();
            }
        }.runTaskTimer(Conquest.getPlugin(), 0, Util.TICKS_PER_SECOND);
    }

    private void runTeams() {
        ArrayList<ConquestTeam> toRemove = new ArrayList<>();

        for (ConquestTeam conquestTeam : conquestTeams) {
            conquestTeam.run();
            if (conquestTeam.isEliminated()) {
                toRemove.add(conquestTeam);
            }
        }

        toRemove.forEach(this::removeTeam);
    }

    public ArrayList<Player> getSpectators() {
        return spectators;
    }

    public void resetGame() {
        gameStatus.reset();

        resetTeams();
        resetPlayers();

        initialise();
        initialiseZones();

        addPlayers();
    }

    private void resetTeams() {
        conquestTeams.forEach(ConquestTeam::delete);
        conquestTeams.clear();

    }

    private void resetPlayers() {
        spectators.clear();
        Bukkit.getOnlinePlayers().forEach(player -> player.teleport(Serialization.loadLobbyLocation()));
    }

    private void addPlayers() {
        Bukkit.getOnlinePlayers().forEach(this::addPlayer);
    }
}
