package net.conquest.entities;

import net.conquest.entities.mobs.ConquestEntity;

import java.util.ArrayList;

public class EntityManager {

    private final ArrayList<ConquestEntity> entities;

    public EntityManager() {
        entities = new ArrayList<>();
    }

    public void addEntity(ConquestEntity entity) {
        if (!entities.contains(entity)) {
            entities.add(entity);
        }
    }

    public void removeEntity(ConquestEntity entity) {
        entities.remove(entity);
    }

    public ArrayList<ConquestEntity> getEntities() {
        return entities;
    }
}