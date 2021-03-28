package com.tokelon.chess.core.logic;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.GameContext;
import com.github.bhlangonijr.chesslib.game.GameMode;
import com.github.bhlangonijr.chesslib.game.VariationType;
import com.github.bhlangonijr.chesslib.move.Move;
import com.tokelon.chess.core.entities.Chessboard;
import com.tokelon.chess.core.entities.ChesspieceType;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.entities.IChesspiece;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import java.util.List;

import javax.inject.Inject;

public class ChesslibBoardController implements IChessBoardController {


    private Chessboard chessboard = new Chessboard();

    private Board board;

    private final ILogger logger;

    @Inject
    public ChesslibBoardController(ILogging logging) {
        this.logger = logging.getLogger(getClass());
    }


    @Override
    public void newGame(IPlayer white, IPlayer black) {
        this.chessboard = Chessboard.createInitial();

        GameContext gameContext = new GameContext(getGameMode(white, black), VariationType.NORMAL);
        this.board = new Board(gameContext, true);
    }

    private GameMode getGameMode(IPlayer white, IPlayer black) {
        GameMode gameMode;
        if(white.isEngine() && black.isEngine()) {
            gameMode = GameMode.MACHINE_VS_MACHINE;
        }
        else if(!white.isEngine() && !black.isEngine()) {
            gameMode = GameMode.HUMAN_VS_HUMAN;
        }
        else if(white.isEngine()) {
            gameMode = GameMode.HUMAN_VS_MACHINE;
        }
        else {
            gameMode = GameMode.MACHINE_VS_HUMAN;
        }

        return gameMode;
    }


    @Override
    public boolean doMove(String move) {
        Move boardMove = new Move(move, null);

        List<Move> moves = board.legalMoves();
        if(!moves.contains(boardMove)) {
            logger.warn("Not a legal boardMove: {}", boardMove);
            return false;
        }

        int fromX = chessboard.notationToFieldX(move.charAt(0));
        int fromY = chessboard.notationToFieldY(Byte.parseByte(String.valueOf(move.charAt(1))));
        int toX = chessboard.notationToFieldX(move.charAt(2));
        int toY = chessboard.notationToFieldY(Byte.parseByte(String.valueOf(move.charAt(3))));
        String addition = move.substring(4);


        boolean success = board.doMove(boardMove, true);
        if(success) {
            if(doSpecialMove(fromX, fromY, toX, toY, addition)) {
                logger.info("Special move completed for: {}", move);
            }

            chessboard.movePiece(fromX, fromY, toX, toY);
            logger.info("Move completed from [{}, {}] to [{}, {}]", fromX, fromY, toX, toY);
        }
        else {
            logger.info("Move failed from [{}, {}] to [{}, {}]", fromX, fromY, toX, toY);
        }

        return success;
    }

    protected boolean doSpecialMove(int fromX, int fromY, int toX, int toY, String addition) {
        // TODO: Implement promotion (use addition), en-passant, etc.
        boolean specialMove = false;

        boolean boardIsFlipped = fromX < chessboard.getSize() / 2;
        IChesspiece fromField = chessboard.getField(fromX, fromY);
        ChesspieceType fromType = fromField == null ? null : fromField.getType();


        boolean isCastle = fromType == ChesspieceType.KING && fromY == toY && Math.abs(fromX - toX) > 1;
        if(isCastle) {
            specialMove = true;

            boolean kingSize = toX > fromX && !boardIsFlipped;
            int fromModifier = kingSize ? 1 : -2;
            int toModifier = kingSize ? 1 : -1;

            chessboard.movePiece(toX + fromModifier, toY, toX - toModifier, toY);

            String castleMove = kingSize ? "0-0" : "0-0-0";
            logger.info("Castle move {} completed from [{}, {}] to [{}, {}]", castleMove, toX + fromModifier, toY, toX - toModifier, toY);
        }

        return specialMove;
    }


    @Override
    public IChessboard getChessboard() {
        return chessboard;
    }

    @Override
    public String getFen() {
        return board.getFen();
    }


    @Override
    public String translateMove(int fromX, int fromY, int toX, int toY) {
        String from = chessboard.fieldToNotationX(fromX) + Byte.toString(chessboard.fieldToNotationY(fromY));
        String to = chessboard.fieldToNotationX(toX) + Byte.toString(chessboard.fieldToNotationY(toY));
        return from + to;
    }

}
