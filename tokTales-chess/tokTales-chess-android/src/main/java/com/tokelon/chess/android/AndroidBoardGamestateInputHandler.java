package com.tokelon.chess.android;

import com.tokelon.chess.core.state.IBoardGamestate;
import com.tokelon.chess.core.state.IBoardGamestateInputHandler;
import com.tokelon.toktales.android.game.state.IAndroidGameStateInputHandler;
import com.tokelon.toktales.android.input.events.IScreenPressInputEvent;
import com.tokelon.toktales.core.game.state.InjectGameState;

public class AndroidBoardGamestateInputHandler implements IBoardGamestateInputHandler, IAndroidGameStateInputHandler {


    private IBoardGamestate gamestate;

    @InjectGameState
    protected void injectGamestate(IBoardGamestate gamestate) {
        this.gamestate = gamestate;
    }


    @Override
    public boolean handleScreenPressInput(IScreenPressInputEvent event) {
        gamestate.getStateControlHandler().handlePress((float) event.getXPos(), (float) event.getYPos());
        return true;
    }

}
