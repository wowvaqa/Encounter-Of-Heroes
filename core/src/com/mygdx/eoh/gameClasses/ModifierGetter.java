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
     * @param playerMob Player mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    static int getAttackModifier(PlayerMob playerMob){
        int attackModifier = 0;
        for (LongEffect longEffect: playerMob.getLongEffects()){
            attackModifier += longEffect.getAttackModifier();
        }

        attackModifier += EquipModifier.getAttackModifiers(playerMob.getWeapon());
        attackModifier += EquipModifier.getAttackModifiers(playerMob.getArmor());
        attackModifier += EquipModifier.getAttackModifiers(playerMob.getArtifact());

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
     * @param playerMob Player Mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    public static int getSpeedModifier(PlayerMob playerMob){
        int speedModifier = 0;
        for (LongEffect longEffect: playerMob.getLongEffects()){
            speedModifier += longEffect.getSpeedModifier();
        }

        speedModifier += EquipModifier.getSpeedModifiers(playerMob.getWeapon());
        speedModifier += EquipModifier.getSpeedModifiers(playerMob.getArmor());
        speedModifier += EquipModifier.getSpeedModifiers(playerMob.getArtifact());

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
     * @param playerMob Player Mob
     * @return Sum of all modifiers from long effects and equipment.
     */
    public static int getWisdomModifier(PlayerMob playerMob){
        int wisdomModifier = 0;
        for (LongEffect longEffect: playerMob.getLongEffects()){
            wisdomModifier += longEffect.getWisdomModifier();
        }

        wisdomModifier += EquipModifier.getWisdomModifiers(playerMob.getWeapon());
        wisdomModifier += EquipModifier.getWisdomModifiers(playerMob.getArmor());
        wisdomModifier += EquipModifier.getWisdomModifiers(playerMob.getArtifact());

        return wisdomModifier;
    }

}
