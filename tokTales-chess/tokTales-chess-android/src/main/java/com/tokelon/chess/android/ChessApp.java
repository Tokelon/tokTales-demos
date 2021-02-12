package com.tokelon.chess.android;

import com.tokelon.chess.core.ChessAdapter;
import com.tokelon.chess.core.CoreInjectModule;
import com.tokelon.toktales.android.application.TokTalesApp;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class ChessApp extends TokTalesApp {


    @Override
    public Class<? extends IGameAdapter> makeDefaultGameAdapter() {
        return ChessAdapter.class;
    }

    @Override
    public IHierarchicalInjectConfig makeDefaultInjectConfig() {
        return super.makeDefaultInjectConfig()
                .extend(new CoreInjectModule())
                .extend(new AndroidInjectModule());
    }

}
