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

        playerOneTable.add(interfaceManager.lblPlayerOneHero).pad(5);
        playerOneTable.row();
        playerOneTable.add(interfaceManager.lblPlayerOneMobClass).pad(5);
        playerOneTable.row();
        playerOneTable.add(interfaceManager.tbPlayerOneNextClass).pad(5);

        playerTwoTable.add(interfaceManager.lblPlyaerTwoHero).pad(5);
        playerTwoTable.row();
        playerTwoTable.add(interfaceManager.lblPlayerTwoMobClass).pad(5);
        playerTwoTable.row();
        playerTwoTable.add(interfaceManager.tbPlayerTwoNextClass).pad(5);

        bottomTable.add(interfaceManager.tbPlay).pad(5).size(300, 100);
        bottomTable.row();
        bottomTable.add(interfaceManager.tbExit).pad(5).size(175, 50).align(Align.bottom);

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
        TextButton tbExit;

        Label lblPlayerOneHero;
        Label lblPlyaerTwoHero;

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

            lblPlayerOneMobClass = new Label("Class: Knight", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
            lblPlayerTwoMobClass = new Label("Class: Knight", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

            lblPlayerOneHero = new Label("Player One Hero", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");
            lblPlyaerTwoHero = new Label("Player Two Hero", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black32");

            tbExit = new TextButton("EXIT", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class));
        }

        private void addListeners(){

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
