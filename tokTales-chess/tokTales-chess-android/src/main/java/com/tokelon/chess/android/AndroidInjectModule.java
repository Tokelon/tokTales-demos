package com.tokelon.chess.android;

import com.google.inject.AbstractModule;
import com.tokelon.chess.core.state.IBoardGamestateInputHandler;

public class AndroidInjectModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(IBoardGamestateInputHandler.class).to(AndroidBoardGamestateInputHandler.class);
    }

}
