package com.mygdx.eoh.mob;

import com.mygdx.eoh.animation.AnimationFreeMobCreator;
import com.mygdx.eoh.enums.FreeMobAnimationTypes;
import com.mygdx.eoh.enums.FreeMobsKinds;
import com.mygdx.eoh.gameClasses.Options;

/**
 * Class to create new free mobs.
 * Created by wowvaqa on 11.04.17.
 */

public class FreeMobCreator {
    private static final FreeMobCreator instance = new FreeMobCreator();

    private FreeMobCreator() {
    }

    public static FreeMobCreator getInstance() {
        return instance;
    }

    /**
     * Create new free mob
     *
     * @param freeMobsKind Kind of free mob
     * @param locX         Location X on map
     * @param locY         Location Y on map
     * @return new free mob
     */
    public FreeMob createFreeMob(FreeMobsKinds freeMobsKind, int locX, int locY) {

        FreeMob freeMob;

        switch (freeMobsKind) {
            case Skeleton:
                freeMob = createSkeleton();
                break;
            case Barbarian:
                freeMob = createBarbarian();
                break;
            default:
                freeMob = createSkeleton();
                break;
        }

//        FreeMob freeMob = new FreeMob(
//                AnimationFreeMobCreator.getInstance().makeAnimation(
//                        FreeMobAnimationTypes.SkeletonStanding),
//                true);

        freeMob.setFreeMobsKind(freeMobsKind);
        freeMob.setCoordinateXonMap(locX);
        freeMob.setCoordinateYonMap(locY);
        freeMob.setSize(Options.tileSize, Options.tileSize);
        freeMob.setPosition(Options.tileSize * locX, Options.tileSize * locY);

        createStatistic(freeMob);

        return freeMob;
    }

    private FreeMob createSkeleton() {
        FreeMob freeMob = new FreeMob(AnimationFreeMobCreator.getInstance().makeAnimation(
                FreeMobAnimationTypes.SkeletonStanding),
                true);
        return freeMob;
    }

    private FreeMob createBarbarian() {
        FreeMob freeMob = new FreeMob(AnimationFreeMobCreator.getInstance().makeAnimation(
                FreeMobAnimationTypes.BarbarianStanding),
                true);
        return freeMob;
    }

    /**
     * Creates statistic for mob.
     *
     * @param freeMob Free mob to create statistics.
     */
    private void createStatistic(FreeMob freeMob) {
        switch (freeMob.getFreeMobsKind()) {
            case Skeleton:
                freeMob.setAttack(4);
                freeMob.setActualAttack(4);
                freeMob.setDefence(4);
                freeMob.setActualDefence(4);
                freeMob.setSpeed(3);
                freeMob.setActualSpeed(3);
                freeMob.setActualhp(8);
                freeMob.setMaxHp(8);
                freeMob.setActionPoints(3);
                break;
            case Barbarian:
                freeMob.setAttack(8);
                freeMob.setActualAttack(8);
                freeMob.setDefence(4);
                freeMob.setActualDefence(4);
                freeMob.setSpeed(4);
                freeMob.setActualSpeed(4);
                freeMob.setActualhp(14);
                freeMob.setMaxHp(14);
                freeMob.setActionPoints(4);
                break;
            default:
                freeMob.setAttack(4);
                freeMob.setActualAttack(4);
                freeMob.setDefence(4);
                freeMob.setActualDefence(4);
                freeMob.setSpeed(3);
                freeMob.setActualSpeed(3);
                freeMob.setActualhp(8);
                freeMob.setMaxHp(8);
                freeMob.setActionPoints(3);
                break;
        }
    }
}
