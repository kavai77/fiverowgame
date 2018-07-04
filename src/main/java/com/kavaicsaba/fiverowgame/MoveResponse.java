package com.kavaicsaba.fiverowgame;

public class MoveResponse {
    private GameState gameState;
    private int machineX, machineY;

    public MoveResponse() {
    }

    public MoveResponse(GameState gameState) {
        this.gameState = gameState;
    }

    public MoveResponse(GameState gameState, int machineX, int machineY) {
        this.gameState = gameState;
        this.machineX = machineX;
        this.machineY = machineY;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getMachineX() {
        return machineX;
    }

    public void setMachineX(int machineX) {
        this.machineX = machineX;
    }

    public int getMachineY() {
        return machineY;
    }

    public void setMachineY(int machineY) {
        this.machineY = machineY;
    }
}
