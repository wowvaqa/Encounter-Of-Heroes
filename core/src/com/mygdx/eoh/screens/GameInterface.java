package com.mygdx.eoh.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.eoh.Options.OptionsInGame;
import com.mygdx.eoh.assets.AssetsGameInterface;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.defaultClasses.DefaultCamera;
import com.mygdx.eoh.enums.Screens;
import com.mygdx.eoh.gameClasses.BuyPlayerMob;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.items.ItemsWindow;
import com.mygdx.eoh.magic.SpellWindow;
import com.mygdx.eoh.net.NetStatus;

/**
 * Game interface
 * Created by v on 2016-10-13.
 */
public class GameInterface {

    public Image imageCoinIcon;
    public Label labelGold;

    public ImageButton imageButtonNewPlayerMob;

    public ImageButton imageButtonBag;
    public ImageButton imageButtonBook;
    public ImageButton imageButtonExit;
    public ImageButton imageButtonHero;

    public ImageButton imageButtonNextTurn;
    public ImageButton imageButtonPromotion;
    public ImageButton imageButtonSettings;
    public ImageButton imageButtonZoomIn;
    public ImageButton imageButtonZoomOut;

    private DefaultCamera camera;

    public GameInterface(DefaultCamera camera) {
        this.camera = camera;
        createButtons();
        createInterfaceElements();
    }

    private void createInterfaceElements() {
        imageCoinIcon = new Image(AssetsGameInterface.getInstance().getManager().get("game/interface/coin.png", Texture.class));
        labelGold = new Label("0", AssetsGameInterface.getInstance().getManager().get("styles/skin.json", Skin.class));
    }

    private void createButtons() {
        ImageButton.ImageButtonStyle imageButtonStyleBag = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleBook = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleExit = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleHero = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleNextTurn = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStylePromotion = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleSettings = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleZoomIn = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleZoomOut = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle imageButtonStyleNewPlayerMob = new ImageButton.ImageButtonStyle();

        imageButtonStyleBag.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonBagUp.png", Texture.class)
        ));
        imageButtonStyleBag.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonBagDown.png", Texture.class)
        ));
        imageButtonStyleBook.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonBookUp.png", Texture.class)
        ));
        imageButtonStyleBook.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonBookDown.png", Texture.class)
        ));
        imageButtonStyleExit.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonExitUp.png", Texture.class)
        ));
        imageButtonStyleExit.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonExitDown.png", Texture.class)
        ));
        imageButtonStyleHero.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonHeroUp.png", Texture.class)
        ));
        imageButtonStyleHero.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonHeroDown.png", Texture.class)
        ));
        imageButtonStyleNextTurn.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonNextTurnUp.png", Texture.class)
        ));
        imageButtonStyleNextTurn.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonNextTurnDown.png", Texture.class)
        ));
        imageButtonStylePromotion.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonPromotionUp.png", Texture.class)
        ));
        imageButtonStylePromotion.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonPromotionDown.png", Texture.class)
        ));
        imageButtonStyleSettings.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonSettingsUp.png", Texture.class)
        ));
        imageButtonStyleSettings.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonSettingsDown.png", Texture.class)
        ));
        imageButtonStyleZoomIn.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonZoomInUp.png", Texture.class)
        ));
        imageButtonStyleZoomIn.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonZoomInDown.png", Texture.class)
        ));
        imageButtonStyleZoomOut.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonZoomOutUp.png", Texture.class)
        ));
        imageButtonStyleZoomOut.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonZoomOutDown.png", Texture.class)
        ));
        imageButtonStyleNewPlayerMob.imageUp = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonNewHeroUp.png", Texture.class)
        ));
        imageButtonStyleNewPlayerMob.imageDown = new TextureRegionDrawable(new TextureRegion(
                AssetsGameInterface.getInstance().getManager().get("game/interface/buttonNewHeroDown.png", Texture.class)
        ));

        imageButtonBag = new ImageButton(imageButtonStyleBag);
        imageButtonBook = new ImageButton(imageButtonStyleBook);
        imageButtonExit = new ImageButton(imageButtonStyleExit);
        imageButtonHero = new ImageButton(imageButtonStyleHero);
        imageButtonNextTurn = new ImageButton(imageButtonStyleNextTurn);
        imageButtonPromotion = new ImageButton(imageButtonStylePromotion);
        imageButtonSettings = new ImageButton(imageButtonStyleSettings);
        imageButtonZoomIn = new ImageButton(imageButtonStyleZoomIn);
        imageButtonZoomOut = new ImageButton(imageButtonStyleZoomOut);
        imageButtonNewPlayerMob = new ImageButton(imageButtonStyleNewPlayerMob);

        imageButtonPromotion.setVisible(false);

        addListeners(camera);
    }

    private void addListeners(final DefaultCamera camera) {

        imageButtonHero.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameStatus.getInstance().getMainStage().addActor(GameStatus.getInstance().getSelectedPlayerMob().getPlayerMobWindow());
            }
        });

        imageButtonPromotion.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameStatus.getInstance().getMainStage().addActor(GameStatus.getInstance().getSelectedPlayerMob().getExpManager().getNextLevelWindow());
            }
        });

        imageButtonNextTurn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
//                if (NetStatus.getInstance().getClient() == null) {
//                    TurnManager.getInstance().nextSingleTurn();
//                }

//                Ai ai = new Ai();
//
//                Field startField = GameStatus.getInstance().getPlayers().get(0).getPlayerMobs().get(0).getFieldOfPlayerMob();
//
//                ArrayList<TreasureCell> treasureCells = ai.findAvailableTreasureBoxes(startField);
//
//                Gdx.app.log("Ilość dostępnych skrzyń:", "" + treasureCells.size());
//
//                for (int i = 0; i < treasureCells.size(); i++) {
//                    Gdx.app.log("Skrzynia " + treasureCells.get(i).getTreasure().getFieldOfTreasure().locXonMap + ","
//                                    + treasureCells.get(i).getTreasure().getFieldOfTreasure().locYonMap,
//                            " Distance: " + treasureCells.get(i).getDistance()
//                    );
//                }
//
////                Field endField = ai.findTreasures().get(0).getTreasure().getFieldOfTreasure();
////
////                ai.findPath(startField, endField);
//
//
//                if (treasureCells.size() > 0) {
//
//                    PlayerMob playerMobToMove = GameStatus.getInstance().getPlayers().get(0).getPlayerMobs().get(0);
//
//                    int moveX, moveY;
//
//                    moveX = treasureCells.get(0).getMoveList().get(0).getMoveX();
//                    moveY = treasureCells.get(0).getMoveList().get(0).getMoveY();
//
//                    if (moveX == 0 && moveY == 1) {
//                        playerMobToMove.getMoveManager().movePlayerMobNorthRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    } else if (moveX == 0 && moveY == -1) {
//                        playerMobToMove.getMoveManager().movePlayerMobSouthRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    } else if (moveX == -1 && moveY == -1) {
//                        playerMobToMove.getMoveManager().movePlayerMobSouthWestRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    } else if (moveX == 1 && moveY == -1) {
//                        playerMobToMove.getMoveManager().movePlayerMobSouthEastRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    } else if (moveX == -1 && moveY == 0) {
//                        playerMobToMove.getMoveManager().movePlayerMobWestRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    } else if (moveX == 1 && moveY == 0) {
//                        playerMobToMove.getMoveManager().movePlayerMobEastRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    } else if (moveX == 1 && moveY == 1) {
//                        playerMobToMove.getMoveManager().movePlayerMobNorthEastRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    } else if (moveX == -1 && moveY == 1) {
//                        playerMobToMove.getMoveManager().movePlayerMobNorthWestRecivedFromNET(playerMobToMove);
//                        treasureCells.remove(0);
//                    }
//                }
            }
        });

        imageButtonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                AssetsGameInterface.getInstance().dispose();
                AssetsGameScreen.getInstance().dispose();
                if (NetStatus.getInstance().getClient() != null) {
                    ScreenLoading.destinationScreen = Screens.ScreenNewNetGame;
//                    ScreenManager.getInstance().setScreen(Screens.ScreenNewNetGame);
                    ScreenManager.getInstance().setScreen(Screens.ScreenLoading);
                } else {
                    ScreenLoading.destinationScreen = Screens.ScreenMainMenu;
//                    ScreenManager.getInstance().setScreen(Screens.ScreenMainMenu);
                    ScreenManager.getInstance().setScreen(Screens.ScreenLoading);
                }
            }
        });

        imageButtonZoomIn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                camera.attemptZoom(-0.5f);
            }
        });

        imageButtonZoomOut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                camera.attemptZoom(0.5f);
            }
        });

        imageButtonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                OptionsInGame.getInstance().createSingleGameOptionsWindow();
                GameStatus.getInstance().getMainStage().addActor(
                        OptionsInGame.getInstance().getSingleGameOptionsWindow()
                );
            }
        });

        imageButtonNewPlayerMob.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameStatus.getInstance().getMainStage().addActor(
                        BuyPlayerMob.getInstance().getBuyPlayerMobWindow(GameStatus.getInstance().getSelectedCastleMob())
                );
            }
        });

        imageButtonBook.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameStatus.getInstance().getMainStage().addActor(
                        SpellWindow.getInstance().getSpellWindow(GameStatus.getInstance().getSelectedPlayerMob())
                );
            }
        });

        imageButtonBag.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ItemsWindow.itemWindowActive = true;
                GameStatus.getInstance().getMainStage().addActor(
                        ItemsWindow.getInstance().getItemsWindow(GameStatus.getInstance().getSelectedPlayerMob())
                );
            }
        });
    }
}
