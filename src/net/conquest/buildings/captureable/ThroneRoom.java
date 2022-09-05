package net.conquest.buildings.captureable;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.MobData;
import org.bukkit.Location;

public class ThroneRoom extends Captureable {

    public ThroneRoom(Location location, ConquestTeam defaultConquestTeam) {
        super(Zone.THRONE_ROOM, location, defaultConquestTeam, MobData.GUARD);
    }
}
