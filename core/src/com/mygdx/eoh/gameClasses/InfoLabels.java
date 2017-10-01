package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.defaultClasses.DefaultDamageLabel;

/**
 * Klasa do wyświetlania etykiet informujących o obrażeniach, efektach itp.
 * Created by v on 2017-09-30.
 */

public class InfoLabels {
    private static final InfoLabels instance = new InfoLabels();

    private InfoLabels() {
    }

    public static InfoLabels getInstance() {
        return instance;
    }

    public void showDamageLabel(int damage, int locationXonMap, int locationYonMap) {
        DefaultDamageLabel defaultDamageLabel = new DefaultDamageLabel(
                Integer.toString(damage), (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"), "fight",
                locationXonMap * Options.tileSize + Options.tileSize / 2,
                locationYonMap * Options.tileSize + Options.tileSize / 2);
        GameStatus.getInstance().getMapStage().addActor(defaultDamageLabel);
    }

    public void showGoodEffectLabel(String damage, int locationXonMap, int locationYonMap) {
        DefaultDamageLabel defaultDamageLabel = new DefaultDamageLabel(
                damage, (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"), "good64",
                locationXonMap * Options.tileSize + Options.tileSize / 2,
                locationYonMap * Options.tileSize + Options.tileSize / 2);
        GameStatus.getInstance().getMapStage().addActor(defaultDamageLabel);
    }
}
