package com.tokelon.toktales.helloworld.core;

import com.tokelon.toktales.core.engine.IEngineContext;
import com.tokelon.toktales.core.game.EmptyGameAdapter;

import javax.inject.Inject;

public class HelloWorldAdapter extends EmptyGameAdapter {
    /* The game adapter is the entry point for our own logic, right after the engine configuration.
     * Here we can inject our dependencies and create our initial state.
     *
     * We inherit from EmptyGameAdapter so we only have to implement the methods we use.
     */


    private final HelloWorldGamestate helloWorldGamestate;

    @Inject
    public HelloWorldAdapter(HelloWorldGamestate helloWorldGamestate) {
        this.helloWorldGamestate = helloWorldGamestate;
    }


    @Override
    public void onCreate(IEngineContext engineContext) {
        // Add our gamestate and set as current
        engineContext.getGame().getStateControl().addState("HelloWorld", helloWorldGamestate);
        engineContext.getGame().getStateControl().changeState("HelloWorld");
    }

}
