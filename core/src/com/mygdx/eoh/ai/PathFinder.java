package com.mygdx.eoh.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.GameStatus;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by v on 2017-08-27.
 */

public class PathFinder {

    ArrayList<Field> openLinks;
    ArrayList<Field> closedLinks;

    /**
     * Zwraca pole z najmniejszą wartością współczynnika F
     *
     * @param list ArrayLista z polami
     * @return
     */
    public static Field returnLessFfield(ArrayList<Field> list) {

        Collections.sort(list, new Field.SortByPathF());

        Gdx.app.log("Lista po sortowaniu", "");

        for (int i = 0; i < list.size(); i++) {
            Gdx.app.log("Pole: " + list.get(0).getCoordinateXonStage() + "," + list.get(0).getCoordinateYonStage() + " PathF:" + list.get(0).pathF, "");
        }

        return list.get(0);
    }

    public boolean findPath(Field startField, Field endField, SnapshotArray<Field> road) {

        openLinks = new ArrayList<Field>();
        closedLinks = new ArrayList<Field>();

        startField.startField = true;
        endField.endField = true;

        countF(startField, startField, endField);
        openLinks.add(startField);

        Field q;

        while (openLinks.size() > 0) {

            q = returnLessFfield(openLinks);

            moveToClosedList(q);

            if (q.equals(endField)) {
                startField.startField = false;
                endField.endField = false;
                Gdx.app.log("Znaleziono pole końcowe", "");
                return true;
            }

            ArrayList<Field> neighbors = new ArrayList<Field>();

            fillNeighbors(q, neighbors, endField);

            for (Field field : neighbors) {
                if (!checkOpenList(field)) {
                    openLinks.add(field);
                    field.parentField = q;

                    field.pathG = countG(q, field);
                    field.pathH = countH(field, endField);
                    field.pathF = field.pathG + field.pathH;
                } else {
                    double tmpG = countG(q, field);
                    field.parentField = q;
                    field.pathG = tmpG;

                    field.pathH = countH(field, endField);
                    field.pathF = tmpG + field.pathH;

                }
            }
        }

        startField.endField = false;
        endField.endField = false;

        Field.clearParentsOfFields();

        return false;
    }

    /**
     * Liczy współczynnik G dla zadanych pól
     *
     * @param startField Pole startowe
     * @param endField   Pole końcowe
     * @return współczynnik G
     */
    private double countG(Field startField, Field endField) {

        if (startField == endField)
            return 0;

        double distance;
        double distanceX;
        double distanceY;

        distanceX = Math.abs(endField.locXonMap - startField.locXonMap);
        distanceY = Math.abs(endField.locYonMap - startField.locYonMap);

        distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        return startField.pathG + distance;
    }

    /**
     * Liczy współczynnik H dla zadanych pól
     *
     * @param startField Pole startowe
     * @param endField   Pole końcowe
     * @return współczynnik H
     */
    private double countH(Field startField, Field endField) {
        double distance;
        double distanceX;
        double distanceY;

        distanceX = Math.abs(endField.locXonMap - startField.locXonMap);
        distanceY = Math.abs(endField.locYonMap - startField.locYonMap);

        distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        return distance;
    }

    /**
     * Liczy współczynnik F dla zadanego pola
     *
     * @param field       Pole dla którego ma być wyliczony współczynnik
     * @param endField    Pole końcowe
     * @param partntField
     */
    private void countF(Field field, Field endField, Field partntField) {
        field.pathF = countG(partntField, field) + countH(field, endField);
    }

    /**
     * Przenosi zadane pole z listy pól otwartych do zamkniętych.
     *
     * @param field
     */
    private void moveToClosedList(Field field) {
        closedLinks.add(field);

        openLinks.remove(field);
    }

    private void fillNeighbors(Field field, ArrayList<Field> neighborsList, Field endField) {

        neighborsList.clear();

        for (int i = field.getCoordinateXonStage() - 1; i < field.getCoordinateXonStage() + 2; i++) {
            for (int j = field.getCoordinateYonStage() - 1; j < field.getCoordinateYonStage() + 2; j++) {

                if (i >= 0 && j >= 0 && i < GameStatus.getInstance().getMap().getFieldsRows() && j < GameStatus.getInstance().getMap().getFieldsColumns()) {
                    Field fiedToAdd = GameStatus.getInstance().getMap().getFields()[i][j];

                    if (fiedToAdd.isMovable() && fiedToAdd != field) {
                        neighborsList.add(fiedToAdd);
                    }
                }
            }
        }
    }

    private boolean checkOpenList(Field field) {
        for (Field tmpField : openLinks) {
            if (tmpField.equals(field)) {
                return true;
            }
        }
        return false;
    }
}
