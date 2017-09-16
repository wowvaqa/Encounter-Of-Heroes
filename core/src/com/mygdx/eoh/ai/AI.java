package com.mygdx.eoh.ai;

import com.badlogic.gdx.Gdx;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Map;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by v on 2017-08-27.
 */

public class Ai {

    /**
     * Czyści wartości zmiennych do wyszukiwania ściezki.
     */
    public static void clearFieldsVaribles() {
        for (int i = 0; i < GameStatus.getInstance().getMap().getFieldsColumns(); i++) {
            for (int j = 0; j < GameStatus.getInstance().getMap().getFieldsRows(); j++) {
                GameStatus.getInstance().getMap().getFields()[i][j].parentField = null;
                GameStatus.getInstance().getMap().getFields()[i][j].pathF = 0;
                GameStatus.getInstance().getMap().getFields()[i][j].pathG = 0;
                GameStatus.getInstance().getMap().getFields()[i][j].pathH = 0;
            }
        }
    }

    /**
     * Zwraca posortowaną tablicę zawierającą dostępne skrzynie, do których bohater gracza może
     * się udać.
     *
     * @param startField Pole na którym stoi bohater gracza.
     * @return Lista z posortowanymi skrzyniami.
     */
    public ArrayList<TreasureCell> findAvailableTreasureBoxes(Field startField) {

        ArrayList<TreasureCell> treasureCells = new ArrayList<TreasureCell>();

        Map map = GameStatus.getInstance().getMap();

        for (int i = 0; i < map.getFieldsColumns(); i++) {
            for (int j = 0; j < map.getFieldsRows(); j++) {
                if (map.getFields()[i][j].getTreasure() != null) {

                    TreasureCell treasureCell = new TreasureCell(map.getFields()[i][j].getTreasure(), 0);

                    FindPath pathFinder = new FindPath();

                    if (pathFinder.findPath(startField, map.getFields()[i][j].getTreasure().getFieldOfTreasure(), treasureCell.getMoveList())) {
                        treasureCell.setDistance(treasureCell.getMoveList().size());
                    }

                    treasureCells.add(treasureCell);
                }
            }
        }

        Collections.sort(treasureCells, new TreasureCell.SortByDistance());
        return treasureCells;
    }

    public ArrayList<TreasureCell> findTreasures() {

        Map map = GameStatus.getInstance().getMap();
        ArrayList<TreasureCell> treasuresList = new ArrayList<TreasureCell>();

        for (int i = 0; i < map.getFieldsRows(); i++) {
            for (int j = 0; j < map.getFieldsColumns(); j++) {
                if (map.getFields()[i][j].getTreasure() != null) {
                    TreasureCell treasureCell = new TreasureCell(map.getFields()[i][j].getTreasure(), 0);
                    treasuresList.add(treasureCell);
                }
            }
        }
        return treasuresList;
    }

    public void findPath(Field startField, Field endField) {

        ArrayList<Move> moves = new ArrayList<Move>();

        FindPath pathFinder = new FindPath();

        if (pathFinder.findPath(startField, endField, moves)) {
            Gdx.app.log("Znaleziono ścieżkę", "");
            Gdx.app.log("****** Ilość ruchów", "" + moves.size());

            for (int i = 0; i < moves.size(); i++) {
                Gdx.app.log("Ruch " + i, "X: " + moves.get(i).getMoveX() + ", Y: " + moves.get(i).getMoveY());
            }

        } else {
            Gdx.app.log("Nie znaleziono ścieżki", "");
        }

    }
}
