package com.tokelon.toktales.helloworld.desktop;

import com.tokelon.toktales.core.engine.EngineException;
import com.tokelon.toktales.desktop.engine.inject.MasterDesktopInjectConfig;
import com.tokelon.toktales.desktop.lwjgl.DefaultDesktopEngineLauncher;
import com.tokelon.toktales.helloworld.core.HelloWorldAdapter;

public class HelloWorldMain {


    public static void main(String[] args) throws EngineException {
        DefaultDesktopEngineLauncher launcher = new DefaultDesktopEngineLauncher(new MasterDesktopInjectConfig());

        launcher.useDefaultWindowBuilderWith(800, 600, "Hello World");
        launcher.launch(HelloWorldAdapter.class);
    }

}
