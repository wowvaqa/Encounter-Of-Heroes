package com.mygdx.eoh.gameClasses;

import com.mygdx.eoh.mob.FreeMob;

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
    private static int getFactorOfAttack(Object object) {

        int attack = 0;
        Random rnd = new Random();

        if (object.getClass().equals(PlayerMob.class)) {
            attack = rnd.nextInt(((PlayerMob) object).getActualAttack() + ModifierGetter.getAttackModifier(((PlayerMob) object)) + 1);
        }

        if (object.getClass().equals(FreeMob.class)) {
            attack = rnd.nextInt(((FreeMob) object).getActualAttack() + ModifierGetter.getAttackModifier(((FreeMob) object)) + 1);
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
    private static int getFactorOfDefence(Object object) {
        int defence = 0;
        Random rnd = new Random();

        if (object.getClass().equals(PlayerMob.class)) {
            defence = rnd.nextInt(((PlayerMob) object).getActualDefence() + ModifierGetter.getDefenceModifier(object) + 1);
        }

        if (object.getClass().equals(FreeMob.class)) {
            defence = rnd.nextInt(((FreeMob) object).getActualDefence() + ModifierGetter.getDefenceModifier(object) + 1);
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
    public static int getDamage(Object attackingMob, Object defendingMob) {
        int damage;
        FightManager.decreseAP(attackingMob, 1);
        damage = getFactorOfAttack(attackingMob) - getFactorOfDefence(defendingMob);
        if (damage < 0) damage = 0;
        else setActualHPofMob(defendingMob, damage);

        chceckExpReward(attackingMob, defendingMob);

        System.out.println("Damage: " + damage);
        return damage;
    }

    public static void chceckExpReward(Object attackingMob, Object defendingMob){
        if (attackingMob.getClass().equals(PlayerMob.class) && defendingMob.getClass().equals(FreeMob.class)){
            if (((FreeMob)defendingMob).getActualhp() < 1) {
                int expReward = ((FreeMob) defendingMob).getRewardExp();
                ((PlayerMob) attackingMob).showGoodEffectLabel("+ " + expReward + " EXP", ((PlayerMob) attackingMob).getX(), ((PlayerMob) attackingMob).getY());
                ((PlayerMob) attackingMob).setActualExp(((PlayerMob) attackingMob).getActualExp() + expReward);
            }
        } else if (attackingMob.getClass().equals(PlayerMob.class) && defendingMob.getClass().equals(PlayerMob.class)){
            if (((PlayerMob)defendingMob).getActualhp() < 1) {
                int expReward = ((PlayerMob) defendingMob).getRewardExp();
                ((PlayerMob) attackingMob).showGoodEffectLabel("+ " + expReward + " EXP", ((PlayerMob) attackingMob).getX(), ((PlayerMob) attackingMob).getY());
                ((PlayerMob) attackingMob).setActualExp(((PlayerMob) attackingMob).getActualExp() + expReward);
            }
        }
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
        } else if (object.getClass().equals(FreeMob.class)) {
            ((FreeMob) object).setActualhp(
                    ((FreeMob) object).getActualhp() - damage
            );
            if (((FreeMob) object).getActualhp() < 1) {
                ((FreeMob) object).addFadeOutActionWhenPlayerMobIsDead((FreeMob) object);
            }
            System.out.println("Actual HP of mob: " + ((FreeMob) object).getActualhp());
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

        if (object.getClass().equals(FreeMob.class)) {
            int ap = ((FreeMob) object).getActionPoints();
            ap -= factor;
            ((FreeMob) object).setActionPoints(ap);
        }
    }
}
