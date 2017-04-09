package com.mygdx.eoh.effects;

import com.mygdx.eoh.Equipment.EquipKinds;

/**
 * Modificators of statistics
 * Created by wowvaqa on 06.04.17.
 */

public class EquipModificator {

    private int attackModificator;
    private int defenceModificator;
    private int speedModificator;
    private int powerModificator;
    private int wisdomModificator;

    /**
     * Creates equipment modificator.
     * @param equipKind Kind of weapon.
     * @return New modificator.
     */
    public static EquipModificator createEquipModificator(EquipKinds equipKind) {
        EquipModificator equipModificator = new EquipModificator();
        switch (equipKind) {
            case WoodenStick:
                equipModificator.setAttackModificator(10);
                return equipModificator;
            case MagicStaff:
                equipModificator.setPowerModificator(10);
                return equipModificator;
            case LeatherArmor:
                equipModificator.setDefenceModificator(10);
                return equipModificator;
            case LeatherPants:
                equipModificator.setDefenceModificator(10);
                equipModificator.setSpeedModificator(10);
                return equipModificator;
            case GoldRing:
                equipModificator.setWisdomModificator(10);
        }
        return equipModificator;
    }

    public int getAttackModificator() {
        return attackModificator;
    }

    public void setAttackModificator(int attackModificator) {
        this.attackModificator = attackModificator;
    }

    public int getDefenceModificator() {
        return defenceModificator;
    }

    public void setDefenceModificator(int defenceModificator) {
        this.defenceModificator = defenceModificator;
    }

    public int getSpeedModificator() {
        return speedModificator;
    }

    public void setSpeedModificator(int speedModificator) {
        this.speedModificator = speedModificator;
    }

    public int getPowerModificator() {
        return powerModificator;
    }

    public void setPowerModificator(int powerModificator) {
        this.powerModificator = powerModificator;
    }

    public int getWisdomModificator() {
        return wisdomModificator;
    }

    public void setWisdomModificator(int wisdomModificator) {
        this.wisdomModificator = wisdomModificator;
    }
}
