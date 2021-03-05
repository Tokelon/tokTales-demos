package com.tokelon.chess.core.setup.inject;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.logic.IChessController;
import com.tokelon.chess.core.logic.mock.MockChessController;
import com.tokelon.chess.core.logic.mock.MockUCIChessAI;
import com.tokelon.chess.core.logic.mock.MockUCIConnector;
import com.tokelon.chess.core.logic.uci.IUCIChessAI;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;

public class MockOverrideInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessController.class).to(MockChessController.class);
        bind(IUCIChessAI.class).to(MockUCIChessAI.class);
        bind(IUCIConnectorFactory.class).to(MockUCIConnector.MockUCIConnectorFactory.class);
    }

}
