package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.IChessboard;

public interface IChessBoardController {


    public void newGame(IPlayer white, IPlayer black);
    //public void newGame(IPlayer white, IPlayer black, String fen);

    public boolean doMove(String from, String to);


    public IChessboard getChessboard();

    public String getFen();

}
