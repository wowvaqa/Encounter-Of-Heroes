package com.mygdx.eoh.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.eoh.Eoh;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new Eoh(), config);

        config.width = 800;
        config.height = 600;
        config.fullscreen = false;

//        config.width = 1920;
//        config.height = 1080;
//        config.fullscreen = true;
    }
}
