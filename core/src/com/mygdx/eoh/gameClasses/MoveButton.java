package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.eoh.animation.AnimatedImage;

/**
 *
 * Created by v on 2017-01-11.
 */

class MoveButton extends AnimatedImage{

    private PlayerMob buttonOwner;

    MoveButton(Animation animation, boolean isLooped, float coordinateXonStage, float coordinateYonStage, PlayerMob playerMob) {
        super(animation, isLooped);
        super.setPosition(coordinateXonStage, coordinateYonStage);
        super.setSize(Options.tileSize, Options.tileSize);
        this.buttonOwner = playerMob;
        setZIndex(8);
    }

    PlayerMob getButtonOwner() {
        return buttonOwner;
    }
}
