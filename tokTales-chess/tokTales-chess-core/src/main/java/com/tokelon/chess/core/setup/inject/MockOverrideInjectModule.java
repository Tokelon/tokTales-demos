package com.tokelon.chess.core.setup.inject;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.logic.IChessBoardController;
import com.tokelon.chess.core.logic.IChessController;
import com.tokelon.chess.core.logic.mock.MockChessBoardController;
import com.tokelon.chess.core.logic.mock.MockChessController;
import com.tokelon.chess.core.logic.mock.MockUCIChessEngine;
import com.tokelon.chess.core.logic.mock.MockUCIConnector;
import com.tokelon.chess.core.logic.uci.IUCIChessEngine;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;

public class MockOverrideInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessController.class).to(MockChessController.class);
        bind(IChessBoardController.class).to(MockChessBoardController.class);
        bind(IUCIChessEngine.class).to(MockUCIChessEngine.class);
        bind(IUCIConnectorFactory.class).to(MockUCIConnector.MockUCIConnectorFactory.class);
    }

}
