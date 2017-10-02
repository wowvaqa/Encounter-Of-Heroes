package com.mygdx.eoh.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.eoh.assets.AssetsMainMenu;
import com.mygdx.eoh.defaultClasses.DefaultScreen;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Options;

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
        TextButton tbPlayerOneNextClass;
        TextButton tbPlayerTwoNextClass;
        TextButton tbPlayerOneCPU;
        TextButton tbPlayerTwoCPU;
        TextButton tbPlayerOneDificulty;
        TextButton tbPlayerTwoDificulty;
        TextButton tbFog;
        TextButton tbExit;

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

                    if (GameStatus.getInstance().getPlayerOneDifficultyTime() == 0.5f) {
                        tbPlayerOneDificulty.setText("Easy");
                        GameStatus.getInstance().setPlayerOneDifficultyTime(1.0f);
                    } else if (GameStatus.getInstance().getPlayerOneDifficultyTime() == 1.0f) {
                        tbPlayerOneDificulty.setText("Hard");
                        GameStatus.getInstance().setPlayerOneDifficultyTime(0.5f);
                    }
                }
            });

            tbPlayerTwoDificulty.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (GameStatus.getInstance().getPlayerTwoDifficultyTime() == 0.5f) {
                        tbPlayerTwoDificulty.setText("Easy");
                        GameStatus.getInstance().setPlayerTwoDifficultyTime(1.0f);
                    } else if (GameStatus.getInstance().getPlayerTwoDifficultyTime() == 1.0f) {
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
    }

}
