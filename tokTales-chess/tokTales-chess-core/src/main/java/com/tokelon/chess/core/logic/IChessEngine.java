package com.tokelon.chess.core.logic;

public interface IChessEngine {


    public IChessAI getAI();

    public boolean doMove(String from, String to);

}
