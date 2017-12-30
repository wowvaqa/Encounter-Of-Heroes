package com.mygdx.eoh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.eoh.assets.AssetsMainMenu;
import com.mygdx.eoh.assets.AssetsMapEditor;
import com.mygdx.eoh.defaultClasses.DefaultGestureDetector;
import com.mygdx.eoh.defaultClasses.DefaultGestureListener;
import com.mygdx.eoh.defaultClasses.DefaultScreen;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.Positioning;
import com.mygdx.eoh.mapEditor.MapEditor;

/**
 * Screen for single player game start.
 * Created by v on 2017-07-27.
 */

public class ScreenNewSingleGame extends DefaultScreen {

    private Interface interfaceManager;

    ScreenNewSingleGame(){
        interfaceManager = new Interface();
        createTables();
    }

    private void createTables(){
        Table upperTable = new Table();
        Table middleTable = new Table();
        Table bottomTable = new Table();
        Table playerOneTable = new Table();
        Table playerTwoTable = new Table();

        upperTable.add(interfaceManager.tbMap).pad(3).padTop(20);
        upperTable.row();
        upperTable.add(interfaceManager.lblMap).pad(3);

        playerOneTable.add(interfaceManager.lblPlayerOneHero).pad(3);
        playerOneTable.row();
        playerOneTable.add(interfaceManager.lblPlayerOneMobClass).pad(3);
        playerOneTable.row();
        playerOneTable.add(interfaceManager.tbPlayerOneNextClass).pad(3);
        playerOneTable.row();
        playerOneTable.add(interfaceManager.lblPlayerOneDificulty).pad(3);
        playerOneTable.row();
        playerOneTable.add(interfaceManager.tbPlayerOneDificulty).pad(3).size(210, 65);
        playerOneTable.row();
        playerOneTable.add(interfaceManager.tbPlayerOneCPU).pad(3).size(210, 65);

        playerTwoTable.add(interfaceManager.lblPlyaerTwoHero).pad(3);
        playerTwoTable.row();
        playerTwoTable.add(interfaceManager.lblPlayerTwoMobClass).pad(3);
        playerTwoTable.row();
        playerTwoTable.add(interfaceManager.tbPlayerTwoNextClass).pad(3);
        playerTwoTable.row();
        playerTwoTable.add(interfaceManager.lblPlayerTwoDificulty).pad(3);
        playerTwoTable.row();
        playerTwoTable.add(interfaceManager.tbPlayerTwoDificulty).pad(3).size(210, 65);
        playerTwoTable.row();
        playerTwoTable.add(interfaceManager.tbPlayerTwoCPU).pad(3).size(210, 65);

        bottomTable.add(interfaceManager.tbFog).pad(3);
        bottomTable.row();
        bottomTable.add(interfaceManager.tbPlay).pad(3).size(300, 100);
        bottomTable.row();
        bottomTable.add(interfaceManager.tbExit).pad(3).size(175, 50).align(Align.bottom);

        middleTable.add(playerOneTable).pad(10);
        middleTable.add(playerTwoTable).pad(10);

        super.getMainTable().add(upperTable);
        super.getMainTable().row();
        super.getMainTable().add(middleTable).expand();
        super.getMainTable().row();
        super.getMainTable().add(bottomTable);
    }

    @Override
    public void show() {
        super.show();
        GameStatus.getInstance().setSingleGamePlayerOneMobClass(PlayerMobClasses.Knight);
        GameStatus.getInstance().setSingleGamePlayerTwoMobClass(PlayerMobClasses.Knight);
    }

    private class Interface{

        TextButton tbPlay;
        TextButton tbMap;
        TextButton tbPlayerOneNextClass;
        TextButton tbPlayerTwoNextClass;
        TextButton tbPlayerOneCPU;
        TextButton tbPlayerTwoCPU;
        TextButton tbPlayerOneDificulty;
        TextButton tbPlayerTwoDificulty;
        TextButton tbFog;
        TextButton tbExit;

        Label lblMap;
        Label lblPlayerOneHero;
        Label lblPlyaerTwoHero;
        Label lblPlayerOneDificulty;
        Label lblPlayerTwoDificulty;

        Label lblPlayerOneMobClass;
        Label lblPlayerTwoMobClass;

        Interface(){
            createButtons();
            addListeners();
        }

        private void createButtons(){

            tbMap = new TextButton("Map", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            lblMap = new Label("None", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

            tbPlay = new TextButton("PLAY", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

            tbPlayerOneNextClass = new TextButton("NEXT", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            tbPlayerTwoNextClass = new TextButton("NEXT", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

            tbPlayerOneCPU = new TextButton("Player One AI OFF", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            tbPlayerTwoCPU = new TextButton("Player Two AI ON", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

            tbFog = new TextButton("Fog ON", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

            tbPlayerOneDificulty = new TextButton("Hard", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
            tbPlayerTwoDificulty = new TextButton("Hard", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));

            lblPlayerOneMobClass = new Label("Class: Knight", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
            lblPlayerTwoMobClass = new Label("Class: Knight", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

            lblPlayerOneHero = new Label("Player One Hero", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
            lblPlyaerTwoHero = new Label("Player Two Hero", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

            lblPlayerOneDificulty = new Label("Difficulty:", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
            lblPlayerTwoDificulty = new Label("Difficulty:", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

            tbExit = new TextButton("EXIT", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        }

        private void addListeners(){

            tbMap.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    Window mapLoadWindow = interfaceManager.getLoadMapWindow();
                    Positioning.setWindowToCenter(mapLoadWindow);

                    getMainStage().addActor(mapLoadWindow);
                }
            });

            tbFog.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (Options.fog) {
                        Options.fog = false;
                        tbFog.setText("Fog OFF");
                    } else {
                        Options.fog = true;
                        tbFog.setText("Fog ON");
                    }
                }
            });

            tbPlay.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    ScreenLoading.destinationScreen = Screens.ScreenSingleGame;
                    ScreenManager.getInstance().setScreen(Screens.ScreenLoading);

                }
            });


            tbPlayerOneNextClass.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (GameStatus.getInstance().getSingleGamePlayerOneMobClass().equals(PlayerMobClasses.Knight)) {
                        GameStatus.getInstance().setSingleGamePlayerOneMobClass(PlayerMobClasses.Wizard);
                        lblPlayerOneMobClass.setText("Class: Wizard");
                    } else if (GameStatus.getInstance().getSingleGamePlayerOneMobClass().equals(PlayerMobClasses.Wizard)) {
                        GameStatus.getInstance().setSingleGamePlayerOneMobClass(PlayerMobClasses.Knight);
                        lblPlayerOneMobClass.setText("Class: Knight");
                    }
                }
            });

            tbPlayerTwoNextClass.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (GameStatus.getInstance().getSingleGamePlayerTwoMobClass().equals(PlayerMobClasses.Knight)) {
                        GameStatus.getInstance().setSingleGamePlayerTwoMobClass(PlayerMobClasses.Wizard);
                        lblPlayerTwoMobClass.setText("Class: Wizard");
                    } else if (GameStatus.getInstance().getSingleGamePlayerTwoMobClass().equals(PlayerMobClasses.Wizard)) {
                        GameStatus.getInstance().setSingleGamePlayerTwoMobClass(PlayerMobClasses.Knight);
                        lblPlayerTwoMobClass.setText("Class: Knight");
                    }
                }
            });

            tbPlayerOneCPU.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (GameStatus.getInstance().isPlayerOneAI()) {
                        tbPlayerOneCPU.setText("Player One AI OFF");
                        GameStatus.getInstance().setPlayerOneAI(false);
                    } else {
                        tbPlayerOneCPU.setText("Player One AI ON");
                        GameStatus.getInstance().setPlayerOneAI(true);
                    }
                }
            });

            tbPlayerTwoCPU.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (GameStatus.getInstance().isPlayerTwoAI()) {
                        tbPlayerTwoCPU.setText("Player Two AI OFF");
                        GameStatus.getInstance().setPlayerTwoAI(false);
                    } else {
                        tbPlayerTwoCPU.setText("Player Two AI ON");
                        GameStatus.getInstance().setPlayerTwoAI(true);
                    }
                }
            });

            tbPlayerOneDificulty.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (GameStatus.getInstance().getPlayerOneDifficultyTime() < 0.6f) {
                        tbPlayerOneDificulty.setText("Easy");
                        GameStatus.getInstance().setPlayerOneDifficultyTime(1.0f);
                    } else {
                        tbPlayerOneDificulty.setText("Hard");
                        GameStatus.getInstance().setPlayerOneDifficultyTime(0.5f);
                    }
                }
            });

            tbPlayerTwoDificulty.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (GameStatus.getInstance().getPlayerTwoDifficultyTime() < 0.6f) {
                        tbPlayerTwoDificulty.setText("Easy");
                        GameStatus.getInstance().setPlayerTwoDifficultyTime(1.0f);
                    } else {
                        tbPlayerTwoDificulty.setText("Hard");
                        GameStatus.getInstance().setPlayerTwoDifficultyTime(0.5f);
                    }
                }
            });

            tbExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    ScreenManager.getInstance().setScreen(com.mygdx.eoh.enums.Screens.ScreenMainMenu);
                }
            });

        }

        public Window getLoadMapWindow() {
            final Skin skin = AssetsMainMenu.getInstance().getManager().get("styles/skin.json");
            final Window window = new Window("WCZYTAJ MAPĘ", skin);
            Table table = new Table();
            table.setSize(300, 200);
            window.setSize(600, 400);

            final List<FileHandle> listOfMap = new List<FileHandle>(skin);
            listOfMap.setSize(200, 100);
            FileHandle[] files = Gdx.files.local("maps/").list();
            for (FileHandle file : files)
                if (file.extension().equals("map")) listOfMap.getItems().add(file);

            TextButton tbOk = new TextButton("OK", skin);
            TextButton tbCancel = new TextButton("Cancel", skin);

            tbOk.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (listOfMap.getSelected() != null) {

                        Gdx.app.log("Zaznaczono", "" + listOfMap.getSelection().toString());

//                        if (mapStage == null) {
//                            mapStage = new Stage(mapStageViewport);
//                        }

//                        mapEditor = mapEditor.loadMap(listOfMap, mainStage, mapEditor, mapStageViewport);
//                        mapStage = mapEditor.getMapStage();
//                        mapEditor.drawingType = MapEditor.DrawingType.none;

//                        if (mapEditor.isMapLoaded) {
//
//                            mapStage.clear();
//
//                            createFreameAroudMap();
//
//                            DefaultGestureListener myGL = new DefaultGestureListener(mapStage, mapEditor);
//                            DefaultGestureDetector myGD = new DefaultGestureDetector(myGL);
//                            getInputMultiplexer().clear();
//                            getInputMultiplexer().addProcessor(myGD);
//
//                            getInputMultiplexer().addProcessor(mainStage);
//
////                            for (int i = 0; i < mapEditor.mapColumns; i++) {
////                                for (int j = 0; j < mapEditor.mapRows; j++) {
////                                    mapStage.addActor(mapEditor.fields[i][j]);
////                                    mapEditor.fillField(mapEditor.fields[i][j]);
////                                }
////                            }
//                        }
                        window.remove();
                    } else {
                        Dialog dialog = new Dialog("BŁĄD", skin);
                        dialog.text("Nie wybrano mapy");
                        dialog.button("OK");
                        //dialog.show(mainStage);
                    }
                }

            });

            tbCancel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    window.remove();
                }
            });

            table.add(listOfMap);
            ScrollPane scrollPane = new ScrollPane(listOfMap, skin);
            //scrollPane.setSize(500, 200);
            scrollPane.layout();

            window.add(scrollPane).size(500, 275).colspan(3);
            window.row();
            window.add(tbOk).pad(5).size(100, 50);
            window.add(tbCancel).pad(5).size(100, 50);
            window.row();
            return window;
        }
    }

}
