package com.tokelon.chess.core.logic;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.GameContext;
import com.github.bhlangonijr.chesslib.game.GameMode;
import com.github.bhlangonijr.chesslib.game.VariationType;
import com.github.bhlangonijr.chesslib.move.Move;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import java.util.List;

import javax.inject.Inject;

public class ChesslibEngine implements IChessEngine {


    private final Board board;

    private final IChessAI chessAI;
    private final ILogger logger;

    @Inject
    public ChesslibEngine(ILogging logging, IChessAI chessAI) {
        this.logger = logging.getLogger(getClass());

        GameContext gameContext = new GameContext(GameMode.HUMAN_VS_MACHINE, VariationType.NORMAL);
        this.board = new Board(gameContext, true);

        this.chessAI = chessAI;
    }


    @Override
    public IChessAI getAI() {
        return chessAI;
    }

    @Override
    public boolean doMove(String from, String to) {
        Move move = new Move(from + to, null);

        List<Move> moves = board.legalMoves();
        if(!moves.contains(move)) {
            logger.warn("Not a legal move: {}", move);
            return false;
        }

        boolean result = board.doMove(move, true);

        if(result) {
            chessAI.nextMove(board.getFen(), from + to);
        }

        return result;
    }

}
