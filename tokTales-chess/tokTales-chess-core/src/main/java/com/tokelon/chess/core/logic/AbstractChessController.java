package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.Chessboard;
import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

public abstract class AbstractChessController implements IChessController {
    // Probably going to become AbstractGameEngine


    private Chessboard chessboard;

    private ChesspieceColor currentColor;
    private ChesspieceColor playerColor;


    private final ILogger logger;
    private final IChessAI chessAI;

    public AbstractChessController(ILogging logging, IChessAI chessAI) {
        this.logger = logging.getLogger(getClass());
        this.chessAI = chessAI;
    }


    @Override
    public IChessAI getAI() {
        return chessAI;
    }


    @Override
    public IChessboard getChessboard() {
        return chessboard;
    }

    @Override
    public ChesspieceColor getCurrentPlayer() {
        return currentColor;
    }

    @Override
    public ChesspieceColor getPlayerColor() {
        return playerColor;
    }

    @Override
    public void initialize() {
        getAI().initialize();
    }

    @Override
    public void newGame() {
        this.chessboard = Chessboard.createInitial();
        this.currentColor = ChesspieceColor.WHITE;
        this.playerColor = ChesspieceColor.WHITE;

        getAI().newGame();
    }


    @Override
    public void update(long timeMillis) {
        String nextMove = getAI().getAndResetNextMove();
        if(nextMove != null) {
            engineMove(nextMove);
            startNextTurn();
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


    public void startNextTurn() {
        currentColor = currentColor == ChesspieceColor.WHITE ? ChesspieceColor.BLACK : ChesspieceColor.WHITE;

        if(currentColor != playerColor) {
            startAITurn();
        }
        else {
            startPlayerTurn();
        }
    }

    public void startPlayerTurn() {
        // Nothing yet
    }

    public void startAITurn() {
        getAI().startNextMove();
    }


    public boolean playerMove(int fromX, int fromY, int toX, int toY, String addition) {
        String from = chessboard.fieldToNotationX(fromX) + Byte.toString(chessboard.fieldToNotationY(fromY));
        String to = chessboard.fieldToNotationX(toX) + Byte.toString(chessboard.fieldToNotationY(toY));
        logger.info("Move {}{}", from, to);

        if(doMove(from, to)) {
            getChessboard().movePiece(fromX, fromY, toX, toY);

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
