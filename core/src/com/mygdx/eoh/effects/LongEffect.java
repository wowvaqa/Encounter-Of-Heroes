package com.mygdx.eoh.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.enums.LongEffects;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 *
 * Created by v on 2017-02-21.
 */

public class LongEffect extends AnimatedImage {

    private LongEffects longEffects;

    private PlayerMob playerOwner;
    private float effectDuration;
    private float timeStep;

    private int attackModificator;

    public LongEffect(Animation animation, boolean isLooped, LongEffects longEffects, PlayerMob playerOwner) {
        super(animation, isLooped);

        this.setSize(Options.tileSize, Options.tileSize);
        this.longEffects = longEffects;
        this.playerOwner = playerOwner;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (getAnimation().isAnimationFinished(this.getStateTime())){
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
