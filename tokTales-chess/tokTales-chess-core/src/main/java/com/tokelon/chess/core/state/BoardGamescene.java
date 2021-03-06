package com.tokelon.chess.core.state;

import com.tokelon.chess.core.entities.Chesspiece;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.entities.IChesspiece;
import com.tokelon.chess.core.logic.IChessGameController;
import com.tokelon.chess.core.logic.IChessEngine;
import com.tokelon.chess.core.logic.IPlayer;
import com.tokelon.chess.core.logic.Player;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyRegistry;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IPoint2i.IMutablePoint2i;
import com.tokelon.toktales.core.game.model.Point2iImpl;
import com.tokelon.toktales.core.game.state.scene.BaseGamescene;
import com.tokelon.toktales.tools.core.registry.IBasicRegistry;

import javax.inject.Inject;
import javax.inject.Provider;

public class BoardGamescene extends BaseGamescene implements IBoardGamescene {


    private float chessboardLength;
    private float chessboardOffset;

    private final IMutablePoint2i fieldSelection = new Point2iImpl(-1 , -1);


    private final IBasicRegistry assetKeyRegistry;
    private final IChessGameController gameController;
    private final Provider<IChessEngine> chessEngineProvider;

    @Inject
    public BoardGamescene(@GlobalAssetKeyRegistry IBasicRegistry assetKeyRegistry, IChessGameController gameController, Provider<IChessEngine> chessEngineProvider) {
        this.assetKeyRegistry = assetKeyRegistry;
        this.gameController = gameController;
        this.chessEngineProvider = chessEngineProvider;
    }


    @Override
    public void onAssign() {
        super.onAssign();


        //IChessEngine whiteChessEngine = chessEngineProvider.get();
        //IPlayer white = new Player(whiteChessEngine);
        IPlayer white = new Player();

        IChessEngine blackChessEngine = chessEngineProvider.get();
        blackChessEngine.initialize();
        IPlayer black = new Player(blackChessEngine);

        gameController.newGame(white, black);
    }

    @Override
    public void onUpdate(long timeMillis) {
        gameController.update(timeMillis);
    }


    @Override
    public IChessboard getChessboard() {
        return gameController.getBoardController().getChessboard();
    }


    @Override
    public float getChessboardLength() {
        return chessboardLength;
    }

    @Override
    public float getChessboardOffset() {
        return chessboardOffset;
    }

    @Override
    public IPoint2i getFieldSelection() {
        return fieldSelection;
    }

    @Override
    public IBitmapAssetKey getAssetKey(String assetKeyName) {
        return assetKeyRegistry.resolveAs(assetKeyName, IBitmapAssetKey.class);
    }

    @Override
    public IBitmapAssetKey getChesspieceAssetKey(IChesspiece chesspiece) {
        return assetKeyRegistry.resolveAs(Chesspiece.keyOf(chesspiece), IBitmapAssetKey.class);
    }


    @Override
    public void setChessboardLength(float length) {
        this.chessboardLength = length;
    }

    @Override
    public void setChessboardOffset(float offset) {
        this.chessboardOffset = offset;
    }

    @Override
    public void setFieldSelection(int fieldX, int fieldY) {
        if(!getChessboard().isFieldValid(fieldX, fieldY)) {
            throw new IllegalArgumentException("Coordinates must be >= 0 and <= chessboard size");
        }

        fieldSelection.set(fieldX, fieldY);
    }

    @Override
    public boolean hasFieldSelection() {
        return fieldSelection.x() >= 0 && fieldSelection.y() >= 0;
    }

    @Override
    public void resetFieldSelection() {
        fieldSelection.set(-1, -1);
    }


    @Override
    public void selectField(int fieldX, int fieldY) {
        if(!getChessboard().isFieldValid(fieldX, fieldY)) {
            throw new IllegalArgumentException("Coordinates must be >= 0 and <= chessboard size");
        }

        if(hasFieldSelection()) {
            if (fieldX == fieldSelection.x() && fieldY == fieldSelection.y()) {
                resetFieldSelection();
            }
            else {
                if(gameController.tryMove(fieldSelection.x(), fieldSelection.y(), fieldX, fieldY, "")) {
                    resetFieldSelection();
                }
            }
        }
        else {
            IChesspiece field = getChessboard().getField(fieldX, fieldY);
            if(field != null && field.getColor() == gameController.getCurrentColor() && !gameController.getCurrentPlayer().isEngine()) {
                setFieldSelection(fieldX, fieldY);
            }
        }
    }

}
