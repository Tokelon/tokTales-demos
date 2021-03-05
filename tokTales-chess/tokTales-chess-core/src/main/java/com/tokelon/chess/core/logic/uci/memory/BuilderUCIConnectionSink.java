package com.tokelon.chess.core.logic.uci.memory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuilderUCIConnectionSink extends OutputStream implements IUCIConnectionSink {
    // Make inner class for outputstream?


    private StringBuilder builder;

    public BuilderUCIConnectionSink() {
        this.builder = new StringBuilder();
    }

    @Override
    public List<String> receiveUntilLineContains(String terminator) {
        String input = readFromBuilder(terminator, getBuilder());
        return splitIntoLines(input);
    }

    @Override
    public List<String> readInputLines() {
        String input = getBuilder().toString();
        List<String> lines = splitIntoLines(input);

        getBuilder().delete(0, getBuilder().length());
        return lines;
    }

    @Override
    public OutputStream getOutputStream() {
        return this;
    }


    protected String readFromBuilder(String until, StringBuilder builder) {
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

    protected List<String> splitIntoLines(String value) {
        String[] lines = value.split("\n"); // Check for \r as well?
        List<String> result = new ArrayList<>(Arrays.asList(lines));
        return result;
    }

    protected StringBuilder getBuilder() {
        return builder;
    }


    @Override
    public void write(int i) {
        if(builder == null) {
            throw new IllegalStateException("Stream already closed");
        }

        builder.append((char)i); // TODO: No support for UNICODE
    }

    @Override
    public void flush() {
        // Nothing to do
    }

    @Override
    public void close() {
        this.builder = null;
    }

}
