package com.tokelon.toktales.helloworld.desktop;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.desktop.application.TokTalesApplication;
import com.tokelon.toktales.desktop.ui.window.IWindowBuilder;
import com.tokelon.toktales.helloworld.core.HelloWorldAdapter;

public class HelloWorldMain extends TokTalesApplication {
    /* Our application inherits from TokTalesApplication, which will do all the work for us.
     * We just have to tell it how to initialize the engine, using the makeDefault*() methods.
     * For that we can utilize the provided factory getters like getWindowFactory().
     *
     * Works by calling run() for this class in the main method.
     */


    @Override
    public IWindowBuilder makeDefaultWindowBuilder() {
        // Define the size and title for the main application window
        return getWindowFactory().createBuilder(800, 600, "Hello World");
    }

    @Override
    public Class<? extends IGameAdapter> makeDefaultGameAdapter() {
        return HelloWorldAdapter.class;
    }


    public static void main(String[] args) throws EngineException {
        // Run the application. This call will not return until the engine is stopped
        new HelloWorldMain().run(args);
    }

}
