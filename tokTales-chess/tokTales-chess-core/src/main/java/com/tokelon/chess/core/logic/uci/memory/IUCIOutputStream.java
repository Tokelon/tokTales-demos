package com.tokelon.chess.core.logic.uci.memory;

import java.io.OutputStream;

public interface IUCIOutputStream {


    public StringBuilder getBuilder();

    public OutputStream getUnderlyingStream();

}
