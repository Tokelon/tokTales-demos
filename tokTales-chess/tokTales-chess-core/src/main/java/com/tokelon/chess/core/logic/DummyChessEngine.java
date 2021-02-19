package com.tokelon.chess.core.logic;

public class DummyChessEngine implements IChessEngine {


    private final DummyChessAI chessAI = new DummyChessAI();

    @Override
    public IChessAI getAI() {
        return chessAI;
    }

    @Override
    public boolean doMove(String from, String to) {
        return true;
    }

}
