package com.tokelon.chess.core.logic.uci.memory;

import com.tokelon.chess.core.logic.uci.IUCIConnector;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;
import com.tokelon.chess.core.tools.ISplitterOutputStream;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import javax.inject.Inject;
import javax.inject.Provider;

import karballo.Config;
import karballo.book.FileBook;
import karballo.log.Logger;
import karballo.search.SearchEngineThreaded;
import karballo.uci.Uci;
import karballo.util.JvmPlatformUtils;
import karballo.util.Utils;

public class KarballoUCIConnector extends AbstractMemoryUCIConnector {


    static {
        Logger.Companion.setNoLog(true);

        Utils.instance = new JvmPlatformUtils();
    }

    private final Uci internalUci;

    private final ILogger logger;

    @Inject
    public KarballoUCIConnector(ILogging logging, ISplitterOutputStream splitterOut, IUCIConnectionSink defaultSink) {
        super(logging, splitterOut, defaultSink);

        this.logger = logging.getLogger(getClass());
        this.internalUci = createInternalUci(new Config());
    }

    protected Uci createInternalUci(Config config) {
        FileBook fileBook = new FileBook("/book_small.bin");
        config.setBook(fileBook);

        return new Uci(config, (conf) -> new SearchEngineThreaded(conf));
    }


    @Override
    public void send(String input) {
        logger.debug("Sending UCI: {}", input);
        internalUci.processLine(input);
    }



    public static final class KarballoUCIConnectorFactory implements IUCIConnectorFactory {


        private final ILogging logging;
        private final Provider<IUCIConnectionSink> defaultSinkProvider;

        @Inject
        public KarballoUCIConnectorFactory(ILogging logging, Provider<IUCIConnectionSink> defaultSinkProvider) {
            this.logging = logging;
            this.defaultSinkProvider = defaultSinkProvider;
        }

        @Override
        public IUCIConnector create(ISplitterOutputStream splitterOut) {
            return new KarballoUCIConnector(logging, splitterOut, defaultSinkProvider.get());
        }
    }

}
