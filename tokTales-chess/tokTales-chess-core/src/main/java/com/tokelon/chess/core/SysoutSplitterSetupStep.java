package com.tokelon.chess.core;

import com.tokelon.chess.core.tools.ISplitterOutputStream;
import com.tokelon.chess.core.tools.SplitterOutputStream;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineContext;

import java.io.IOException;
import java.io.PrintStream;

public class SysoutSplitterSetupStep implements ISysoutSplitterSetupStep {


    private PrintStream originalOutStream;
    private ISplitterOutputStream splitterOutStream;

    @Override
    public void onBuildUp(IEngineContext engineContext) throws EngineException {
        this.originalOutStream = System.out;

        SplitterOutputStream splitterOutputStream = new SplitterOutputStream(originalOutStream);
        this.splitterOutStream = splitterOutputStream;

        System.setOut(new PrintStream(splitterOutputStream));
    }

    @Override
    public void onTearDown(IEngineContext engineContext) throws EngineException {
        System.setOut(originalOutStream);

        try {
            splitterOutStream.flush();
            splitterOutStream.close();
        } catch (IOException ex) {
            engineContext.getLogger().error("Stream error on teardown", ex);
        }
    }


    @Override
    public ISplitterOutputStream getSplitterOutStream() {
        return splitterOutStream;
    }

}
