package com.tokelon.chess.desktop;

import com.tokelon.chess.core.ChessAdapter;
import com.tokelon.chess.core.setup.ISysoutSplitterSetupStep;
import com.tokelon.chess.core.setup.SysoutSplitterSetupStep;
import com.tokelon.chess.core.setup.inject.CoreChessInjectConfig;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.desktop.application.TokTalesApplication;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
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
        return new MasterDesktopInjectConfig()
                .extend(new CoreChessInjectConfig())
                .extend(new DesktopChessInjectConfig());
    }

    @Override
    public IEngineSetup makeDefaultEngineSetup() {
        IEngineSetup engineSetup = super.makeDefaultEngineSetup();

        // Remove this step because we replace stdout
        engineSetup.getSteps().removeStep(DefaultEngineSetup.SETUP_STEP_REDIRECT_SYSTEM_OUTPUT);
        // Insert stdout splitter step
        engineSetup.getSteps().insertStep(ISysoutSplitterSetupStep.DEFAULT_SYSOUT_SPLITTER_SETUP_STEP_NAME, new SysoutSplitterSetupStep());

        return engineSetup;
    }


    public static void main(String[] args) throws EngineException {
        new ChessApplication().run(args);
    }

}
