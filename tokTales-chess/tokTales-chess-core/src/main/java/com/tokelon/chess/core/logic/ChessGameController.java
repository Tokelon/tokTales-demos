package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import javax.inject.Inject;

public class ChessGameController implements IChessGameController {


    private ChesspieceColor currentColor;

    private IPlayer white;
    private IPlayer black;

    private final ILogger logger;
    private final IChessBoardController boardController;

    @Inject
    public ChessGameController(ILogging logging, IChessBoardController boardController) {
        this.logger = logging.getLogger(getClass());
        this.boardController = boardController;
    }


    @Override
    public IChessBoardController getBoardController() {
        return boardController;
    }

    @Override
    public ChesspieceColor getCurrentColor() {
        return currentColor;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return getCurrentColor() == ChesspieceColor.WHITE ? white : black;
    }


    @Override
    public void newGame(IPlayer white, IPlayer black) {
        getBoardController().newGame(white, black);

        this.white = white;
        this.black = black;

        this.currentColor = ChesspieceColor.WHITE;

        if(white.isEngine()) {
            white.getEngine().newGame();

            white.getEngine().startNextMove();
        }

        if(black.isEngine()) {
            black.getEngine().newGame();
        }
    }


    @Override
    public void update(long timeMillis) {
        if(getCurrentPlayer().isEngine()) {
            String nextMove = getCurrentPlayer().getEngine().getAndResetNextMove();

            if(nextMove != null) {
                if(!tryMove(nextMove)) {
                    logger.warn("Invalid engine move {}", nextMove);
                }
            }
        }
    }


    @Override
    public boolean tryMove(int fromX, int fromY, int toX, int toY, String addition) {
        String translation = getBoardController().translateMove(fromX, fromY, toX, toY);
        String move = translation + addition;

        return tryMove(move);
    }

    @Override
    public boolean tryMove(String move) {
        logger.info("Trying move {}", move);

        if(getBoardController().doMove(move)) {
            if(white.isEngine()) {
                white.getEngine().nextMove(getBoardController().getFen(), move);
            }
            if(black.isEngine()) {
                black.getEngine().nextMove(getBoardController().getFen(), move);
            }

            startNextTurn();
            return true;
        }
        else {
            return false;
        }
    }

    protected void startNextTurn() {
        currentColor = currentColor == ChesspieceColor.WHITE ? ChesspieceColor.BLACK : ChesspieceColor.WHITE;

        if(getCurrentPlayer().isEngine()) {
            getCurrentPlayer().getEngine().startNextMove();
        }
    }

}
