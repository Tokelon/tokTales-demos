package com.tokelon.chess.android;

import com.tokelon.chess.core.ChessAdapter;
import com.tokelon.chess.core.CoreInjectModule;
import com.tokelon.chess.core.ISysoutSplitterSetupStep;
import com.tokelon.chess.core.SysoutSplitterSetupStep;
import com.tokelon.toktales.android.application.TokTalesApp;
import com.tokelon.toktales.core.engine.setup.DefaultEngineSetup;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
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

    @Override
    public IEngineSetup makeDefaultEngineSetup() {
        IEngineSetup engineSetup = super.makeDefaultEngineSetup();

        // Remove this step because we replace stdout
        engineSetup.getSteps().removeStep(DefaultEngineSetup.SETUP_STEP_REDIRECT_SYSTEM_OUTPUT);
        // Insert stdout splitter step
        engineSetup.getSteps().insertStep(ISysoutSplitterSetupStep.DEFAULT_SYSOUT_SPLITTER_SETUP_STEP_NAME, new SysoutSplitterSetupStep());

        return engineSetup;
    }

}
