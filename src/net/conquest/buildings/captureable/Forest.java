package net.conquest.buildings.captureable;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.MobData;
import org.bukkit.Location;

public class Forest extends Captureable {

    public Forest(Location location, ConquestTeam defaultConquestTeam) {
        super(Zone.FOREST, location, defaultConquestTeam, MobData.SCOUT);
    }
}
