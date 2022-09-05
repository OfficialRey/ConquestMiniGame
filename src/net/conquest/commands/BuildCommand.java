package net.conquest.commands;

import net.conquest.other.Util;
import net.conquest.plugin.Conquest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuildCommand implements CommandExecutor {

    private static final ArrayList<Player> building = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.isOp() || p.hasPermission("conquest.build")) {
                if (args.length == 0) {
                    if (!building.remove(p)) {
                        enterBuildMode(p);
                    } else {
                        exitBuildMode(p);
                    }
                }
            } else {
                sender.sendMessage(Util.NO_PERMISSION);
            }
        }

        return false;
    }

    private void enterBuildMode(Player player) {
        Conquest.getGame().removePlayer(player);
        Util.addBuildItems(player);
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(ChatColor.WHITE + "Build Mode: " + ChatColor.GREEN + "Enabled");
        building.add(player);
    }

    private void exitBuildMode(Player player) {
        Conquest.getGame().addPlayer(player);
        player.sendMessage(ChatColor.WHITE + "Build Mode: " + ChatColor.RED + "Disabled");
        building.remove(player);
    }

    public static boolean canBuild(Player player) {
        return building.contains(player);
    }
}
