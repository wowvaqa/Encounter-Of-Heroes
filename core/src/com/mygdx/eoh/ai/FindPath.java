package com.mygdx.eoh.ai;

import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.GameStatus;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class to find path between fields.
 * Created by v on 2017-09-16.
 */

public class FindPath {

    private ArrayList<Field> openFieldsList;
    private ArrayList<Field> closedFieldsList;
    private Field finalField;

    public FindPath() {
        openFieldsList = new ArrayList<Field>();
        closedFieldsList = new ArrayList<Field>();
    }

    /**
     * Find path between start field and end field. Return true if path will be found.
     * @param startField Pole od którego ma rozpocząć się poszukiwanie ścieżki.
     * @param endField Pole na którym poszukiwanie ścieżki ma się zakończyć.
     * @param moveList Lista z ruchami z pola początkowego do końcowego
     * @param searchDestination Cel poszukiwań: FreeMob, CastleMob, PlayerMob.
     * @return TRUE jeżeli droga zostanie odnaleziona, FALSE jeżeli nie.
     */
    public boolean findPath(Field startField, Field endField, ArrayList<Move> moveList, SearchDestination searchDestination) {

        finalField = endField;

        //Gdx.app.log("****** Pole początkowe", " X: " + startField.locXonMap + ", Y: " + startField.locYonMap);
        //Gdx.app.log("****** Pole końcowe   ", " X: " + endField.locXonMap + ", Y: " + endField.locYonMap);

        // Czyszczenie listy pól otwartych i zamkniętych.
        openFieldsList.clear();
        closedFieldsList.clear();
        AI.clearFieldsVaribles();

        // Dodanie pola startowego do listy pól otwartych.
        openFieldsList.add(startField);

        Field fieldQ = startField;

        // Uruchomienie pętli działającej do momentu kiedy lista pól otwartych będzie zawierała pola do sprawdzenia.
        while (openFieldsList.size() > 0) {

            //Gdx.app.log("Pola przed sortowaniem:", "");
            //for (int i = 0; i < openFieldsList.size(); i++) {
            //    Gdx.app.log("Pole " + openFieldsList.get(i).locXonMap + ", " + openFieldsList.get(i).locYonMap, "PathF: " + openFieldsList.get(i).pathF);
            //}
            Collections.sort(openFieldsList, new Field.SortByPathF());

            //Gdx.app.log("Pola po sortowaniu:", "");
            //for (int i = 0; i < openFieldsList.size(); i++) {
            //    Gdx.app.log("Pole " + openFieldsList.get(i).locXonMap + ", " + openFieldsList.get(i).locYonMap, "PathF: " + openFieldsList.get(i).pathF);
            //}

            if (openFieldsList.size() > 0)
                fieldQ = openFieldsList.get(0);
            else
                return false;

            if (fieldQ.equals(finalField)) {
                setMoveList(moveList);
                return true;
            }

            closedFieldsList.add(fieldQ);
            openFieldsList.remove(fieldQ);

            findNeighbors(fieldQ, endField, searchDestination);
        }
        return false;
    }

    /**
     * Tworzy listę ruchów dla moba aby mógł dotrzeć do celu.
     *
     * @param moveList Miejce gdzie ruchy mają być zapisane.
     */
    private void setMoveList(ArrayList<Move> moveList) {
        //Gdx.app.log("****** Kształt ścieżki", "");
        Field tmpField = finalField;
        //Gdx.app.log("* Krok", "" + tmpField.locXonMap + ", " + tmpField.locYonMap);

        moveList.add(new Move(tmpField.locXonMap - tmpField.parentField.locXonMap, tmpField.locYonMap - tmpField.parentField.locYonMap));

        while (tmpField.parentField != null) {
            tmpField = tmpField.parentField;
            //Gdx.app.log("* Krok", "" + tmpField.locXonMap + ", " + tmpField.locYonMap);

            if (tmpField.parentField != null)
                moveList.add(new Move(tmpField.locXonMap - tmpField.parentField.locXonMap, tmpField.locYonMap - tmpField.parentField.locYonMap));
        }
        Collections.reverse(moveList);
    }

    /**
     * Znajduje wszystkie pola przyległe do pola zadanego parametrem i dodaje je do listy pól otwartych.
     *
     * @param field Pole dla którego mają znostać znalezione pola przyległe.
     */
    private void findNeighbors(Field field, Field endField, SearchDestination searchDestination) {

        //Gdx.app.log("**** START wyszukiwania pól sąsiadujących", "");

        for (int i = field.locXonMap - 1; i < field.locXonMap + 2; i++) {
            for (int j = field.locYonMap - 1; j < field.locYonMap + 2; j++) {

                //Gdx.app.log("Szerokość: ", "" + GameStatus.getInstance().getMap().getFieldsColumns());
                //Gdx.app.log("Wysokość:  ", "" + GameStatus.getInstance().getMap().getFieldsRows());

                if (i >= 0 && j >= 0 && i < GameStatus.getInstance().getMap().getFieldsColumns() && j < GameStatus.getInstance().getMap().getFieldsRows()) {
                    Field fiedToAdd = GameStatus.getInstance().getMap().getFields()[i][j];

                    if (checkMobility(fiedToAdd, endField, searchDestination) && !closedFieldsList.contains(fiedToAdd) && fiedToAdd != field) {

                        //Gdx.app.log("** Dodaje pole", " X: " + fiedToAdd.locXonMap + ", Y: " + fiedToAdd.locYonMap + " do listy pól sąsiadujących.");

                        if (!openFieldsList.contains(fiedToAdd)) {
                            // Dodanie pola sąsiadującego do listy pól otwartych
                            openFieldsList.add(fiedToAdd);
                            // Ustawienie rodzica pola sąsiadującego
                            fiedToAdd.parentField = field;
                            countFGH(fiedToAdd);
                        } else {

                            double tmpG = countG(fiedToAdd.parentField, fiedToAdd);
                            if (tmpG < fiedToAdd.pathG)
                                fiedToAdd.parentField = field;
                            countFGH(fiedToAdd);

                            Collections.sort(openFieldsList, new Field.SortByPathF());

                        }
                    }
                }
            }
        }
        //Gdx.app.log("**** KONIEC wyszukiwania pól sąsiadujących", "");
    }

    /**
     * Liczy współczynniki F G H dla zadanego pola.
     *
     * @param field Pole dla którego ścieżki mają być wyliczone
     */
    private void countFGH(Field field) {
        field.pathG = countG(field.parentField, field);
        //Gdx.app.log("Pole " + field.locXonMap + "," + field.locYonMap, "PathG: " + field.pathG);

        field.pathH = countH(field);
        //Gdx.app.log("Pole " + field.locXonMap + "," + field.locYonMap, "PathH: " + field.pathH);

        field.pathF = field.pathG + field.pathH;
        //Gdx.app.log("Pole " + field.locXonMap + "," + field.locYonMap, "PathF: " + field.pathF);
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

        return Math.round(startField.pathG + distance * 10);
    }

    /**
     * Liczy współczynnik H dla zadanych pól
     *
     * @param startField Pole startowe
     * @return współczynnik H
     */
    private double countH(Field startField) {
        double distance;
        double distanceX;
        double distanceY;

        distanceX = Math.abs(finalField.locXonMap - startField.locXonMap);
        distanceY = Math.abs(finalField.locYonMap - startField.locYonMap);

        distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        return Math.round(distance * 10);
    }

    /**
     * Sprawdza czy po podanym polu można się poruszać.
     *
     * @param field Pole do sprawdzenia.
     * @return True jeżeli można się po nim poruszać, Fale jeżeli nie można.
     */
    private boolean checkMobility(Field field, Field endField, SearchDestination searchDestination) {
        if (field.getFreeMob() != null && endField == field && searchDestination.equals(SearchDestination.FREE_MOB))
            return true;
        if (field.getFreeMob() != null)
            return false;
        if (searchDestination.equals(SearchDestination.TREASURE) &&
                endField.getPlayerMob() != null &&
                endField.getTreasure() != null)
            return false;
        if (field.getPlayerMob() != null && endField == field)
            return true;
        if (field.getPlayerMob() != null)
            return false;
        if (!field.isMovable())
            return false;
        return true;
    }

    public enum SearchDestination {
        TREASURE,
        PLAYER_MOB,
        CASTLE,
        FREE_MOB,
        ITEM;
    }
}

