package com.tokelon.chess.core.render;

import com.tokelon.chess.core.entities.IChessboard;
import com.tokelon.chess.core.entities.IChesspiece;
import com.tokelon.chess.core.state.IBoardGamescene;
import com.tokelon.chess.core.state.IBoardGamestate;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAsset;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetKey;
import com.tokelon.toktales.core.content.manage.bitmap.IBitmapAssetManager;
import com.tokelon.toktales.core.game.model.ICamera;
import com.tokelon.toktales.core.game.model.IPoint2i;
import com.tokelon.toktales.core.game.state.InjectGameState;
import com.tokelon.toktales.core.render.opengl.gl20.IGL11;
import com.tokelon.toktales.core.render.renderer.IImageRenderer;
import com.tokelon.toktales.core.render.texture.ITexture;
import com.tokelon.toktales.core.render.texture.Texture;
import com.tokelon.toktales.core.screen.view.IViewTransformer;
import com.tokelon.toktales.tools.core.objects.options.INamedOptions;

import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

@InjectGameState
public class BoardRenderer implements IBoardRenderer {


    private IBoardGamestate gamestate;

    private final Map<IBitmapAsset, ITexture> textureCache = new HashMap<>();

    private final IImageRenderer imageRenderer;
    private final IBitmapAssetManager bitmapAssetManager;

    @Inject
    public BoardRenderer(IImageRenderer imageRenderer, IBitmapAssetManager bitmapAssetManager) {
        this.imageRenderer = imageRenderer;
        this.bitmapAssetManager = bitmapAssetManager;
    }

    @InjectGameState
    private void injectGamestate(IBoardGamestate gamestate) {
        this.gamestate = gamestate;
    }


    @Override
    public void contextCreated() {
        imageRenderer.contextCreated();
    }

    @Override
    public void contextChanged(IViewTransformer viewTransformer, Matrix4f projectionMatrix) {
        imageRenderer.contextChanged(viewTransformer, projectionMatrix);
    }

    @Override
    public void contextDestroyed() {
        imageRenderer.contextDestroyed();
    }


    @Override
    public void renderContents(INamedOptions renderOptions) {
        IBoardGamescene gamescene = gamestate.getActiveScene();
        ICamera camera = gamescene.getSceneCamera(); // Get from view transformer?

        IChessboard chessboard = gamescene.getChessboard();
        IPoint2i fieldSelection = gamescene.getFieldSelection();

        float chessboardLength = gamescene.getChessboardLength();
        float chesspieceOffset = gamescene.getChessboardOffset();
        float chesspieceLength = (chessboardLength - chesspieceOffset) / chessboard.getSize();

        for(int x = 0; x < chessboard.getSize(); x++) {
            for(int y = 0; y < chessboard.getSize(); y++) {
                float sizeModifier = chesspieceOffset; // Draw fields a bit larger to avoid gaps

                String fieldAssetKeyName = x % 2 == y % 2 ? IBoardGamescene.ASSET_KEY_SQUARE_LIGHT : IBoardGamescene.ASSET_KEY_SQUARE_DARK;
                ITexture fieldTexture = getTexture(gamescene.getAssetKey(fieldAssetKeyName));
                if(fieldTexture != null) {
                    drawField(camera, chesspieceLength, 0f, sizeModifier, x, y, fieldTexture);
                }

                IChesspiece chesspiece = chessboard.getField(x, y);
                if(chesspiece != null) {
                    IBitmapAssetKey chesspieceAssetKey = gamescene.getChesspieceAssetKey(chesspiece);

                    if(chesspieceAssetKey != null) {
                        ITexture chesspieceTexture = getTexture(chesspieceAssetKey);
                        if(chesspieceTexture != null) {
                            drawField(camera, chesspieceLength, chesspieceOffset, 0f, x, y, chesspieceTexture);
                        }
                    }

                    if(fieldSelection.x() == x && fieldSelection.y() == y) {
                        ITexture selectionTexture = getTexture(gamescene.getAssetKey(IBoardGamescene.ASSET_KEY_SELECTION));
                        if(selectionTexture != null) {
                            drawField(camera, chesspieceLength, 0f, sizeModifier, x, y, selectionTexture);
                        }
                    }
                }
            }
        }
    }

    protected void drawField(ICamera camera, float chesspieceLength, float chesspieceOffset, float sizeModifier, int x, int y, ITexture chesspieceTexture) {
        imageRenderer.drawImage(chesspieceTexture,
                camera.worldToCameraX(x * chesspieceLength + chesspieceOffset),
                camera.worldToCameraY(y * chesspieceLength + chesspieceOffset),
                camera.worldToCameraX((x + 1) * chesspieceLength + chesspieceOffset + sizeModifier),
                camera.worldToCameraY(y * chesspieceLength + chesspieceOffset),
                camera.worldToCameraX((x + 1) * chesspieceLength + chesspieceOffset + sizeModifier),
                camera.worldToCameraY((y + 1) * chesspieceLength + chesspieceOffset + sizeModifier),
                camera.worldToCameraX(x * chesspieceLength + chesspieceOffset),
                camera.worldToCameraY((y + 1) * chesspieceLength + chesspieceOffset + sizeModifier));
    }


    protected ITexture getTexture(IBitmapAssetKey assetKey) { // Add null-check for assetKey?
        IBitmapAsset asset = bitmapAssetManager.getAsset(assetKey);
        if(!bitmapAssetManager.isAssetValid(asset)) {
            // Asset not loaded or invalid

            textureCache.remove(asset); // Remove texture from cache if present
            return null;
        }

        ITexture texture = textureCache.get(asset);
        if(texture == null) {
            texture = new Texture(asset.getBitmap());
            texture.setFilter(IGL11.GL_LINEAR, IGL11.GL_LINEAR); // Set scaling to linear

            textureCache.put(asset, texture);
        }

        return texture;
    }

}
