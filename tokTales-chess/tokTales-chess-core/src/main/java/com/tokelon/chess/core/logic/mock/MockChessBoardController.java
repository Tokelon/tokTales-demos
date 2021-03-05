package com.tokelon.chess.core.logic.mock;

import com.tokelon.chess.core.entities.Chessboard;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.logic.IChessBoardController;
import com.tokelon.chess.core.logic.IPlayer;

public class MockChessBoardController implements IChessBoardController {


    private final Chessboard chessboard = new Chessboard();

    @Override
    public void newGame(IPlayer white, IPlayer black) {
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
    public String getFen() {
        return "";
    }

}
