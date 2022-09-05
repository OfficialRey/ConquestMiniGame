package net.conquest.gamestates;

import net.conquest.plugin.Conquest;

public class InGameState extends GameState {
    @Override
    public void start() {
        //Equip players
        Conquest.getGame().startGame();
    }

    @Override
    public void stop() {
        Conquest.getGame().endGame();
    }
}
