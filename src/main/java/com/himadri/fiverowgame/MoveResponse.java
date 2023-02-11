package com.himadri.fiverowgame;

import lombok.Data;

@Data
public class MoveResponse {
    private final GameState gameState;
    private final int machineX, machineY;
}
