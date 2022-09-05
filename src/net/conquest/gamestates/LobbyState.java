package net.conquest.gamestates;

public class LobbyState extends GameState {
    private final LobbyCountdown countdown;

    public LobbyState(GameStateManager gameStateManager) {
        countdown = new LobbyCountdown(gameStateManager);
    }

    @Override
    public void start() {
        countdown.startIdleTask();
    }

    @Override
    public void stop() {
        countdown.stop();
    }

    public LobbyCountdown getCountdown() {
        return countdown;
    }
}
