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

        getManager().load("sounds/walk.wav", Sound.class);
        getManager().load("sounds/boxOpen.wav", Sound.class);
        getManager().load("sounds/levelUp.mp3", Sound.class);
        getManager().load("sounds/spellGood.wav", Sound.class);
        getManager().load("sounds/fireball.wav", Sound.class);
        getManager().load("sounds/swordSound.wav", Sound.class);

        getManager().finishLoading();
    }

    @Override
    public void dispose() {
        super.dispose();
        instance = null;
    }
}
