package com.mygdx.eoh.effects;

import com.mygdx.eoh.Equipment.Equip;
import com.mygdx.eoh.Equipment.EquipKinds;

/**
 * Modificators of statistics
 * Created by wowvaqa on 06.04.17.
 */

public class EquipModifier {

    private int attackModifier;
    private int defenceModifier;
    private int speedModifier;
    private int powerModifier;
    private int wisdomModifier;

    /**
     * Creates equipment modificator.
     *
     * @param equipKind Kind of weapon.
     * @return New modifier.
     */
    public static EquipModifier createEquipModifier(EquipKinds equipKind) {
        EquipModifier equipModifier = new EquipModifier();
        switch (equipKind) {
            case WoodenStick:
                equipModifier.setAttackModifier(1);
                equipModifier.setDefenceModifier(1);
                return equipModifier;
            case MagicStaff:
                equipModifier.setPowerModifier(2);
                equipModifier.setWisdomModifier(2);
                return equipModifier;
            case LeatherArmor:
                equipModifier.setDefenceModifier(2);
                equipModifier.setAttackModifier(1);
                equipModifier.setSpeedModifier(1);
                return equipModifier;
            case LeatherPants:
                equipModifier.setDefenceModifier(2);
                return equipModifier;
            case GoldRing:
                equipModifier.setWisdomModifier(1);
                equipModifier.setPowerModifier(1);
                return equipModifier;
            case SphereOfSpeed:
                equipModifier.setWisdomModifier(1);
                equipModifier.setSpeedModifier(3);
                return equipModifier;
            case Sword:
                equipModifier.setAttackModifier(2);
                return equipModifier;
            case None:
                return equipModifier;
        }
        return equipModifier;
    }

    /**
     * Return sum of all attack modifiers of equipment.
     *
     * @param equip Equipment for sum
     * @return Sum of all attack modifiers
     */
    public static int getAttackModifiers(Equip equip) {
        int atakMod = 0;
        if (equip == null)
            return 0;
        for (EquipModifier equipModifier : equip.getEquipModifiers()) {
            atakMod = +equipModifier.getAttackModifier();
        }
        return atakMod;
    }

    /**
     * Return sum of all defence modifiers of equipment.
     *
     * @param equip Equipment for sum
     * @return Sum of all defence modifiers
     */
    public static int getDefenceModifiers(Equip equip) {
        int defenceMod = 0;
        if (equip == null)
            return 0;
        for (EquipModifier equipModifier : equip.getEquipModifiers()) {
            defenceMod = +equipModifier.getDefenceModifier();
        }
        return defenceMod;
    }

    /**
     * Return sum of all Speed modifiers of equipment.
     *
     * @param equip Equipment for sum
     * @return Sum of all speed modifiers
     */
    public static int getSpeedModifiers(Equip equip) {
        int speedMod = 0;
        if (equip == null)
            return 0;
        for (EquipModifier equipModifier : equip.getEquipModifiers()) {
            speedMod = +equipModifier.getSpeedModifier();
        }
        return speedMod;
    }

    /**
     * Return sum of all Power modifiers of equipment.
     *
     * @param equip Equipment for sum
     * @return Sum of all power modifiers
     */
    public static int getPowerModifiers(Equip equip) {
        int powerMod = 0;
        if (equip == null)
            return 0;
        for (EquipModifier equipModifier : equip.getEquipModifiers()) {
            powerMod = +equipModifier.getPowerModifier();
        }
        return powerMod;
    }

    /**
     * Return sum of all wisdom modifiers of equipment.
     *
     * @param equip Equipment for sum
     * @return Sum of all wisdom modifiers
     */
    public static int getWisdomModifiers(Equip equip) {
        int wisdomMod = 0;
        if (equip == null)
            return 0;
        for (EquipModifier equipModifier : equip.getEquipModifiers()) {
            wisdomMod = +equipModifier.getWisdomModifier();
        }
        return wisdomMod;
    }

    private int getAttackModifier() {
        return attackModifier;
    }

    private void setAttackModifier(int attackModificator) {
        this.attackModifier = attackModificator;
    }

    private int getDefenceModifier() {
        return defenceModifier;
    }

    private void setDefenceModifier(int defenceModifier) {
        this.defenceModifier = defenceModifier;
    }

    private int getSpeedModifier() {
        return speedModifier;
    }

    private void setSpeedModifier(int speedModifier) {
        this.speedModifier = speedModifier;
    }

    private int getPowerModifier() {
        return powerModifier;
    }

    private void setPowerModifier(int powerModifier) {
        this.powerModifier = powerModifier;
    }

    private int getWisdomModifier() {
        return wisdomModifier;
    }

    private void setWisdomModifier(int wisdomModifier) {
        this.wisdomModifier = wisdomModifier;
    }
}
