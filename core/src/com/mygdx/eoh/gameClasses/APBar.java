package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.eoh.animation.AnimatedImage;

/**
 * Representation of animated bar of player mob.
 * Created by v on 2017-01-08.
 */

public class APBar extends AnimatedImage {
    private PlayerMob playerMobParent;
    private boolean apBarAdd= false;

    public APBar(Animation animation, boolean isLooped, PlayerMob playerMob) {
        super(animation, isLooped);
        this.playerMobParent = playerMob;
        this.setSize(Options.tileSize, Options.tileSize);
        setZIndex(4);
        this.setTouchable(Touchable.disabled);
    }

    private void changePosition(){
        this.setPosition(playerMobParent.getX(), playerMobParent.getY());
    }

    /***********************************************************************************************
     * OVERRIDE METHODS
     ***********************************************************************************************/
    @Override
    public void act(float delta) {
        super.act(delta);

        if (getAnimation().isAnimationFinished(getStateTime())){
            playerMobParent.setActionPoints(playerMobParent.getActionPoints() + 1);
            this.setStateTime(0);
            this.apBarAdd = false;
            this.remove();
        }

        if (playerMobParent.getActualhp() < 1) {
            this.remove();
        }

        if (this.getX() != playerMobParent.getX()){
            changePosition();
        }

        if (this.getY() != playerMobParent.getY()){
            changePosition();
        }
    }

    /***********************************************************************************************
     * GETTERS & SETTERS
     ***********************************************************************************************/
    public PlayerMob getPlayerMobParent() {
        return playerMobParent;
    }

    public void setPlayerMobParent(PlayerMob playerMobParent) {
        this.playerMobParent = playerMobParent;
    }

    public boolean isApBarAdd() {
        return apBarAdd;
    }

    public void setApBarAdd(boolean apBarAdd) {
        this.apBarAdd = apBarAdd;
    }
}
