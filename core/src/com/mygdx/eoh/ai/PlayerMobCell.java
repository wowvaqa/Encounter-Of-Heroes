package com.mygdx.eoh.ai;

import com.mygdx.eoh.gameClasses.PlayerMob;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by v on 2017-09-20.
 */

public class PlayerMobCell {

    private PlayerMob playerMob;
    private int distance;
    private ArrayList<Move> moveList;

    public PlayerMobCell(PlayerMob playerMob, int distance) {
        this.distance = distance;
        this.playerMob = playerMob;
        moveList = new ArrayList<Move>();
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

    public PlayerMob getPlayerMob() {
        return playerMob;
    }

    /**
     * Sortowanie wg dystansu.
     */
    public static class SortByDistance implements Comparator<PlayerMobCell> {
        @Override
        public int compare(PlayerMobCell o1, PlayerMobCell o2) {
            return (o1.distance - o2.distance);
        }
    }
}
