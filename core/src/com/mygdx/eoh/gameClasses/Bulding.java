package com.mygdx.eoh.gameClasses;

import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.enums.AnimationTypes;

/**
 * Bulding representation
 * Created by v on 2016-10-19.
 */
public class Bulding extends AnimatedImage {

    public Bulding(AnimationTypes animationType) {
        super(AnimationCreator.getInstance().makeAnimation(animationType), true);
    }
}
