package net.conquest.buildings.captureable;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.MobData;
import org.bukkit.Location;

public class Village extends Captureable {

    public Village(Location location, ConquestTeam defaultConquestTeam) {
        super(Zone.VILLAGE, location, defaultConquestTeam, MobData.PEASANT);
    }
}
