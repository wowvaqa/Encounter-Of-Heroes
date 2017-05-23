package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.eoh.items.ItemsWindow;
import com.mygdx.eoh.mapEditor.MapEditor;
import com.mygdx.eoh.mapEditor.MapFile;

/**
 * GestureListener to pan camera moves.
 * Created by v on 2016-09-27.
 */
public class DefaultGestureListener implements GestureDetector.GestureListener {

    private Stage stage;
    //private Stage backgroundStage;
    private int mapRows;
    private int mapColumns;
    //private MapEditor mapEditor;
    //private MapFile mapFile;


    public DefaultGestureListener(Stage stage, /*Stage backgroundStage,*/ MapEditor mapEditor) {
        this.stage = stage;
        //this.backgroundStage = backgroundStage;
        mapRows = mapEditor.mapRows;
        mapColumns = mapEditor.mapColumns;
        //this.mapEditor = mapEditor;
    }

    public DefaultGestureListener(Stage stage, /*Stage backgroundStage,*/ MapFile mapFile) {
        this.stage = stage;
        mapRows = mapFile.mapRows;
        mapColumns = mapFile.mapColumns;
        //this.backgroundStage = backgroundStage;
        //this.mapFile = mapEditor;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

//        Gdx.app.log("camera position: " + stage.getCamera().position.x + " " + stage.getCamera().position.y, "");
//        Gdx.app.log("camera ZOOM: " + ((DefaultCamera) stage.getCamera()).zoom, "");


        //int zoom = (int) ((DefaultCamera) stage.getCamera()).zoom;

//        ((DefaultCamera) stage.getCamera()).setWorldBounds(
//                -500, -500, (mapEditor.mapColumns * 125 + 500) * zoom, (mapEditor.mapRows * 125 + 500) * zoom);
//        ((DefaultCamera) stage.getCamera()).translateSafe(-deltaX, deltaY);


//        if (stage.getCamera().position.x < 350) {
//            stage.getCamera().position.x = 350;
//        }
//
//        if (stage.getCamera().position.x > mapEditor.mapColumns * 100) {
//            stage.getCamera().position.x = mapEditor.mapColumns * 100;
//        }
//
//        if (stage.getCamera().position.y > mapEditor.mapRows * 100) {
//            stage.getCamera().position.y = mapEditor.mapRows * 100;
//        }
//
//        if (stage.getCamera().position.y < 50) {
//            stage.getCamera().position.y = 50;
//        }
//
        ((DefaultCamera) stage.getCamera()).setWorld(mapRows, mapColumns);
        if (!ItemsWindow.itemWindowActive) {
            ((DefaultCamera) stage.getCamera()).translateSafe(-deltaX * ((DefaultCamera) stage.getCamera()).zoom, deltaY * ((DefaultCamera) stage.getCamera()).zoom);
        }

        //stage.getCamera().update();
//
//        if (stage.getCamera().position.x > 350
//                && stage.getCamera().position.x < mapEditor.mapColumns * 100
//                && stage.getCamera().position.y > 50
//                && stage.getCamera().position.y < mapEditor.mapRows * 100) {
//
//            backgroundStage.getCamera().translate(-deltaX / 10, deltaY / 10, 0);
//            backgroundStage.getCamera().update();
//        }

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float ratio = (initialDistance / distance);
        ((DefaultCamera) stage.getCamera()).attemptZoom((1 - ratio) / 4);
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
