package com.tokelon.chess.core.state;

import com.tokelon.toktales.core.game.state.ITypedGameState;

public interface IBoardGamestate extends ITypedGameState<IBoardGamescene> {


    @Override
    public IBoardGamestateControlHandler getStateControlHandler();

}
