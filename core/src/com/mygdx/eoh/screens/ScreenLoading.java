package com.mygdx.eoh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.eoh.assets.AssetsGameInterface;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.assets.AssetsMainMenu;
import com.mygdx.eoh.assets.AssetsSounds;
import com.mygdx.eoh.enums.Screens;

/**
 * Loading screen.
 * Created by v on 2017-01-28.
 */

public class ScreenLoading implements Screen {
    private Stage stage;
    private Label lblLoading;
    private Table table = new Table();

    private float progress;

    private int conunter = 0;

    public static Screens destinationScreen;

    public ScreenLoading(){

        this.progress = 0f;

        stage = new Stage();
        lblLoading = new Label("Loading...", new Skin(Gdx.files.internal("styles/skin.json")), "black32");
        table.setFillParent(true);
        table.add(lblLoading);
        stage.addActor(table);

    }

    private void update(float delta){
        if (ScreenLoading.destinationScreen == Screens.ScreenSingleGame ||
                ScreenLoading.destinationScreen == Screens.ScreenNetGame) {
            if (AssetsGameScreen.getInstance().getManager().update() &&
                    AssetsGameInterface.getInstance().getManager().update() &&
                    AssetsSounds.getInstance().getManager().update()) {
                ScreenManager.getInstance().setScreen(ScreenLoading.destinationScreen);
            }
        }

        if (ScreenLoading.destinationScreen == Screens.ScreenNewNetGame ||
                ScreenLoading.destinationScreen == Screens.ScreenMainMenu ||
                ScreenLoading.destinationScreen == Screens.ScreenNewSingleGame) {
            if (AssetsMainMenu.getInstance().getManager().update()) {
                ScreenManager.getInstance().setScreen(ScreenLoading.destinationScreen);
            }
        }
    }

    @Override
    public void show() {
        System.out.println("LOADING ...");
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1, 1, 1, 1);

        stage.act();
        stage.draw();

        if (conunter == 0)
            conunter += 1;
        else
            update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
