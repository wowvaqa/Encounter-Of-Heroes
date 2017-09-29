package com.mygdx.eoh.ai;

import com.mygdx.eoh.Equipment.Treasure;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * Created by v on 2017-08-27.
 */

public class TreasureCell {

    private Treasure treasure;
    private int distance;
    private ArrayList<Move> moveList;

    public TreasureCell(Treasure treasure, int distance) {
        this.distance = distance;
        this.treasure = treasure;
        moveList = new ArrayList<Move>();
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Sorting class based on distance.
     */
    public static class SortByDistance implements Comparator<TreasureCell> {
        @Override
        public int compare(TreasureCell o1, TreasureCell o2) {
            return (o1.distance - o2.distance);
        }
    }
}
