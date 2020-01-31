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
    private static final float CHARACTER_SIZE = 64f;
    private static final String FONT_FILE_NAME = "m5x7.ttf";
    private static final IRGBAColor TEXT_COLOR = RGBAColor.createFromCode("#fff");
    private static final IRGBAColor BACKGROUND_COLOR = RGBAColor.createFromCode("#000");

    // We don't know the actual text size, so we calculate it on the fly
    private float finalTextSize = 0f;
    // We will render properly when all text has been drawn at least once
    private boolean isTextLoaded = false;


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
        float characterSize = Math.max(camera.getWidth(), camera.getHeight()) > 1000 ? CHARACTER_SIZE * 2 : CHARACTER_SIZE; // Make text larger on high resolutions
        float characterDistance = characterSize / 8f; // The distance from the end of one character to the start of the next
        IRGBAColor textColor = isTextLoaded ? TEXT_COLOR : BACKGROUND_COLOR; // Set to "invisible" color if text is not complete

        charRenderer.setFont(fontAsset.getFont());
        charRenderer.setColor(textColor);
        charRenderer.setSize(characterSize, characterSize);


        // Calculate our loop values
        float currentX = (camera.getWidth() - finalTextSize) / 2f;
        float currentY = (camera.getHeight() - characterSize) / 2f;
        float calculatedTextSize = 0f;
        boolean allTextRendered = true;

        // Render our text
        charRenderer.startBatchDraw();
        try {
            for(int i = 0; i < TEXT.length(); i++) {
                // Draw the current character
                char character = TEXT.charAt(i);
                charRenderer.setPosition(currentX, currentY);
                float charWidth = charRenderer.drawChar(character);

                // Calculate the offset to the next character
                float charDistance = character == ' ' ? characterDistance * 3f : characterDistance; // Make the distance for whitespace larger
                float charOffset = charWidth + charDistance;
                currentX += charOffset;
                calculatedTextSize += charOffset;

                // If a width of zero is returned that character was not rendered or was whitespace
                allTextRendered = allTextRendered && !(charWidth == 0f && character != ' ');
            }
        }
        finally {
            charRenderer.finishBatchDraw();
        }

        if(allTextRendered) {
            // We now know the actual text size and can use it
            isTextLoaded = true;
            finalTextSize = calculatedTextSize;
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
