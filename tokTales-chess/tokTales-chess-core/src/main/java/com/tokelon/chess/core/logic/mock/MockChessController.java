package com.tokelon.chess.core.logic.mock;

import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.chess.core.logic.IChessBoardController;
import com.tokelon.chess.core.logic.IChessController;
import com.tokelon.chess.core.logic.IPlayer;
import com.tokelon.chess.core.logic.Player;

public class MockChessController implements IChessController {


    private final MockChessBoardController boardController = new MockChessBoardController();
    private final Player player = new Player();


    @Override
    public IChessBoardController getBoardController() {
        return boardController;
    }

    @Override
    public ChesspieceColor getCurrentColor() {
        return ChesspieceColor.WHITE;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return player;
    }

    @Override
    public void newGame(IPlayer white, IPlayer black) {
    }


    @Override
    public void update(long timeMillis) {
    }

    @Override
    public boolean tryMove(int fromX, int fromY, int toX, int toY, String addition) {
        return true;
    }

}
