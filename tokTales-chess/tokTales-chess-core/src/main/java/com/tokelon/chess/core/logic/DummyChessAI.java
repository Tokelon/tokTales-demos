package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.logic.uci.IUCI;
import com.tokelon.chess.core.logic.uci.IUCIChessAI;

public class DummyChessAI implements IUCIChessAI {


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
