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
     * Recalculate freame duration of HpBar
     * @param o Object of Freemob.CLASS or PlayerMob.CLASS
     */
    static public void recalculateHpBarFrameDuration(Object o) {
        float animationSpeed = getHpBarFrameDuration(o);

        if (animationSpeed <= 0)
            animationSpeed = 0.001f;

        if (o.getClass().equals(PlayerMob.class)) {
            ((PlayerMob) o).getHpBar().getAnimation().setFrameDuration(animationSpeed);
        } else if (o.getClass().equals(FreeMob.class))
            ((FreeMob) o).getHpBar().getAnimation().setFrameDuration(animationSpeed);
    }

    /**
     * Count new frame duration
     *
     * @param o Object of PlayerMob or FreeMobc CLASS
     * @return new frame duration
     */
    static public float getHpBarFrameDuration(Object o) {
        float frameDuration = 1.0f;

        if (o.getClass().equals(PlayerMob.class)) {
            frameDuration = (30.0f - (((PlayerMob) o).getMaxHp() + ModifierGetter.getHpModifier(o)) * 0.5f) / 6;
        } else if (o.getClass().equals(FreeMob.class)) {
            frameDuration = (30.0f - (((FreeMob) o).getMaxHp() + ModifierGetter.getHpModifier(o)) * 0.5f) / 6;
        }

        return frameDuration;
    }

    /**
     * Change position of HpBar
     */
    private void changePosition() {
        if (playerMobParent != null)
            this.setPosition(playerMobParent.getX() + 5, playerMobParent.getY() + 115);
        if (freeMobParent != null)
            this.setPosition(freeMobParent.getX() + 5, freeMobParent.getY() + 60);
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
            //this.hpBarAdd = false;
            this.remove();
        }

        if (playerMobParent != null && playerMobParent.getActualhp() >= playerMobParent.getMaxHp()) {
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

    @Override
    public boolean remove() {
        this.hpBarAdd = false;
        return super.remove();
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
