package com.tokelon.chess.core.logic.uci.memory;

import java.io.OutputStream;
import java.util.List;

public interface IUCIConnectionSink {


    public List<String> receiveUntilLineContains(String until);

    public List<String> readInputLines();


    public OutputStream getOutputStream();

}
