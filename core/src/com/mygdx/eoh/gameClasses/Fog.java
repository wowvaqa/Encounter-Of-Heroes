package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.eoh.Options.OptionsInGame;

/**
 * Fog class to cover fields.
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

    /**
     * Wyłącza widoczność elementów po mgłą.
     *
     * @param fog
     */
    public static void turnOffVisibility(Fog fog) {
        if (fog.getFieldOwner().getTreasure() != null) {
            fog.getFieldOwner().getTreasure().setVisible(false);
        }
        if (fog.getFieldOwner().getFreeMob() != null) {
            fog.getFieldOwner().getFreeMob().setVisible(false);
            fog.getFieldOwner().getFreeMob().getHpBar().setVisible(false);
            fog.getFieldOwner().getFreeMob().getApBar().setVisible(false);
        }
        if (fog.getFieldOwner().getItem() != null) {
            fog.getFieldOwner().getItem().setVisible(false);
        }
        if (fog.getFieldOwner().getPlayerMob() != null && fog.getFieldOwner().getPlayerMob().getAi() != null) {
            fog.getFieldOwner().getPlayerMob().setVisible(false);
            fog.getFieldOwner().getPlayerMob().getPlayerColorImage().setVisible(false);
            fog.getFieldOwner().getPlayerMob().getApBar().setVisible(false);
            fog.getFieldOwner().getPlayerMob().getHpBar().setVisible(false);
            fog.getFieldOwner().getPlayerMob().getManaBar().setVisible(false);
        }


    }

    /**
     * Włącza widoczność elemntów pod mgłą.
     * @param fog
     */
    public static void turnOnVisibility(Fog fog) {
        if (fog.getFieldOwner().getTreasure() != null) {
            fog.getFieldOwner().getTreasure().setVisible(true);
        }
        if (fog.getFieldOwner().getFreeMob() != null) {
            fog.getFieldOwner().getFreeMob().setVisible(true);
            fog.getFieldOwner().getFreeMob().getHpBar().setVisible(true);
            fog.getFieldOwner().getFreeMob().getApBar().setVisible(true);
        }
        if (fog.getFieldOwner().getItem() != null) {
            fog.getFieldOwner().getItem().setVisible(true);
        }
        if (fog.getFieldOwner().getPlayerMob() != null && fog.getFieldOwner().getPlayerMob().getAi() != null) {
            fog.getFieldOwner().getPlayerMob().setVisible(true);
            fog.getFieldOwner().getPlayerMob().getPlayerColorImage().setVisible(true);
            fog.getFieldOwner().getPlayerMob().getApBar().setVisible(true);
            fog.getFieldOwner().getPlayerMob().getHpBar().setVisible(true);
            fog.getFieldOwner().getPlayerMob().getManaBar().setVisible(true);
        }
    }

    /**
     * Wyłącza całkowicie mgłę
     */
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
            if (this.visibility) {
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
}
