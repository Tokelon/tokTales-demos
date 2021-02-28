package com.tokelon.chess.core.logic.uci;

import javax.inject.Inject;

public class UCIChessAI implements IUCIChessAI {


    public static final int DEFAULT_SEARCH_TIME_MILLIS = 1000;

    private final IUCI uci;

    @Inject
    public UCIChessAI(IUCI uci) {
        this.uci = uci;
    }


    @Override
    public void initialize() {
        uci.uci();
        //uci.isReady(); needed?
    }

    @Override
    public void newGame() {
        uci.uciNewGame();
        uci.isReady();
        uci.position(null);
    }

    @Override
    public void nextMove(String fen, String move) {
        uci.position(fen, move);
    }

    @Override
    public void startNextMove() {
        uci.isReady();
        uci.go(DEFAULT_SEARCH_TIME_MILLIS);
    }

    @Override
    public String getAndResetNextMove() {
        return uci.getLastMove();
    }


    @Override
    public IUCI getUCI() {
        return uci;
    }

}
