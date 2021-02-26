package com.tokelon.chess.core.logic.mock;

import com.tokelon.chess.core.logic.uci.IUCIConnector;
import com.tokelon.chess.core.logic.uci.IUCIConnectorFactory;
import com.tokelon.chess.core.tools.ISplitterOutputStream;

import java.io.IOException;

import java9.util.stream.Stream;

public class MockUCIConnector implements IUCIConnector {


    @Override
    public void send(String input) {
    }

    @Override
    public String send(String input, String until) {
        return null;
    }

    @Override
    public String receive(String until) {
        return null;
    }

    @Override
    public Stream<String> getInputLines() {
        return null;
    }

    @Override
    public void close() throws IOException {
    }



    public static class MockUCIConnectorFactory implements IUCIConnectorFactory {

        @Override
        public IUCIConnector create(ISplitterOutputStream splitterOut) {
            return new MockUCIConnector();
        }
    }

}
