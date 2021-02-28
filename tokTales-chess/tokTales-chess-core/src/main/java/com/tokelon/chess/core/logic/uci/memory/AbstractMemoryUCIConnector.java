package com.tokelon.chess.core.logic.uci.memory;

import com.tokelon.chess.core.logic.uci.IUCIConnector;
import com.tokelon.chess.core.tools.ISplitterOutputStream;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import java.io.IOException;
import java.util.List;

import java9.util.stream.Stream;
import java9.util.stream.StreamSupport;

public abstract class AbstractMemoryUCIConnector implements IUCIConnector {


    private final ILogger logger;
    private final IUCIConnectionSink sink;
    private final ISplitterOutputStream splitterOut;

    public AbstractMemoryUCIConnector(ILogging logging, ISplitterOutputStream splitterOut, IUCIConnectionSink defaultSink) {
        this.logger = logging.getLogger(getClass());
        this.splitterOut = splitterOut;

        this.sink = defaultSink;
        splitterOut.addStream(sink.getOutputStream());
    }


    @Override
    public Stream<String> send(String input, String until) {
        send(input);
        return receive(until);
    }

    @Override
    public Stream<String> receive(String until) {
        List<String> result = sink.receiveUntilLineContains(until);
        return getLines(result);
    }


    @Override
    public void close() throws IOException {
        splitterOut.removeStream(sink.getOutputStream());
        sink.getOutputStream().close();
    }


    @Override
    public Stream<String> readInput() {
        return getLines(sink.readInputLines());
    }

    protected Stream<String> getLines(List<String> lines) {
        if(logger.isDebugEnabled()) {
            for(String line: lines) {
                if(!line.trim().isEmpty()) {
                    logger.debug("Read UCI: {}", line.trim());
                }
            }
        }

        Stream<String> result = StreamSupport.stream(lines).map(s -> s.trim());
        return result;
    }

}
