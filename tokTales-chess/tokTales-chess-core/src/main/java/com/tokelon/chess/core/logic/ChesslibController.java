package com.tokelon.chess.core.logic;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.GameContext;
import com.github.bhlangonijr.chesslib.game.GameMode;
import com.github.bhlangonijr.chesslib.game.VariationType;
import com.github.bhlangonijr.chesslib.move.Move;
import com.tokelon.chess.core.entities.Chessboard;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import java.util.List;

import javax.inject.Inject;

public class ChesslibController implements IChessBoardController {


    private Chessboard chessboard = new Chessboard();

    private Board board;

    private final ILogger logger;

    @Inject
    public ChesslibController(ILogging logging) {
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
    public boolean doMove(String from, String to) {
        Move move = new Move(from + to, null);

        List<Move> moves = board.legalMoves();
        if(!moves.contains(move)) {
            logger.warn("Not a legal move: {}", move);
            return false;
        }

        return board.doMove(move, true);
    }

    @Override
    public IChessboard getChessboard() {
        return chessboard;
    }

    @Override
    public String getFen() {
        return board.getFen();
    }

}
