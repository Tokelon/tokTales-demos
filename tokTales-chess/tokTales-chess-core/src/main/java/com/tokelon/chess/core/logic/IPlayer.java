package com.tokelon.chess.core.logic;

import com.tokelon.chess.core.entities.ChesspieceColor;

public interface IPlayer {


    public boolean isEngine();

    public IChessEngine getEngine();

}
