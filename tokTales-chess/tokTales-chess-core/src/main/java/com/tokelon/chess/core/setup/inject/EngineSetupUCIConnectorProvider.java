package com.tokelon.chess.core.setup.inject;

import com.google.inject.ProvisionException;
import com.tokelon.chess.core.setup.ISysoutSplitterSetupStep;
import com.tokelon.chess.core.logic.uci.IUCIConnector;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;
import com.tokelon.chess.core.tools.ISplitterOutputStream;
import com.tokelon.toktales.core.engine.setup.IEngineSetup;
import com.tokelon.toktales.core.engine.setup.ISetupStep;

import javax.inject.Inject;
import javax.inject.Provider;

public class EngineSetupUCIConnectorProvider implements Provider<IUCIConnector> {


    private final IUCIConnectorFactory uciConnectorFactory;
    private final IEngineSetup engineSetup;

    @Inject
    public EngineSetupUCIConnectorProvider(IUCIConnectorFactory uciConnectorFactory, IEngineSetup engineSetup) {
        this.uciConnectorFactory = uciConnectorFactory;
        this.engineSetup = engineSetup;
    }


    @Override
    public IUCIConnector get() {
        ISetupStep setupStep = engineSetup.getSteps().getStep(ISysoutSplitterSetupStep.DEFAULT_SYSOUT_SPLITTER_SETUP_STEP_NAME);
        if(!(setupStep instanceof ISysoutSplitterSetupStep)) {
            throw new ProvisionException(String.format("Cannot create %s: No step for %s", IUCIConnector.class.getName(), ISysoutSplitterSetupStep.class.getName()));
        }

        ISysoutSplitterSetupStep sysoutSplitterSetupStep = (ISysoutSplitterSetupStep) setupStep;

        ISplitterOutputStream splitterOutStream = sysoutSplitterSetupStep.getSplitterOutStream();
        return uciConnectorFactory.create(splitterOutStream);
    }

}
