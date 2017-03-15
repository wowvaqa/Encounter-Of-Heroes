package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.screens.ScreenManager;

import java.util.ArrayList;

/**
 * Defines default screen
 * Created by v on 2016-09-27.
 */
public class DefaultScreen implements Screen {

    private ArrayList<Label> labels = new ArrayList<Label>();

    private InputMultiplexer inputMultiplexer;

    private Stage mainStage;

    private Table mainTable;

    protected DefaultScreen() {

        Camera camera = new OrthographicCamera();
        Viewport viewport = new FitViewport(ScreenManager.WIDTH, ScreenManager.HEIGHT, camera);

        inputMultiplexer = new InputMultiplexer();

        mainStage = new Stage();
        GameStatus.getInstance().setMainStage(mainStage);
        mainStage.setViewport(viewport);

        mainTable = new Table();
        mainTable.setFillParent(true);

        mainStage.addActor(mainTable);
    }

    @Override
    public void show() {
        mainStage.getViewport().update((int) ScreenManager.WIDTH, (int) ScreenManager.HEIGHT, true);

        inputMultiplexer.addProcessor(mainStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.act();
        mainStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        mainStage.getViewport().update(width, height, true);
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
        mainStage.dispose();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    protected Table getMainTable() {
        return mainTable;
    }

    protected InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }
}
