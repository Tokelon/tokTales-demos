package com.tokelon.chess.core.state;

import com.tokelon.toktales.core.game.state.IControlHandler;

public interface IBoardGamestateControlHandler extends IControlHandler {


    public void handlePress(float screenX, float screenY);

}
