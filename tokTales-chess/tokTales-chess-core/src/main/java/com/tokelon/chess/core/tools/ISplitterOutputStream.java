package com.tokelon.chess.core.tools;

import java.io.Closeable;
import java.io.Flushable;
import java.io.OutputStream;
import java.util.List;

public interface ISplitterOutputStream extends Closeable, Flushable {


    public boolean addStream(OutputStream stream);

    public boolean hasStream(OutputStream stream);

    public boolean removeStream(OutputStream stream);


    public List<OutputStream> getStreams();

}
