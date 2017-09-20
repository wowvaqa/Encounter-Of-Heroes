package com.mygdx.eoh.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * States of playerMob
 * Created by v on 2017-09-17.
 */

public enum PlayerMobState implements State<PlayerMob> {

    ATTACK_ENEMY() {
        @Override
        public void update(PlayerMob playerMob) {
            Gdx.app.log("Przechodzę w tryb ataku na przeciwnika", "");

            playerMob.setBusy(true);

            playerMob.changeToAttackAnimation(playerMob,
                    playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().getCoordinateXonMap(),
                    playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().getCoordinateYonMap());

            int damage = FightManager.getDamage(playerMob, playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob());
            playerMob.getAi().showDamageLabel(damage, playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().getCoordinateXonMap(),
                    playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().getCoordinateYonMap(),
                    GameStatus.getInstance().getMapStage());

            if (playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().getActualhp() < 1) {
                playerMob.getAi().getPlayerMobCells().remove(0);
            }

            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    MOVE_TO_ENEMY() {
        @Override
        public void update(PlayerMob playerMob) {

            playerMob.setBusy(true);

            if (playerMob.getAi().getPlayerMobCells().size() > 0) {
                int moveX, moveY;
                moveX = playerMob.getAi().getPlayerMobCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getPlayerMobCells().get(0).getMoveList().get(0).getMoveY();


                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getPlayerMobCells().remove(0);

                playerMob.getStateMachine().changeState(WAIT);
            }
        }
    },

    MOVE_TO_TREASURE() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);

            if (playerMob.getAi().getTreasureCells().size() > 0) {

                int moveX, moveY;
                moveX = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveY();

                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getTreasureCells().remove(0);
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    WAIT() {
        @Override
        public void update(PlayerMob playerMob) {

            // Sprawdzenie czy bohater jest zajęty np. wykonywaniem ruchu oraz czy upłynął czas bezczynności
            // zadany w pozimie trudności gry.
            if (!playerMob.isBusy() && playerMob.getAi().checkDificultyTimeCounter(playerMob)) {

                // Sprawdzenie czy są dostępne skrzynie ze skarbami.
                if (playerMob.getAi().findAvailableTreasureBoxes(playerMob.getFieldOfPlayerMob()).size() > 0) {
                    playerMob.getStateMachine().changeState(MOVE_TO_TREASURE);
                    //
                } else if (playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).size() > 0 &&
                        playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).get(0).getDistance() < 2) {
                    //if( playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).get(0).getDistance() < 2 )
                    playerMob.getStateMachine().changeState(ATTACK_ENEMY);
                    // Sprawdzenie dostępności wrogów.
                } else if (playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).size() > 0) {
                    playerMob.getStateMachine().changeState(MOVE_TO_ENEMY);
                } else {
                    playerMob.getStateMachine().changeState(WAIT);
                }
            }

            // Sprawdzenie czy ilość punktów akcji bohatera jest większa od 0.
            if (playerMob.getActionPoints() < 1) {
                playerMob.getStateMachine().changeState(AP_RECOVERY);
            }
        }
    },

    AP_RECOVERY() {
        @Override
        public void update(PlayerMob playerMob) {
            if (playerMob.getActionPoints() > 0) {
                playerMob.getStateMachine().changeState(WAIT);
            }
        }
    };

    @Override
    public void enter(PlayerMob entity) {

    }

    @Override
    public void update(PlayerMob entity) {

    }

    @Override
    public void exit(PlayerMob entity) {

    }

    @Override
    public boolean onMessage(PlayerMob entity, Telegram telegram) {
        return false;
    }
}
