package com.tokelon.chess.core.logic.uci.memory;

import com.tokelon.chess.core.logic.uci.IUCIConnector;
import com.tokelon.chess.core.tools.ISplitterOutputStream;
import com.tokelon.toktales.core.engine.log.ILogger;
import com.tokelon.toktales.core.engine.log.ILogging;

import java.io.IOException;

import java9.util.stream.Stream;

public abstract class AbstractMemoryUCIConnector implements IUCIConnector {


    private final IUCIOutputStream stream;

    private final ILogger logger;
    private final ISplitterOutputStream splitterOut;

    public AbstractMemoryUCIConnector(ILogging logging, ISplitterOutputStream splitterOut) {
        this.logger = logging.getLogger(getClass());
        this.splitterOut = splitterOut;

        this.stream = createUCIOutputStream(splitterOut);
    }

    protected IUCIOutputStream createUCIOutputStream(ISplitterOutputStream splitterOut) {
        UCIOutputStream uciOutputStream = new UCIOutputStream();
        splitterOut.addStream(uciOutputStream);

        return uciOutputStream;
    }


    @Override
    public String send(String input, String until) {
        send(input);
        return receive(until);
    }

    @Override
    public String receive(String until) {
        String result = receiveFromBuilder(until, stream.getBuilder());

        logger.debug("Received UCI: {}", result);
        return result;
    }


    protected String receiveFromBuilder(String until, StringBuilder builder) {
        String line = null;
        do {
            int index = builder.indexOf(until);
            if(index != -1) {
                int end = index + until.length();
                line = builder.substring(0, end);
            }
        }
        while(line == null);

        builder.delete(0, line.length());
        return line;
    }


    @Override
    public void close() throws IOException {
        splitterOut.removeStream(stream.getUnderlyingStream());
        stream.getUnderlyingStream().close();
    }


    @Override
    public Stream<String> getInputLines() {
        String[] lines = this.stream.getBuilder().toString().split("\n");
        for(String line: lines) {
            if(!line.trim().isEmpty()) {
                logger.debug("Read UCI: {}", line.trim());
            }
        }

        Stream<String> result = Stream.of(lines).map(s -> s.trim());
        stream.getBuilder().delete(0, stream.getBuilder().length());
        return result;
    }

}
