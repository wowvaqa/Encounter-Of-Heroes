package com.mygdx.eoh.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.enums.LongEffects;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * Long effects class
 * Created by v on 2017-02-21.
 */

public class LongEffect extends AnimatedImage {

    private LongEffects longEffects;

    private PlayerMob playerOwner;
    private float timeEnd;

    private int attackModificator;

    LongEffect(Animation animation, boolean isLooped, LongEffects longEffects, PlayerMob playerOwner) {
        super(animation, isLooped);

        this.setSize(Options.tileSize, Options.tileSize);
        this.longEffects = longEffects;
        this.playerOwner = playerOwner;

        float timeStart = (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000);
        timeEnd = timeStart + this.getAnimation().getAnimationDuration();

//        System.out.println("Time Start: " + (TimeUtils.nanosToMillis(TimeUtils.nanoTime())/1000));
//        System.out.println("Time End: " + getTimeEnd());
//        System.out.println("Duration: " + this.getAnimation().getAnimationDuration());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float actualTime = (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) / 1000);
        this.setStateTime(this.getAnimation().getAnimationDuration() - (timeEnd - actualTime));

        if (getAnimation().isAnimationFinished(this.getStateTime())) {
            this.remove();
            playerOwner.getLongEffects().removeValue(this, true);
        }
    }

    /***********************************************************************************************
     * GETTERS AND SETTERS
     **********************************************************************************************/

    public int getAttackModificator() {
        return attackModificator;
    }

    public void setAttackModificator(int attackModificator) {
        this.attackModificator = attackModificator;
    }

}
