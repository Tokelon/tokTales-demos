package com.tokelon.chess.core.setup.inject;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.logic.ChessController;
import com.tokelon.chess.core.logic.ChesslibEngine;
import com.tokelon.chess.core.logic.IChessController;
import com.tokelon.chess.core.logic.IChessEngine;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;
import com.tokelon.chess.core.logic.uci.memory.KarballoUCIConnector;

public class EngineInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessEngine.class).to(ChesslibEngine.class);
        bind(IUCIConnectorFactory.class).to(KarballoUCIConnector.KarballoUCIConnectorFactory.class);

        bind(IChessController.class).to(ChessController.class);
    }

}
