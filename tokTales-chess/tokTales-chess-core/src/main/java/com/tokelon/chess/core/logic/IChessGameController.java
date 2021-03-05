package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.ChesspieceColor;

public interface IChessGameController {


    public IChessBoardController getBoardController();


    public ChesspieceColor getCurrentColor();

    public IPlayer getCurrentPlayer();
    //public IPlayer getWhite();
    //public IPlayer getBlack();


    // TODO: Add gamemode, elo, fen, options etc
    public void newGame(IPlayer white, IPlayer black);
    //public void newGame(IPlayer playerOne, IPlayer playerTwo, String fen);

    public void update(long timeMillis);


    public boolean tryMove(int fromX, int fromY, int toX, int toY, String addition);
    //public boolean tryMove(String move); // TODO: Implement

}
