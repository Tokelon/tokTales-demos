package com.tokelon.chess.android;

import com.tokelon.chess.core.ChessAdapter;
import com.tokelon.chess.core.setup.ISysoutSplitterSetupStep;
import com.tokelon.chess.core.setup.SysoutSplitterSetupStep;
import com.tokelon.chess.core.setup.inject.CoreChessInjectConfig;
import com.tokelon.toktales.android.application.TokTalesApp;
import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
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
        return new MasterAndroidInjectConfig()
                .extend(new CoreChessInjectConfig())
                .extend(new AndroidChessInjectConfig());
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
