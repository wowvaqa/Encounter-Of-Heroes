package com.mygdx.eoh.ai;

import com.badlogic.gdx.Gdx;
import com.mygdx.eoh.Equipment.Equip;
import com.mygdx.eoh.Equipment.EquipKinds;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Map;
import com.mygdx.eoh.gameClasses.PlayerMob;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Control AI
 * Created by v on 2017-08-27.
 */

public class Ai {

    private FindPath pathFinder;

    private ArrayList<TreasureCell> treasureCells;

    private float difficultyTimeCounter = 0;
    private float difficultyTime = 0;

    public Ai() {
        pathFinder = new FindPath();
    }

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

        // Utworzenie listy do gromadzenia pól z skrzyniami
        treasureCells = new ArrayList<TreasureCell>();

        Map map = GameStatus.getInstance().getMap();

        for (int i = 0; i < map.getFieldsColumns(); i++) {
            for (int j = 0; j < map.getFieldsRows(); j++) {
                if (map.getFields()[i][j].getTreasure() != null) {

                    TreasureCell treasureCell = new TreasureCell(map.getFields()[i][j].getTreasure(), 0);

                    if (pathFinder.findPath(startField, map.getFields()[i][j].getTreasure().getFieldOfTreasure(), treasureCell.getMoveList())) {
                        treasureCell.setDistance(treasureCell.getMoveList().size());
                        treasureCells.add(treasureCell);
                    }

                }
            }
        }

        Collections.sort(treasureCells, new TreasureCell.SortByDistance());
        return treasureCells;
    }

    /**
     * Zakłada ekwipunek
     *
     * @param playerMob Bohater, który zakłada ekwipunek.
     */
    private void putInEquipment(PlayerMob playerMob) {
        for (Equip equip : playerMob.getEquip()) {
            switch (equip.getEquipType()) {
                case Armor:
                    if (playerMob.getArmor().getEquipKind() == EquipKinds.None) {
                        playerMob.setArmor(equip);
                        playerMob.getEquip().removeValue(equip, true);
                    } else {
                        if (playerMob.getArmor().getPreferredPlayerMobClass() == equip.getPreferredPlayerMobClass()) {
                            if (playerMob.getArmor().getEquipLevel() < equip.getEquipLevel()) {
                                playerMob.getEquip().add(playerMob.getArmor());
                                playerMob.setArmor(equip);
                                playerMob.getEquip().removeValue(equip, true);
                            } else
                                break;
                        } else {
                            if (equip.getEquipLevel() > playerMob.getArmor().getEquipLevel()) {
                                playerMob.getEquip().add(playerMob.getArmor());
                                playerMob.setArmor(equip);
                                playerMob.getEquip().removeValue(equip, true);
                            } else
                                break;
                        }
                    }
                    break;

                case Weapon:
                    if (playerMob.getWeapon().getEquipKind() == EquipKinds.None) {
                        playerMob.setWeapon(equip);
                        playerMob.getEquip().removeValue(equip, true);
                    } else {
                        if (playerMob.getWeapon().getPreferredPlayerMobClass() == equip.getPreferredPlayerMobClass()) {
                            if (playerMob.getWeapon().getEquipLevel() < equip.getEquipLevel()) {
                                playerMob.getEquip().add(playerMob.getWeapon());
                                playerMob.setWeapon(equip);
                                playerMob.getEquip().removeValue(equip, true);
                            } else
                                break;
                        } else {
                            if (equip.getEquipLevel() > playerMob.getWeapon().getEquipLevel()) {
                                playerMob.getEquip().add(playerMob.getWeapon());
                                playerMob.setWeapon(equip);
                                playerMob.getEquip().removeValue(equip, true);
                            } else
                                break;
                        }
                    }
                    break;

                case Artifact:
                    if (playerMob.getArtifact().getEquipKind() == EquipKinds.None) {
                        playerMob.setArtifact(equip);
                        playerMob.getEquip().removeValue(equip, true);
                    } else {
                        if (playerMob.getArtifact().getPreferredPlayerMobClass() == equip.getPreferredPlayerMobClass()) {
                            if (playerMob.getArtifact().getEquipLevel() < equip.getEquipLevel()) {
                                playerMob.getEquip().add(playerMob.getArtifact());
                                playerMob.setArtifact(equip);
                                playerMob.getEquip().removeValue(equip, true);
                            } else
                                break;
                        } else {
                            if (equip.getEquipLevel() > playerMob.getArtifact().getEquipLevel()) {
                                playerMob.getEquip().add(playerMob.getArtifact());
                                playerMob.setArtifact(equip);
                                playerMob.getEquip().removeValue(equip, true);
                            } else
                                break;
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Otwiera skrzynię i przekazuje ekwipunek bohaterowi.
     *
     * @param playerMob Bohater który otworzył skrzynię.
     */
    public void takeTreasure(PlayerMob playerMob) {
        if (playerMob.getFieldOfPlayerMob().getTreasure() != null) {
            for (Equip equip : playerMob.getFieldOfPlayerMob().getTreasure().getEquips()) {
                playerMob.getEquip().add(equip);
            }
            playerMob.getFieldOfPlayerMob().getTreasure().getEquips().clear();
            playerMob.getFieldOfPlayerMob().getTreasure().remove();
            playerMob.getFieldOfPlayerMob().setTreasure(null);

            putInEquipment(playerMob);
        }
    }

    /**
     * Sprawdza czy bohater może wykonać akcje wg zadanego czasu poziomu trudności
     *
     * @return TRUE jeżeli może wykonać akcje, FALSE jeżeli nie.
     */
    public boolean checkDificultyTimeCounter(PlayerMob playerMob) {
        difficultyTimeCounter += Gdx.app.getGraphics().getDeltaTime();
        if (difficultyTimeCounter > playerMob.getAi().getDifficultyTime()) {
            difficultyTimeCounter = 0;
            return true;
        } else
            return false;
    }

    /**
     * GETTERS AND SETTERS
     */

    public ArrayList<TreasureCell> getTreasureCells() {
        return treasureCells;
    }

    public float getDifficultyTime() {
        return difficultyTime;
    }

    public void setDifficultyTime(float difficultyTime) {
        this.difficultyTime = difficultyTime;
    }
}
