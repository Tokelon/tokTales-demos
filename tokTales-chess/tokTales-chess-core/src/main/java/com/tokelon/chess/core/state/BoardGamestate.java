package com.tokelon.chess.core.state;

import com.tokelon.chess.core.ChessboardOffset;
import com.tokelon.chess.core.render.IBoardRenderer;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.state.BaseGamestate;

import javax.inject.Inject;

public class BoardGamestate extends BaseGamestate<IBoardGamescene> implements IBoardGamestate {


    private final IBoardRenderer boardRenderer;
    private final Float chessboardOffset;

    @Inject
    public BoardGamestate(
            IBoardRenderer boardRenderer,
            IBoardGamestateInputHandler gamestateInputHandler,
            IBoardGamestateControlHandler gamestateControlHandler,
            @ChessboardOffset Float chessboardOffset
    ) {
        super(null, gamestateInputHandler, null, gamestateControlHandler);

        this.boardRenderer = boardRenderer;
        this.chessboardOffset = chessboardOffset;
    }


    @Override
    public void onEngage() {
        super.onEngage();

        // Use the initial scene for the board
        IBoardGamescene activeScene = getActiveScene();

        // Setup our camera
        ICamera sceneCamera = activeScene.getSceneCamera();
        // Our camera will record from 0 to 100 units
        sceneCamera.setSize(100f, 100f);
        // Moves the camera into the positive coordinate space. Technically not needed.
        sceneCamera.setWorldCoordinates(sceneCamera.getWidth() / 2f, sceneCamera.getHeight() / 2f);

        // Setup our board to cover the whole camera
        activeScene.setChessboardLength(100f);
        activeScene.setChessboardOffset(chessboardOffset);


        getStateRenderer().getContextManager().addContext(boardRenderer);
        getGamestateInjector().injectInto(boardRenderer);

        getStateRenderer().getRenderOrder().insertLayerAt(10, "Board");
        getStateRenderer().getRenderOrder().registerCall("Board", 1, () -> boardRenderer.renderContents(null));
    }


    @Override
    public IBoardGamestateControlHandler getStateControlHandler() {
        return (IBoardGamestateControlHandler) super.getStateControlHandler();
    }

}
