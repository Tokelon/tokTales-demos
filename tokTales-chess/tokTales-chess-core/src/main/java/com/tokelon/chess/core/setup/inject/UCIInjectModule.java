package com.tokelon.chess.core.setup.inject;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.logic.IChessEngine;
import com.tokelon.chess.core.logic.uci.IUCI;
import com.tokelon.chess.core.logic.uci.IUCIChessEngine;
import com.tokelon.chess.core.logic.uci.IUCIConnector;
import com.tokelon.chess.core.logic.uci.UCI;
import com.tokelon.chess.core.logic.uci.UCIChessEngine;
import com.tokelon.chess.core.logic.uci.memory.IUCIConnectionSink;
import com.tokelon.chess.core.logic.uci.memory.QueueUCIConnectionSink;

public class UCIInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IChessEngine.class).to(IUCIChessEngine.class);
        bind(IUCIChessEngine.class).to(UCIChessEngine.class);

        bind(IUCI.class).to(UCI.class);

        bind(IUCIConnector.class).toProvider(EngineSetupUCIConnectorProvider.class);

        bind(IUCIConnectionSink.class).to(QueueUCIConnectionSink.class);
    }

}
