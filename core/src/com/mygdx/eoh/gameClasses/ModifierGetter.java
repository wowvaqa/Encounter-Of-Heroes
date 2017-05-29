package com.mygdx.eoh.gameClasses;

import com.mygdx.eoh.effects.EquipModifier;
import com.mygdx.eoh.effects.LongEffect;
import com.mygdx.eoh.mob.FreeMob;

/**
 *
 * Created by v on 2017-03-09.
 */

public class ModifierGetter {

    /**
     * Returns the sum of all attack modifiers from long effects and equipment
     * @param object Player mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    static int getAttackModifier(Object object) {
        int attackModifier = 0;

        if (object.getClass().equals(PlayerMob.class)) {
            for (LongEffect longEffect : ((PlayerMob) object).getLongEffects()) {
                attackModifier += longEffect.getAttackModifier();
            }

            attackModifier += EquipModifier.getAttackModifiers(((PlayerMob) object).getWeapon());
            attackModifier += EquipModifier.getAttackModifiers(((PlayerMob) object).getArmor());
            attackModifier += EquipModifier.getAttackModifiers(((PlayerMob) object).getArtifact());
        }

        if (object.getClass().equals(FreeMob.class)) {

        }

        return attackModifier;
    }

    /**
     * Returns the sum of all defence modifiers from long effects and equipment
     * @param object Player Mob, Free mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    public static int getDefenceModifier(Object object) {
        int defenceModifier = 0;

        // PLAYER MOB CLASS
        if (object.getClass().equals(PlayerMob.class)) {
            for (LongEffect longEffect : ((PlayerMob) object).getLongEffects()) {
                defenceModifier += longEffect.getDefenceModifier();
            }

            defenceModifier += EquipModifier.getDefenceModifiers(((PlayerMob) object).getWeapon());
            defenceModifier += EquipModifier.getDefenceModifiers(((PlayerMob) object).getArmor());
            defenceModifier += EquipModifier.getDefenceModifiers(((PlayerMob) object).getArtifact());
        }

        // FREE MOB CLASS
        if (object.getClass().equals(FreeMob.class)) {

        }

        return defenceModifier;
    }

    /**
     * Returns the sum of all speed modificators from long effects and equipment
     * @param object Player Mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    public static int getSpeedModifier(Object object) {
        int speedModifier = 0;

        if (object.getClass().equals(PlayerMob.class)) {
            for (LongEffect longEffect : ((PlayerMob) object).getLongEffects()) {
                speedModifier += longEffect.getSpeedModifier();
            }

            speedModifier += EquipModifier.getSpeedModifiers(((PlayerMob) object).getWeapon());
            speedModifier += EquipModifier.getSpeedModifiers(((PlayerMob) object).getArmor());
            speedModifier += EquipModifier.getSpeedModifiers(((PlayerMob) object).getArtifact());
        }

        if (object.getClass().equals(FreeMob.class)) {

        }

        return speedModifier;
    }

    /**
     * Returns the sum of all power modificators from long effects and equipment
     * @param playerMob Player Mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    public static int getPowerModifier(PlayerMob playerMob){
        int powerModifier = 0;
        for (LongEffect longEffect: playerMob.getLongEffects()){
            powerModifier += longEffect.getPowerModifier();
        }

        powerModifier += EquipModifier.getPowerModifiers(playerMob.getWeapon());
        powerModifier += EquipModifier.getPowerModifiers(playerMob.getArmor());
        powerModifier += EquipModifier.getPowerModifiers(playerMob.getArtifact());

        return powerModifier;
    }

    /**
     * Returns the sum of all wisdom modificators from long effects and equipment
     * @param object Player Mob, Free mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    public static int getWisdomModifier(Object object) {
        int wisdomModifier = 0;

        if (object.getClass().equals(PlayerMob.class)) {
            for (LongEffect longEffect : ((PlayerMob) object).getLongEffects()) {
                wisdomModifier += longEffect.getWisdomModifier();
            }

            wisdomModifier += EquipModifier.getWisdomModifiers(((PlayerMob) object).getWeapon());
            wisdomModifier += EquipModifier.getWisdomModifiers(((PlayerMob) object).getArmor());
            wisdomModifier += EquipModifier.getWisdomModifiers(((PlayerMob) object).getArtifact());
        }

        if (object.getClass().equals(FreeMob.class)) {

        }

        return wisdomModifier;
    }

    /**
     * Returns the sum of all HP modificators from long effects and equipment
     * @param object Player Mob, Free mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    public static int getHpModifier(Object object) {
        int hpModifier = 0;

        if (object.getClass().equals(PlayerMob.class)) {
            for (LongEffect longEffect : ((PlayerMob) object).getLongEffects()) {
                hpModifier += longEffect.getHpModifier();
            }

            hpModifier += EquipModifier.getHpModifiers(((PlayerMob) object).getWeapon());
            hpModifier += EquipModifier.getHpModifiers(((PlayerMob) object).getArmor());
            hpModifier += EquipModifier.getHpModifiers(((PlayerMob) object).getArtifact());
        }

        if (object.getClass().equals(FreeMob.class)) {

        }

        return hpModifier;
    }

}
