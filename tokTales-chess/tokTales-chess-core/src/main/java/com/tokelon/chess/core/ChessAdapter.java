package com.tokelon.chess.core;

import com.tokelon.chess.core.state.IBoardGamestate;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.engine.inject.annotation.GlobalAssetKeyRegistry;
import com.tokelon.toktales.core.game.EmptyGameAdapter;
import com.tokelon.toktales.tools.core.registry.IBasicRegistry;

import javax.inject.Inject;

public class ChessAdapter extends EmptyGameAdapter {


    private final IBoardGamestate boardGamestate;
    private final IBasicRegistry assetKeyRegistry;

    @Inject
    public ChessAdapter(IBoardGamestate boardGamestate, @GlobalAssetKeyRegistry IBasicRegistry assetKeyRegistry) {
        this.boardGamestate = boardGamestate;
        this.assetKeyRegistry = assetKeyRegistry;
    }


    @Override
    public void onCreate(IEngineContext engineContext) {
        engineContext.getGame().getStateControl().addState("BoardGamestate", boardGamestate);

        engineContext.getGame().getStateControl().changeState("BoardGamestate");

        // TODO: Move into loading gamestate
        preloadAssets(engineContext.getGame().getContentManager().getBitmapAssetManager());
    }

    private void preloadAssets(IBitmapAssetManager bitmapAssetManager) {
        for(Object assetKey: assetKeyRegistry.getEntries().values()) {
            if(assetKey instanceof IBitmapAssetKey) {
                // Enqueue for loading
                IBitmapAssetKey bitmapAssetKey = (IBitmapAssetKey) assetKey;
                bitmapAssetManager.addAssetResult(bitmapAssetManager.getLoader().enqueue(bitmapAssetKey), bitmapAssetKey);
            }
        }
    }

    @Override
    public void onDestroy() {
        System.exit(0); // Destroy executor threads
    }

}
