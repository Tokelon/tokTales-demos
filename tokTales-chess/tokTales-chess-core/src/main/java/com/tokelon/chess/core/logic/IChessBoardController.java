package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.IChessboard;

public interface IChessBoardController {


    public void newGame(IPlayer white, IPlayer black);
    //public void newGame(IPlayer white, IPlayer black, String fen);

    public boolean doMove(String move);


    public IChessboard getChessboard();

    public String getFen();


    public String translateMove(int fromX, int fromY, int toX, int toY); // addition needed?

}
