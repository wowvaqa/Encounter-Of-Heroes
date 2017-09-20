package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.defaultClasses.DefaultDamageLabel;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 *
 * Created by v on 2017-01-11.
 */

public class AttackButton extends AnimatedImage {

    private PlayerMob buttonOwner;

    /**
     * Attack button constructor
     *
     * @param animation      Animation of button
     * @param isLooped       is looped
     * @param locationXonMap location X of button on map
     * @param locationYonMap location Y of button on map
     * @param playerMob      button owner.
     */
    AttackButton(Animation animation, boolean isLooped, final int locationXonMap,
                 final int locationYonMap, final PlayerMob playerMob) {
        super(animation, isLooped);
        super.setPosition(locationXonMap * Options.tileSize, locationYonMap * Options.tileSize);
        super.setSize(Options.tileSize, Options.tileSize);
        this.buttonOwner = playerMob;
        setZIndex(8);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                buttonOwner.changeToAttackAnimation(buttonOwner, locationXonMap, locationYonMap);

                int damage = 0;

                if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob() != null) {
                    damage = FightManager.getDamage(playerMob, GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob());
                }

                if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob() != null) {
                    damage = FightManager.getDamage(playerMob, GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob());

                    GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob().setAttacked(true);
                    GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob().setAttackingPlayerMob(playerMob);
                }

                showDamageLabel(damage, locationXonMap, locationYonMap, getStage());

                if (NetStatus.getInstance().getClient() != null) {
                    if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob() != null) {
                        attackOfPlayerMobNET(locationXonMap, locationYonMap, damage,
                                GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob().getActualhp(),
                                buttonOwner.getCoordinateXonMap(), buttonOwner.getCoordinateYonMap());
                    } else if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob() != null) {
                        attackOfPlayerMobNET(locationXonMap, locationYonMap, damage,
                                GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob().getActualhp(),
                                buttonOwner.getCoordinateXonMap(), buttonOwner.getCoordinateYonMap());
                    }
                }
            }
        });
    }

    private void showDamageLabel(int damage, int locationXonMap, int locationYonMap, Stage stage) {
        DefaultDamageLabel defaultDamageLabel = new DefaultDamageLabel(
                Integer.toString(damage), (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"), "fight",
                locationXonMap * Options.tileSize + Options.tileSize / 2,
                locationYonMap * Options.tileSize + Options.tileSize / 2);
        stage.addActor(defaultDamageLabel);
    }

    private void attackOfPlayerMobNET(int locationXofEnemy, int locationYofEnemy,
                                      int damage, int hpLeft, int locationXofAttacker, int locationYofAttacker) {

        if (GameStatus.getInstance().getMap().getFields()[locationXofEnemy][locationYofEnemy].getPlayerMob() != null) {

            Network.AttackPlayerMob attackPlayerMob = new Network.AttackPlayerMob();
            attackPlayerMob.enemyId = NetStatus.getInstance().getEnemyId();
            attackPlayerMob.locationXofEnemy = locationXofEnemy;
            attackPlayerMob.locationYofEnemy = locationYofEnemy;
            attackPlayerMob.indexInArray = PlayerMob.getPlayerMobIndex(
                    GameStatus.getInstance().getMap().getFields()[locationXofEnemy][locationYofEnemy].getPlayerMob(),
                    GameStatus.getInstance().getMap().getFields()[locationXofEnemy][locationYofEnemy].getPlayerMob().getPlayerOwner());
            attackPlayerMob.indexPlayerOwner =
                    GameStatus.getInstance().getMap().getFields()[locationXofEnemy][locationYofEnemy].getPlayerMob().getPlayerOwner().getInedxOfPlayerInArrayOfPlayer();
            attackPlayerMob.damage = damage;
            attackPlayerMob.hpLeft = hpLeft;
            attackPlayerMob.locationXofAttacker = locationXofAttacker;
            attackPlayerMob.locationYofAttacker = locationYofAttacker;
            NetStatus.getInstance().getClient().sendTCP(attackPlayerMob);

        } else if (GameStatus.getInstance().getMap().getFields()[locationXofEnemy][locationYofEnemy].getFreeMob() != null) {

            Network.AttackPlayerMob attackPlayerMob = new Network.AttackPlayerMob();
            attackPlayerMob.enemyId = NetStatus.getInstance().getEnemyId();
            attackPlayerMob.locationXofEnemy = locationXofEnemy;
            attackPlayerMob.locationYofEnemy = locationYofEnemy;
            attackPlayerMob.indexInArray = -1;
            attackPlayerMob.indexPlayerOwner = -1;
            attackPlayerMob.damage = damage;
            attackPlayerMob.hpLeft = hpLeft;
            attackPlayerMob.locationXofAttacker = locationXofAttacker;
            attackPlayerMob.locationYofAttacker = locationYofAttacker;
            NetStatus.getInstance().getClient().sendTCP(attackPlayerMob);
        }

    }

//    /**
//     *
//     * @param playerMob
//     * @param mapXcoordinateOfEnemy
//     * @param mapYcoordinageOfEnemy
//     * @return
//     */
//    private Animation getAnimationForAttack(
//            PlayerMob playerMob, int mapXcoordinateOfEnemy, int mapYcoordinageOfEnemy) {
//        //  SOUTH
//        if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackS, playerMob);
//            //  NORTH
//        } else if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackN, playerMob);
//            // WEST
//        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackW, playerMob);
//            // EAST
//        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackE, playerMob);
//            // NORTH - EAST
//        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackW, playerMob);
//            // NORTH - WEST
//        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackE, playerMob);
//            // SOUTH - EAST
//        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackW, playerMob);
//            // SOUTH - WEST
//        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
//            return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackE, playerMob);
//        }
//        return AnimationCreator.getInstance().makeAnimation(AnimationTypes.KnightAttackS, playerMob);
//    }

//    private SequenceAction getSequenceForAttack(
//            PlayerMob playerMob, int mapXcoordinateOfEnemy, int mapYcoordinageOfEnemy) {
//
//        float velocity = 2.0f - playerMob.getActualSpeed() * 0.05f;
//        //  NORTH
//        if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(0, -25, velocity), Actions.moveBy(0, 25, velocity));
//            //  SOUTH
//        } else if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(0, 25, velocity), Actions.moveBy(0, -25, velocity));
//            // EAST
//        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(-25, 0, velocity), Actions.moveBy(25, 0, velocity));
//            // WEST
//        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(25, 0, velocity), Actions.moveBy(-25, 0, velocity));
//            // NORTH - EAST
//        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(-25, -25, velocity), Actions.moveBy(25, 25, velocity));
//            // NORTH - WEST
//        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(25, -25, velocity), Actions.moveBy(-25, 25, velocity));
//            // SOUTH - EAST
//        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(-25, 25, velocity), Actions.moveBy(25, -25, velocity));
//            // SOUTH - WEST
//        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
//                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
//            return new SequenceAction(Actions.moveBy(25, 25, velocity), Actions.moveBy(-25, -25, velocity));
//        }
//        return new SequenceAction(Actions.moveBy(-250, -250, velocity), Actions.moveBy(250, 250, velocity));
//    }

    PlayerMob getButtonOwner() {
        return buttonOwner;
    }

//    public void setButtonOwner(PlayerMob buttonOwner) {
//        this.buttonOwner = buttonOwner;
//    }
}
