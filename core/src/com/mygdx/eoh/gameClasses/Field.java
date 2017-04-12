package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.eoh.Equipment.Treasure;
import com.mygdx.eoh.enums.Terrains;
import com.mygdx.eoh.items.Item;
import com.mygdx.eoh.mob.FreeMob;

/**
 * Representation of field
 * Created by v on 2016-10-12.
 */
public class Field extends Image {

    private int coordinateXonStage;
    private int coordinateYonStage;

    private boolean isMovable = true;

    private Terrains terrain;

    /***********************************************************************************************
     * BULDING VARIBLES
     **********************************************************************************************/
    private Bulding bulding;
    private Item item;
    private PlayerMob playerMob;
    private CastleMob castleMob;
    private Treasure treasure;
    private FreeMob freeMob;

    public Field() {
        super();
    }

    public Field(Texture texture) {
        super(texture);
    }

    public Field(TextureRegion region) {
        super(region);
    }


    public Terrains getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrains terrain) {
        this.terrain = terrain;
    }

    /**
     * Getting X coordinate on stage.
     *
     * @return X coordinate
     */
    public int getCoordinateXonStage() {
        return coordinateXonStage;
    }

    /**
     * Setting X coordinate on stage.
     *
     * @param coordinateXonStage
     */
    public void setCoordinateXonStage(int coordinateXonStage) {
        this.coordinateXonStage = coordinateXonStage;
    }

    /**
     * Getting X coordinate on stage.
     *
     * @return Y coordinate
     */
    public int getCoordinateYonStage() {
        return coordinateYonStage;
    }

    /**
     * Setting Y coordinate on stage.
     *
     * @param coordinateYonStage
     */
    public void setCoordinateYonStage(int coordinateYonStage) {
        this.coordinateYonStage = coordinateYonStage;
    }

    /**
     * Getting movable of the filed
     *
     * @return TRUE - is movable, FALS - isn't movable
     */
    public boolean isMovable() {
        return isMovable;
    }

    /**
     * Setting movable parametr of field.
     *
     * @param movable
     */
    public void setMovable(boolean movable) {
        isMovable = movable;
    }

    public Bulding getBulding() {
        return bulding;
    }

    public void setBulding(Bulding bulding) {
        this.bulding = bulding;
    }

    public PlayerMob getPlayerMob() {
        return playerMob;
    }

    public void setPlayerMob(PlayerMob playerMob) {
        this.playerMob = playerMob;
    }

    public CastleMob getCastleMob() {
        return castleMob;
    }

    public void setCastleMob(CastleMob castleMob) {
        this.castleMob = castleMob;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public void setTreasure(Treasure treasure) {
        this.treasure = treasure;
    }

    public FreeMob getFreeMob() {
        return freeMob;
    }

    public void setFreeMob(FreeMob freeMob) {
        this.freeMob = freeMob;
    }
}


