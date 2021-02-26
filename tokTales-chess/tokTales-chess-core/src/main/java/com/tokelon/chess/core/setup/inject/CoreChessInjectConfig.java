package com.tokelon.chess.core.setup.inject;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class CoreChessInjectConfig extends HierarchicalInjectConfig {


    @Override
    public void configure() {
        super.configure();

        extend(new CoreInjectModule());
        extend(new UCIInjectModule());

        override(new MockOverrideInjectModule());
    }

}
