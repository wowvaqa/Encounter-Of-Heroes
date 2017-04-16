package com.mygdx.eoh.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.defaultClasses.DefaultAssets;

/**
 * To access assets used in Main Menu.
 * Created by l.wawrzyniak on 2016-10-02.
 */

public class AssetsMainMenu extends DefaultAssets {

    private static AssetsMainMenu instance;

    private AssetsMainMenu() {

//        createAssets();
    }

    public static AssetsMainMenu getInstance() {
        if (instance == null) {
            instance = new AssetsMainMenu();
        }
        return instance;
    }

    @Override
    public void createAssets() {
        super.createAssets();

        getManager().load("styles/skin.json", Skin.class);
        getManager().load("mainMenu/interface/titleLogo.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonSinglePlayerUp.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonSinglePlayerDown.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonHotSeatUp.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonHotSeatDown.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonMapEditorUp.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonMapEditorDown.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonOptionsUp.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonOptionsDown.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonExitUp.png", Texture.class, getParameter());
        getManager().load("mainMenu/interface/buttonExitDown.png", Texture.class, getParameter());

        getManager().finishLoading();
    }

    @Override
    public void dispose() {
        super.dispose();
        instance = null;
    }
}
