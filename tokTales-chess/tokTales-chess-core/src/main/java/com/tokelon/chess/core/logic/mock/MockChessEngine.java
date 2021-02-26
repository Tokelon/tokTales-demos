package com.tokelon.chess.core.logic.mock;

import com.tokelon.chess.core.logic.IChessAI;
import com.tokelon.chess.core.logic.IChessEngine;

public class MockChessEngine implements IChessEngine {


    private final MockUCIChessAI chessAI = new MockUCIChessAI();

    @Override
    public IChessAI getAI() {
        return chessAI;
    }

    @Override
    public boolean doMove(String from, String to) {
        return true;
    }

}
