package com.tokelon.chess.core;

import com.tokelon.chess.core.tools.ISplitterOutputStream;
import com.tokelon.toktales.core.engine.setup.ISetupStep;

public interface ISysoutSplitterSetupStep extends ISetupStep {


    public static final String DEFAULT_SYSOUT_SPLITTER_SETUP_STEP_NAME = "DEFAULT_SYSOUT_SPLITTER_SETUP_STEP_NAME";


    public ISplitterOutputStream getSplitterOutStream();

}
