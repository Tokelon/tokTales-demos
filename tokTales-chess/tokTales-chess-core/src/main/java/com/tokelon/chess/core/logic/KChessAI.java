package com.tokelon.chess.core.logic;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.kengine.Search;
import com.github.bhlangonijr.kengine.SearchParams;
import com.github.bhlangonijr.kengine.montecarlo.MonteCarloSearch;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import java9.util.concurrent.CompletableFuture;

public class KChessAI implements IChessAI {


    public static final int DEFAULT_SEARCH_TIME_MILLIS = 1000;

    private final Executor delayedExecutor;

    private final KWrapperSearchEngine searchEngine;
    private final Search search;

    public KChessAI(Board board) {
        this(board, DEFAULT_SEARCH_TIME_MILLIS);
    }

    public KChessAI(Board board, int searchTimeMillis) {
        this.searchEngine = new KWrapperSearchEngine(new MonteCarloSearch()); //new AlphaBetaSearch()
        this.search = new Search(board, searchEngine);

        this.delayedExecutor = CompletableFuture.delayedExecutor(searchTimeMillis, TimeUnit.MILLISECONDS);
    }


    protected SearchParams createSearchParams() {
        /*
        SearchParams params = new SearchParams(
                whiteTime = getLong(tokens, "wtime", "6000000"),
                blackTime = getLong(tokens, "btime", "6000000"),
                whiteIncrement = getLong(tokens, "winc", "0"),
                blackIncrement = getLong(tokens, "binc", "0"),
                moveTime = getLong(tokens, "movetime", "0"),
                movesToGo = getInt(tokens, "movestogo", "0"),
                depth = getInt(tokens, "depth", "100"),
                nodes = getLong(tokens, "nodes", "5000000000"),
                infinite = getBoolean(tokens, "infinite", "false"),
                ponder = getBoolean(tokens, "ponder", "false"),
                searchMoves = getString(tokens, "searchmoves", ""),
                threads = search.threads
        );
        */
        SearchParams searchParams = new SearchParams();
        searchParams.setThreads(Math.max(1, Runtime.getRuntime().availableProcessors() / 2 - 2));

        return searchParams;
    }

    @Override
    public void startNextMove() {
        SearchParams searchParams = createSearchParams();
        search.start(searchParams);

        CompletableFuture.runAsync(() -> search.stop(), delayedExecutor);
    }

    @Override
    public String getAndResetNextMove() {
        Move lastMove = searchEngine.getLastMove();
        if(lastMove == null) {
            return null;
        }

        searchEngine.setLastMove(null);
        return lastMove.getFrom().toString().toLowerCase() + lastMove.getTo().toString().toLowerCase();
    }


    @Override
    public String calculateNextMove() {
        SearchParams searchParams = createSearchParams();
        search.start(searchParams);


        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> search.stop(), delayedExecutor).thenRun(() -> {
            while (searchEngine.getLastMove() == null) {
                try {
                    Thread.sleep(10); // Wait for the stop to go through
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        completableFuture.join();

        Move lastMove = searchEngine.getLastMove();
        return lastMove.getFrom().toString().toLowerCase() + lastMove.getTo().toString().toLowerCase();
    }

}
