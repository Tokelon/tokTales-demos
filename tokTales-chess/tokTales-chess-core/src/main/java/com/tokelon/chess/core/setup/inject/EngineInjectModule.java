package com.tokelon.chess.core.setup.inject;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.logic.ChesslibController;
import com.tokelon.chess.core.logic.IChessController;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;
import com.tokelon.chess.core.logic.uci.memory.KarballoUCIConnector;

public class EngineInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessController.class).to(ChesslibController.class);

        bind(IUCIConnectorFactory.class).to(KarballoUCIConnector.KarballoUCIConnectorFactory.class);
    }

}
