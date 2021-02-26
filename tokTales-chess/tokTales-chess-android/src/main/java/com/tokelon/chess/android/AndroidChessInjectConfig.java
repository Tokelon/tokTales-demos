package com.tokelon.chess.android;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class AndroidChessInjectConfig extends HierarchicalInjectConfig {


    @Override
    protected void configure() {
        super.configure();

        extend(new AndroidInjectModule());
    }

}
