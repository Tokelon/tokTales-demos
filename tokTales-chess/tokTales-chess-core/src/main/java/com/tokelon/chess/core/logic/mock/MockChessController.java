package com.tokelon.chess.core.logic.mock;

import com.tokelon.chess.core.entities.Chessboard;
import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.logic.IChessEngine;
import com.tokelon.chess.core.logic.IChessController;

public class MockChessController implements IChessController {


    private final Chessboard chessboard = new Chessboard();
    private final MockUCIChessEngine chessEngine = new MockUCIChessEngine();


    @Override
    public IChessEngine getEngine() {
        return chessEngine;
    }

    @Override
    public boolean doMove(String from, String to) {
        return true;
    }

    @Override
    public IChessboard getChessboard() {
        return chessboard;
    }

    @Override
    public ChesspieceColor getCurrentPlayer() {
        return ChesspieceColor.WHITE;
    }

    @Override
    public ChesspieceColor getPlayerColor() {
        return ChesspieceColor.WHITE;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void newGame() {
    }

    @Override
    public void update(long timeMillis) {
    }

    @Override
    public boolean tryMove(int fromX, int fromY, int toX, int toY, String addition) {
        return true;
    }

}
