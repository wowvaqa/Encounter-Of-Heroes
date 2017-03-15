package com.mygdx.eoh.gameClasses;

import com.mygdx.eoh.effects.LongEffect;

import java.util.Random;

/**
 * Class to manage fight.
 * Created by v on 2016-11-28.
 */
public class FightManager {

    /**
     * Return random attack factor of given Mob
     *
     * @param object PlayerMob object
     * @return Factor of attack
     */
    static public int getFactorOfAttack(Object object) {

        int attack = 0;
        Random rnd = new Random();

        if (object.getClass().equals(PlayerMob.class)) {
            attack = rnd.nextInt(((PlayerMob) object).getActualAttack() + ModificatorGetter.getAttackModificator(((PlayerMob) object)) + 1);
        }
        System.out.println("Attack Factor: " + attack);
        return attack;
    }

    /**
     * Return random defence factor of given Mob
     *
     * @param object PlayerMob object
     * @return Factor of defence
     */
    static public int getFactorOfDefence(Object object) {
        int defence = 0;
        Random rnd = new Random();

        if (object.getClass().equals(PlayerMob.class)) {
            defence = rnd.nextInt(((PlayerMob) object).getActualDefence() + 1);
        }
        System.out.println("Defence Factor: " + defence);
        return defence;
    }

    /**
     * Returns damage given from attacking mob to defending
     *
     * @param attackingMob PlayerMob class object
     * @param defendingMob PlayerMob class object
     * @return Damage
     */
    static public int getDamage(Object attackingMob, Object defendingMob) {
        int damage;
        FightManager.decreseAP(attackingMob, 1);
        damage = getFactorOfAttack(attackingMob) - getFactorOfDefence(defendingMob);
        if (damage < 0) damage = 0;
        else setActualHPofMob(defendingMob, damage);
        System.out.println("Damage: " + damage);
        return damage;
    }

    /**
     * Sets new actual Hp for mob.
     *
     * @param object PlayerMob class object.
     * @param damage damage.
     */
    static public void setActualHPofMob(Object object, int damage) {
        if (object.getClass().equals(PlayerMob.class)) {
            ((PlayerMob) object).setActualhp(
                    ((PlayerMob) object).getActualhp() - damage
            );
            if (((PlayerMob) object).getActualhp() < 1) {
                ((PlayerMob) object).addFadeOutActionWhenPlayerMobIsDead((PlayerMob) object);
            }
            System.out.println("Actual HP of mob: " + ((PlayerMob) object).getActualhp());
        }
    }

    /**
     * Decresing actual speed of mob .
     *
     * @param object Mob which speed have to decrease.
     * @param factor How many points to decrease.
     */
    static public void decreseAP(Object object, int factor) {
        if (object.getClass().equals(PlayerMob.class)) {
            int ap = ((PlayerMob) object).getActionPoints();
            ap -= factor;
            ((PlayerMob) object).setActionPoints(ap);
        }
    }
}
