package com.tokelon.chess.core.setup.inject;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.logic.ChessGameController;
import com.tokelon.chess.core.logic.ChesslibBoardController;
import com.tokelon.chess.core.logic.IChessBoardController;
import com.tokelon.chess.core.logic.IChessGameController;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;
import com.tokelon.chess.core.logic.uci.memory.KarballoUCIConnector;

public class EngineInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessGameController.class).to(ChessGameController.class);
        bind(IChessBoardController.class).to(ChesslibBoardController.class);

        bind(IUCIConnectorFactory.class).to(KarballoUCIConnector.KarballoUCIConnectorFactory.class);
    }

}
