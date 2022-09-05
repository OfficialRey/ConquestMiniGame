package net.conquest.buildings.captureable;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.MobData;
import org.bukkit.Location;

public class Courtyard extends Captureable {

    public Courtyard(Location location, ConquestTeam defaultConquestTeam) {
        super(Zone.YARD, location, defaultConquestTeam, MobData.KNIGHT);
    }
}
