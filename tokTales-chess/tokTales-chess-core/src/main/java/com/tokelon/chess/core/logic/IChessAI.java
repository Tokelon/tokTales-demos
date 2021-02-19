package com.tokelon.chess.core.logic;

public interface IChessAI {
    // Implement listener support?


    public void startNextMove();

    public String getAndResetNextMove();


    public String calculateNextMove();

}
