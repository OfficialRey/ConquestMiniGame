package net.conquest.entities.mobs.storage;

import net.conquest.other.ConquestTeam;
import net.conquest.entities.mobs.ConquestEntity;
import net.conquest.entities.mobs.MobData;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;

public class HorseMob extends ConquestEntity {

    public HorseMob(Location location, MobData data, ConquestTeam conquestTeam) {
        super(createEntity(location, data.TYPE), data, conquestTeam);
        conquestTeam.addEntity(this);
    }

    public List<Entity> getRiders() {
        return entity.getPassengers();
    }

    public void setRider(ConquestEntity rider) {
        entity.getPassengers().clear();
        entity.addPassenger(rider.getBukkitEntity());
    }

    @Override
    public void run() {
        if(getRiders().isEmpty()) {
            killEntity();
        }
    }

    @Override
    public void onDeath(ConquestEntity killer) {

    }

    @Override
    public void onSummon(ConquestTeam conquestTeam) {

    }
}
