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

    private int attackModifier;
    private int defenceModifier;
    private int speedModifier;
    private int wisdomModifier;
    private int powerModifier;

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

    public int getAttackModifier() {
        return attackModifier;
    }

    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }

    public int getDefenceModifier() {
        return defenceModifier;
    }

    public void setDefenceModifier(int defenceModifier) {
        this.defenceModifier = defenceModifier;
    }

    public int getSpeedModifier() {
        return speedModifier;
    }

    public void setSpeedModifier(int speedModifier) {
        this.speedModifier = speedModifier;
    }

    public int getWisdomModifier() {
        return wisdomModifier;
    }

    public void setWisdomModifier(int wisdomModifier) {
        this.wisdomModifier = wisdomModifier;
    }

    public int getPowerModifier() {
        return powerModifier;
    }

    public void setPowerModifier(int powerModifier) {
        this.powerModifier = powerModifier;
    }
}
