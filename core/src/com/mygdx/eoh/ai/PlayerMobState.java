package com.mygdx.eoh.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * States of playerMob
 * Created by v on 2017-09-17.
 */

public enum PlayerMobState implements State<PlayerMob> {

    MOVE_TO_TREASURE() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);

            if (playerMob.getAi().getTreasureCells().size() > 0) {

                int moveX, moveY;

                moveX = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveY();

                if (moveX == 0 && moveY == 1) {
                    playerMob.getMoveManager().movePlayerMobNorthRecivedFromNET(playerMob);
                } else if (moveX == 0 && moveY == -1) {
                    playerMob.getMoveManager().movePlayerMobSouthRecivedFromNET(playerMob);
                } else if (moveX == -1 && moveY == -1) {
                    playerMob.getMoveManager().movePlayerMobSouthWestRecivedFromNET(playerMob);
                } else if (moveX == 1 && moveY == -1) {
                    playerMob.getMoveManager().movePlayerMobSouthEastRecivedFromNET(playerMob);
                } else if (moveX == -1 && moveY == 0) {
                    playerMob.getMoveManager().movePlayerMobWestRecivedFromNET(playerMob);
                } else if (moveX == 1 && moveY == 0) {
                    playerMob.getMoveManager().movePlayerMobEastRecivedFromNET(playerMob);
                } else if (moveX == 1 && moveY == 1) {
                    playerMob.getMoveManager().movePlayerMobNorthEastRecivedFromNET(playerMob);
                } else if (moveX == -1 && moveY == 1) {
                    playerMob.getMoveManager().movePlayerMobNorthWestRecivedFromNET(playerMob);
                }
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
