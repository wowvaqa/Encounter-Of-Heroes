package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.MoveManager;

/**
 * Representation of default mob
 * Created by v on 2016-10-25.
 */
public class DefaultMob extends AnimatedImage {

    private int coordinateXonMap;
    private int coordinateYonMap;

    private int level;
    private int actualExp;
    private int rewardExp;
    private int[] levelingArray;

    private int attack;
    private int actualAttack;
    private int defence;
    private int actualDefence;
    private int maxHp;
    private int actualhp;
    private int hpRegeneration;
    private int maxMana;
    private int actualMana;
    private int manaRegeneration;
    private int wisdom;
    private int actualWisdom;
    private int power;
    private int actualPower;
    private int speed;
    private int actualSpeed;
    private int actionPoints;

    private boolean expGiven;

    public DefaultMob(Animation animation, boolean isLooped) {
        super(animation, isLooped);
    }

    /**
     * Removes dead mobs from map.
     */
    public static void removeDeadMobs() {
        for (int i = 0; i < GameStatus.getInstance().getMap().getFieldsColumns(); i++) {
            for (int j = 0; j < GameStatus.getInstance().getMap().getFieldsRows(); j++) {
                if (GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob() != null) {
                    if (GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().getActualhp() < 1) {
                        GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().getPlayerOwner().getPlayerMobs().remove(
                                GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob()
                        );
                        if (GameStatus.getInstance().getSelectedPlayerMob() != null) {
                            if (GameStatus.getInstance().getSelectedPlayerMob().equals(
                                    GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob()
                            )) {
                                GameStatus.getInstance().getEquipmentTable().setVisible(false);
                                GameStatus.getInstance().getHeroTable().setVisible(false);
                                GameStatus.getInstance().getSpellEffectsTable().clear();
                                GameStatus.getInstance().getUpperBarRightTable().clear();
                            }
                        }

                        GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().getPlayerColorImage().remove();
                        GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().getPlayerOwner().chceckLoseCondition();
                        GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().remove();
                        MoveManager.removeButtons(GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob());
                        GameStatus.getInstance().getMap().getFields()[i][j].setPlayerMob(null);


                    }
                } else if (GameStatus.getInstance().getMap().getFields()[i][j].getFreeMob() != null) {
                    if (GameStatus.getInstance().getMap().getFields()[i][j].getFreeMob().getActualhp() < 1) {
                        if (GameStatus.getInstance().getMap().getFields()[i][j].getFreeMob().isSelected()) {
                            GameStatus.getInstance().getSpellEffectsTable().clear();
                            GameStatus.getInstance().getUpperBarRightTable().clear();
                        }
                        GameStatus.getInstance().getMap().getFields()[i][j].getFreeMob().remove();
                        GameStatus.getInstance().getMap().getFields()[i][j].setFreeMob(null);

                    }
                }
            }
        }
    }

    public int getCoordinateXonMap() {
        return coordinateXonMap;
    }

    public void setCoordinateXonMap(int coordinateXonMap) {
        this.coordinateXonMap = coordinateXonMap;
    }

    public int getCoordinateYonMap() {
        return coordinateYonMap;
    }

    public void setCoordinateYonMap(int coordinateYonMap) {
        this.coordinateYonMap = coordinateYonMap;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getActualhp() {
        return actualhp;
    }

    public void setActualhp(int actualhp) {
        this.actualhp = actualhp;
    }

    public int getHpRegeneration() {
        return hpRegeneration;
    }

    public void setHpRegeneration(int hpRegeneration) {
        this.hpRegeneration = hpRegeneration;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getActualMana() {
        return actualMana;
    }

    public void setActualMana(int actualMana) {
        this.actualMana = actualMana;
    }

    public int getManaRegeneration() {
        return manaRegeneration;
    }

    public void setManaRegeneration(int manaRegeneration) {
        this.manaRegeneration = manaRegeneration;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getActualSpeed() {
        return actualSpeed;
    }

    public void setActualSpeed(int actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public int getActualAttack() {
        return actualAttack;
    }

    public void setActualAttack(int actualAttack) {
        this.actualAttack = actualAttack;
    }

    public int getActualDefence() {
        return actualDefence;
    }

    public void setActualDefence(int actualDefence) {
        this.actualDefence = actualDefence;
    }

    public int getActualWisdom() {
        return actualWisdom;
    }

    public void setActualWisdom(int actualWisdom) {
        this.actualWisdom = actualWisdom;
    }

    public int getActualPower() {
        return actualPower;
    }

    public void setActualPower(int actualPower) {
        this.actualPower = actualPower;
    }

    public int getActualExp() {
        return actualExp;
    }

    public void setActualExp(int actualExp) {
        this.actualExp = actualExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int[] getLevelingArray() {
        return levelingArray;
    }

    public void setLevelingArray(int[] levelingArray) {
        this.levelingArray = levelingArray;
    }

    public int getRewardExp() {
        return rewardExp;
    }

    public void setRewardExp(int rewardExp) {
        this.rewardExp = rewardExp;
    }

    public boolean isExpGiven() {
        return expGiven;
    }

    public void setExpGiven(boolean expGiven) {
        this.expGiven = expGiven;
    }
}
