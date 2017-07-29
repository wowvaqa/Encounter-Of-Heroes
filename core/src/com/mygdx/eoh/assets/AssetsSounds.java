package com.mygdx.eoh.assets;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.eoh.defaultClasses.DefaultAssets;

/**
 * Storing sounds
 * Created by l.wawrzyniak on 2017-07-29.
 */

public class AssetsSounds extends DefaultAssets {

    private static AssetsSounds instance;

    private AssetsSounds() {
    }

    public static AssetsSounds getInstance() {
        if (instance == null) {
            instance = new AssetsSounds();
        }
        return instance;
    }

    @Override
    public void createAssets() {
        super.createAssets();

        getManager().load("sounds/skin.json", Sound.class);
    }
}
