package com.tokelon.chess.core.state;

import com.tokelon.chess.core.entities.Chesspiece;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.entities.IChesspiece;
import com.tokelon.chess.core.logic.IChessController;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyRegistry;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IPoint2i.IMutablePoint2i;
import com.tokelon.toktales.core.game.model.Point2iImpl;
import com.tokelon.toktales.core.game.state.scene.BaseGamescene;
import com.tokelon.toktales.tools.core.registry.IBasicRegistry;

import javax.inject.Inject;

public class BoardGamescene extends BaseGamescene implements IBoardGamescene {


    private float chessboardLength;
    private float chessboardOffset;

    private final IMutablePoint2i fieldSelection = new Point2iImpl(-1 , -1);


    private final IBasicRegistry assetKeyRegistry;
    private final IChessController chessController;

    @Inject
    public BoardGamescene(@GlobalAssetKeyRegistry IBasicRegistry assetKeyRegistry, IChessController chessController) {
        this.assetKeyRegistry = assetKeyRegistry;
        this.chessController = chessController;
    }


    @Override
    public void onAssign() {
        super.onAssign();

        chessController.initialize();

        chessController.newGame();
    }

    @Override
    public void onUpdate(long timeMillis) {
        chessController.update(timeMillis);
    }


    @Override
    public IChessboard getChessboard() {
        return chessController.getChessboard();
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

        if(hasFieldSelection() ) {
            if (fieldX == fieldSelection.x() && fieldY == fieldSelection.y()) {
                resetFieldSelection();
            }
            else {
                if(chessController.tryMove(fieldSelection.x(), fieldSelection.y(), fieldX, fieldY, "")) {
                    resetFieldSelection();
                }
            }
        }
        else {
            IChesspiece field = getChessboard().getField(fieldX, fieldY);
            if(field != null && field.getColor() == chessController.getPlayerColor()) { // currentColor
                setFieldSelection(fieldX, fieldY);
            }
        }
    }

}
