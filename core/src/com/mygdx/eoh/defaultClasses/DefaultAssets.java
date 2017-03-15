package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.Interfaces.AssetsInterface;

/**
 * Default class of assets
 * Created by v on 2016-10-13.
 */
public class DefaultAssets implements AssetsInterface {

    private AssetManager manager;
    private TextureLoader.TextureParameter parameter;

    public DefaultAssets() {
        createAssets();
    }

    @Override
    public void createAssets() {
        parameter = new TextureLoader.TextureParameter();

        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.MipMap;

        manager = new AssetManager();
        //manager.load("styles/skin2.json", Skin.class);
    }

    @Override
    public void dispose() {
        manager.dispose();
        System.gc();
    }

    public AssetManager getManager() {
        return manager;
    }

    public TextureLoader.TextureParameter getParameter() {
        return parameter;
    }
}
