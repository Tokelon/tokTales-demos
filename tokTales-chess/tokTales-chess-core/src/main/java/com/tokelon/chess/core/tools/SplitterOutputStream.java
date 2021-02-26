package com.tokelon.chess.core.tools;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SplitterOutputStream extends OutputStream implements ISplitterOutputStream {


    private final List<OutputStream> streams;
    private final List<OutputStream> streamsUnmodifiable;

    public SplitterOutputStream(OutputStream... streams) {
        this(Arrays.asList(streams));
    }

    public SplitterOutputStream(List<OutputStream> streams) {
        this.streams = new ArrayList<>(streams);
        this.streamsUnmodifiable = Collections.unmodifiableList(streams);
    }


    @Override
    public void write(int i) throws IOException {
        for(OutputStream out: streams) {
            out.write(i);
        }
    }

    @Override
    public void flush() throws IOException {
        for(OutputStream out: streams) {
            out.flush();
        }
    }

    @Override
    public void close() throws IOException {
        for(OutputStream out: streams) {
            out.close();
        }

        streams.clear();
    }


    @Override
    public boolean addStream(OutputStream stream) {
        if(hasStream(stream)) {
            return false;
        }

        return streams.add(stream);
    }

    @Override
    public boolean hasStream(OutputStream stream) {
        return streams.contains(stream);
    }

    @Override
    public boolean removeStream(OutputStream stream) {
        return streams.remove(stream);
    }

    @Override
    public List<OutputStream> getStreams() {
        return streamsUnmodifiable;
    }

}
