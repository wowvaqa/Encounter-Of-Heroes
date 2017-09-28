package com.mygdx.eoh.ai;

import com.mygdx.eoh.items.Item;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Item cell for ai
 * Created by l.wawrzyniak on 2017-09-28.
 */

public class ItemCell {

    private Item item;
    private int distance;
    private ArrayList<Move> moveList;

    public ItemCell(Item item, int distance) {
        this.distance = distance;
        this.item = item;
        moveList = new ArrayList<Move>();
    }

    public Item getItem() {
        return item;
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
    public static class SortByDistance implements Comparator<ItemCell> {
        @Override
        public int compare(ItemCell o1, ItemCell o2) {
            return (o1.distance - o2.distance);
        }
    }
}
