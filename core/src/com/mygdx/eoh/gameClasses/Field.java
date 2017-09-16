package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.eoh.Equipment.Treasure;
import com.mygdx.eoh.enums.Terrains;
import com.mygdx.eoh.items.Item;
import com.mygdx.eoh.mob.FreeMob;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Representation of field
 * Created by v on 2016-10-12.
 */
public class Field extends Image {

    // Długość (koszt) ścieżki prowadzącej z punktu startu do aktualnej, rozpatrywanej pozycji w
    // przestrzeni (jest to rzeczywista długość ścieżki, którą już wygenerowaliśmy)
    public double pathG;
    //szacunkowa długość ścieżki prowadząca z aktualnej pozycji do punktu docelowego; wartość H
    // jest najczęściej wyznaczana metodami heurystycznymi, gdyż z oczywistego względu nie znamy tej
    // długości (gdyby tak było, użycie takiego algorytmu byłoby niepotrzebne)
    public double pathH;
    // F = G+H – wartość równa sumie długości dwóch powyższych ścieżek.
    public double pathF;

    // Pole rodzica do znajdywania ścieżki.
    public Field parentField;

    // Określa czy pole jest początkiem ścieżki.
    public boolean startField = false;
    // Określa czy pole jest końcem ścieżki.
    public boolean endField = false;

    // położenie pola na mapie
    public int locXonMap;
    public int locYonMap;


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

    /***********************************************************************************************
     * PATHFINDING
     **********************************************************************************************/
    private ArrayList<DefaultConnection> connections;

    public Field() {
        super();
    }

    public Field(Texture texture, Terrains terrain) {
        super(texture);
        this.terrain = terrain;

        if (terrain.equals(Terrains.Mountain))
            setMovable(false);

        connections = new ArrayList<DefaultConnection>();
    }

    public Field(TextureRegion region, Terrains terrain) {
        super(region);
        this.terrain = terrain;

        if (terrain.equals(Terrains.Mountain))
            setMovable(false);

        connections = new ArrayList<DefaultConnection>();
    }

    /**
     * Czyści rodziców każdego z pól na mapie.
     */
    public static void clearParentsOfFields() {

        for (int i = 0; i < GameStatus.getInstance().getMap().getFieldsColumns(); i++) {
            for (int j = 0; j < GameStatus.getInstance().getMap().getFieldsRows(); j++) {
                if (GameStatus.getInstance().getMap().getFields()[i][j].parentField != null) {
                    GameStatus.getInstance().getMap().getFields()[i][j].parentField = null;
                }
            }
        }
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

    public static class SortByPathF implements Comparator<Field> {

        @Override
        public int compare(Field o1, Field o2) {
            return (int) (o1.pathF - o2.pathF);
        }
    }
}


