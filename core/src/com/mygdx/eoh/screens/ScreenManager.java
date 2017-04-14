package com.mygdx.eoh.screens;

import com.badlogic.gdx.Game;
import com.mygdx.eoh.gameClasses.GameStatus;

/**
 * Managing screens
 * Created by v on 2016-09-19.
 */
public final class ScreenManager {

    public static float WIDTH;
    public static float HEIGHT;
    private static ScreenManager instance;
    private Game game;
    private Object currentScreen = null;

    private ScreenManager() {
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initalize(Game game) {
        this.game = game;
    }

    public void setScreen(com.mygdx.eoh.enums.Screens screen) {
        switch (screen) {
            case ScreenMainMenu:
                disposeCurrentScreen(currentScreen);
                currentScreen = new ScreenMainMenu();
                game.setScreen((ScreenMainMenu) currentScreen);
                break;
            case ScreenSingleGame:
                disposeCurrentScreen(currentScreen);
                currentScreen = new ScreenSingleGame();
                game.setScreen((ScreenSingleGame) currentScreen);
                break;
            case ScreenMapEditor:
                disposeCurrentScreen(currentScreen);
                currentScreen = new ScreenMapEditor();
                game.setScreen((ScreenMapEditor) currentScreen);
                break;
            case ScreenNewNetGame:
                disposeCurrentScreen(currentScreen);
                currentScreen = new ScreenNewNetGame();
                game.setScreen((ScreenNewNetGame) currentScreen);
                break;
            case ScreenNetGame:
                disposeCurrentScreen(currentScreen);
                currentScreen = new ScreenNetGame(GameStatus.getInstance());
                game.setScreen((ScreenNetGame) currentScreen);
                break;
            case ScreenLoading:
                disposeCurrentScreen(currentScreen);
                currentScreen = new ScreenLoading();
                game.setScreen((ScreenLoading) currentScreen);
                break;
        }
    }

    private void disposeCurrentScreen(Object object) {
        if (object != null) {
            if (object.getClass().equals(ScreenMainMenu.class)) {
                ScreenMainMenu screen = (ScreenMainMenu) object;
                screen.dispose();
            } else if (object.getClass().equals(ScreenMapEditor.class)) {
                ScreenMapEditor screen = (ScreenMapEditor) object;
                screen.dispose();
            } else if (object.getClass().equals(ScreenSingleGame.class)) {
                ScreenSingleGame screen = (ScreenSingleGame) object;
                screen.dispose();
            } else if (object.getClass().equals(ScreenNewNetGame.class)) {
                ScreenNewNetGame screen = (ScreenNewNetGame) object;
                screen.dispose();
            } else if (object.getClass().equals(ScreenNetGame.class)) {
                ScreenNetGame screen = (ScreenNetGame) object;
                screen.dispose();
            } else if (object.getClass().equals(ScreenLoading.class)){
                ScreenLoading screen = (ScreenLoading) object;
                screen.dispose();
            }
        }
    }
}
