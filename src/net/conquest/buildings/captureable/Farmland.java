package net.conquest.buildings.captureable;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.MobData;
import org.bukkit.Location;

public class Farmland extends Captureable {

    public Farmland(Location location, ConquestTeam defaultConquestTeam) {
        super(Zone.FARMLAND, location, defaultConquestTeam, MobData.SCARECROW);
    }
}