package com.tokelon.chess.desktop;

import com.tokelon.chess.core.ChessAdapter;
import com.tokelon.chess.core.CoreInjectModule;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.desktop.application.TokTalesApplication;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.desktop.ui.window.IWindowConfigurator;
import com.tokelon.toktales.tools.core.sub.inject.config.IHierarchicalInjectConfig;

public class ChessApplication extends TokTalesApplication {


    @Override
    public IWindowBuilder makeDefaultWindowBuilder() {
        return getWindowFactory().createBuilder(1000, 1000, "tokTales Chess");
    }

    @Override
    public IWindowConfigurator makeDefaultWindowConfigurator() {
        // Override the default so the window is resizable
        return (window, windowToolkit) -> {};
    }

    @Override
    public Class<? extends IGameAdapter> makeDefaultGameAdapter() {
        return ChessAdapter.class;
    }

    @Override
    public IHierarchicalInjectConfig makeDefaultInjectConfig() {
        return super.makeDefaultInjectConfig()
                .extend(new CoreInjectModule())
                .extend(new DesktopInjectModule());
    }


    public static void main(String[] args) throws EngineException {
        new ChessApplication().run(args);
    }

}
