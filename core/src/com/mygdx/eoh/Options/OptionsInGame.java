package com.mygdx.eoh.Options;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Grid;
import com.mygdx.eoh.gameClasses.Positioning;

import java.util.ArrayList;

/**
 * Options in game.
 * Created by v on 2017-01-03.
 */

public class OptionsInGame {

    private static OptionsInGame instance;

    private Window singleGameOptionsWindow;
    private boolean grid = true;
    private boolean showEquipInfo = false;
    private ArrayList<Grid> gridArrayList;

    private OptionsInGame() {
        //createSingleGameOptionsWindow();
    }

    public static OptionsInGame getInstance() {
        if (instance == null) {
            instance = new OptionsInGame();
        }
        return instance;
    }

    /**
     * Creates single game options window.
     */
    public void createSingleGameOptionsWindow() {

        //Skin skin = AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class);

        Label labelGrid = new Label("Grid: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        //Label showEquipInfo = new Label("Show equip info: ", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        final Label labelGridInfo = new Label("ON", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        TextButton tbGridOn = new TextButton("On", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbGridOn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                labelGridInfo.setText("ON");
                grid = true;
            }
        });

        TextButton tbGridOff = new TextButton("Off", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbGridOff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                labelGridInfo.setText("OFF");
                grid = false;
            }
        });

        TextButton showEquipInfoOn = new TextButton("On", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbGridOn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //labelGridInfo.setText("ON");
                setShowEquipInfo(true);
            }
        });

        TextButton showEquipInfoOff = new TextButton("Off", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbGridOff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //labelGridInfo.setText("OFF");
                setShowEquipInfo(false);
            }
        });

        TextButton tbOk = new TextButton("OK", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbOk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!grid) {
                    removeGridFromMapStage(GameStatus.getInstance().getMapStage());
                } else {
                    for (Grid grid : gridArrayList) {
                        GameStatus.getInstance().getMapStage().addActor(grid);
                    }
                    //DefaultGameScreen.sortZindex();
                }
                singleGameOptionsWindow.remove();
            }
        });

        singleGameOptionsWindow = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        singleGameOptionsWindow.setSize(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 200);
        singleGameOptionsWindow.setModal(true);
        Positioning.setWindowToCenter(singleGameOptionsWindow);

        singleGameOptionsWindow.add(labelGrid).pad(5);
        singleGameOptionsWindow.add(labelGridInfo).pad(5).size(100, 50);
        singleGameOptionsWindow.add(tbGridOn).pad(5);
        singleGameOptionsWindow.add(tbGridOff).pad(5);
//        singleGameOptionsWindow.row();
//        singleGameOptionsWindow.add(showEquipInfo).pad(5);
//        singleGameOptionsWindow.add(showEquipInfoOn).pad(5);
//        singleGameOptionsWindow.add(showEquipInfoOff).pad(5);
        singleGameOptionsWindow.row();

        singleGameOptionsWindow.add(tbOk).pad(5).size(175, 50).colspan(4);
    }

    /**
     * Removes Grid objects from mapStage.
     *
     * @param mapStage MapStage object where grid will be removed.
     */
    private void removeGridFromMapStage(Stage mapStage) {
        int amount = 0;

        for (Actor actor : GameStatus.getInstance().getMapStage().getActors()) {
            if (actor.getClass().equals(Grid.class)) {
                actor.remove();
            }
        }
        for (Actor actor : GameStatus.getInstance().getMapStage().getActors()) {
            if (actor.getClass().equals(Grid.class)) {
                amount += 1;
            }
        }
        if (amount > 0) {
            removeGridFromMapStage(mapStage);
        }
    }

    public Window getSingleGameOptionsWindow() {
        return singleGameOptionsWindow;
    }

    public boolean isGrid() {
        return grid;
    }

    public ArrayList<Grid> getGridArrayList() {
        return gridArrayList;
    }

    public void setGridArrayList(ArrayList<Grid> gridArrayList) {
        this.gridArrayList = gridArrayList;
    }

    public boolean isShowEquipInfo() {
        return showEquipInfo;
    }

    public void setShowEquipInfo(boolean showEquipInfo) {
        this.showEquipInfo = showEquipInfo;
    }
}
