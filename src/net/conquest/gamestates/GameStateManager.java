package net.conquest.gamestates;

public class GameStateManager {

    private GameState[] gameStates;
    private GameState currentState;

    public GameStateManager() {
        gameStates = new GameState[3];
        gameStates[GameState.LOBBY_STATE] = new LobbyState(this);
        gameStates[GameState.INGAME_STATE] = new InGameState();
        gameStates[GameState.ENDGAME_STATE] = new EndGameState(this);
    }

    public void setGameState(int id) {
        if (currentState != null) {
            currentState.stop();
        }
        currentState = gameStates[id];
        currentState.start();
    }

    public void stopCurrentState() {
        if (currentState != null) {
            currentState.stop();
            currentState = null;
        }
    }

    public GameState getCurrentGameState() {
        return currentState;
    }
}
