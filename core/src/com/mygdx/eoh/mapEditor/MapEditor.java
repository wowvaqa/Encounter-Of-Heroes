package com.mygdx.eoh.mapEditor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.eoh.assets.AssetsMapEditor;
import com.mygdx.eoh.enums.Terrains;
import com.mygdx.eoh.gameClasses.FileOperations;
import com.mygdx.eoh.gameClasses.Map;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Engine of Map Editor
 * Created by v on 2016-09-28.
 */
public class MapEditor {

//    private static final long serialVersionUID = -5585469060185774793L;

    public FieldOfEditor[][] fields;

    public String nameOfMap;
    public int mapColumns = 0;
    public int mapRows = 0;
    public boolean isMapLoaded = true;

    public DrawingType drawingType = DrawingType.none;

    private DefaultStage mapStage;

    private ArrayList<FieldOfEditorImage> fieldOfEditorImageArrayList = new ArrayList<FieldOfEditorImage>();

    public MapEditor() {
    }

    public void redrawMap() {
        for (int i = 0; i < this.mapColumns; i++) {
            for (int j = 0; j < this.mapRows; j++) {
                fillField(this.fields[i][j]);
            }
        }
    }

    public void fillField(FieldOfEditor field) {

        if (field.terrains.equals(Terrains.Grass)) {
            field.setDrawable(new TextureRegionDrawable(new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/terrains/grass.png", Texture.class))));
            field.setPosition(field.coordinateXonMap * 100, field.coordinateYonMap * 100);
        } else if (field.terrains.equals(Terrains.Forest)) {
            field.setDrawable(new TextureRegionDrawable(AssetsMapEditor.getInstance().getManager().get(
                    "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                    Map.getTextureRegionName(field.coordinateXonMap, field.coordinateYonMap, this, Terrains.Forest))));
            field.setPosition(field.coordinateXonMap * 100, field.coordinateYonMap * 100);
        } else if (field.terrains.equals(Terrains.Mountain)) {
            field.setDrawable(new TextureRegionDrawable(AssetsMapEditor.getInstance().getManager().get(
                    "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                    Map.getTextureRegionName(field.coordinateXonMap, field.coordinateYonMap, this, Terrains.Mountain))));
            field.setPosition(field.coordinateXonMap * 100, field.coordinateYonMap * 100);
        } else if (field.terrains.equals(Terrains.River)) {
            field.setDrawable(new TextureRegionDrawable(AssetsMapEditor.getInstance().getManager().get(
                    "game/terrains/terrain.atlas", TextureAtlas.class).findRegion(
                    Map.getTextureRegionName(field.coordinateXonMap, field.coordinateYonMap, this, Terrains.River))));
            field.setPosition(field.coordinateXonMap * 100, field.coordinateYonMap * 100);
        }

        FieldOfEditorImage fieldOfEditorImage;

        if (field.player1StartLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/players/player1.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.player2StartLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/players/player2.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.player3StartLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/players/player3.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.player4StartLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/players/player4.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        }

        if (field.tresureBoxLvl1) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/boxes/texTresureBoxLvl1.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.tresureBoxLvl2) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/boxes/texTresureBoxLvl2.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        }

        if (field.itemGold) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/items/goldCoins.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        }

        if (field.manaPotion) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mixtures/manaPotion.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        }

        if (field.healthPotion) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mixtures/healthPotion.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        }

        if (field.towerMagic) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/towerOfMagic.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.towerWisdom) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/towerOfWisdom.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.towerDefence) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/towerOfDefence.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.towerSpeed) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/towerOfSpeed.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.towerAttack) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/towerOfAttack.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.towerHp) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/towerOfHp.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.towerWell) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/well.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.towerHospital) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/buldings/hospital.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        }

        if (field.mobSkeletonLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mobs/mobSkeleton.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.mobWolfLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mobs/mobWolf.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.mobRandomLevel1) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mobs/mobRandomLevel1.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.mobSpiderLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mobs/mobSpider.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.mobZombieLocation) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mobs/mobZombie.png", Texture.class)),
                    field
            );
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        } else if (field.mobRandomLevel2) {
            fieldOfEditorImage = new FieldOfEditorImage(
                    new TextureRegion(AssetsMapEditor.getInstance().getManager().get(
                            "mapEditor/mobs/mobRandomLevel2.png", Texture.class)), field);
            setupFieldOfEditorImage(mapStage, fieldOfEditorImage, field);
        }

        field.setWidth(100);
        field.setHeight(100);
    }

    private void setupFieldOfEditorImage(DefaultStage mapStage, FieldOfEditorImage fieldOfEditorImage,
                                         FieldOfEditor field) {
        fieldOfEditorImage.setPosition(field.getX(), field.getY());
        fieldOfEditorImage.setSize(100, 100);
        fieldOfEditorImage.setTouchable(Touchable.disabled);
        fieldOfEditorImageArrayList.add(fieldOfEditorImage);
        mapStage.addActor(fieldOfEditorImage);
    }

    /**
     * Load map from file
     *
     * @param listOfMap List of Map
     * @return Map object
     */
    public MapEditor loadMap(List listOfMap, Stage mainStage, MapEditor map, Viewport viewport) {

        this.mapStage = new DefaultStage(viewport);

        FileHandle file = (FileHandle) listOfMap.getSelected();

        MapFile mapFile;
        try {
            mapFile = (MapFile) FileOperations.deserialize(file.readBytes());
            map.fields = new FieldOfEditor[mapFile.mapColumns][mapFile.mapRows];
            for (int i = 0; i < mapFile.mapColumns; i++) {
                for (int j = 0; j < mapFile.mapRows; j++) {
                    map.fields[i][j] = new FieldOfEditor();
                }
            }
            map = MapEditor.convertMapFileToMapEditor(map, mapFile);
            map.isMapLoaded = true;
        } catch (IOException e) {
            Dialog dialog = new Dialog("BŁĄD", (Skin) AssetsMapEditor.getInstance().getManager().get("styles/skin.json"));
            dialog.text("Nie można wczytać mapy. Mapa w utworzona w starej wersji edytora.");
            dialog.button("OK");
            dialog.show(mainStage);
            e.printStackTrace();
            map.isMapLoaded = false;
        } catch (ClassNotFoundException e) {
            Dialog dialog = new Dialog("BŁĄD", (Skin) AssetsMapEditor.getInstance().getManager().get("styles/skin.json"));
            dialog.text("Nie można wczytać mapy.");
            dialog.button("OK");
            dialog.show(mainStage);
            e.printStackTrace();
            map.isMapLoaded = false;
        }
        return map;
    }

    public Stage createStage(String nameOfMap, int sizeColumns, int sizeRow, Viewport viewport) {

        DefaultStage stage = new DefaultStage(viewport);
        this.mapStage = stage;

        this.nameOfMap = nameOfMap;
        this.mapColumns = sizeColumns;
        this.mapRows = sizeRow;

        fields = new FieldOfEditor[sizeColumns][sizeRow];

        for (int i = 0; i < sizeColumns; i++) {
            for (int j = 0; j < sizeRow; j++) {
                fields[i][j] = new FieldOfEditor();
                fields[i][j].terrains = Terrains.Grass;
                fields[i][j].terrainGrass = true;
                fields[i][j].coordinateXonMap = i;
                fields[i][j].coordinateYonMap = j;
                fillField(fields[i][j]);
            }
        }

        for (int i = 0; i < sizeColumns; i++) {
            for (int j = 0; j < sizeRow; j++) {
                stage.addActor(fields[i][j]);
            }
        }
        return stage;
    }

    public DefaultStage getMapStage() {
        return mapStage;
    }

    public FieldOfEditor[][] createFields(int columns, int rows) {
        FieldOfEditor[][] fields = new FieldOfEditor[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                fields[i][j] = new FieldOfEditor();
            }
        }
        return fields;
    }

    /**
     * Types of drawing
     */
    public enum DrawingType {
        forestDraw,
        mountainDraw,
        riverDraw,
        rubberDraw,
        player1Draw,
        player2Draw,
        player3Draw,
        player4Draw,
        mobSkeletonDraw,
        mobWolfDraw,
        mobRandomLvl1Draw,
        mobSpiederDraw,
        mobZombieDraw,
        mobRandomLvl2Draw,
        tresureBox1Draw,
        tresureBox2Draw,
        towerMagicDraw,
        towerWisdomDraw,
        towerDefenceDraw,
        towerSpeedDraw,
        towerAttackDraw,
        towerHpDraw,
        towerWellDraw,
        towerHospitalDraw,
        itemGold,
        healthPotionDraw,
        manaPotionDraw,
        none
    }

    public class DefaultStage extends Stage implements Serializable {
        public DefaultStage(Viewport viewport) {
            super(viewport);
        }
    }

    public class FieldOfEditorImage extends Image implements Serializable {

        FieldOfEditor field;

        public FieldOfEditorImage(TextureRegion region, FieldOfEditor field) {
            super(region);
            this.field = field;
        }
    }

    public class FieldOfEditor extends Image {

        public boolean player1StartLocation = false;
        public boolean player2StartLocation = false;
        public boolean player3StartLocation = false;
        public boolean player4StartLocation = false;
        public boolean mobSkeletonLocation = false;
        public boolean mobWolfLocation = false;
        public boolean mobZombieLocation = false;
        public boolean mobSpiderLocation = false;
        public boolean mobRandomLevel1 = false;
        public boolean mobRandomLevel2 = false;
        public boolean terrainForest = false;
        public boolean terrainMountain = false;
        public boolean terrainRiver = false;
        public boolean terrainGrass = false;
        public boolean tresureBoxLvl1 = false;
        public boolean tresureBoxLvl2 = false;
        public boolean towerMagic = false;
        public boolean towerWisdom = false;
        public boolean towerDefence = false;
        public boolean towerSpeed = false;
        public boolean towerAttack = false;
        public boolean towerHp = false;
        public boolean towerWell = false;
        public boolean towerHospital = false;
        public boolean itemGold = false;
        public boolean manaPotion = false;
        public boolean healthPotion = false;
        public Terrains terrains;
        public int coordinateXonMap;
        public int coordinateYonMap;

        public FieldOfEditor() {
            //super(AssetsMapEditor.getInstance().getManager().get("mapEditor/terrains/grass.png", Texture.class));
            super(AssetsMapEditor.getInstance().getManager().get(
                    "game/terrains/terrain.atlas", TextureAtlas.class).findRegion("grass"));

            addListeners(this);
        }

        public void addListeners(final FieldOfEditor field) {

            this.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    for (FieldOfEditorImage fieldOfEditorImage : fieldOfEditorImageArrayList) {
                        fieldOfEditorImage.remove();
                    }

                    if (drawingType != null) {
                        if (drawingType.equals(DrawingType.forestDraw)) {
                            field.terrainForest = true;
                            field.terrains = Terrains.Forest;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.mountainDraw)) {
                            field.terrainMountain = true;
                            field.terrains = Terrains.Mountain;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.riverDraw)) {
                            field.terrainRiver = true;
                            field.terrains = Terrains.River;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.rubberDraw)) {
                            clearField(field);
                            field.terrainGrass = true;
                            field.terrains = Terrains.Grass;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.player1Draw)) {
                            for (int i = 0; i < mapColumns; i++) {
                                for (int j = 0; j < mapRows; j++) {
                                    if (fields[i][j].player1StartLocation) {
                                        fields[i][j].player1StartLocation = false;
                                        fillField(fields[i][j]);
                                    }
                                }
                            }
                            field.player1StartLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.player2Draw)) {
                            for (int i = 0; i < mapColumns; i++) {
                                for (int j = 0; j < mapRows; j++) {
                                    if (fields[i][j].player2StartLocation) {
                                        fields[i][j].player2StartLocation = false;
                                        fillField(fields[i][j]);
                                    }
                                }
                            }
                            field.player2StartLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.player3Draw)) {
                            for (int i = 0; i < mapColumns; i++) {
                                for (int j = 0; j < mapRows; j++) {
                                    if (fields[i][j].player3StartLocation) {
                                        fields[i][j].player3StartLocation = false;
                                        fillField(fields[i][j]);
                                    }
                                }
                            }
                            field.player3StartLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.player4Draw)) {
                            for (int i = 0; i < mapColumns; i++) {
                                for (int j = 0; j < mapRows; j++) {
                                    if (fields[i][j].player4StartLocation) {
                                        fields[i][j].player4StartLocation = false;
                                        fillField(fields[i][j]);
                                    }
                                }
                            }
                            field.player4StartLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.mobSkeletonDraw)) {
                            field.mobSkeletonLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.mobWolfDraw)) {
                            field.mobWolfLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.mobRandomLvl1Draw)) {
                            field.mobRandomLevel1 = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.mobSpiederDraw)) {
                            field.mobSpiderLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.mobZombieDraw)) {
                            field.mobZombieLocation = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.mobRandomLvl2Draw)) {
                            field.mobRandomLevel2 = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.tresureBox1Draw)) {
                            field.tresureBoxLvl1 = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.tresureBox2Draw)) {
                            field.tresureBoxLvl2 = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.itemGold)) {
                            field.itemGold = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.manaPotionDraw)) {
                            field.manaPotion = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.healthPotionDraw)) {
                            field.healthPotion = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerMagicDraw)) {
                            field.towerMagic = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerWisdomDraw)) {
                            field.towerWisdom = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerDefenceDraw)) {
                            field.towerDefence = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerSpeedDraw)) {
                            field.towerSpeed = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerAttackDraw)) {
                            field.towerAttack = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerHpDraw)) {
                            field.towerHp = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerWellDraw)) {
                            field.towerWell = true;
                            fillField(field);
                        } else if (drawingType.equals(DrawingType.towerHospitalDraw)) {
                            field.towerHospital = true;
                            fillField(field);
                        }
                    }

                    redrawMap();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }

        /**
         * Clears field
         *
         * @param field Object of FieldOfEditor
         */
        private void clearField(FieldOfEditor field) {

            field.terrains = Terrains.Grass;
            field.terrainForest = false;
            field.terrainMountain = false;
            field.terrainRiver = false;
            field.terrainGrass = true;
            field.player1StartLocation = false;
            field.player2StartLocation = false;
            field.mobRandomLevel1 = false;
            field.mobSkeletonLocation = false;
            field.mobWolfLocation = false;
            field.mobSpiderLocation = false;
            field.mobZombieLocation = false;
            field.mobRandomLevel2 = false;
            field.tresureBoxLvl1 = false;
            field.tresureBoxLvl2 = false;
            field.towerMagic = false;
            field.towerWisdom = false;
            field.towerSpeed = false;
            field.towerDefence = false;
            field.towerAttack = false;
            field.towerHp = false;
            field.towerWell = false;
            field.towerHospital = false;
            field.itemGold = false;
            field.manaPotion = false;
            field.healthPotion = false;
        }
    }

    static public MapEditor convertMapFileToMapEditor(MapEditor mapEditor, MapFile mapFile) {

        mapEditor.nameOfMap = mapFile.nameOfMap;
        mapEditor.mapColumns = mapFile.mapColumns;
        mapEditor.mapRows = mapFile.mapRows;

        for (int i = 0; i < mapFile.mapColumns; i++) {
            for (int j = 0; j < mapFile.mapRows; j++) {
                mapEditor.fields[i][j].coordinateXonMap = mapFile.fields[i][j].coordinateXonMap;
                mapEditor.fields[i][j].coordinateYonMap = mapFile.fields[i][j].coordinateYonMap;
                mapEditor.fields[i][j].player1StartLocation = mapFile.fields[i][j].player1StartLocation;
                mapEditor.fields[i][j].player2StartLocation = mapFile.fields[i][j].player2StartLocation;
                mapEditor.fields[i][j].player3StartLocation = mapFile.fields[i][j].player3StartLocation;
                mapEditor.fields[i][j].player4StartLocation = mapFile.fields[i][j].player4StartLocation;
                mapEditor.fields[i][j].terrainGrass = mapFile.fields[i][j].terrainGrass;
                mapEditor.fields[i][j].terrainForest = mapFile.fields[i][j].terrainForest;
                mapEditor.fields[i][j].terrainMountain = mapFile.fields[i][j].terrainMountain;
                mapEditor.fields[i][j].terrainRiver = mapFile.fields[i][j].terrainRiver;

                mapEditor.fields[i][j].mobRandomLevel1 = mapFile.fields[i][j].mobRandomLevel1;
                mapEditor.fields[i][j].mobSkeletonLocation = mapFile.fields[i][j].mobSkeletonLocation;
                mapEditor.fields[i][j].mobWolfLocation = mapFile.fields[i][j].mobWolfLocation;
                mapEditor.fields[i][j].mobSpiderLocation = mapFile.fields[i][j].mobSpiderLocation;
                mapEditor.fields[i][j].mobZombieLocation = mapFile.fields[i][j].mobZombieLocation;
                mapEditor.fields[i][j].mobRandomLevel2 = mapFile.fields[i][j].mobRandomLevel2;
                mapEditor.fields[i][j].tresureBoxLvl1 = mapFile.fields[i][j].tresureBoxLvl1;
                mapEditor.fields[i][j].tresureBoxLvl2 = mapFile.fields[i][j].tresureBoxLvl2;
                mapEditor.fields[i][j].towerMagic = mapFile.fields[i][j].towerMagic;
                mapEditor.fields[i][j].towerWisdom = mapFile.fields[i][j].towerWisdom;
                mapEditor.fields[i][j].towerSpeed = mapFile.fields[i][j].towerSpeed;
                mapEditor.fields[i][j].towerDefence = mapFile.fields[i][j].towerDefence;
                mapEditor.fields[i][j].towerAttack = mapFile.fields[i][j].towerAttack;
                mapEditor.fields[i][j].towerHp = mapFile.fields[i][j].towerHp;
                mapEditor.fields[i][j].towerWell = mapFile.fields[i][j].towerWell;
                mapEditor.fields[i][j].towerHospital = mapFile.fields[i][j].towerHospital;
                mapEditor.fields[i][j].itemGold = mapFile.fields[i][j].itemGold;
                mapEditor.fields[i][j].manaPotion = mapFile.fields[i][j].manaPotion;
                mapEditor.fields[i][j].healthPotion = mapFile.fields[i][j].healthPotion;

                switch (mapFile.fields[i][j].terrains) {
                    case Grass:
                        mapEditor.fields[i][j].terrains = Terrains.Grass;
                        break;
                    case Forest:
                        mapEditor.fields[i][j].terrains = Terrains.Forest;
                        break;
                    case Mountain:
                        mapEditor.fields[i][j].terrains = Terrains.Mountain;
                        break;
                    case River:
                        mapEditor.fields[i][j].terrains = Terrains.River;
                        break;
                    default:
                        mapEditor.fields[i][j].terrains = Terrains.Grass;
                }
            }
        }
        return mapEditor;
    }

    static public MapFile convertMapEditorToMapFile(MapEditor mapEditor, MapFile mapFile) {

        mapFile.nameOfMap = mapEditor.nameOfMap;
        mapFile.mapColumns = mapEditor.mapColumns;
        mapFile.mapRows = mapEditor.mapRows;

        for (int i = 0; i < mapEditor.mapColumns; i++) {
            for (int j = 0; j < mapEditor.mapRows; j++) {
                mapFile.fields[i][j].coordinateXonMap = mapEditor.fields[i][j].coordinateXonMap;
                mapFile.fields[i][j].coordinateYonMap = mapEditor.fields[i][j].coordinateYonMap;
                mapFile.fields[i][j].player1StartLocation = mapEditor.fields[i][j].player1StartLocation;
                mapFile.fields[i][j].player2StartLocation = mapEditor.fields[i][j].player2StartLocation;
                mapFile.fields[i][j].player3StartLocation = mapEditor.fields[i][j].player3StartLocation;
                mapFile.fields[i][j].player4StartLocation = mapEditor.fields[i][j].player4StartLocation;
                mapFile.fields[i][j].terrainGrass = mapEditor.fields[i][j].terrainGrass;
                mapFile.fields[i][j].terrainForest = mapEditor.fields[i][j].terrainForest;
                mapFile.fields[i][j].terrainMountain = mapEditor.fields[i][j].terrainMountain;
                mapFile.fields[i][j].terrainRiver = mapEditor.fields[i][j].terrainRiver;

                mapFile.fields[i][j].mobRandomLevel1 = mapEditor.fields[i][j].mobRandomLevel1;
                mapFile.fields[i][j].mobSkeletonLocation = mapEditor.fields[i][j].mobSkeletonLocation;
                mapFile.fields[i][j].mobWolfLocation = mapEditor.fields[i][j].mobWolfLocation;
                mapFile.fields[i][j].mobSpiderLocation = mapEditor.fields[i][j].mobSpiderLocation;
                mapFile.fields[i][j].mobZombieLocation = mapEditor.fields[i][j].mobZombieLocation;
                mapFile.fields[i][j].mobRandomLevel2 = mapEditor.fields[i][j].mobRandomLevel2;
                mapFile.fields[i][j].tresureBoxLvl1 = mapEditor.fields[i][j].tresureBoxLvl1;
                mapFile.fields[i][j].tresureBoxLvl2 = mapEditor.fields[i][j].tresureBoxLvl2;
                mapFile.fields[i][j].towerMagic = mapEditor.fields[i][j].towerMagic;
                mapFile.fields[i][j].towerWisdom = mapEditor.fields[i][j].towerWisdom;
                mapFile.fields[i][j].towerSpeed = mapEditor.fields[i][j].towerSpeed;
                mapFile.fields[i][j].towerDefence = mapEditor.fields[i][j].towerDefence;
                mapFile.fields[i][j].towerAttack = mapEditor.fields[i][j].towerAttack;
                mapFile.fields[i][j].towerHp = mapEditor.fields[i][j].towerHp;
                mapFile.fields[i][j].towerWell = mapEditor.fields[i][j].towerWell;
                mapFile.fields[i][j].towerHospital = mapEditor.fields[i][j].towerHospital;
                mapFile.fields[i][j].itemGold = mapEditor.fields[i][j].itemGold;
                mapFile.fields[i][j].manaPotion = mapEditor.fields[i][j].manaPotion;
                mapFile.fields[i][j].healthPotion = mapEditor.fields[i][j].healthPotion;


                switch (mapEditor.fields[i][j].terrains) {
                    case Grass:
                        mapFile.fields[i][j].terrains = MapFile.Terrains.Grass;
                        break;
                    case Forest:
                        mapFile.fields[i][j].terrains = MapFile.Terrains.Forest;
                        break;
                    case Mountain:
                        mapFile.fields[i][j].terrains = MapFile.Terrains.Mountain;
                        break;
                    case River:
                        mapFile.fields[i][j].terrains = MapFile.Terrains.River;
                        break;
                    default:
                        mapFile.fields[i][j].terrains = MapFile.Terrains.Grass;
                }
            }
        }
        return mapFile;
    }

}
