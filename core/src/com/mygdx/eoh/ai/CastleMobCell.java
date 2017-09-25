package com.mygdx.eoh.ai;

import com.mygdx.eoh.gameClasses.CastleMob;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Klasa przechowuje informacje o zamku i dystansie do niego. Wykorzystywane przez algorytm
 * wyszukiwania ścieżek.
 * Created by v on 2017-09-25.
 */

public class CastleMobCell {

    private CastleMob castleMob;
    private int distance;
    private ArrayList<Move> moveList;

    public CastleMobCell(CastleMob castleMob, int distance) {
        this.distance = distance;
        this.castleMob = castleMob;
        moveList = new ArrayList<Move>();
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public CastleMob getCastleMob() {
        return castleMob;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    /**
     * Sortowanie wg dystansu.
     */
    public static class SortByDistance implements Comparator<CastleMobCell> {
        @Override
        public int compare(CastleMobCell o1, CastleMobCell o2) {
            return (o1.distance - o2.distance);
        }
    }
}
