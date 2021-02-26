package com.tokelon.chess.core.logic.uci;

import com.tokelon.chess.core.tools.ISplitterOutputStream;

public interface IUCIConnectorFactory {


    public IUCIConnector create(ISplitterOutputStream splitterOut);

}
