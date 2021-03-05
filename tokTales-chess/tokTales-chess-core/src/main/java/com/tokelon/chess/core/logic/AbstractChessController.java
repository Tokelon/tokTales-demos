package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.Chessboard;
import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

public abstract class AbstractChessController implements IChessController {


    private Chessboard chessboard;

    private ChesspieceColor currentColor;

    private IPlayer white;
    private IPlayer black;

    private final ILogger logger;

    public AbstractChessController(ILogging logging) {
        this.logger = logging.getLogger(getClass());
    }


    protected abstract boolean doMove(String from, String to);

    protected abstract String getFen();


    @Override
    public IChessboard getChessboard() {
        return chessboard;
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
        this.white = white;
        this.black = black;

        this.chessboard = Chessboard.createInitial();
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
                engineMove(nextMove);
                startNextTurn();
            }
        }
    }

    @Override
    public boolean tryMove(int fromX, int fromY, int toX, int toY, String addition) {
        if(playerMove(fromX, fromY, toX, toY, addition)) {
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


    public boolean playerMove(int fromX, int fromY, int toX, int toY, String addition) {
        String from = chessboard.fieldToNotationX(fromX) + Byte.toString(chessboard.fieldToNotationY(fromY));
        String to = chessboard.fieldToNotationX(toX) + Byte.toString(chessboard.fieldToNotationY(toY));
        logger.info("Move {}{}", from, to);

        if(doMove(from, to)) {
            getChessboard().movePiece(fromX, fromY, toX, toY);

            if(getCurrentPlayer().isEngine()) {
                getCurrentPlayer().getEngine().nextMove(getFen(), from + to);
            }

            logger.info("Move completed from [{}, {}] to [{}, {}]", fromX, fromY, toX, toY);
            return true;
        }
        else {
            logger.info("Move failed from [{}, {}] to [{}, {}]", fromX, fromY, toX, toY);
            return false;
        }
    }

    public boolean engineMove(String move) {
        int fromX = chessboard.notationToFieldX(move.charAt(0));
        int fromY = chessboard.notationToFieldY(Byte.parseByte(String.valueOf(move.charAt(1))));
        int toX = chessboard.notationToFieldX(move.charAt(2));
        int toY = chessboard.notationToFieldY(Byte.parseByte(String.valueOf(move.charAt(3))));
        String addition = move.substring(4);

        return playerMove(fromX, fromY, toX, toY, addition);
    }

}
