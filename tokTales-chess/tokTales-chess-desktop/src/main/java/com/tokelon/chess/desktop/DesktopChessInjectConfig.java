package com.tokelon.chess.desktop;

import com.tokelon.toktales.tools.core.sub.inject.config.HierarchicalInjectConfig;

public class DesktopChessInjectConfig extends HierarchicalInjectConfig {


    @Override
    protected void configure() {
        super.configure();

        extend(new DesktopInjectModule());
    }

}
