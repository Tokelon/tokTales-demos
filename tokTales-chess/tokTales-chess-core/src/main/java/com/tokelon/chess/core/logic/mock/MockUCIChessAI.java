package com.tokelon.chess.core.logic.mock;

import com.tokelon.chess.core.logic.uci.IUCI;
import com.tokelon.chess.core.logic.uci.IUCIChessAI;

public class MockUCIChessAI implements IUCIChessAI {


    @Override
    public void newGame() {
    }

    @Override
    public void nextMove(String fen, String move) {
    }

    @Override
    public void startNextMove() {
    }

    @Override
    public String getAndResetNextMove() {
        return null;
    }

    @Override
    public IUCI getUCI() {
        return null;
    }

}
