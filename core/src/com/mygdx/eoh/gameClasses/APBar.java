package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.mob.FreeMob;

/**
 * Representation of animated bar of player mob.
 * Created by v on 2017-01-08.
 */

public class APBar extends AnimatedImage {
    private PlayerMob playerMobParent;
    private FreeMob freeMobParent;
    private boolean apBarAdd = false;

    public APBar(Animation animation, boolean isLooped, PlayerMob playerMob) {
        super(animation, isLooped);
        this.playerMobParent = playerMob;
        this.setSize(50, 50);
        setZIndex(4);
        this.setTouchable(Touchable.disabled);
    }

    public APBar(Animation animation, boolean isLooped, FreeMob freeMob) {
        super(animation, isLooped);
        this.freeMobParent = freeMob;
        this.setSize(50, 50);
        setZIndex(4);
        this.setTouchable(Touchable.disabled);
    }

    /**
     * Recalculate freame duration of HpBar
     * @param o Object of Freemob.CLASS or PlayerMob.CLASS
     */
    static public void recalculateApBarFrameDuration(Object o) {
        float animationSpeed = getApBarFrameDuration(o);

        if (animationSpeed <= 0)
            animationSpeed = 0.001f;

        if (o.getClass().equals(PlayerMob.class)) {
            ((PlayerMob) o).getApBar().getAnimation().setFrameDuration(animationSpeed);
        } else if (o.getClass().equals(FreeMob.class))
            ((FreeMob) o).getApBar().getAnimation().setFrameDuration(animationSpeed);
    }

    /**
     * Count new frame duration
     *
     * @param o Object of PlayerMob or FreeMobc CLASS
     * @return new frame duration
     */
    static public float getApBarFrameDuration(Object o) {
        float frameDuration = 1.0f;

        if (o.getClass().equals(PlayerMob.class)) {
            frameDuration = (17.0f - (((PlayerMob) o).getActualSpeed() + ModifierGetter.getSpeedModifier(o)) * 0.5f) / 24;
        } else if (o.getClass().equals(FreeMob.class)) {
            frameDuration = (17.0f - (((FreeMob) o).getActualSpeed() + ModifierGetter.getSpeedModifier(o)) * 0.5f) / 24;
        }

        return frameDuration;
    }

//    /**
//     * Change length of animation frame
//     *
//     * @param playerMob Player mob to recalculate apBar frame duration.
//     */
//    static public void recalculateApBarFrameDuration(PlayerMob playerMob) {
//
//        float animationSpeed = (17.0f - (playerMob.getActualSpeed() + ModifierGetter.getSpeedModifier(playerMob)) * 0.5f) / 24;
//        //System.out.println("Frame duration of APBAR: " + animationSpeed);
//
//        if (animationSpeed <= 0)
//            animationSpeed = 0.001f;
//
//        playerMob.getApBar().getAnimation().setFrameDuration(
//                animationSpeed
//        );
//    }

    private void changePosition() {
        if (playerMobParent != null)
            this.setPosition(playerMobParent.getX() + 5, playerMobParent.getY() + 5);
        if (freeMobParent != null)
            this.setPosition(freeMobParent.getX() + 5, freeMobParent.getY() + 5);
    }

    /***********************************************************************************************
     * OVERRIDE METHODS
     ***********************************************************************************************/
    @Override
    public void act(float delta) {
        super.act(delta);

        if (getAnimation().isAnimationFinished(getStateTime())) {
            if (playerMobParent != null)
                playerMobParent.setActionPoints(playerMobParent.getActionPoints() + 1);
            if (freeMobParent != null)
                freeMobParent.setActionPoints(freeMobParent.getActionPoints() + 1);
            this.setStateTime(0);
            this.apBarAdd = false;
            this.remove();
        }

        if (playerMobParent != null && playerMobParent.getActualhp() < 1) {
            this.remove();
        }

        if (freeMobParent != null && freeMobParent.getActualhp() < 1) {
            this.remove();
        }

        if (playerMobParent != null && this.getX() != playerMobParent.getX()) {
            changePosition();
        }

        if (freeMobParent != null && this.getX() != freeMobParent.getX()) {
            changePosition();
        }

        if (playerMobParent != null && this.getY() != playerMobParent.getY()) {
            changePosition();
        }

        if (freeMobParent != null && this.getY() != freeMobParent.getY()) {
            changePosition();
        }
    }

    /***********************************************************************************************
     * GETTERS & SETTERS
     ***********************************************************************************************/
    public boolean isApBarAdd() {
        return apBarAdd;
    }

    public void setApBarAdd(boolean apBarAdd) {
        this.apBarAdd = apBarAdd;
    }
}