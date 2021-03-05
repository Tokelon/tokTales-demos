package com.tokelon.chess.core.logic;

public class Player implements IPlayer {


    private final IChessEngine engine;

    public Player() {
        this.engine = null;
    }

    public Player(IChessEngine engine) {
        this.engine = engine;
    }


    @Override
    public boolean isEngine() {
        return engine != null;
    }

    @Override
    public IChessEngine getEngine() {
        return engine;
    }

}
