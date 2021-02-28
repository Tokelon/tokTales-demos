package com.tokelon.chess.core.setup.inject;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.logic.IChessAI;
import com.tokelon.chess.core.logic.uci.IUCI;
import com.tokelon.chess.core.logic.uci.IUCIChessAI;
import com.tokelon.chess.core.logic.uci.IUCIConnector;
import com.tokelon.chess.core.logic.uci.UCI;
import com.tokelon.chess.core.logic.uci.UCIChessAI;
import com.tokelon.chess.core.logic.uci.memory.BuilderUCIConnectionSink;
import com.tokelon.chess.core.logic.uci.memory.IUCIConnectionSink;

public class UCIInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessAI.class).to(IUCIChessAI.class);
        bind(IUCIChessAI.class).to(UCIChessAI.class);

        bind(IUCI.class).to(UCI.class);

        bind(IUCIConnector.class).toProvider(EngineSetupUCIConnectorProvider.class);

        bind(IUCIConnectionSink.class).to(BuilderUCIConnectionSink.class); // Not very efficient memory wise
    }

}
