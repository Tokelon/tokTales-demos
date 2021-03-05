package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.chess.core.entities.IChessboard;

public interface IChessController {


    public IChessEngine getEngine();

    public boolean doMove(String from, String to);


    public IChessboard getChessboard();

    public ChesspieceColor getCurrentPlayer();
    public ChesspieceColor getPlayerColor();



    public void initialize();

    // TODO: Add gamemode, elo, fen, options etc
    public void newGame();
    //public void newGame(IPlayer playerOne, IPlayer playerTwo);

    public void update(long timeMillis);


    public boolean tryMove(int fromX, int fromY, int toX, int toY, String addition);
    //public boolean tryMove(String move); // TODO: Implement

}
