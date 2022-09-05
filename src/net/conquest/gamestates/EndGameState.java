package net.conquest.gamestates;

import net.conquest.plugin.Conquest;

public class EndGameState extends GameState {

    private final EndGameCountdown countdown;

    public EndGameState(GameStateManager gameStateManager) {
        countdown = new EndGameCountdown(gameStateManager);
    }

    @Override
    public void start() {
        countdown.start();
    }

    @Override
    public void stop() {
    }
}
