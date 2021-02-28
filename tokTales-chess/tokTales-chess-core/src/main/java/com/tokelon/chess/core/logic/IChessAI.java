package com.tokelon.chess.core.logic;

public interface IChessAI {
    // Implement listener support?


    public void initialize();

    public void newGame();

    public void nextMove(String fen, String move);

    public void startNextMove();

    public String getAndResetNextMove();

}
