package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.eoh.animation.AnimatedImage;

/**
 * Mana Bar of player Mob
 * Created by v on 2017-02-28.
 */

public class ManaBar extends AnimatedImage {
    private PlayerMob playerMobParent;
    private boolean manaBarAdd = false;

    protected ManaBar(Animation animation, boolean isLooped, PlayerMob playerMob) {
        super(animation, isLooped);
        this.playerMobParent = playerMob;
        this.setSize(Options.tileSize, Options.tileSize);
        setZIndex(4);
        this.setTouchable(Touchable.disabled);
    }

    private void changePosition(){
        this.setPosition(playerMobParent.getX(), playerMobParent.getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getAnimation().isAnimationFinished(getStateTime())){
            playerMobParent.setActualMana(playerMobParent.getActualMana() + 1);
            this.setStateTime(0);
            this.manaBarAdd = false;
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
     **********************************************************************************************/

    public boolean isManaBarAdd() {
        return manaBarAdd;
    }

    public void setManaBarAdd(boolean manaBarAdd) {
        this.manaBarAdd = manaBarAdd;
    }
}
