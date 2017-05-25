package com.mygdx.eoh.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.eoh.assets.AssetsMapEditor;
import com.mygdx.eoh.defaultClasses.DefaultCamera;
import com.mygdx.eoh.defaultClasses.DefaultGestureDetector;
import com.mygdx.eoh.defaultClasses.DefaultGestureListener;
import com.mygdx.eoh.defaultClasses.DefaultScreen;
import com.mygdx.eoh.gameClasses.FileOperations;
import com.mygdx.eoh.mapEditor.MapEditor;
import com.mygdx.eoh.mapEditor.MapFile;

import java.io.IOException;

/**
 * Represents visualisation (screen)
 * Created by v on 2016-09-28.
 */
public class ScreenMapEditor extends DefaultScreen {

    private Interface interfaceManager;

    private FitViewport mapStageViewport;

    private Camera mapStageCamera;

    private MapEditor mapEditor;

    private Stage backgroundStage;
    private Stage mapStage;
    private Stage mainStage;

    public ScreenMapEditor() {

        mapStageCamera = new DefaultCamera(ScreenManager.WIDTH, ScreenManager.HEIGHT);
        mapStageViewport = new FitViewport(ScreenManager.WIDTH, ScreenManager.HEIGHT, mapStageCamera);

        mainStage = super.getMainStage();

        mapEditor = new MapEditor();

        interfaceManager = new Interface();

        backgroundStage = new Stage();

        createBackgroundStage();

        createTables();
    }

    /**
     * Create tables witch buttons.
     */
    private void createTables() {

        Table leftButtonsTable = new Table();
        Table rightButtonsTable = new Table();

        leftButtonsTable.add(interfaceManager.imageButtonNew).align(Align.topLeft).size(50, 50);
        leftButtonsTable.add(interfaceManager.imageButtonLoad).align(Align.topLeft).size(50, 50);
        leftButtonsTable.row();
        leftButtonsTable.add(interfaceManager.imageButtonSave).align(Align.topLeft).size(50, 50);
        leftButtonsTable.add(interfaceManager.imageButtonExit).align(Align.topLeft).size(50, 50);
        leftButtonsTable.row();
        leftButtonsTable.add(interfaceManager.imageButtonBrush).align(Align.topLeft).size(100, 100).colspan(2);
        leftButtonsTable.row();
        leftButtonsTable.add(interfaceManager.imageButtonCancelDraw).align(Align.topLeft).size(100, 100).colspan(2);
        leftButtonsTable.row();

        rightButtonsTable.add(interfaceManager.imageButtonZoomIn).size(75, 75);
        rightButtonsTable.row();
        rightButtonsTable.add(interfaceManager.imageButtonZoomOut).size(75, 75);
        rightButtonsTable.row();

        super.getMainTable().add(leftButtonsTable).align(Align.topLeft);
        super.getMainTable().add(new Table()).expandX();
        super.getMainTable().add(rightButtonsTable).align(Align.topRight);
        super.getMainTable().row();
        super.getMainTable().add(new Table()).expandY();
    }

    /**
     * Fills background stage with stars.
     */
    private void createBackgroundStage() {
        for (int j = -800; j < Gdx.graphics.getHeight() + 400; j += 400) {
            for (int i = -800; i < Gdx.graphics.getWidth() + 400; i += 400) {
                Image backgroundImage = new Image(
                        AssetsMapEditor.getInstance().getManager().get(
                                "mapEditor/background/starTile.png", Texture.class)
                );
                backgroundImage.setPosition(i, j);
                backgroundStage.addActor(backgroundImage);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (mapStage != null)
            mapStage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        backgroundStage.act();
        backgroundStage.draw();

        if (mapStage != null) {
            getInputMultiplexer().addProcessor(mapStage);
            mapStage.act();
            mapStage.draw();
        }

        super.getMainStage().act();
        super.getMainStage().draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (mapStage != null) {
            mapStage.dispose();
        }
        backgroundStage.dispose();
        AssetsMapEditor.getInstance().dispose();
    }

    @Override
    public void show() {
        super.show();
        if (mapStage != null) {
            super.getInputMultiplexer().addProcessor(mapStage);
        }
        Gdx.input.setInputProcessor(getInputMultiplexer());
    }

    private class Interface {

        public ImageButton imageButtonNew;
        public ImageButton imageButtonLoad;
        public ImageButton imageButtonSave;
        public ImageButton imageButtonExit;

        public ImageButton imageButtonCancelDraw;
        public ImageButton imageButtonBrush;
        public ImageButton imageButtonZoomIn;
        public ImageButton imageButtonZoomOut;

        public ImageButton.ImageButtonStyle imageButtonStyleBrush;

        public Interface() {
            createButtons();
        }

        /**
         * Creates frame around map.
         */
        private void createFreameAroudMap() {
            Texture leftFrameTexture = AssetsMapEditor.getInstance().getManager().get
                    ("mapEditor/background/frameLeft.png", Texture.class);
            Texture upFrameTexture = AssetsMapEditor.getInstance().getManager().get
                    ("mapEditor/background/frameUp.png", Texture.class);
            Texture rightFrameTexture = AssetsMapEditor.getInstance().getManager().get
                    ("mapEditor/background/frameRight.png", Texture.class);
            Texture downFrameTexture = AssetsMapEditor.getInstance().getManager().get
                    ("mapEditor/background/frameDown.png", Texture.class);

            for (int i = 0; i < mapEditor.mapRows * 100; i += 10) {
                Image frame = new Image(leftFrameTexture);
                frame.setPosition(-10, i);
                mapStage.addActor(frame);
            }

            for (int i = 0; i < mapEditor.mapRows * 100; i += 10) {
                Image frame = new Image(rightFrameTexture);
                frame.setPosition(mapEditor.mapColumns * 100, i);
                mapStage.addActor(frame);
            }

            for (int i = 0; i < mapEditor.mapColumns * 100; i += 10) {
                Image frame = new Image(upFrameTexture);
                frame.setPosition(i, mapEditor.mapRows * 100);
                mapStage.addActor(frame);
            }

            for (int i = 0; i < mapEditor.mapColumns * 100; i += 10) {
                Image frame = new Image(downFrameTexture);
                frame.setPosition(i, -10);
                mapStage.addActor(frame);
            }

            Image frame = new Image(AssetsMapEditor.getInstance().getManager().get("mapEditor/background/frameRightDown.png", Texture.class));
            frame.setPosition(mapEditor.mapColumns * 100, -10);
            mapStage.addActor(frame);

            frame = new Image(AssetsMapEditor.getInstance().getManager().get("mapEditor/background/frameLeftDown.png", Texture.class));
            frame.setPosition(-10, -10);
            mapStage.addActor(frame);

            frame = new Image(AssetsMapEditor.getInstance().getManager().get("mapEditor/background/frameLeftUp.png", Texture.class));
            frame.setPosition(-10, mapEditor.mapRows * 100);
            mapStage.addActor(frame);

            frame = new Image(AssetsMapEditor.getInstance().getManager().get("mapEditor/background/frameRightUp.png", Texture.class));
            frame.setPosition(mapEditor.mapColumns * 100, mapEditor.mapRows * 100);
            mapStage.addActor(frame);
        }

        /**
         * Create buttons.
         */
        private void createButtons() {

            imageButtonStyleBrush = new ImageButton.ImageButtonStyle();

            ImageButton.ImageButtonStyle imageButtonStyleNew = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleLoad = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleSave = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleExit = new ImageButton.ImageButtonStyle();

            ImageButton.ImageButtonStyle imageButtonStyleZoomIn = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleZoomOut = new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle imageButtonStyleCancelDraw = new ImageButton.ImageButtonStyle();

            imageButtonStyleNew.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/newButtonUp.png", Texture.class)
            ));
            imageButtonStyleNew.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/newButtonDown.png", Texture.class)
            ));
            imageButtonStyleLoad.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/loadButtonUp.png", Texture.class)
            ));
            imageButtonStyleLoad.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/loadButtonDown.png", Texture.class)
            ));
            imageButtonStyleSave.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/saveButtonUp.png", Texture.class)
            ));
            imageButtonStyleSave.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/saveButtonDown.png", Texture.class)
            ));
            imageButtonStyleExit.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/exitButtonUp.png", Texture.class)
            ));
            imageButtonStyleExit.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/exitButtonDown.png", Texture.class)
            ));
            imageButtonStyleBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/brushButtonDown.png", Texture.class)
            ));
            imageButtonStyleBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/brushButtonUp.png", Texture.class)
            ));
            imageButtonStyleCancelDraw.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/cancelBrushDown.png", Texture.class)
            ));
            imageButtonStyleCancelDraw.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/cancelBrushUp.png", Texture.class)
            ));
            imageButtonStyleZoomIn.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/zoomButtonInDown.png", Texture.class)
            ));
            imageButtonStyleZoomIn.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/zoomButtonInUp.png", Texture.class)
            ));
            imageButtonStyleZoomOut.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/zoomButtonOutDown.png", Texture.class)
            ));
            imageButtonStyleZoomOut.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/zoomButtonOutUp.png", Texture.class)
            ));

            imageButtonNew = new ImageButton(imageButtonStyleNew);
            imageButtonLoad = new ImageButton(imageButtonStyleLoad);
            imageButtonSave = new ImageButton(imageButtonStyleSave);
            imageButtonExit = new ImageButton(imageButtonStyleExit);
            imageButtonCancelDraw = new ImageButton(imageButtonStyleCancelDraw);
            imageButtonZoomIn = new ImageButton(imageButtonStyleZoomIn);
            imageButtonZoomOut = new ImageButton(imageButtonStyleZoomOut);
            imageButtonBrush = new ImageButton(imageButtonStyleBrush);

            addListeners();
        }

        /*******************************************************************************************
         * Adding listeners to main buttons of map editors
         ******************************************************************************************/
        private void addListeners() {
            imageButtonExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //AssetsMapEditor.getInstance().dispose();
                    ScreenManager.getInstance().setScreen(com.mygdx.eoh.enums.Screens.ScreenMainMenu);
                }
            });

            imageButtonNew.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Window window = getNewMapWindow();
                    window.setMovable(false);
                    window.setModal(true);

                    window.setPosition(
                            ScreenManager.WIDTH - ScreenManager.WIDTH / 2 - window.getWidth() / 2,
                            ScreenManager.HEIGHT - ScreenManager.HEIGHT / 2 - window.getHeight() / 2
                    );

                    mainStage.addActor(window);
                }
            });

            imageButtonSave.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    if (mapStage == null) {
                        Dialog dialog = new Dialog("UWAGA", (Skin) AssetsMapEditor.getInstance().getManager().get("styles/skin2.json")) {
                        };
                        dialog.text("Brak mapy do zapisu.");
                        dialog.button("OK");
                        dialog.show(mainStage);
                    } else {

                        Window window = getSaveMapWindow();
                        window.setMovable(false);
                        window.setModal(true);

                        window.setPosition(
                                ScreenManager.WIDTH - ScreenManager.WIDTH / 2 - window.getWidth() / 2,
                                ScreenManager.HEIGHT - ScreenManager.HEIGHT / 2 - window.getHeight() / 2
                        );

                        mainStage.addActor(window);
                    }
                }
            });

            imageButtonLoad.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    Window window = getLoadMapWindow();
                    window.setMovable(false);
                    window.setModal(true);

                    window.setPosition(
                            ScreenManager.WIDTH - ScreenManager.WIDTH / 2 - window.getWidth() / 2,
                            ScreenManager.HEIGHT - ScreenManager.HEIGHT / 2 - window.getHeight() / 2
                    );

                    mainStage.addActor(window);

                }
            });

            imageButtonBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    Window window = getBrushWindow();
                    window.setMovable(false);
                    window.setModal(true);

                    window.setPosition(
                            ScreenManager.WIDTH - ScreenManager.WIDTH / 2 - window.getWidth() / 2,
                            ScreenManager.HEIGHT - ScreenManager.HEIGHT / 2 - window.getHeight() / 2
                    );

                    mainStage.addActor(window);
                }
            });

            imageButtonCancelDraw.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(interfaceManager.imageButtonStyleBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.none;
                }
            });

            imageButtonZoomIn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ((DefaultCamera) mapStageCamera).attemptZoom(-0.5f);
                }
            });

            imageButtonZoomOut.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ((DefaultCamera) mapStageCamera).attemptZoom(0.5f);
                }
            });

        }

        /*******************************************************************************************
         * Returns New Map Window
         *
         * @return Window object
         *******************************************************************************************/
        public Window getNewMapWindow() {

            final Skin skin = AssetsMapEditor.getInstance().getManager().get("styles/skin.json");

            final Window window = new Window("Tworzenie nowej mapy", skin);
            window.setSize(600, 400);
            Label lblAmountOfXfields = new Label("Szerokość: ", skin);
            Label lblAmountOfYfields = new Label("Wysokość: ", skin);
            final TextField tfNameofMap = new TextField("", skin);
            final TextField tfAmountOfXflilds = new TextField("10", skin);
            final TextField tfAmountOfYflilds = new TextField("10", skin);
            TextButton tBmakeMap = new TextButton("OK", skin);
            TextButton tBcancel = new TextButton("ANULUJ", skin);

            tBmakeMap.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    try {
                        boolean isMapCreated = true;

                        if (mapStage != null) {
                            mapStage.clear();
                            isMapCreated = false;
                        }

                        mapStage = mapEditor.createStage(
                                tfNameofMap.getText(),
                                Integer.parseInt(tfAmountOfXflilds.getText()),
                                Integer.parseInt(tfAmountOfYflilds.getText()),
                                mapStageViewport
                        );

                        createFreameAroudMap();

                        if (isMapCreated) {
                            DefaultGestureListener myGL = new DefaultGestureListener(mapStage, mapEditor);
                            DefaultGestureDetector myGD = new DefaultGestureDetector(myGL);

                            getInputMultiplexer().clear();
                            getInputMultiplexer().addProcessor(myGD);
                            getInputMultiplexer().addProcessor(mainStage);
                        }

                        AssetsMapEditor.getInstance().disposePixmaps();

                        window.remove();
                    } catch (NumberFormatException e) {

                        Dialog dialog = new Dialog("BŁĄD", skin);
                        dialog.text("Niepoprawne wartości rozmiaru mapy");
                        dialog.button("OK");
                        dialog.show(mainStage);
                    }

                }
            });

            tBcancel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    window.remove();
                }
            });

            window.add(new Label("Nazwa mapy:", skin)).size(200, 70).colspan(2);
            window.add(tfNameofMap).pad(5).size(200, 70).colspan(2);
            window.row();
            window.add(lblAmountOfXfields).pad(5).size(150, 70).colspan(2);
            window.add(tfAmountOfXflilds).pad(5).size(200, 70).colspan(2);
            window.row();
            window.add(lblAmountOfYfields).pad(5).size(150, 70).colspan(2);
            window.add(tfAmountOfYflilds).pad(5).size(200, 70).colspan(2);
            window.row();
            window.add(tBcancel).pad(5).size(100, 50);
            window.add(new Table()).expandX();
            window.add(new Table()).expandX();
            window.add(tBmakeMap).pad(5).size(100, 50);
            window.row();

            return window;
        }

        /*******************************************************************************************
         * Returns Load Map Window
         *
         * @return Window object
         *******************************************************************************************/
        public Window getLoadMapWindow() {
            final Skin skin = AssetsMapEditor.getInstance().getManager().get("styles/skin.json");
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
            TextButton tbRemove = new TextButton("Usuń", skin);
            TextButton tbCancel = new TextButton("Cancel", skin);

            tbOk.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (listOfMap.getSelected() != null) {

                        if (mapStage == null) {
                            mapStage = new Stage(mapStageViewport);
                        }

                        mapEditor = mapEditor.loadMap(listOfMap, mainStage, mapEditor, mapStageViewport);
                        mapStage = mapEditor.getMapStage();
                        mapEditor.drawingType = MapEditor.DrawingType.none;

                        if (mapEditor.isMapLoaded) {

                            mapStage.clear();

                            createFreameAroudMap();

                            DefaultGestureListener myGL = new DefaultGestureListener(mapStage, mapEditor);
                            DefaultGestureDetector myGD = new DefaultGestureDetector(myGL);
                            getInputMultiplexer().clear();
                            getInputMultiplexer().addProcessor(myGD);

                            getInputMultiplexer().addProcessor(mainStage);

                            for (int i = 0; i < mapEditor.mapColumns; i++) {
                                for (int j = 0; j < mapEditor.mapRows; j++) {
                                    mapStage.addActor(mapEditor.fields[i][j]);
                                    mapEditor.fillField(mapEditor.fields[i][j]);
                                }
                            }
                        }
                        window.remove();
                    } else {
                        Dialog dialog = new Dialog("BŁĄD", skin);
                        dialog.text("Nie wybrano mapy");
                        dialog.button("OK");
                        dialog.show(mainStage);
                    }
                }

            });

            tbRemove.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    FileHandle file = listOfMap.getSelected();
                    Gdx.files.local(String.valueOf(file)).delete();
                    window.remove();

                    Window window = getLoadMapWindow();
                    window.setMovable(false);
                    window.setModal(true);

                    window.setPosition(
                            ScreenManager.WIDTH - ScreenManager.WIDTH / 2 - window.getWidth() / 2,
                            ScreenManager.HEIGHT - ScreenManager.HEIGHT / 2 - window.getHeight() / 2
                    );

                    mainStage.addActor(window);

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
            window.add(tbRemove).pad(5).size(100, 50);
            window.add(tbCancel).pad(5).size(100, 50);
            window.row();
            return window;
        }

        /*******************************************************************************************
         * Returns save map Window
         *
         * @return Window object
         ******************************************************************************************/
        public Window getSaveMapWindow() {
            final Skin skin = AssetsMapEditor.getInstance().getManager().get("styles/skin.json");

            final Window window = new Window("ZAPISZ MAPĘ", skin);
            window.setSize(600, 400);
            final TextField tfNameofMap = new TextField(mapEditor.nameOfMap, skin);
            TextButton tbOk = new TextButton("OK", skin);
            TextButton tbCancel = new TextButton("Cancel", skin);

            tbOk.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    boolean exists = Gdx.files.local("maps/" + tfNameofMap.getText() + ".map").exists();
                    if (exists) {
                        Dialog dialog = new Dialog("UWAGA", skin) {
                            public void result(Object obj) {
                                if (obj.equals(true)) {
                                    try {
                                        FileHandle file = Gdx.files.local("maps/" + tfNameofMap.getText() + ".map");
                                        //FileOperations.saveMap(file, mapEditor);
                                        MapFile mapFile = new MapFile(mapEditor.mapColumns, mapEditor.mapRows);
                                        mapFile = MapEditor.convertMapEditorToMapFile(mapEditor, mapFile);
                                        FileOperations.saveMap(file, mapFile);
                                        //mapEditor.saveMap(tfNameofMap.getText());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Dialog dialog = new Dialog("BŁĄD", skin);
                                        dialog.text("Nie można zapisać mapy");
                                        dialog.button("OK");
                                        dialog.show(mainStage);
                                    }
                                }
                            }
                        };
                        dialog.text("Mapa isnieje. Nadpisać?");
                        dialog.button("TAK", true); //sends "true" as the result
                        dialog.button("NIE", false);  //sends "false" as the result
                        dialog.show(mainStage);
                    } else {
                        try {
                            FileHandle file = Gdx.files.local("maps/" + tfNameofMap.getText() + ".map");
                            MapFile mapFile = new MapFile(mapEditor.mapColumns, mapEditor.mapRows);
                            mapFile = MapEditor.convertMapEditorToMapFile(mapEditor, mapFile);
                            FileOperations.saveMap(file, mapFile);
                            //mapEditor.saveMap(tfNameofMap.getText());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Dialog dialog = new Dialog("BŁĄD", skin);
                            dialog.text("Nie można zapisać mapy");
                            dialog.button("OK");
                            dialog.show(mainStage);
                        }
                    }
                    window.remove();
                }
            });
            tbCancel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    window.remove();
                }
            });

            window.add(new Label("Nazwa mapy:", skin)).colspan(2);
            window.row();
            window.add(tfNameofMap).pad(5).colspan(2);
            window.row();
            window.add(tbOk).pad(5).size(100, 50);
            window.add(tbCancel).pad(5).size(100, 50);
            window.row();

            return window;
        }

        /*******************************************************************************************
         * Returns brush Window
         *
         * @return Window object
         ******************************************************************************************/
        public Window getBrushWindow() {

            final Skin skin = AssetsMapEditor.getInstance().getManager().get("styles/skin.json");

            final Window window = new Window("Pedzel", skin);
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 600);

            final ImageButton.ImageButtonStyle imageButtonStyleTerrainBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleMobsBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStylePlayerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleBuldingBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleTresureBoxBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleRubberBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleMixtureBrush = new ImageButton.ImageButtonStyle();

            Texture terrainBrushUp = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/terrainBrushUp.png", Texture.class);
            Texture terrainBrushDown = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/terrainBrushDown.png", Texture.class);
            Texture mobsBrushUp = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/mobsBrushUp.png", Texture.class);
            Texture mobsBrushDown = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/mobsBrushDown.png", Texture.class);
            Texture playerBrushUp = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/playerBrushUp.png", Texture.class);
            Texture plyerBrushDown = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/playerBrushDown.png", Texture.class);
            Texture buldingBrushUp = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/buldingBrushUp.png", Texture.class);
            Texture buldingBrushDown = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/buldingBrushDown.png", Texture.class);
            Texture tresureBoxBrushUp = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/tresureBoxBrushUp.png", Texture.class);
            Texture tresureBoxBrushDown = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/tresureBoxBrushDown.png", Texture.class);
            Texture rubberBrushUp = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/rubberButtonUp.png", Texture.class);
            Texture rubberBrushDown = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/rubberButtonDown.png", Texture.class);
            Texture mixtureBrushUp = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/mixtureBrushUp.png", Texture.class);
            Texture mixtureBrushDown = AssetsMapEditor.getInstance().getManager().get(
                    "mapEditor/interface/brushWindow/mixtureBrushDown.png", Texture.class);

            imageButtonStyleTerrainBrush.imageUp = new TextureRegionDrawable(new TextureRegion(terrainBrushUp));
            imageButtonStyleTerrainBrush.imageDown = new TextureRegionDrawable(new TextureRegion(terrainBrushDown));
            imageButtonStyleMobsBrush.imageUp = new TextureRegionDrawable(new TextureRegion(mobsBrushUp));
            imageButtonStyleMobsBrush.imageDown = new TextureRegionDrawable(new TextureRegion(mobsBrushDown));
            imageButtonStylePlayerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(playerBrushUp));
            imageButtonStylePlayerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(plyerBrushDown));
            imageButtonStyleBuldingBrush.imageUp = new TextureRegionDrawable(new TextureRegion(buldingBrushUp));
            imageButtonStyleBuldingBrush.imageDown = new TextureRegionDrawable(new TextureRegion(buldingBrushDown));
            imageButtonStyleTresureBoxBrush.imageUp = new TextureRegionDrawable(new TextureRegion(tresureBoxBrushUp));
            imageButtonStyleTresureBoxBrush.imageDown = new TextureRegionDrawable(new TextureRegion(tresureBoxBrushDown));
            imageButtonStyleRubberBrush.imageUp = new TextureRegionDrawable(new TextureRegion(rubberBrushUp));
            imageButtonStyleRubberBrush.imageDown = new TextureRegionDrawable(new TextureRegion(rubberBrushDown));
            imageButtonStyleMixtureBrush.imageUp = new TextureRegionDrawable(new TextureRegion(mixtureBrushUp));
            imageButtonStyleMixtureBrush.imageDown = new TextureRegionDrawable(new TextureRegion(mixtureBrushDown));

            ImageButton imageButtonTerrainBrush = new ImageButton(imageButtonStyleTerrainBrush);
            ImageButton imageButtonPlayerBrush = new ImageButton(imageButtonStylePlayerBrush);
            ImageButton imageButtonMobsBrush = new ImageButton(imageButtonStyleMobsBrush);
            ImageButton imageButtonTresureBoxBrush = new ImageButton(imageButtonStyleTresureBoxBrush);
            ImageButton imageButtonBuldingBrush = new ImageButton(imageButtonStyleBuldingBrush);
            ImageButton imageButtonRubberBrush = new ImageButton(imageButtonStyleRubberBrush);
            ImageButton imageButtonMixtureBrush = new ImageButton(imageButtonStyleMixtureBrush);

            // Listeners
            imageButtonPlayerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    mainStage.addActor(getPlayerBrushWindow());
                    window.remove();
                }
            });

            imageButtonTerrainBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    mainStage.addActor(getTerrainBrushWindow());
                    window.remove();
                }
            });

            imageButtonTresureBoxBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    mainStage.addActor(getTresureBoxBrushWindow());
                    window.remove();
                }
            });

            imageButtonBuldingBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    mainStage.addActor(getBuldingBrushWindow());
                    window.remove();
                }
            });

            imageButtonMobsBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    mainStage.addActor(getMobsBrushWindow());
                    window.remove();
                }
            });

            imageButtonRubberBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleRubberBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.rubberDraw;
                    window.remove();
                }
            });

            imageButtonMixtureBrush.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    mainStage.addActor(getMixturesWindow());
                    window.remove();
                }
            });

            // Close button setup
            TextButton buttonClose = new TextButton("Zamknij", skin);
            buttonClose.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    window.remove();
                }
            });

            // Setting up size of buttons
            int buttonWidth = 200;
            int buttonHeight = 200;
            if (Gdx.graphics.getWidth() < window.getWidth()) {
                buttonWidth /= 2;
                buttonHeight /= 2;
                window.setSize(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50);
            }

            window.add(imageButtonTerrainBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonPlayerBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonMobsBrush).size(buttonWidth, buttonHeight);
            window.row();
            window.add(imageButtonTresureBoxBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonBuldingBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonRubberBrush).size(buttonWidth, buttonHeight);
            window.row();
            window.add(imageButtonMixtureBrush).size(buttonWidth, buttonHeight);
            window.row();
            window.add(buttonClose).size(100, 50).colspan(window.getColumns()).pad(10);

            return window;
        }

        /*******************************************************************************************
         * Returns player Brush Window
         *
         * @return Window object
         ******************************************************************************************/
        public Window getPlayerBrushWindow() {
            final Skin skin = AssetsMapEditor.getInstance().getManager().get("styles/skin.json");

            final Window window = new Window("Gracze", skin);
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 600);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStylePlayer01Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStylePlayer02Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStylePlayer03Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStylePlayer04Brush = new ImageButton.ImageButtonStyle();

            imageButtonStylePlayer01Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player01BrushUp.png", Texture.class)
            ));
            imageButtonStylePlayer01Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player01BrushDown.png", Texture.class)
            ));
            imageButtonStylePlayer02Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player02BrushUp.png", Texture.class)
            ));
            imageButtonStylePlayer02Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player02BrushDown.png", Texture.class)
            ));
            imageButtonStylePlayer03Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player03BrushUp.png", Texture.class)
            ));
            imageButtonStylePlayer03Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player03BrushDown.png", Texture.class)
            ));
            imageButtonStylePlayer04Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player04BrushUp.png", Texture.class)
            ));
            imageButtonStylePlayer04Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/playerWindow/player04BrushDown.png", Texture.class)
            ));

            ImageButton imagePlayer01 = new ImageButton(imageButtonStylePlayer01Brush);
            ImageButton imagePlayer02 = new ImageButton(imageButtonStylePlayer02Brush);
            ImageButton imagePlayer03 = new ImageButton(imageButtonStylePlayer03Brush);
            ImageButton imagePlayer04 = new ImageButton(imageButtonStylePlayer04Brush);

            imagePlayer01.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.player1Draw;
                    imageButtonBrush.setStyle(imageButtonStylePlayer01Brush);
                    window.remove();
                }
            });
            imagePlayer02.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.player2Draw;
                    imageButtonBrush.setStyle(imageButtonStylePlayer02Brush);
                    window.remove();
                }
            });
            imagePlayer03.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.player3Draw;
                    imageButtonBrush.setStyle(imageButtonStylePlayer03Brush);
                    window.remove();
                }
            });
            imagePlayer04.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.player4Draw;
                    imageButtonBrush.setStyle(imageButtonStylePlayer04Brush);
                    window.remove();
                }
            });

            int buttonWidth = 200;
            int buttonHeight = 200;
            if (Gdx.graphics.getWidth() < window.getWidth() ||
                    Gdx.graphics.getHeight() < window.getHeight()) {
                buttonWidth /= 2;
                buttonHeight /= 2;
                window.setSize(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100);

                window.setPosition(
                        Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                        Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
                );
            }

            window.add(imagePlayer01).size(buttonWidth, buttonHeight);
            window.add(imagePlayer02).size(buttonWidth, buttonHeight);
            window.add(imagePlayer03).size(buttonWidth, buttonHeight);
            window.add(imagePlayer04).size(buttonWidth, buttonHeight);
            window.add(getCancelImageButton(window)).size(buttonWidth, buttonHeight);

            return window;
        }

        /*******************************************************************************************
         * Returns terrain Brush Window
         *
         * @return Window object
         ******************************************************************************************/
        public Window getTerrainBrushWindow() {
            final Skin skin = AssetsMapEditor.getInstance().getManager().get("styles/skin.json");

            final Window window = new Window("Teren", skin);
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 400);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStyleForestBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleMountainBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleRiverBrush = new ImageButton.ImageButtonStyle();

            imageButtonStyleForestBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/terrainWindow/forestBrushUp.png", Texture.class)
            ));
            imageButtonStyleForestBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/terrainWindow/forestBrushDown.png", Texture.class)
            ));
            imageButtonStyleMountainBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/terrainWindow/mountainBrushUp.png", Texture.class)
            ));
            imageButtonStyleMountainBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/terrainWindow/mountainBrushDown.png", Texture.class)
            ));
            imageButtonStyleRiverBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/terrainWindow/riverBrushUp.png", Texture.class)
            ));
            imageButtonStyleRiverBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/terrainWindow/riverBrushDown.png", Texture.class)
            ));

            ImageButton imageButtonForestBrush = new ImageButton(imageButtonStyleForestBrush);
            ImageButton imageButtonMountainBrush = new ImageButton(imageButtonStyleMountainBrush);
            ImageButton imageButtonRiverBrush = new ImageButton(imageButtonStyleRiverBrush);

            imageButtonForestBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleForestBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.forestDraw;
                    window.remove();
                }
            });

            imageButtonMountainBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleMountainBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.mountainDraw;
                    window.remove();
                }
            });

            imageButtonRiverBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleRiverBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.riverDraw;
                    window.remove();
                }
            });

            // Setting up size of buttons
            int buttonWidth = 200;
            int buttonHeight = 200;
            if (Gdx.graphics.getWidth() < window.getWidth()) {
                buttonWidth /= 2;
                buttonHeight /= 2;
                window.setSize(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50);
            }

            window.add(imageButtonForestBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonMountainBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonRiverBrush).size(buttonWidth, buttonHeight);
            window.add(getCancelImageButton(window)).size(buttonWidth, buttonHeight);

            return window;
        }

        /*******************************************************************************************
         * Returns tresure box brush window.
         *
         * @return Window object
         ******************************************************************************************/
        public Window getTresureBoxBrushWindow() {
            final Skin skin = AssetsMapEditor.getInstance().getManager().get("styles/skin.json");

            final Window window = new Window("Skrzynie ze skarbem", skin);
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 400);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStyleTresureBoxLvl01Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleTresureBoxLvl02Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleGoldBrush = new ImageButton.ImageButtonStyle();

            imageButtonStyleTresureBoxLvl01Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/tresureBoxWindow/tresureBoxLvl1BrushUp.png", Texture.class)
            ));
            imageButtonStyleTresureBoxLvl01Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/tresureBoxWindow/tresureBoxLvl1BrushDown.png", Texture.class)
            ));
            imageButtonStyleTresureBoxLvl02Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/tresureBoxWindow/tresureBoxLvl2BrushUp.png", Texture.class)
            ));
            imageButtonStyleTresureBoxLvl02Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/tresureBoxWindow/tresureBoxLvl2BrushDown.png", Texture.class)
            ));
            imageButtonStyleGoldBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/tresureBoxWindow/goldBrushUp.png", Texture.class)
            ));
            imageButtonStyleGoldBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/tresureBoxWindow/goldBrushDown.png", Texture.class)
            ));

            ImageButton imageButtonTresureBox01 = new ImageButton(imageButtonStyleTresureBoxLvl01Brush);
            ImageButton imageButtonTresureBox02 = new ImageButton(imageButtonStyleTresureBoxLvl02Brush);
            final ImageButton imageButtonGold = new ImageButton(imageButtonStyleGoldBrush);

            imageButtonTresureBox01.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.tresureBox1Draw;
                    imageButtonBrush.setStyle(imageButtonStyleTresureBoxLvl01Brush);
                    window.remove();
                }
            });

            imageButtonTresureBox02.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.tresureBox2Draw;
                    imageButtonBrush.setStyle(imageButtonStyleTresureBoxLvl02Brush);
                    window.remove();
                }
            });

            imageButtonGold.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.itemGold;
                    imageButtonBrush.setStyle(imageButtonStyleGoldBrush);
                    window.remove();
                }
            });

            window.add(imageButtonTresureBox01);
            window.add(imageButtonTresureBox02);
            window.add(imageButtonGold);
            window.add(getCancelImageButton(window));

            return window;
        }

        /*******************************************************************************************
         * Returns bulding Brush Window
         *
         * @return Window object
         *******************************************************************************************/
        public Window getBuldingBrushWindow() {

            final Window window = new Window("Budynki", AssetsMapEditor.getInstance().getManager().get("styles/skin.json", Skin.class));
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 600);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStyleMagicTowerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleWisdomTowerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleDefenceTowerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleSpeedTowerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleAttackTowerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleHpTowerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleWellTowerBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleHospitalTowerBrush = new ImageButton.ImageButtonStyle();

            imageButtonStyleMagicTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/magicTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleMagicTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/magicTowerBrushDown.png", Texture.class)
            ));
            imageButtonStyleWisdomTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/wisdomTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleWisdomTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/wisdomTowerBrushDown.png", Texture.class)
            ));
            imageButtonStyleDefenceTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/defenceTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleDefenceTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/defenceTowerBrushDown.png", Texture.class)
            ));
            imageButtonStyleSpeedTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/speedTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleSpeedTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/speedTowerBrushDown.png", Texture.class)
            ));
            imageButtonStyleAttackTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/attackTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleAttackTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/attackTowerBrushDown.png", Texture.class)
            ));
            imageButtonStyleHpTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/hpTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleHpTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/hpTowerBrushDown.png", Texture.class)
            ));
            imageButtonStyleWellTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/wellTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleWellTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/wellTowerBrushDown.png", Texture.class)
            ));
            imageButtonStyleHospitalTowerBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/hospitalTowerBrushUp.png", Texture.class)
            ));
            imageButtonStyleHospitalTowerBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/buldingsWindow/hospitalTowerBrushDown.png", Texture.class)
            ));

            ImageButton imageButtonMagicTowerBrush = new ImageButton(imageButtonStyleMagicTowerBrush);
            ImageButton imageButtonWisdomTowerBrush = new ImageButton(imageButtonStyleWisdomTowerBrush);
            ImageButton imageButtonDefenceTowerBrush = new ImageButton(imageButtonStyleDefenceTowerBrush);
            ImageButton imageButtonSpeedTowerBrush = new ImageButton(imageButtonStyleSpeedTowerBrush);
            ImageButton imageButtonAttackTowerBrush = new ImageButton(imageButtonStyleAttackTowerBrush);
            ImageButton imageButtonHpTowerBrush = new ImageButton(imageButtonStyleHpTowerBrush);
            ImageButton imageButtonWellTowerBrush = new ImageButton(imageButtonStyleWellTowerBrush);
            ImageButton imageButtonHospitalTowerBrush = new ImageButton(imageButtonStyleHospitalTowerBrush);

            imageButtonMagicTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleMagicTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerMagicDraw;
                    window.remove();
                }
            });

            imageButtonWisdomTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleWisdomTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerWisdomDraw;
                    window.remove();
                }
            });

            imageButtonDefenceTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleDefenceTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerDefenceDraw;
                    window.remove();
                }
            });

            imageButtonSpeedTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleSpeedTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerSpeedDraw;
                    window.remove();
                }
            });

            imageButtonAttackTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleAttackTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerAttackDraw;
                    window.remove();
                }
            });

            imageButtonHpTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleHpTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerHpDraw;
                    window.remove();
                }
            });

            imageButtonWellTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleWellTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerWellDraw;
                    window.remove();
                }
            });

            imageButtonHospitalTowerBrush.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    imageButtonBrush.setStyle(imageButtonStyleHospitalTowerBrush);
                    mapEditor.drawingType = MapEditor.DrawingType.towerHospitalDraw;
                    window.remove();
                }
            });

            // Setting up size of buttons
            int buttonWidth = 200;
            int buttonHeight = 200;
            if (Gdx.graphics.getWidth() < window.getWidth() ||
                    Gdx.graphics.getHeight() < window.getHeight()) {
                buttonWidth /= 2;
                buttonHeight /= 2;
                window.setSize(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100);

                window.setPosition(
                        Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                        Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
                );
            }

            window.add(imageButtonMagicTowerBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonWisdomTowerBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonDefenceTowerBrush).size(buttonWidth, buttonHeight);
            window.row();
            window.add(imageButtonSpeedTowerBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonAttackTowerBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonHpTowerBrush).size(buttonWidth, buttonHeight);
            window.row();
            window.add(imageButtonWellTowerBrush).size(buttonWidth, buttonHeight);
            window.add(imageButtonHospitalTowerBrush).size(buttonWidth, buttonHeight);
            window.add(getCancelImageButton(window)).size(buttonWidth, buttonHeight);

            return window;
        }

        /*******************************************************************************************
         * Returns mobs Brush Window
         *
         * @return Window object
         *******************************************************************************************/
        public Window getMobsBrushWindow() {

            final Window window = new Window("Skrzynie ze skarbem",
                    (Skin) AssetsMapEditor.getInstance().getManager().get("styles/skin.json"));
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 400);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStyleMobLvl1Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleMobLvl2Brush = new ImageButton.ImageButtonStyle();

            imageButtonStyleMobLvl1Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushLvl1Up.png", Texture.class)
            ));
            imageButtonStyleMobLvl1Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushLvl1Down.png", Texture.class)
            ));
            imageButtonStyleMobLvl2Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushLvl2Up.png", Texture.class)
            ));
            imageButtonStyleMobLvl2Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushLvl2Down.png", Texture.class)
            ));

            ImageButton imageButtonMobsLvl1 = new ImageButton(imageButtonStyleMobLvl1Brush);
            ImageButton imageButtonMobsLvl2 = new ImageButton(imageButtonStyleMobLvl2Brush);

            imageButtonMobsLvl1.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mainStage.addActor(getMobsLvl1BrushWindow());
                    window.remove();
                }
            });

            imageButtonMobsLvl2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mainStage.addActor(getMobsLvl2BrushWindow());
                    window.remove();
                }
            });

            window.add(imageButtonMobsLvl1);
            window.add(imageButtonMobsLvl2);
            window.add(getCancelImageButton(window));

            return window;
        }

        /*******************************************************************************************
         * Returns mobs Brush Window
         *
         * @return Window object
         *******************************************************************************************/
        public Window getMobsLvl1BrushWindow() {

            final Window window = new Window("Skrzynie ze skarbem",
                    (Skin) AssetsMapEditor.getInstance().getManager().get("styles/skin.json"));
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 400);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStyleMob01Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleMob02Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleMobRandomBrush = new ImageButton.ImageButtonStyle();

            imageButtonStyleMob01Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushSkeletonUp.png", Texture.class)
            ));
            imageButtonStyleMob01Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushSkeletonDown.png", Texture.class)
            ));
            imageButtonStyleMob02Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushWolfUp.png", Texture.class)
            ));
            imageButtonStyleMob02Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushWolfDown.png", Texture.class)
            ));
            imageButtonStyleMobRandomBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushRandomUp.png", Texture.class)
            ));
            imageButtonStyleMobRandomBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushRandomDown.png", Texture.class)
            ));

            ImageButton imageButtonMob01 = new ImageButton(imageButtonStyleMob01Brush);
            ImageButton imageButtonMob02 = new ImageButton(imageButtonStyleMob02Brush);
            ImageButton imageButtonMobRandom = new ImageButton(imageButtonStyleMobRandomBrush);

            imageButtonMob01.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.mobSkeletonDraw;
                    imageButtonBrush.setStyle(imageButtonStyleMob01Brush);
                    window.remove();
                }
            });

            imageButtonMob02.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.mobWolfDraw;
                    imageButtonBrush.setStyle(imageButtonStyleMob02Brush);
                    window.remove();
                }
            });

            imageButtonMobRandom.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.mobRandomLvl1Draw;
                    imageButtonBrush.setStyle(imageButtonStyleMobRandomBrush);
                    window.remove();
                }
            });

            window.add(imageButtonMob01);
            window.add(imageButtonMob02);
            window.add(imageButtonMobRandom);
            window.add(getCancelImageButton(window));

            return window;
        }

        /*******************************************************************************************
         * Returns mobs Brush Window
         *
         * @return Window object
         ******************************************************************************************/
        public Window getMobsLvl2BrushWindow() {

            final Window window = new Window("Skrzynie ze skarbem",
                    (Skin) AssetsMapEditor.getInstance().getManager().get("styles/skin.json"));
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 400);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStyleMob01Brush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleMob02Brush = new ImageButton.ImageButtonStyle();
            //final ImageButton.ImageButtonStyle imageButtonStyleMob02Brush = new ImageButton.ImageButtonStyle();
            //final ImageButton.ImageButtonStyle imageButtonStyleMobRandomBrush = new ImageButton.ImageButtonStyle();

            // BARBARIAN
            imageButtonStyleMob01Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushBarbarianUp.png", Texture.class)
            ));
            imageButtonStyleMob01Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushBarbarianDown.png", Texture.class)
            ));
            // GOLEM
            imageButtonStyleMob02Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushLavaGolemUp.png", Texture.class)
            ));
            imageButtonStyleMob02Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushLavaGolemDown.png", Texture.class)
            ));

//            imageButtonStyleMob01Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
//                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushSpiderUp.png", Texture.class)
//            ));
//            imageButtonStyleMob01Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
//                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushSpiderDown.png", Texture.class)
//            ));
//            imageButtonStyleMob02Brush.imageUp = new TextureRegionDrawable(new TextureRegion(
//                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushZombieUp.png", Texture.class)
//            ));
//            imageButtonStyleMob02Brush.imageDown = new TextureRegionDrawable(new TextureRegion(
//                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushZombieDown.png", Texture.class)
//            ));
//            imageButtonStyleMobRandomBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
//                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushRandomUp.png", Texture.class)
//            ));
//            imageButtonStyleMobRandomBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
//                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mobsWindow/mobsBrushRandomDown.png", Texture.class)
//            ));

            ImageButton imageButtonMob01 = new ImageButton(imageButtonStyleMob01Brush);
            ImageButton imageButtonMob02 = new ImageButton(imageButtonStyleMob02Brush);
//            ImageButton imageButtonMob02 = new ImageButton(imageButtonStyleMob02Brush);
//            ImageButton imageButtonMobRandom = new ImageButton(imageButtonStyleMobRandomBrush);

            imageButtonMob01.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.mobBarbarianDraw;
                    imageButtonBrush.setStyle(imageButtonStyleMob01Brush);
                    window.remove();
                }
            });

            imageButtonMob02.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.mobLavaGolemDraw;
                    imageButtonBrush.setStyle(imageButtonStyleMob02Brush);
                    window.remove();
                }
            });

//            imageButtonMob02.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    mapEditor.drawingType = MapEditor.DrawingType.mobZombieDraw;
//                    imageButtonBrush.setStyle(imageButtonStyleMob02Brush);
//                    window.remove();
//                }
//            });
//
//            imageButtonMobRandom.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    mapEditor.drawingType = MapEditor.DrawingType.mobRandomLvl2Draw;
//                    imageButtonBrush.setStyle(imageButtonStyleMobRandomBrush);
//                    window.remove();
//                }
//            });

            window.add(imageButtonMob01);
            window.add(imageButtonMob02);
//            window.add(imageButtonMob02);
//            window.add(imageButtonMobRandom);
            window.add(getCancelImageButton(window));

            return window;
        }

        /*******************************************************************************************
         * Returns mobs Brush Window
         *
         * @return Window object
         ******************************************************************************************/
        public Window getMixturesWindow() {

            final Window window = new Window("Skrzynie ze skarbem",
                    (Skin) AssetsMapEditor.getInstance().getManager().get("styles/skin.json"));
            window.setMovable(false);
            window.setModal(true);
            window.setSize(1000, 400);
            window.setPosition(
                    Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 2 - window.getWidth() / 2,
                    Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 2 - window.getHeight() / 2
            );

            final ImageButton.ImageButtonStyle imageButtonStyleHealthBrush = new ImageButton.ImageButtonStyle();
            final ImageButton.ImageButtonStyle imageButtonStyleManaBrush = new ImageButton.ImageButtonStyle();

            imageButtonStyleHealthBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mixtureWindow/healthPotionBrushUp.png", Texture.class)
            ));
            imageButtonStyleHealthBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mixtureWindow/healthPotionBrushDown.png", Texture.class)
            ));
            imageButtonStyleManaBrush.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mixtureWindow/manaPotionBrushUp.png", Texture.class)
            ));
            imageButtonStyleManaBrush.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/mixtureWindow/manaPotionBrushDown.png", Texture.class)
            ));

            ImageButton imageButtonHealth = new ImageButton(imageButtonStyleHealthBrush);
            ImageButton imageButtonMana = new ImageButton(imageButtonStyleManaBrush);

            imageButtonHealth.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.healthPotionDraw;
                    imageButtonBrush.setStyle(imageButtonStyleHealthBrush);
                    window.remove();
                }
            });

            imageButtonMana.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mapEditor.drawingType = MapEditor.DrawingType.manaPotionDraw;
                    imageButtonBrush.setStyle(imageButtonStyleManaBrush);
                    window.remove();
                }
            });

            window.add(imageButtonHealth);
            window.add(imageButtonMana);
            window.add(getCancelImageButton(window));

            return window;
        }

        /*******************************************************************************************
         * Return image button Cancel
         *
         * @param window Window which will be removed over button
         * @return ImageButton object
         ******************************************************************************************/
        public ImageButton getCancelImageButton(final Window window) {
            ImageButton.ImageButtonStyle imageButtonStyleCancel = new ImageButton.ImageButtonStyle();

            imageButtonStyleCancel.imageUp = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/brushWindow/cancelBrushUp.png", Texture.class)
            ));
            imageButtonStyleCancel.imageDown = new TextureRegionDrawable(new TextureRegion(
                    AssetsMapEditor.getInstance().getManager().get("mapEditor/interface/brushWindow/cancelBrushDown.png", Texture.class)
            ));

            ImageButton imageButtonCancel = new ImageButton(imageButtonStyleCancel);

            imageButtonCancel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    window.remove();
                }
            });

            return imageButtonCancel;
        }
    }
}
