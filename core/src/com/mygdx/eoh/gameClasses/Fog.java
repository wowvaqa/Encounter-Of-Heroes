package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.eoh.Options.OptionsInGame;

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
        this.visibility = false;
        this.setTouchable(Touchable.disabled);
    }

    public static void turnOffVisibility(Fog fog) {
        if (fog.getFieldOwner().getTreasure() != null) {
            fog.getFieldOwner().getTreasure().setVisible(false);
        }
        if (fog.getFieldOwner().getFreeMob() != null) {
            fog.getFieldOwner().getFreeMob().setVisible(false);
        }
        if (fog.getFieldOwner().getItem() != null) {
            fog.getFieldOwner().getItem().setVisible(false);
        }
    }

    public static void turnOnVisibility(Fog fog) {
        if (fog.getFieldOwner().getTreasure() != null) {
            fog.getFieldOwner().getTreasure().setVisible(true);
        }
        if (fog.getFieldOwner().getFreeMob() != null) {
            fog.getFieldOwner().getFreeMob().setVisible(true);
        }
        if (fog.getFieldOwner().getItem() != null) {
            fog.getFieldOwner().getItem().setVisible(true);
        }
    }

    public static void turnOffFog() {
        for (Field field : GameStatus.getInstance().getFields()) {
            field.getFog().setVisible(false);

            Fog.turnOnVisibility(field.getFog());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (OptionsInGame.getInstance().isFog()) {
            if (this.visibility == true) {
                this.setVisible(false);
                Fog.turnOnVisibility(this);
            } else {
                this.setVisible(true);
                Fog.turnOffVisibility(this);
            }
        }
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
