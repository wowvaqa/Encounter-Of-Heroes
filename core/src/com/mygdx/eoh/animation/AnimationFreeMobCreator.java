package com.mygdx.eoh.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.enums.FreeMobAnimationTypes;

/**
 * Class for create animations of free mobs.
 * Created by wowvaqa on 11.04.17.
 */

public class AnimationFreeMobCreator {
    private static final AnimationFreeMobCreator instance = new AnimationFreeMobCreator();

    private AnimationFreeMobCreator() {
    }

    public static AnimationFreeMobCreator getInstance() {
        return instance;
    }

    public Animation makeAnimation(FreeMobAnimationTypes freeMobAnimationType) {
        //float animationSpeed = (2.0f - playerMob.getActualSpeed() * 0.05f) / 24;
        //float animationSpeed = (2.0f - (playerMob.getActualSpeed() + ModifierGetter.getSpeedModifier(playerMob)) * 0.05f) / 24;
        float animationSpeed = (2.0f - (5) * 0.05f) / 24;

        TextureRegion[] walkFrames = null;
        TextureAtlas textureAtlas;

        switch (freeMobAnimationType) {

            case SkeletonStanding:
                animationSpeed = (2.0f - (5) * 0.05f) / 24;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/skeletonAnimation/skeletonStanding.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case SkeletonStandingSelected:
                animationSpeed = (2.0f - (5) * 0.05f) / 24;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/skeletonAnimation/skeletonStandingSelected.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
        }

        if (walkFrames == null) throw new AssertionError();
        return new Animation(animationSpeed, walkFrames);
    }
}
