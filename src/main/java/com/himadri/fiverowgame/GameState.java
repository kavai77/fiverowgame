package com.himadri.fiverowgame;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GameState {
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
}
