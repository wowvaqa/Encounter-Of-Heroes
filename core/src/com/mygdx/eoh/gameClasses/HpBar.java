package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.mob.FreeMob;

/**
 * HP BAR Animation
 * Created by v on 27.05.17.
 */

public class HpBar extends AnimatedImage {

    private PlayerMob playerMobParent;
    private FreeMob freeMobParent;
    private boolean hpBarAdd = false;

    public HpBar(Animation animation, boolean isLooped, PlayerMob playerMob) {
        super(animation, isLooped);
        this.playerMobParent = playerMob;
        this.setSize(50, 50);
        setZIndex(4);
        this.setTouchable(Touchable.disabled);
    }

    public HpBar(Animation animation, boolean isLooped, FreeMob freeMob) {
        super(animation, isLooped);
        this.freeMobParent = freeMob;
        this.setSize(50, 50);
        setZIndex(4);
        this.setTouchable(Touchable.disabled);
    }

    /**
     * Change length of animation frame
     *
     * @param playerMob Player mob to recalculate apBar frame duration.
     */
    static public void recalculateApBarFrameDuration(PlayerMob playerMob) {

        float animationSpeed = (17.0f - (playerMob.getMaxHp() + ModifierGetter.getHpModifier(playerMob)) * 0.5f) / 24;
        //System.out.println("Frame duration of APBAR: " + animationSpeed);

        if (animationSpeed <= 0)
            animationSpeed = 0.001f;

        playerMob.getHpBar().getAnimation().setFrameDuration(
                animationSpeed
        );
    }

    private void changePosition() {
        if (playerMobParent != null)
            this.setPosition(playerMobParent.getX() + 5, playerMobParent.getY() + 110);
//        if (freeMobParent != null)
//            this.setPosition(freeMobParent.getX() + 5, freeMobParent.getY() + 5);
    }

    /***********************************************************************************************
     * OVERRIDE METHODS
     ***********************************************************************************************/
    @Override
    public void act(float delta) {
        super.act(delta);

        if (getAnimation().isAnimationFinished(getStateTime())) {
            if (playerMobParent != null)
                playerMobParent.setActualhp(playerMobParent.getActualhp() + 1);
            if (freeMobParent != null)
                freeMobParent.setActualhp(freeMobParent.getActualhp() + 1);
            this.setStateTime(0);
            this.hpBarAdd = false;
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
    public boolean isHpBarAdd() {
        return hpBarAdd;
    }

    public void setHpBarAdd(boolean hpBarAdd) {
        this.hpBarAdd = hpBarAdd;
    }
}
