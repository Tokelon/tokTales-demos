package com.tokelon.chess.core.state;

import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.entities.IChesspiece;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.state.scene.IGameScene;
import com.tokelon.toktales.tools.core.registry.IBasicRegistry;
import com.tokelon.toktales.tools.core.registry.IStringRegistry;

public interface IBoardGamescene extends IGameScene {


    public static final String ASSET_KEY_SELECTION = "asset_key_selection";
    public static final String ASSET_KEY_SQUARE_DARK = "asset_key_square_dark";
    public static final String ASSET_KEY_SQUARE_LIGHT = "asset_key_square_light";


    public IChessboard getChessboard();

    /**
     * @return The size of each of the chessboard's edges.
     */
    public float getChessboardLength();

    /** The offset is the length from the top left of the chessboard asset,
     * to the top left corner of the first field.
     *
     * @return The size of the border, or 0 if there is none.
     */
    public float getChessboardOffset();

    public IPoint2i getFieldSelection();


    public IBitmapAssetKey getAssetKey(String assetKeyName);

    public IBitmapAssetKey getChesspieceAssetKey(IChesspiece chesspiece);



    public void setChessboardLength(float length);

    public void setChessboardOffset(float offset);


    public void setFieldSelection(int fieldX, int fieldY);
    public boolean hasFieldSelection();
    public void resetFieldSelection();


    public void selectField(int fieldX, int fieldY);

}
