package com.kavaicsaba.fiverowgame;

public class  GameState {
    enum State {
        WINNING,
        DRAW,
        GAME_IN_PROGRESS;
    }

    private final State state;
    private final int player;
    private final int winStartX, winStartY, winEndX, winEndY;

    public GameState(State state) {
        this.state = state;
        winStartX = winStartY = winEndX = winEndY = player = 0;
    }

    public GameState(State state, int player, int winStartX, int winStartY, int winEndX, int winEndY) {
        this.state = state;
        this.player = player;
        this.winStartX = winStartX;
        this.winStartY = winStartY;
        this.winEndX = winEndX;
        this.winEndY = winEndY;
    }

    public State getState() {
        return state;
    }

    public int getWinStartX() {
        return winStartX;
    }

    public int getWinStartY() {
        return winStartY;
    }

    public int getWinEndX() {
        return winEndX;
    }

    public int getWinEndY() {
        return winEndY;
    }

    public int getPlayer() {
        return player;
    }
}
