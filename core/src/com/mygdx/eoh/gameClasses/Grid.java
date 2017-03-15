package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

/**
 * Representation of gird
 * Created by v on 2017-01-03.
 */

public class Grid extends Image {

    //private float locationXonStage;
    //private float locationYonStage;

    public Grid(Texture texture, float locationXonStage, float locationYonStage) {
        super(texture);

        setZIndex(2);

        //this.locationXonStage = locationXonStage;
        //this.locationYonStage = locationYonStage;

        this.setSize(Options.tileSize, Options.tileSize);
        this.setPosition(locationXonStage * Options.tileSize, locationYonStage * Options.tileSize);
        this.setTouchable(Touchable.disabled);
    }
}
