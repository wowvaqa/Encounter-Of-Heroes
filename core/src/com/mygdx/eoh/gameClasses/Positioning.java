package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 *
 * Created by v on 2016-12-26.
 */

public class Positioning {
    static public void setWindowToCenter(Window window){
        window.setPosition(
                (Gdx.graphics.getWidth() / 2) - (window.getWidth() / 2),
                (Gdx.graphics.getHeight() / 2) - (window.getHeight() / 2)
        );

    }
}
