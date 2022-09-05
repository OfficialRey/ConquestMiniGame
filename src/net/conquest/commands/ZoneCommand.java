package net.conquest.commands;

import net.conquest.buildings.captureable.Captureable;
import net.conquest.buildings.captureable.Zone;
import net.conquest.other.ConquestTeam;
import net.conquest.other.Util;
import net.conquest.serialization.Serialization;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZoneCommand implements CommandExecutor {


    //        Command     Team  Structure
    //Syntax: "/setzone <0|1> <throne|yard|village|farmland|forest>"

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.isOp() || p.hasPermission("conquest.struct")) {
                if (args.length == 2) {
                    int team;
                    try {
                        team = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        sendSyntax(p);
                        return false;
                    }
                    switch (team) {
                        case 0, 1 -> {
                            Captureable captureable = Captureable.fromName(args[1], ((Player) sender).getLocation(), ConquestTeam.fromId(team));
                            if (captureable != null) {
                                Serialization.saveCaptureable(captureable);
                                sendSuccess(p, captureable);
                            } else {
                                sendSyntax(p);
                            }
                        }
                        default -> sendSyntax(p);
                    }
                } else {
                    sendSyntax(p);
                }
            } else {
                p.sendMessage(Util.NO_PERMISSION);
            }
        }
        return false;
    }

    private static void sendSyntax(Player p) {
        StringBuilder params = new StringBuilder();
        for (Zone z : Zone.values()) {
            params.append(z.CFG).append(" | ");
        }
        p.sendMessage(ChatColor.RED + "Syntax: /setzone < 0 | 1 > < " + params + ">");
    }

    private static void sendSuccess(Player p, Captureable c) {
        p.sendMessage(ChatColor.WHITE + "Successfully created zone " + ChatColor.GREEN + c.getTitle());
    }
}
