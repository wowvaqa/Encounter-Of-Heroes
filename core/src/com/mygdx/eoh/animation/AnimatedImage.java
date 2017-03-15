package com.mygdx.eoh.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Animated image
 * Created by v on 2016-10-19.
 */
public class AnimatedImage extends Image {

    private Animation animation = null;
    private boolean isLooped = true;
    private float stateTime = 0;

    protected AnimatedImage(Animation animation, boolean isLooped) {

        super(animation.getKeyFrame(0));
        this.animation = animation;
        this.isLooped = isLooped;

        this.setSize(100, 100);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isLooped) {
            ((TextureRegionDrawable) getDrawable()).setRegion(animation.getKeyFrame(stateTime += delta, true));
            super.act(delta);
        } else {
            if (!animation.isAnimationFinished(stateTime)) {
                ((TextureRegionDrawable) getDrawable()).setRegion(animation.getKeyFrame(stateTime += delta, true));
                super.act(delta);
//            } else {
//                this.remove();
            }
        }
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

//    public boolean isLooped() {
//        return isLooped;
//    }

    public void setLooped(boolean looped) {
        isLooped = looped;
    }

    protected float getStateTime() {
        return stateTime;
    }


}
