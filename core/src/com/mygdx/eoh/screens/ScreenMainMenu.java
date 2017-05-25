package com.mygdx.eoh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.eoh.assets.AssetsMainMenu;
import com.mygdx.eoh.defaultClasses.DefaultScreen;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.gameClasses.GameStatus;

/**
 * Represents Main Menu Screen
 * Created by v on 2016-09-19.
 */
public class ScreenMainMenu extends DefaultScreen {

    public ScreenMainMenu() {

        GameStatus.getInstance().clearVariblesOfGameStatus();

        Interface interfaceManager = new Interface();

        super.getMainTable().add(new Image(AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/titleLogo.png", Texture.class))).size(350, 168).padBottom(25);
        super.getMainTable().row();
        super.getMainTable().add(interfaceManager.imageButtonSinglePlayer).size(150, 57).pad(2);
        super.getMainTable().row();
        super.getMainTable().add(interfaceManager.imageButtonHotSeat).size(150, 57).pad(2);
        super.getMainTable().row();
        super.getMainTable().add(interfaceManager.imageButtonMapEditor).size(150, 57).pad(2);
        super.getMainTable().row();
        //super.getMainTable().add(interfaceManager.imageButtonOptions).size(150, 57).pad(2);
        //super.getMainTable().row();
        super.getMainTable().add(interfaceManager.imageButtonExit).size(150, 57).pad(2);
        super.getMainTable().row();
        super.getMainTable().add(new Label("version 0.0004 ALPHA", AssetsMainMenu.getInstance().getManager().get("styles/skin.json", Skin.class), "black16"));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetsMainMenu.getInstance().dispose();
    }

    public class Interface{
        public ImageButton imageButtonSinglePlayer;
        public ImageButton imageButtonHotSeat;
        public ImageButton imageButtonMapEditor;
        public ImageButton imageButtonOptions;
        public ImageButton imageButtonExit;

        public Interface() {
            createButtons();
        }

        /**
         * Creates buttons
         */
        private void createButtons(){
            ImageButton.ImageButtonStyle imageButtonStyleSinglePlayer = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleHotSeat = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleMapEditor = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleOptions = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleExit = new ImageButton.ImageButtonStyle();

            imageButtonStyleSinglePlayer.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonSinglePlayerUp.png", Texture.class)
            ));
            imageButtonStyleSinglePlayer.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonSinglePlayerDown.png", Texture.class)
            ));
            imageButtonStyleHotSeat.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonHotSeatUp.png", Texture.class)
            ));
            imageButtonStyleHotSeat.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonHotSeatDown.png", Texture.class)
            ));
            imageButtonStyleMapEditor.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonMapEditorUp.png", Texture.class)
            ));
            imageButtonStyleMapEditor.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonMapEditorDown.png", Texture.class)
            ));
            imageButtonStyleOptions.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonOptionsUp.png", Texture.class)
            ));
            imageButtonStyleOptions.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonOptionsDown.png", Texture.class)
            ));
            imageButtonStyleExit.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonExitUp.png", Texture.class)
            ));
            imageButtonStyleExit.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMainMenu.getInstance().getManager().get("mainMenu/interface/buttonExitDown.png", Texture.class)
            ));

            imageButtonSinglePlayer = new ImageButton(imageButtonStyleSinglePlayer);
            imageButtonHotSeat = new ImageButton(imageButtonStyleHotSeat);
            imageButtonMapEditor = new ImageButton(imageButtonStyleMapEditor);
            imageButtonOptions = new ImageButton(imageButtonStyleOptions);
            imageButtonExit = new ImageButton(imageButtonStyleExit);

            addListeners();
        }

        /**
         * Add listeners to buttons
         */
        private void addListeners(){
            imageButtonMapEditor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ScreenManager.getInstance().setScreen(com.mygdx.eoh.enums.Screens.ScreenMapEditor);
                }
            });

            imageButtonSinglePlayer.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //ScreenManager.getInstance().setScreen(com.mygdx.eoh.enums.Screens.ScreenSingleGame);
                    ScreenLoading.destinationScreen = Screens.ScreenSingleGame;
                    ScreenManager.getInstance().setScreen(Screens.ScreenLoading);
                }
            });

            imageButtonHotSeat.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ScreenLoading.destinationScreen = Screens.ScreenNewNetGame;
//                    ScreenManager.getInstance().setScreen(Screens.ScreenNewNetGame);
                    ScreenManager.getInstance().setScreen(Screens.ScreenLoading);
                }
            });

            imageButtonExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    AssetsMainMenu.getInstance().dispose();
                    Gdx.app.exit();
                }
            });
        }
    }
}
