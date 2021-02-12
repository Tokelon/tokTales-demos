package com.tokelon.chess.core.state;

import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.game.state.IControlHandler.EmptyControlHandler;
import com.tokelon.toktales.core.game.state.InjectGameState;

public class BoardGamestateControlHandler extends EmptyControlHandler implements IBoardGamestateControlHandler {


    private ILogger logger;
    private IBoardGamestate gamestate;

    @InjectGameState
    protected void injectGamestate(IBoardGamestate gamestate) {
        this.gamestate = gamestate;
        this.logger = gamestate.getLogging().getLogger(getClass());
    }


    @Override
    public void handlePress(float screenX, float screenY) {
        float worldX = gamestate.getStateRenderer().getViewTransformer().unprojectX(screenX);
        float worldY = gamestate.getStateRenderer().getViewTransformer().unprojectY(screenY);

        IBoardGamescene gamescene = gamestate.getActiveScene();
        IChessboard chessboard = gamescene.getChessboard();

        float chessboardLength = gamescene.getChessboardLength() - gamescene.getChessboardOffset(); // Offset needed to be added somewhere?
        float fieldLength = chessboardLength / chessboard.getSize();

        int fieldX = (int) (worldX / fieldLength);
        int fieldY = (int) (worldY / fieldLength);

        if(!chessboard.isFieldValid(fieldX, fieldY)) {
            logger.debug("Field pressed outside board [{},{}]", fieldX, fieldY);
            return;
        }

        gamescene.selectField(fieldX,  fieldY);
        logger.info("Field pressed [{}, {}]", fieldX, fieldY);
    }

}
