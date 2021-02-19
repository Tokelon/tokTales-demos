package com.tokelon.chess.core.state;

import com.tokelon.chess.core.entities.Chessboard;
import com.tokelon.chess.core.entities.Chesspiece;
import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.entities.IChesspiece;
import com.tokelon.chess.core.logic.IChessEngine;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyRegistry;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.model.IPoint2i.IMutablePoint2i;
import com.tokelon.toktales.core.game.model.Point2iImpl;
import com.tokelon.toktales.core.game.state.scene.BaseGamescene;
import com.tokelon.toktales.tools.core.registry.IBasicRegistry;

import javax.inject.Inject;

public class BoardGamescene extends BaseGamescene implements IBoardGamescene {


    private float chessboardLength;
    private float chessboardOffset;

    private ChesspieceColor currentColor = ChesspieceColor.WHITE;
    private ChesspieceColor playerColor = ChesspieceColor.WHITE;

    private final IMutablePoint2i fieldSelection = new Point2iImpl(-1 , -1);

    private final Chessboard chessboard = Chessboard.createInitial();

    private final ILogger logger;
    private final IBasicRegistry assetKeyRegistry;
    private final IChessEngine chessEngine;

    @Inject
    public BoardGamescene(ILogging logging, @GlobalAssetKeyRegistry IBasicRegistry assetKeyRegistry, IChessEngine chessEngine) {
        this.logger = logging.getLogger(getClass());
        this.assetKeyRegistry = assetKeyRegistry;
        this.chessEngine = chessEngine;
    }


    @Override
    public void onStart() {
        if(playerColor == ChesspieceColor.BLACK) {
            startAITurn();
        }
    }

    @Override
    public void onUpdate(long timeMillis) {
        String nextMove = chessEngine.getAI().getAndResetNextMove();
        if(nextMove != null) {
            engineMove(nextMove);
            startNextTurn();
        }
    }


    @Override
    public IChessboard getChessboard() {
        return chessboard;
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
    public ChesspieceColor getCurrentPlayer() {
        return currentColor;
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
        if(!chessboard.isFieldValid(fieldX, fieldY)) {
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
        if(!chessboard.isFieldValid(fieldX, fieldY)) {
            throw new IllegalArgumentException("Coordinates must be >= 0 and <= chessboard size");
        }

        if(hasFieldSelection() ) {
            if (fieldX == fieldSelection.x() && fieldY == fieldSelection.y()) {
                resetFieldSelection();
            }
            else {
                if(playerMove(fieldSelection.x(), fieldSelection.y(), fieldX, fieldY)) {
                    resetFieldSelection();
                    startNextTurn();
                }
            }
        }
        else {
            IChesspiece field = getChessboard().getField(fieldX, fieldY);
            if(field != null && field.getColor() == currentColor) { // TODO: playerColor
                setFieldSelection(fieldX, fieldY);
            }
        }
    }


    public void startNextTurn() {
        currentColor = currentColor == ChesspieceColor.WHITE ? ChesspieceColor.BLACK : ChesspieceColor.WHITE;

        if(currentColor != playerColor) {
            startAITurn();
        }
        else {
            startPlayerTurn();
        }
    }

    public void startPlayerTurn() {
        // Nothing yet
    }

    public void startAITurn() {
        chessEngine.getAI().startNextMove();
    }


    public boolean playerMove(int fromX, int fromY, int toX, int toY) {
        String from = chessboard.fieldToNotationX(fromX) + Byte.toString(chessboard.fieldToNotationY(fromY));
        String to = chessboard.fieldToNotationX(toX) + Byte.toString(chessboard.fieldToNotationY(toY));
        logger.info("Move from {} to {}", from, to);

        if(chessEngine.doMove(from, to)) {
            getChessboard().movePiece(fromX, fromY, toX, toY);

            logger.info("Move completed from [{}, {}] to [{}, {}]", fromX, fromY, toX, toY);
            return true;
        }
        else {
            logger.info("Move failed from [{}, {}] to [{}, {}]", fromX, fromY, toX, toY);
            return false;
        }
    }

    public boolean engineMove(String move) {
        int fromX = chessboard.notationToFieldX(move.charAt(0));
        int fromY = chessboard.notationToFieldY(Byte.parseByte(String.valueOf(move.charAt(1))));
        int toX = chessboard.notationToFieldX(move.charAt(2));
        int toY = chessboard.notationToFieldY(Byte.parseByte(String.valueOf(move.charAt(3))));

        return playerMove(fromX, fromY, toX, toY);
    }

}
