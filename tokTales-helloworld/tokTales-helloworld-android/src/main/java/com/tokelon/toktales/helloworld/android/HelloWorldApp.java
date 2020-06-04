package com.tokelon.toktales.helloworld.android;

import com.tokelon.toktales.android.application.TokTalesApp;
import com.tokelon.toktales.core.game.IGameAdapter;
import com.tokelon.toktales.helloworld.core.HelloWorldAdapter;

public class HelloWorldApp extends TokTalesApp {
    /* Our application inherits from TokTalesApp, which will do all the work for us.
     * We just have to tell it how to initialize the engine, using the makeDefault*() methods.
     *
     * Works by setting this class as the Application implementation for Android, in the AndroidManifest.
     */


    @Override
    public Class<? extends IGameAdapter> makeDefaultGameAdapter() {
        return HelloWorldAdapter.class;
    }

}
