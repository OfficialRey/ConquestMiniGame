package net.conquest.other;

import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.ConquestPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Random;

public class DeathMessage {

    /* Conversion Information

        "//": Player Name
        "..": Player Kit Name
        "##" Killer Name

     */

    private static final String[] suicides = {
            "// hated to be an ...",
            "// slipped.",
            "// took the short way out.",
            "// is counting his organs.",
            "// forgot to eat beans.",
            "// has been reduced to atoms.",
            "// didn't complete the tutorial.",
            "// was removed.",
            "// lost his soul."
    };

    private static final String[] kills = {
            "## ended //'s life.",
            "## showed their hate against //",
            "## wanted // to die.",
            "// is finally in heaven thanks to ##.",
            "## couldn't resist the urge to harm //.",
            "// needs some milk.",
            "## released // from their pain.",
            "## banished // to hell.",
            "## dances on //'s grave.",
            "## sold //'s kidneys.",
            "## has reduced // to atoms.",
            "## showed // how the game is played.",
            "// did not respect ##.",
            "// did not obey ##."
    };

    public static void createDeathMessage(ConquestPlayer victim, ConquestEntity murderer) {
        if (victim != null) {
            if (murderer != null) {
                sendKillMessage(victim, murderer);
            } else {
                sendSuicideMessage(victim);
            }
        }
    }

    private static void sendSuicideMessage(ConquestPlayer victim) {
        String message = ChatColor.WHITE + suicides[new Random().nextInt(suicides.length)];
        message = message.replace("//", victim.getTeam().getChatColor() + victim.getName() + ChatColor.WHITE);
        message = message.replace("..", victim.getKit().getTitle() + ChatColor.WHITE);

        Bukkit.broadcastMessage(Util.PREFIX + message);
    }

    private static void sendKillMessage(ConquestPlayer victim, ConquestEntity murderer) {
        String message = ChatColor.WHITE + kills[new Random().nextInt(kills.length)];
        message = message.replace("//", victim.getTeam().getChatColor() + victim.getName() + ChatColor.WHITE);
        message = message.replace("..", victim.getKit().getTitle() + ChatColor.WHITE);
        message = message.replace("##", murderer.getTeam().getChatColor() + murderer.getName() + ChatColor.WHITE);

        Bukkit.broadcastMessage(Util.PREFIX + message);
    }
}