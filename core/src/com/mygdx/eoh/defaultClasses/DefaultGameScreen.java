package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.eoh.Options.OptionsInGame;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.gameClasses.APBar;
import com.mygdx.eoh.gameClasses.AttackButton;
import com.mygdx.eoh.gameClasses.Bulding;
import com.mygdx.eoh.gameClasses.CastleMob;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Grid;
import com.mygdx.eoh.gameClasses.Map;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.mapEditor.MapFile;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.screens.GameInterface;
import com.mygdx.eoh.screens.ScreenManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Default Game Screen
 * Created by v on 2016-10-12.
 */
public abstract class DefaultGameScreen extends DefaultScreen {

    private Stage mapStage;
    private Stage backgroundStage;

    private FitViewport mapStageViewport;
    private Camera mapStageCamera;

    private GameInterface interfaceManager;

    /**
     * Consturctor should be called when single player game start.
     */
    public DefaultGameScreen() {
        super();

        backgroundStage = new Stage();

        this.mapStageCamera = new DefaultCamera(ScreenManager.WIDTH, ScreenManager.HEIGHT);
        GameStatus.getInstance().setCamera((DefaultCamera) this.mapStageCamera);
        this.mapStageViewport = new FitViewport(ScreenManager.WIDTH, ScreenManager.HEIGHT, mapStageCamera);

        interfaceManager = new GameInterface((DefaultCamera) getMapStageCamera());

        createBackgroundStage();
    }

    /**
     * Consturctor should be called when net game start.
     *
     * @param gs GameStatus object contains Client class object.
     */
    public DefaultGameScreen(GameStatus gs) {
        super();

        backgroundStage = new Stage();

        this.mapStageCamera = new DefaultCamera(ScreenManager.WIDTH, ScreenManager.HEIGHT);
        this.mapStageViewport = new FitViewport(ScreenManager.WIDTH, ScreenManager.HEIGHT, mapStageCamera);

        interfaceManager = new GameInterface((DefaultCamera) getMapStageCamera());

        createBackgroundStage();
    }

    protected void createTables() {
        Table upperBarTable = new Table();
        Table upperBarRightTable = new Table();
        Table spellEffectsTable = new Table();
        Table equipmentTable = new Table();
        Table playerMobTable = new Table();
        Table castleMobTable = new Table();
        Table heroTable = new Table();
        Table gameTable = new Table();

//        getMainTable().setDebug(true);
//        upperBarTable.setDebug(true);
//        upperBarRightTable.setDebug(true);
//        equipmentTable.setDebug(true);
//        heroTable.setDebug(true);
//        gameTable.setDebug(true);
//        spellEffectsTable.setDebug(true);

        GameStatus.getInstance().setCurrentPlayerIcon(new Image(
                AssetsGameScreen.getInstance().getManager().get(
                        "game/interface/playersIcon/redPlayerIcon.png", Texture.class)));

        upperBarTable.add(GameStatus.getInstance().getCurrentPlayerIcon()).size(25, 25).pad(2).padLeft(10);

        upperBarTable.add(interfaceManager.imageCoinIcon).size(25, 25).pad(2).padLeft(10);
        upperBarTable.add(interfaceManager.labelGold).pad(2);

        playerMobTable.add(interfaceManager.imageButtonHero).size(100, 100).pad(5);
        playerMobTable.add(interfaceManager.imageButtonPromotion).size(100, 100).pad(5);
        playerMobTable.row();
        playerMobTable.add(interfaceManager.imageButtonBag).size(100, 100).pad(5);
        playerMobTable.add(interfaceManager.imageButtonBook).size(100, 100).pad(5);

//        equipmentTable.add(interfaceManager.imageButtonHero).size(100, 100).pad(5);
//        equipmentTable.add(interfaceManager.imageButtonPromotion).size(100, 100).pad(5);
//        equipmentTable.row();
//        equipmentTable.add(interfaceManager.imageButtonBag).size(100, 100).pad(5);
//        equipmentTable.add(interfaceManager.imageButtonBook).size(100, 100).pad(5);

        equipmentTable.add(playerMobTable);

        castleMobTable.add(interfaceManager.imageButtonNewPlayerMob).size(150, 150).align(Align.center).pad(5);

        gameTable.add(interfaceManager.imageButtonZoomOut).size(50, 50).pad(5);
        gameTable.add(interfaceManager.imageButtonZoomIn).size(50, 50).pad(5);
        gameTable.row();
        if (NetStatus.getInstance().getClient() == null) {
            gameTable.add(interfaceManager.imageButtonNextTurn).size(150, 150).pad(5).colspan(2);
            gameTable.row();
        }
        gameTable.add(interfaceManager.imageButtonExit).size(50, 50).pad(5);
        gameTable.add(interfaceManager.imageButtonSettings).size(50, 50).pad(5);

        super.getMainTable().add(upperBarTable).align(Align.left);
        super.getMainTable().add(new Table()).expandX();
        super.getMainTable().add(upperBarRightTable).expandX().align(Align.right);
        super.getMainTable().row();

        super.getMainTable().add(new Table()).expandX();
        super.getMainTable().add(new Table()).expandX();
        super.getMainTable().add(spellEffectsTable).align(Align.right);
        super.getMainTable().row();

        super.getMainTable().add(new Table()).expandX();
        super.getMainTable().add(new Table()).expandX();
        super.getMainTable().add(heroTable).align(Align.right);
        super.getMainTable().row();

        super.getMainTable().add(new Table()).expandY().colspan(3);
        super.getMainTable().row();

        super.getMainTable().add(equipmentTable).align(Align.left);
        super.getMainTable().add(new Table()).expandX();
        super.getMainTable().add(gameTable).align(Align.right);

        GameStatus.getInstance().setEquipmentTable(equipmentTable);
        GameStatus.getInstance().setHeroTable(heroTable);
        GameStatus.getInstance().setUpperBarTable(upperBarTable);
        GameStatus.getInstance().setUpperBarRightTable(upperBarRightTable);
        GameStatus.getInstance().setPlayerMobTable(playerMobTable);
        GameStatus.getInstance().setCastleMobTable(castleMobTable);
        GameStatus.getInstance().setSpellEffectsTable(spellEffectsTable);

        equipmentTable.setVisible(false);
        heroTable.setVisible(false);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (NetStatus.getInstance().isEnemyDisconnected()){
            Dialog dialog = new Dialog("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
            dialog.text("Przeciwnik rozlaczyl sie");
            dialog.button("OK");
            dialog.show(GameStatus.getInstance().getMainStage());
            NetStatus.getInstance().setEnemyDisconnected(false);
        }

        if (backgroundStage != null) {
            backgroundStage.act();
            backgroundStage.draw();
        }

        if (mapStage != null) {
            mapStage.act();
            mapStage.draw();
        }

        super.getMainStage().act();
        super.getMainStage().draw();
    }

    @Override
    public void dispose() {
        super.dispose();

        AssetsGameScreen.getInstance().dispose();

        if (mapStage != null) {
            mapStage.dispose();
        }
        if (backgroundStage != null) {
            backgroundStage.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (mapStage != null) {
            mapStage.getViewport().update(width, height, true);

        }
    }

    @Override
    public void show() {
        super.show();

        if (mapStage != null) {
            super.getInputMultiplexer().addProcessor(mapStage);
        }
        Gdx.input.setInputProcessor(getInputMultiplexer());
    }

    protected void createMapStage() {
        this.mapStage = new Stage(this.mapStageViewport);
        GameStatus.getInstance().setMapStage(this.mapStage);
    }

    /**
     * Creates frame around map
     *
     * @param mapFile Object of MapFile class.
     */
    protected void createFreameAroudMap(MapFile mapFile) {
        Texture leftFrameTexture = AssetsGameScreen.getInstance().getManager().get
                ("mapEditor/background/frameLeft.png", Texture.class);
        Texture upFrameTexture = AssetsGameScreen.getInstance().getManager().get
                ("mapEditor/background/frameUp.png", Texture.class);
        Texture rightFrameTexture = AssetsGameScreen.getInstance().getManager().get
                ("mapEditor/background/frameRight.png", Texture.class);
        Texture downFrameTexture = AssetsGameScreen.getInstance().getManager().get
                ("mapEditor/background/frameDown.png", Texture.class);

        for (int i = 0; i < mapFile.mapRows * Options.tileSize; i += 10) {
            Image frame = new Image(leftFrameTexture);
            frame.setPosition(-10, i);
            mapStage.addActor(frame);
        }

        for (int i = 0; i < mapFile.mapRows * Options.tileSize; i += 10) {
            Image frame = new Image(rightFrameTexture);
            frame.setPosition(mapFile.mapColumns * Options.tileSize, i);
            mapStage.addActor(frame);
        }

        for (int i = 0; i < mapFile.mapColumns * Options.tileSize; i += 10) {
            Image frame = new Image(upFrameTexture);
            frame.setPosition(i, mapFile.mapRows * Options.tileSize);
            mapStage.addActor(frame);
        }

        for (int i = 0; i < mapFile.mapColumns * Options.tileSize; i += 10) {
            Image frame = new Image(downFrameTexture);
            frame.setPosition(i, -10);
            mapStage.addActor(frame);
        }

        Image frame = new Image(AssetsGameScreen.getInstance().getManager().get("mapEditor/background/frameRightDown.png", Texture.class));
        frame.setPosition(mapFile.mapColumns * Options.tileSize, -10);
        mapStage.addActor(frame);

        frame = new Image(AssetsGameScreen.getInstance().getManager().get("mapEditor/background/frameLeftDown.png", Texture.class));
        frame.setPosition(-10, -10);
        mapStage.addActor(frame);

        frame = new Image(AssetsGameScreen.getInstance().getManager().get("mapEditor/background/frameLeftUp.png", Texture.class));
        frame.setPosition(-10, mapFile.mapRows * Options.tileSize);
        mapStage.addActor(frame);

        frame = new Image(AssetsGameScreen.getInstance().getManager().get("mapEditor/background/frameRightUp.png", Texture.class));
        frame.setPosition(mapFile.mapColumns * Options.tileSize, mapFile.mapRows * Options.tileSize);
        mapStage.addActor(frame);
    }

    /**
     * Fills background stage with stars.
     */
    private void createBackgroundStage() {
        for (int j = -800; j < Gdx.graphics.getHeight() + 400; j += 400) {
            for (int i = -800; i < Gdx.graphics.getWidth() + 400; i += 400) {
                Image backgroundImage = new Image(
                        AssetsGameScreen.getInstance().getManager().get(
                                "mapEditor/background/starTile.png", Texture.class)
                );
                backgroundImage.setPosition(i, j);
                backgroundStage.addActor(backgroundImage);
            }
        }
    }

    /**
     * Filling stage with mobs and buldings.
     *
     * @param map object of Map Class.
     */
    protected void fillStage(Map map) {
        if (mapStage != null) {
            //OptionsInGame.gridArrayList = new ArrayList<Grid>();
            OptionsInGame.getInstance().setGridArrayList(new ArrayList<Grid>());
            for (int i = 0; i < map.getFieldsColumns(); i++) {
                for (int j = 0; j < map.getFieldsRows(); j++) {
                    mapStage.addActor(map.getFields()[i][j]);
                }
            }

            for (int i = 0; i < map.getFieldsColumns(); i++) {
                for (int j = 0; j < map.getFieldsRows(); j++) {
                    if (OptionsInGame.getInstance().isGrid())
                        addGridToField(mapStage, i, j);
                }
            }

            for (int i = 0; i < map.getFieldsColumns(); i++) {
                for (int j = 0; j < map.getFieldsRows(); j++) {
                    if (map.getFields()[i][j].getItem() != null) {
                        mapStage.addActor(map.getFields()[i][j].getItem());
                    }
                }
            }

            for (int i = 0; i < map.getFieldsColumns(); i++) {
                for (int j = 0; j < map.getFieldsRows(); j++) {
                    if (map.getFields()[i][j].getBulding() != null) {
                        mapStage.addActor(map.getFields()[i][j].getBulding());
                    }
                }
            }

            for (int i = 0; i < map.getFieldsColumns(); i++) {
                for (int j = 0; j < map.getFieldsRows(); j++) {
                    if (map.getFields()[i][j].getCastleMob() != null) {
                        mapStage.addActor(map.getFields()[i][j].getCastleMob().getPlayerColorImage());
                        mapStage.addActor(map.getFields()[i][j].getCastleMob());
                    }
                }
            }

            for (int i = 0; i < map.getFieldsColumns(); i++) {
                for (int j = 0; j < map.getFieldsRows(); j++) {
                    if (map.getFields()[i][j].getPlayerMob() != null) {
                        mapStage.addActor(map.getFields()[i][j].getPlayerMob().getPlayerColorImage());
                        mapStage.addActor(map.getFields()[i][j].getPlayerMob());
                    }
                }
            }

            System.out.println("Sort Z index wywolany z fillStage");
            //sortZindex();
        }
    }

    /**
     * Adding grid to map stage.
     * @param mapStage Stage where grid have to be add.
     */
    private void addGridToField(Stage mapStage, int x, int y){
        OptionsInGame.getInstance().getGridArrayList().add(
                new Grid(AssetsGameScreen.getInstance().getManager().get(
                        "game/map/hexGrid.png", Texture.class), x, y));
        mapStage.addActor(
                OptionsInGame.getInstance().getGridArrayList().get(
                        OptionsInGame.getInstance().getGridArrayList().size() - 1));
    }


    public Camera getMapStageCamera() {
        return mapStageCamera;
    }

    public Stage getMapStage() {
        return mapStage;
    }

    public GameInterface getInterfaceManager() {
        return interfaceManager;
    }
}
