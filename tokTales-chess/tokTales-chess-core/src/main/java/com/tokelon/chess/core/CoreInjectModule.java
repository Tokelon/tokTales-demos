package com.tokelon.chess.core;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.tokelon.chess.core.entities.Chesspiece;
import com.tokelon.chess.core.entities.ChesspieceColor;
import com.tokelon.chess.core.entities.ChesspieceType;
import com.tokelon.chess.core.logic.ChesslibEngine;
import com.tokelon.chess.core.logic.IChessEngine;
import com.tokelon.chess.core.render.BoardRenderer;
import com.tokelon.chess.core.render.IBoardRenderer;
import com.tokelon.chess.core.state.BoardGamescene;
import com.tokelon.chess.core.state.BoardGamestate;
import com.tokelon.chess.core.state.BoardGamestateControlHandler;
import com.tokelon.chess.core.state.IBoardGamescene;
import com.tokelon.chess.core.state.IBoardGamestate;
import com.tokelon.chess.core.state.IBoardGamestateControlHandler;
import com.tokelon.toktales.core.content.manage.bitmap.ReadDelegateBitmapAssetKey;
import com.tokelon.toktales.core.engine.inject.annotation.ContentRoot;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyAliases;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyEntries;
import com.tokelon.toktales.tools.assets.files.RelativeFileKey;

import java.io.File;

public class CoreInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessEngine.class).to(ChesslibEngine.class);

        bind(IBoardGamestate.class).to(BoardGamestate.class);
        bind(IBoardGamescene.class).to(BoardGamescene.class);
        bind(IBoardRenderer.class).to(BoardRenderer.class);
        bind(IBoardGamestateControlHandler.class).to(BoardGamestateControlHandler.class);

        configureAssets();
    }

    protected void configureAssets() {
        MapBinder<Object, Object> globalAssetKeyEntriesBinder = MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyEntries.class);
        MapBinder<Object, Object> globalAssetKeyAliasesBinder = MapBinder.newMapBinder(binder(), Object.class, Object.class, GlobalAssetKeyAliases.class);


        bind(Float.class).annotatedWith(ChessboardOffset.class).toInstance(0.25f);

        configureAsset(globalAssetKeyEntriesBinder, IBoardGamescene.ASSET_KEY_SELECTION, "graphics/selection gray dark _2x.png", ContentRoot.class);
        configureAsset(globalAssetKeyEntriesBinder, IBoardGamescene.ASSET_KEY_SQUARE_DARK, "graphics/square brown dark_2x.png", ContentRoot.class);
        configureAsset(globalAssetKeyEntriesBinder, IBoardGamescene.ASSET_KEY_SQUARE_LIGHT, "graphics/square brown light_2x.png", ContentRoot.class);

        for(ChesspieceColor color: ChesspieceColor.values()) {
            for(ChesspieceType type: ChesspieceType.values()) {
                String key = "graphics/" + color.toString().toLowerCase().charAt(0) + "_" + type.toString().toLowerCase() + "_2x.png";

                File chesspieceAssetFile = new File(key);
                RelativeFileKey chesspieceAssetKey = new RelativeFileKey(chesspieceAssetFile, ContentRoot.class);

                globalAssetKeyEntriesBinder.addBinding(key).toInstance(new ReadDelegateBitmapAssetKey(chesspieceAssetKey));
                globalAssetKeyAliasesBinder.addBinding(Chesspiece.keyOf(color, type)).toInstance(key);
            }
        }
    }

    protected void configureAsset(MapBinder<Object, Object> globalAssetKeyEntriesBinder, String name, String path, Object parentIdentifier) {
        File assetFile = new File(path);
        RelativeFileKey assetFileKey = new RelativeFileKey(assetFile, parentIdentifier);
        globalAssetKeyEntriesBinder.addBinding(name).toInstance(new ReadDelegateBitmapAssetKey(assetFileKey));
    }

}
