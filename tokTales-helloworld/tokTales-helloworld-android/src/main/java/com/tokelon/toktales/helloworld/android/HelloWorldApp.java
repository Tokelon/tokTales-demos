package com.tokelon.toktales.helloworld.android;

import com.tokelon.toktales.android.app.TokTalesApp;
import com.tokelon.toktales.android.engine.AndroidEngineLauncher;
import com.tokelon.toktales.android.engine.inject.MasterAndroidInjectConfig;
import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.core.engine.IEngineLauncher;
import com.tokelon.toktales.helloworld.core.HelloWorldAdapter;

public class HelloWorldApp extends TokTalesApp {


    @Override
    protected void launchEngine(IEngineLauncher defaultLauncher) throws EngineException {
        AndroidEngineLauncher launcher = new AndroidEngineLauncher(new MasterAndroidInjectConfig(), getApplicationContext());

        launcher.launch(HelloWorldAdapter.class);
    }

}
