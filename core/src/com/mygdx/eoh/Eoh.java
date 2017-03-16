package com.mygdx.eoh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.screens.ScreenManager;

public class Eoh extends Game {

    @Override
    public void create() {

        ScreenManager.WIDTH = Gdx.graphics.getWidth();
        ScreenManager.HEIGHT = Gdx.graphics.getHeight();
        ScreenManager.getInstance().initalize(this);
        ScreenManager.getInstance().setScreen(Screens.ScreenMainMenu);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
