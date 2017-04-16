package com.mygdx.eoh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.eoh.Equipment.Treasure;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.creators.BuldingCreator;
import com.mygdx.eoh.creators.PlayerMobCreator;
import com.mygdx.eoh.defaultClasses.DefaultCamera;
import com.mygdx.eoh.defaultClasses.DefaultGameScreen;
import com.mygdx.eoh.defaultClasses.DefaultGestureDetector;
import com.mygdx.eoh.defaultClasses.DefaultGestureListener;
import com.mygdx.eoh.enums.Buldings;
import com.mygdx.eoh.enums.FreeMobsKinds;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.enums.Terrains;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.FileOperations;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Map;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.Player;
import com.mygdx.eoh.items.AvailableItems;
import com.mygdx.eoh.items.ItemCreator;
import com.mygdx.eoh.mapEditor.MapFile;
import com.mygdx.eoh.mob.FreeMobCreator;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Single Game Screen representation.
 * Created by v on 2016-09-27.
 */
class ScreenSingleGame extends DefaultGameScreen {

    private boolean cameraStartPosition = false;

    ScreenSingleGame() {
        super();

        createTables();

        super.createMapStage();
        GameStatus.getInstance().setMap(createMap(loadMap(getMainStage())));
        super.fillStage(GameStatus.getInstance().getMap());
    }

    private Map createMap(MapFile mapfile) {

        Map map = new Map();
        GameStatus.getInstance().setPlayers(new ArrayList<Player>());

        map.setFieldsColumns(mapfile.mapColumns);
        map.setFieldsRows(mapfile.mapRows);

        Field[][] fields = new Field[mapfile.mapColumns][mapfile.mapRows];


        map.setFields(fields);

        DefaultGestureListener myGL = new DefaultGestureListener(super.getMapStage(), mapfile);
        DefaultGestureDetector myGD = new DefaultGestureDetector(myGL);
        getInputMultiplexer().clear();
        getInputMultiplexer().addProcessor(myGD);

        getInputMultiplexer().addProcessor(getMainStage());

        super.createFreameAroudMap(mapfile);

        GameStatus.getInstance().getPlayers().add(new Player(1));
        GameStatus.getInstance().getPlayers().add(new Player(2));
        GameStatus.getInstance().getPlayers().add(new Player(3));
        GameStatus.getInstance().getPlayers().add(new Player(4));

        GameStatus.getInstance().setCurrentPlayerTurn(GameStatus.getInstance().getPlayers().get(0));

        for (int i = 0; i < mapfile.mapColumns; i++) {
            for (int j = 0; j < mapfile.mapRows; j++) {

                if (mapfile.fields[i][j].terrains.equals(MapFile.Terrains.River)) {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                            Map.getTextureRegionName(i, j, mapfile, Terrains.River)
                    ), Terrains.River);
                } else if (mapfile.fields[i][j].terrains.equals(MapFile.Terrains.Mountain)) {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                            Map.getTextureRegionName(i, j, mapfile, Terrains.Mountain)
                    ), Terrains.Mountain);
                } else if (mapfile.fields[i][j].terrains.equals(MapFile.Terrains.Forest)) {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                            Map.getTextureRegionName(i, j, mapfile, Terrains.Forest)
                    ), Terrains.Forest);
                } else {
                    fields[i][j] = new Field(AssetsGameScreen.getInstance().getManager().get(
                            "game/terrains/terrain.atlas", TextureAtlas.class).findRegion("grass"), Terrains.Grass);
                }

                if (mapfile.fields[i][j].itemGold) {
                    fields[i][j].setItem(ItemCreator.getInstance().createItem(AvailableItems.Gold, i, j));
                }

                if (mapfile.fields[i][j].manaPotion) {
                    fields[i][j].setItem(ItemCreator.getInstance().createItem(AvailableItems.ManaPotion, i, j));
                }

                if (mapfile.fields[i][j].healthPotion) {
                    fields[i][j].setItem(ItemCreator.getInstance().createItem(AvailableItems.HealthPotion, i, j));
                }

                if (mapfile.fields[i][j].towerHospital)
                    fields[i][j].setBulding(BuldingCreator.getInstance().createBulding(Buldings.Hospital, i, j));

                if (mapfile.fields[i][j].tresureBoxLvl1) {
                    fields[i][j].setTreasure(Treasure.createTreasure(1, i, j));
                }

                if (mapfile.fields[i][j].tresureBoxLvl2) {
                    fields[i][j].setTreasure(Treasure.createTreasure(2, i, j));
                }

                if (mapfile.fields[i][j].mobRandomLevel1) {
                    fields[i][j].setFreeMob(FreeMobCreator.getInstance().createFreeMob(FreeMobsKinds.Skeleton, i, j));
                }

                if (mapfile.fields[i][j].mobSkeletonLocation) {
                    fields[i][j].setFreeMob(FreeMobCreator.getInstance().createFreeMob(FreeMobsKinds.Skeleton, i, j));
                }

                if (mapfile.fields[i][j].player1StartLocation) {
                    fields[i][j].setPlayerMob(
                            PlayerMobCreator.getInstance().createPlayerMob(
                                    PlayerMobClasses.Knight, map, 0, i, j, fields[i][j]
                            )
                    );
                    fields[i][j].setCastleMob(
                            PlayerMobCreator.getInstance().createCastleMob(
                                    map, 0, i, j, fields[i][j]
                            )
                    );
                }

                if (mapfile.fields[i][j].player2StartLocation) {
                    fields[i][j].setPlayerMob(
                            PlayerMobCreator.getInstance().createPlayerMob(
                                    PlayerMobClasses.Wizard, map, 1, i, j, fields[i][j]
                            )
                    );
                    fields[i][j].setCastleMob(
                            PlayerMobCreator.getInstance().createCastleMob(
                                    map, 1, i, j, fields[i][j]
                            )
                    );
                }

                if (mapfile.fields[i][j].player3StartLocation) {
                    fields[i][j].setPlayerMob(
                            PlayerMobCreator.getInstance().createPlayerMob(
                                    PlayerMobClasses.Knight, map, 2, i, j, fields[i][j]
                            )
                    );
                    fields[i][j].setCastleMob(
                            PlayerMobCreator.getInstance().createCastleMob(
                                    map, 2, i, j, fields[i][j]
                            )
                    );
                }

                if (mapfile.fields[i][j].player4StartLocation) {
                    fields[i][j].setPlayerMob(
                            PlayerMobCreator.getInstance().createPlayerMob(
                                    PlayerMobClasses.Knight, map, 3, i, j, fields[i][j]
                            )
                    );
                    fields[i][j].setCastleMob(
                            PlayerMobCreator.getInstance().createCastleMob(
                                    map, 3, i, j, fields[i][j]
                            )
                    );
                }

                fields[i][j].setSize(Options.tileSize, Options.tileSize);
                fields[i][j].setPosition(i * Options.tileSize, j * Options.tileSize);
            }
        }

//        PlayerMob playerMob = new PlayerMob(AnimationCreator.getInstance().makeAnimation(
//                AnimationTypes.KnightStanding), true, super.getMapStage(), map, GameStatus.getInstance().getPlayers().get(0));
//        playerMob.setCoordinateXonMap(1);
//        playerMob.setCoordinateYonMap(1);
//        playerMob.setSpeed(1);
//        playerMob.setAttack(5);
//        playerMob.setDefence(5);
//        playerMob.setActualhp(5);
//        playerMob.setActualSpeed(1);
//        playerMob.setSize(Options.tileSize, Options.tileSize);
//        playerMob.setPosition(
//                playerMob.getCoordinateXonMap() * Options.tileSize,
//                playerMob.getCoordinateYonMap() * Options.tileSize);
//        playerMob.getPlayerColorImage().setPosition(
//                playerMob.getCoordinateXonMap() * Options.tileSize,
//                playerMob.getCoordinateYonMap() * Options.tileSize);
//        fields[1][1].setPlayerMob(playerMob);
//        GameStatus.getInstance().getPlayers().get(0).getPlayerMobs().add(playerMob);
//
//        PlayerMob playerMob2 = new PlayerMob(AnimationCreator.getInstance().makeAnimation(
//                AnimationTypes.KnightStanding), true, super.getMapStage(), map, GameStatus.getInstance().getPlayers().get(1));
//        playerMob2.setCoordinateXonMap(7);
//        playerMob2.setCoordinateYonMap(7);
//        playerMob2.setSpeed(30);
//        playerMob2.setAttack(5);
//        playerMob2.setDefence(5);
//        playerMob2.setActualhp(5);
//        playerMob2.setActualSpeed(30);
//        playerMob2.setSize(Options.tileSize, Options.tileSize);
//        playerMob2.setPosition(
//                playerMob2.getCoordinateXonMap() * Options.tileSize,
//                playerMob2.getCoordinateYonMap() * Options.tileSize);
//        playerMob2.getPlayerColorImage().setPosition(
//                playerMob2.getCoordinateXonMap() * Options.tileSize,
//                playerMob2.getCoordinateYonMap() * Options.tileSize);
//        fields[7][7].setPlayerMob(playerMob2);
//        GameStatus.getInstance().getPlayers().get(1).getPlayerMobs().add(playerMob2);
//
//        PlayerMob playerMob3 = new PlayerMob(AnimationCreator.getInstance().makeAnimation(
//                AnimationTypes.KnightStanding), true, super.getMapStage(), map, GameStatus.getInstance().getPlayers().get(1));
//        playerMob3.setCoordinateXonMap(5);
//        playerMob3.setCoordinateYonMap(5);
//        playerMob3.setSpeed(15);
//        playerMob3.setAttack(5);
//        playerMob3.setDefence(5);
//        playerMob3.setActualhp(5);
//        playerMob3.setActualSpeed(15);
//        playerMob3.setSize(Options.tileSize, Options.tileSize);
//        playerMob3.setPosition(
//                playerMob3.getCoordinateXonMap() * Options.tileSize,
//                playerMob3.getCoordinateYonMap() * Options.tileSize);
//        playerMob3.getPlayerColorImage().setPosition(
//                playerMob3.getCoordinateXonMap() * Options.tileSize,
//                playerMob3.getCoordinateYonMap() * Options.tileSize);
//        fields[5][5].setPlayerMob(playerMob3);
//        GameStatus.getInstance().getPlayers().get(1).getPlayerMobs().add(playerMob3);

        GameStatus.getInstance().setMap(map);

        return map;
    }

    private MapFile loadMap(Stage mainStage) {
        FileHandle file = Gdx.files.internal("maps/1.map");
        //MapEditor map = new MapEditor();
        MapFile map = new MapFile();

        try {
            //map = (Map) FileOperations.deserialize(file.readBytes());
            map = (MapFile) FileOperations.deserialize(file.readBytes());
            map.setMapLoaded(true);
        } catch (IOException e) {
            Dialog dialog = new Dialog("BŁĄD", (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"));
            dialog.text("Nie można wczytać mapy. Mapa w utworzona w starej wersji edytora.");
            dialog.button("OK");
            dialog.show(mainStage);
            e.printStackTrace();
            map.setMapLoaded(false);
        } catch (ClassNotFoundException e) {
            Dialog dialog = new Dialog("BŁĄD", (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"));
            dialog.text("Nie można wczytać mapy.");
            dialog.button("OK");
            dialog.show(mainStage);
            e.printStackTrace();
            map.setMapLoaded(false);
        }
        return map;
    }

    @Override
    public void show() {
        super.show();
        ((DefaultCamera) getMapStageCamera()).zoom = 2.5f;
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!cameraStartPosition) {

            System.out.println("Cam posi X: " + getMapStageCamera().position.x);
            System.out.println("Cam posi Y: " + getMapStageCamera().position.y);

            float positionX = GameStatus.getInstance().getCurrentPlayerTurn().getPlayerMobs().get(0).getX();
            float positiony = GameStatus.getInstance().getCurrentPlayerTurn().getPlayerMobs().get(0).getY();

            System.out.println("Pl MOb posi X: " + positionX);
            System.out.println("Pl MOb posi Y: " + positiony);

            ((DefaultCamera) getMapStageCamera()).position.x = positionX + Options.tileSize / 2;
            ((DefaultCamera) getMapStageCamera()).position.y = positiony + Options.tileSize / 2;
            cameraStartPosition = true;

        }


    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
