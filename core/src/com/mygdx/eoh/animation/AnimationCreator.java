package com.mygdx.eoh.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.gameClasses.ModifierGetter;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * Creates animations.
 * Created by v on 2016-10-19.
 */
public class AnimationCreator {

    private static AnimationCreator instance = new AnimationCreator();

    private AnimationCreator() {
    }

    public static AnimationCreator getInstance() {
        return instance;
    }

    public Animation makeAnimation(com.mygdx.eoh.enums.AnimationTypes animationType, PlayerMob playerMob) {
        //float animationSpeed = (2.0f - playerMob.getActualSpeed() * 0.05f) / 24;
        float animationSpeed = (2.0f - (playerMob.getActualSpeed() + ModifierGetter.getSpeedModifier(playerMob)) * 0.05f) / 24;

        TextureRegion[] walkFrames = null;
        TextureAtlas textureAtlas;

        switch (animationType) {

            case ApBarAnimation:
                animationSpeed = (15.0f - (playerMob.getActualSpeed() + ModifierGetter.getSpeedModifier(playerMob)) * 0.05f) / 24;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/apBarAnimation/apBarAnimation.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ManaBarAnimation:
                animationSpeed = (15.0f - (playerMob.getActualWisdom() + ModifierGetter.getWisdomModifier(playerMob)) * 0.05f) / 24;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/manaBarAnimation/mpBarAnimation.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case HpBarAnimation:
                animationSpeed = (15.0f - (playerMob.getActualhp() + ModifierGetter.getHpModifier(playerMob)) * 0.05f) / 24;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/apBarAnimation/hpBarAnimation.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightWalkN:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightWalkN.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightWalkS:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightWalkS.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightWalkE:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightWalkE.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightWalkW:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightWalkW.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightAttackN:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightAttackN.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightAttackS:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightAttackS.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightAttackE:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightAttackE.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightAttackW:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightAttackW.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
            case KnightCast:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightCast.atlas", TextureAtlas.class);

                animationSpeed = (2.0f - (playerMob.getActualWisdom() + ModifierGetter.getWisdomModifier(playerMob)) * 0.05f) / 24;

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            /***************************************************************************************
             *  WIZARD ANIMATION ATTACK & MOVE
             **************************************************************************************/
            case WizardWalkN:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardWalkN.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardWalkS:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardWalkS.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardWalkE:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardWalkE.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardWalkW:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardWalkW.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardAttackN:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardAttackN.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardAttackS:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardAttackS.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardAttackE:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardAttackE.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardAttackW:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardAttackW.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardCast:
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardCast.atlas", TextureAtlas.class);

                animationSpeed = (2.0f - (playerMob.getActualWisdom() + ModifierGetter.getWisdomModifier(playerMob)) * 0.05f) / 24;

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;


        }

        assert walkFrames != null;
        return new Animation(animationSpeed, walkFrames);
    }

    public Animation makeAnimation(com.mygdx.eoh.enums.AnimationTypes animationType) {
        float animationSpeed = 0.05f;

        Texture texture;
        TextureRegion[][] tmp;
        TextureRegion[] walkFrames = null;
        TextureAtlas textureAtlas;
        int index = 0;

        switch (animationType) {
            case ApBarAnimation:
                animationSpeed = 0.27f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/apBarAnimation/apBarAnimation.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case HpBarAnimation:
                animationSpeed = 0.27f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/apBarAnimation/hpBarAnimation.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveN:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowN.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveS:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowS.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveE:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowE.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveW:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowW.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveNW:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowNW.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveNE:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowNE.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveSE:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowSE.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ArrowMoveSW:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/arrowSW.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case CancelMove:
                animationSpeed = 0.09f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/cancelMove.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case AttackMove:
                animationSpeed = 0.09f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/attackMove.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case CastMove:
                animationSpeed = 0.09f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/interface/buttonsAnimation/castMove.atlas", TextureAtlas.class);
                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case HospitalAnimation:
                animationSpeed = 0.1f;
                texture = AssetsGameScreen.getInstance().getManager().get("game/buldingAnimations/hospitalAnimation.png", Texture.class);
                tmp = TextureRegion.split(texture, texture.getWidth() / 6, texture.getHeight() / 4);
                walkFrames = new TextureRegion[6 * 4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 6; j++) {
                        walkFrames[index++] = tmp[i][j];
                    }
                }
                break;
//            case KnightAnimation:
//                animationSpeed = 0.07f;
//                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightWalkE.atlas", TextureAtlas.class);
//
//                walkFrames = new TextureRegion[textureAtlas.getRegions().size];
//
//                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
//                    walkFrames[i] = textureAtlas.getRegions().get(i);
//                }
//                break;

            case KnightStanding:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightStanding.atlas", TextureAtlas.class);
                //textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/castleAnimations/castle.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case KnightSelected:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/knightAnimations/knightSelected.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardStanding:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardStanding.atlas", TextureAtlas.class);
                //textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/castleAnimations/castle.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case WizardSelected:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/wizardAnimations/wizardSelected.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case CastleAnimation:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/castleAnimations/castle.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case CastleSelectedAnimation:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/mobsAnimations/castleAnimations/castleSelected.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case ItemGold:
                animationSpeed = 0.09f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/items/gold/itemGold.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
            case ManaPotionAnimation:
                animationSpeed = 0.09f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/items/mixtures/manaPotion.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
            case HealthPotionAnimation:
                animationSpeed = 0.09f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/items/mixtures/healthPotion.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
            case PotionUseAnimation:
                animationSpeed = 0.09f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/items/mixtures/potionUseAnimation.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;

            case TreasureAnimation:
                animationSpeed = 0.07f;
                textureAtlas = AssetsGameScreen.getInstance().getManager().get("game/items/treasure/treasureAnimation.atlas", TextureAtlas.class);

                walkFrames = new TextureRegion[textureAtlas.getRegions().size];

                for (int i = 0; i < textureAtlas.getRegions().size; i++) {
                    walkFrames[i] = textureAtlas.getRegions().get(i);
                }
                break;
        }
        return new Animation(animationSpeed, walkFrames);
    }
}
