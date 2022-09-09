package net.conquest.plugin;

import net.conquest.commands.BuildCommand;
import net.conquest.commands.LobbyCommand;
import net.conquest.commands.ZoneCommand;
import net.conquest.events.*;
import net.conquest.minigame.ConquestGame;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Conquest extends JavaPlugin {

    private static Conquest plugin;
    private static ConquestGame conquestGame;

    @Override
    public void onEnable() {
        plugin = this;

        cleanUp();

        initialise();

        registerEvents(Bukkit.getServer().getPluginManager());
        registerCommands();
    }

    private void cleanUp() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Player) {
                    ((Player) entity).kickPlayer("Reloading");
                } else {
                    entity.remove();
                }
            }
            denyMobDamage(world);
        }
    }

    private void denyMobDamage(World world) {
        world.setGameRule(GameRule.MOB_GRIEFING, false);
    }

    private void initialise() {
        conquestGame = new ConquestGame();
    }

    private void registerEvents(PluginManager pm) {
        pm.registerEvents(new GriefListener(), this);
        pm.registerEvents(new ConnectionListener(), this);
        pm.registerEvents(new MenuListener(), this);
        pm.registerEvents(new CustomNPCListener(), this);
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new BattleListener(), this);
        pm.registerEvents(new ChatListener(), this);
        pm.registerEvents(new AbilityListener(), this);
    }

    private void registerCommands() {
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("setzone").setExecutor(new ZoneCommand());
        getCommand("setlobby").setExecutor(new LobbyCommand());
    }

    @Override
    public void onDisable() {

    }

    public static Conquest getPlugin() {
        return plugin;
    }

    public static ConquestGame getGame() {
        return conquestGame;
    }
}