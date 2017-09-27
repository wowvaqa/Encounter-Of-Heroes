package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by v on 2017-09-27.
 */

public class Fog extends Image {
    private boolean visibility = false;
    private Field fieldOwner;

    public Fog(Texture texture, Field field) {
        super(texture);

        this.fieldOwner = field;
        this.setSize(Options.tileSize, Options.tileSize);
        this.setPosition(fieldOwner.getCoordinateXonStage(), fieldOwner.getCoordinateYonStage());
        this.visibility = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Field getFieldOwner() {
        return fieldOwner;
    }

    public void setFieldOwner(Field fieldOwner) {
        this.fieldOwner = fieldOwner;
    }
}
