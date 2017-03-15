package com.mygdx.eoh.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.eoh.assets.AssetsGameInterface;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.enums.InstantEffects;
import com.mygdx.eoh.enums.LongEffects;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * Animation creator for spells.
 * Created by v on 2017-02-22.
 */
public class AnimationSpellCreator {
    private static AnimationSpellCreator instance = new AnimationSpellCreator();

    public static AnimationSpellCreator getInstance() {
        return instance;
    }

    private AnimationSpellCreator() {
    }

    public Animation makeSpellAnimation(InstantEffects instantEffects) {

        float animationSpeed;

        TextureRegion[] walkFrames;
        TextureAtlas textureAtlas;

        switch (instantEffects) {
            case FiraballDamage:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/spellsAnimations/fireballAnimation.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
            case AttackUpgrade:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/spellsAnimations/attackUpgradeAnimation.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
            default:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/spellsAnimations/fireballAnimation.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
        }

        return new Animation(animationSpeed, walkFrames);
    }

    public Animation makeLongEffectAnimation(LongEffects longEffects, PlayerMob caster){
        float animationSpeed;
        float effectDuration;

        TextureRegion[] walkFrames;
        TextureAtlas textureAtlas;

        switch (longEffects) {
            case AttackUpgrade:
                animationSpeed = caster.getActualPower() * 2.0f;
                System.out.println("Animation Speed: " + animationSpeed);
                textureAtlas = AssetsGameInterface.getInstance().getManager().get("game/interface/spellEffectsAnimations/attackUpgradeEffectIcon.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
            default:
                animationSpeed = caster.getActualPower() * 2.0f;
                textureAtlas = AssetsGameInterface.getInstance().getManager().get("game/interface/spellEffectsAnimations/attackUpgradeEffectIcon.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
        }

        return new Animation(animationSpeed, walkFrames);
    }
}
