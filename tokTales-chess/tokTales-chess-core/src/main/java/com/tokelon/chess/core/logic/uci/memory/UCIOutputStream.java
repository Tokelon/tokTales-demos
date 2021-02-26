package com.tokelon.chess.core.logic.uci.memory;

import java.io.OutputStream;

public class UCIOutputStream extends OutputStream implements IUCIOutputStream {


    private StringBuilder builder;

    public UCIOutputStream() {
        this.builder = new StringBuilder();
    }


    @Override
    public StringBuilder getBuilder() {
        return builder;
    }

    @Override
    public OutputStream getUnderlyingStream() {
        return this;
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
