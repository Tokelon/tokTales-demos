package com.tokelon.chess.core.state;

import com.tokelon.chess.core.setup.inject.ChessboardOffset;
import com.tokelon.chess.core.render.IBoardRenderer;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.state.BaseGamestate;
import com.tokelon.toktales.core.screen.surface.ISurface;
import com.tokelon.toktales.core.screen.view.IScreenViewport;

import javax.inject.Inject;

public class BoardGamestate extends BaseGamestate<IBoardGamescene> implements IBoardGamestate {


    protected final float CHESSBOARD_LENGTH = 100f;

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

        // Using the initial scene for the board
        IBoardGamescene activeScene = getActiveScene();

        // Setup our board to cover the whole camera
        activeScene.setChessboardLength(CHESSBOARD_LENGTH);

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


    @Override
    protected void onSurfaceChanged(ISurface surface) {
        IScreenViewport viewport = surface.getViewport();

        // Setup our camera
        IBoardGamescene activeScene = getActiveScene();
        ICamera sceneCamera = activeScene.getSceneCamera();

        // Set the base size, [0 - 100] so we can work in percent
        sceneCamera.setSize(100f, 100f);

        // Adjust for screen size
        if(viewport.getOrientation() == IScreenViewport.ORIENTATION_PORTRAIT) {
            float aspectRatio = viewport.getHeight() / viewport.getWidth();
            sceneCamera.setSize(sceneCamera.getWidth(), sceneCamera.getHeight() * aspectRatio);
        }
        else { // landscape mode
            float aspectRatio = viewport.getWidth() / viewport.getHeight();
            sceneCamera.setSize(sceneCamera.getWidth() * aspectRatio, sceneCamera.getHeight());
        }

        this.getLogger().debug("Camera size changed to [{}, {}]", sceneCamera.getWidth(), sceneCamera.getHeight());


        // Center camera on the chessboard
        float chessboardLength = activeScene.getChessboardLength() + activeScene.getChessboardOffset();
        float worldX = sceneCamera.getWidth() / 2f - (sceneCamera.getWidth() - chessboardLength) / 2f;
        float worldY = sceneCamera.getHeight() / 2f - (sceneCamera.getHeight() - chessboardLength) / 2f;
        sceneCamera.setWorldCoordinates(worldX, worldY);

        this.getLogger().debug("Camera position changed to [{}, {}]", sceneCamera.getWorldX(), sceneCamera.getWorldY());
    }

}
