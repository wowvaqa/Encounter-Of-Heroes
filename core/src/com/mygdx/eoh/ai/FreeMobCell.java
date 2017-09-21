package com.mygdx.eoh.ai;

import com.mygdx.eoh.mob.FreeMob;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Klasa przechowuje informacje o mobie i dystansie do niego. Wykorzystywane przez algorytm
 * wyszukiwania ścieżek.
 * Created by v on 2017-09-21.
 */
public class FreeMobCell {

    private FreeMob freeMob;
    private int distance;
    private ArrayList<Move> moveList;

    public FreeMobCell(FreeMob freeMob, int distance) {
        this.distance = distance;
        this.freeMob = freeMob;
        moveList = new ArrayList<Move>();
    }


    public FreeMob getFreeMob() {
        return freeMob;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    /**
     * Sortowanie wg dystansu.
     */
    public static class SortByDistance implements Comparator<FreeMobCell> {
        @Override
        public int compare(FreeMobCell o1, FreeMobCell o2) {
            return (o1.distance - o2.distance);
        }
    }
}
