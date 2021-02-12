package com.tokelon.chess.desktop;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.state.IBoardGamestateInputHandler;

public class DesktopInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IBoardGamestateInputHandler.class).to(DesktopBoardGamestateInputHandler.class);
    }

}
