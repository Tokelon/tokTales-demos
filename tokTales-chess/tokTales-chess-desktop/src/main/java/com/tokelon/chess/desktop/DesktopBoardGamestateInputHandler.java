package com.tokelon.chess.desktop;

import com.tokelon.chess.core.state.IBoardGamestateInputHandler;
import com.tokelon.chess.core.state.IBoardGamestate;
import com.tokelon.toktales.core.game.state.InjectGameState;
import com.tokelon.toktales.desktop.game.state.IDesktopGameStateInputHandler;
import com.tokelon.toktales.desktop.input.TInput;
import com.tokelon.toktales.desktop.input.events.ICursorPosInputEvent;
import com.tokelon.toktales.desktop.input.events.IMouseButtonInputEvent;

public class DesktopBoardGamestateInputHandler implements IBoardGamestateInputHandler, IDesktopGameStateInputHandler {


    private double cursorPositionX;
    private double cursorPositionY;

    private IBoardGamestate gamestate;

    @InjectGameState
    protected void injectGamestate(IBoardGamestate gamestate) {
        this.gamestate = gamestate;
    }


    @Override
    public boolean handleCursorPosInput(ICursorPosInputEvent event) {
        this.cursorPositionX = event.getXPos();
        this.cursorPositionY = event.getYPos();

        return true;
    }

    @Override
    public boolean handleMouseButtonInput(IMouseButtonInputEvent event) {
        if(event.getButton() == TInput.VB_MOUSE_BUTTON_LEFT) {
            if(event.getAction() == TInput.KEY_PRESS) {
                gamestate.getStateControlHandler().handlePress((float) cursorPositionX, (float) cursorPositionY);
                return true;
            }
        }

        return false;
    }

}
