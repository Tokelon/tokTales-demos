package com.tokelon.toktales.helloworld.core;

import com.tokelon.toktales.core.content.graphics.IRGBAColor;
import com.tokelon.toktales.core.content.graphics.RGBAColor;
import com.tokelon.toktales.core.content.manage.contents.ContentKey;
import com.tokelon.toktales.core.content.manage.font.IFontAsset;
import com.tokelon.toktales.core.content.manage.font.IFontAssetKey;
import com.tokelon.toktales.core.content.manage.font.IFontAssetManager;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.state.BaseGamestate;
import com.tokelon.toktales.core.game.state.scene.IGameScene;
import com.tokelon.toktales.core.render.renderer.ICharRenderer;
import com.tokelon.toktales.core.screen.surface.ISurface;

import javax.inject.Inject;

public class HelloWorldGamestate extends BaseGamestate<IGameScene> {


    private static final String TEXT = "HELLO WORLD";
    private static final float CHARACTER_SIZE = 32f;
    private static final IRGBAColor TEXT_COLOR = RGBAColor.createFromCode("#fff");
    private static final String FONT_FILE_NAME = "m5x7.ttf";


    private IFontAssetKey fontKey;

    private final IFontAssetManager fontAssetManager;
    private final ICharRenderer charRenderer;

    @Inject
    public HelloWorldGamestate(IFontAssetManager fontAssetManager, ICharRenderer charRenderer) {
        this.fontAssetManager = fontAssetManager;
        this.charRenderer = charRenderer;
    }


    @Override
    public void onEngage() {
        super.onEngage();

        // Register our renderer so it's lifecycle will be managed
        getStateRenderer().addManagedRenderer("CharRenderer", charRenderer);


        // Create a key to load our font resource
        ContentKey contentKey = new ContentKey(FONT_FILE_NAME, "fonts");
        this.fontKey = fontAssetManager.keyOf(contentKey);
    }


    @Override
    public void onRender() {
        super.onRender();

        // Request our asset be loaded if it has not yet / check if loading succeeded
        IFontAsset fontAsset = fontAssetManager.getAsset(fontKey);
        if(!fontAssetManager.isAssetValid(fontAsset)) {
            return;
        }


        ICamera camera = getStateRenderer().getCurrentCamera();

        // Calculate our rendering sizes
        float characterSize = Math.max(camera.getWidth(), camera.getHeight()) > 1000 ? CHARACTER_SIZE * 2 : CHARACTER_SIZE;
        float charDistance = 0f;
        float charOffset = characterSize / 1.5f + charDistance;
        float textSize = TEXT.length() * charOffset;

        charRenderer.setFont(fontAsset.getFont());
        charRenderer.setColor(TEXT_COLOR);
        charRenderer.setSize(characterSize, characterSize);


        float currentX = (camera.getWidth() - textSize) / 2f;
        float currentY = (camera.getHeight() - characterSize) / 2f;

        // Render our text
        charRenderer.startBatchDraw();
        try {
            for(int i = 0; i < TEXT.length(); i++) {
                char character = TEXT.charAt(i);

                charRenderer.setPosition(currentX, currentY);
                charRenderer.drawChar(character);

                currentX += charOffset;
            }
        }
        finally {
            charRenderer.finishBatchDraw();
        }
    }


    @Override
    protected void onSurfaceChanged(ISurface surface) {
        // We only use a single surface, so it's always going to be the same
        super.onSurfaceChanged(surface);

        // Set our camera size to the viewport size of our surface
        float targetCameraWidth = surface.getViewport().getWidth();
        float targetCameraHeight = surface.getViewport().getHeight();

        ICamera camera = getStateRenderer().getCurrentCamera();
        camera.setSize(targetCameraWidth, targetCameraHeight);
    }

}
