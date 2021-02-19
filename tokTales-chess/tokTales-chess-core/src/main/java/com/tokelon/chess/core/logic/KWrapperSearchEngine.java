package com.tokelon.chess.core.logic;

import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.kengine.SearchEngine;
import com.github.bhlangonijr.kengine.SearchState;

import org.jetbrains.annotations.NotNull;

public class KWrapperSearchEngine implements SearchEngine {


    private Move lastMove;

    private final SearchEngine searchEngine;

    public KWrapperSearchEngine(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }


    @NotNull
    @Override
    public Move rooSearch(@NotNull SearchState searchState) {
        Move move = searchEngine.rooSearch(searchState);
        setLastMove(move);
        return move;
    }

    public void setLastMove(Move move) {
        this.lastMove = move;
    }

    public Move getLastMove() {
        return lastMove;
    }

}
