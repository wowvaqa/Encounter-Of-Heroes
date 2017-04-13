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

        FreeMob freeMob = new FreeMob(
                AnimationFreeMobCreator.getInstance().makeAnimation(
                        FreeMobAnimationTypes.SkeletonStanding),
                true);

        freeMob.setFreeMobsKind(freeMobsKind);
        freeMob.setCoordinateXonMap(locX);
        freeMob.setCoordinateYonMap(locY);
        freeMob.setSize(Options.tileSize, Options.tileSize);
        freeMob.setPosition(Options.tileSize * locX, Options.tileSize * locY);

        createStatistic(freeMob);

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
                freeMob.setAttack(5);
                freeMob.setActualAttack(5);
                freeMob.setDefence(5);
                freeMob.setActualDefence(5);
                freeMob.setSpeed(3);
                freeMob.setActualSpeed(3);
                freeMob.setActualhp(10);
                freeMob.setMaxHp(10);
                freeMob.setActionPoints(3);
                break;
        }
    }
}
