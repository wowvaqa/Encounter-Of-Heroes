package com.mygdx.eoh.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Player;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * States of playerMob
 * Created by v on 2017-09-17.
 */

public enum PlayerMobState implements State<PlayerMob> {

    ATTACK_PLAYER_MOB() {
        @Override
        public void update(PlayerMob playerMob) {
            //Gdx.app.log("Przechodzę w tryb ataku na przeciwnika", "");

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

    MOVE_TO_FREE_MOB() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);

            if (playerMob.getAi().getFreeMobCells().size() > 0) {
                // Odczytanie współżędnych dla ruchu.
                int moveX, moveY;
                moveX = playerMob.getAi().getFreeMobCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getFreeMobCells().get(0).getMoveList().get(0).getMoveY();

                // Przerysowanie interfejsu ruchu i ataku.
                for (Player player : GameStatus.getInstance().getPlayers()) {
                    for (PlayerMob playerMob1 : player.getPlayerMobs()) {
                        playerMob1.getMoveManager().redrawButtons(playerMob1.getStage(), playerMob1);
                    }
                }

                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getFreeMobCells().remove(0);

                playerMob.getStateMachine().changeState(WAIT);
            }
        }
    },

    ATTACK_FREE_MOB() {
        @Override
        public void update(PlayerMob playerMob) {
            //Gdx.app.log("Przechodzę w tryb ataku na free moba", "");

            playerMob.setBusy(true);

            playerMob.changeToAttackAnimation(playerMob,
                    playerMob.getAi().getFreeMobCells().get(0).getFreeMob().getCoordinateXonMap(),
                    playerMob.getAi().getFreeMobCells().get(0).getFreeMob().getCoordinateYonMap());

            int damage = FightManager.getDamage(playerMob, playerMob.getAi().getFreeMobCells().get(0).getFreeMob());
            playerMob.getAi().showDamageLabel(damage, playerMob.getAi().getFreeMobCells().get(0).getFreeMob().getCoordinateXonMap(),
                    playerMob.getAi().getFreeMobCells().get(0).getFreeMob().getCoordinateYonMap(),
                    GameStatus.getInstance().getMapStage());

            playerMob.getAi().getFreeMobCells().get(0).getFreeMob().setAttacked(true);
            playerMob.getAi().getFreeMobCells().get(0).getFreeMob().setAttackingPlayerMob(playerMob);

            if (playerMob.getAi().getFreeMobCells().get(0).getFreeMob().getActualhp() < 1) {
                playerMob.getAi().getFreeMobCells().remove(0);
            }

            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    MOVE_TO_PLAYER_MOB() {
        @Override
        public void update(PlayerMob playerMob) {

            playerMob.setBusy(true);

            if (playerMob.getAi().getPlayerMobCells().size() > 0) {
                // Odczytanie współżędnych dla ruchu.
                int moveX, moveY;
                moveX = playerMob.getAi().getPlayerMobCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getPlayerMobCells().get(0).getMoveList().get(0).getMoveY();

                // Przerysowanie interfejsu ruchu i ataku.
                for (Player player : GameStatus.getInstance().getPlayers()) {
                    for (PlayerMob playerMob1 : player.getPlayerMobs()) {
                        playerMob.getMoveManager().redrawButtons(playerMob1.getStage(), playerMob1);
                    }
                }

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
                // Odczytanie współżędnych dla ruchu.
                int moveX, moveY;
                moveX = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveY();

                // Przerysowanie interfejsu ruchu i ataku.
                for (Player player : GameStatus.getInstance().getPlayers()) {
                    for (PlayerMob playerMob1 : player.getPlayerMobs()) {
                        playerMob.getMoveManager().redrawButtons(playerMob1.getStage(), playerMob1);
                    }
                }

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
                } else if (playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).size() > 0 &&
                        playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).get(0).getDistance() < 2) {
                    playerMob.getStateMachine().changeState(ATTACK_FREE_MOB);
                    // Sprawdzenie czy są dostępne wolne moby do zaatakowania.
                } else if (playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).size() > 0) {
                    playerMob.getStateMachine().changeState(MOVE_TO_FREE_MOB);
                    // Sprawdzenie czy lista wrogich bohaterów nie jest pusta oraz sprawdzenie czy pole bohatera sąsiaduje z polem wrogiego bohatera
                } else if (playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).size() > 0 &&
                        playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).get(0).getDistance() < 2) {
                    playerMob.getStateMachine().changeState(ATTACK_PLAYER_MOB);
                    // Sprawdzenie dostępności wrogich bohaterów.
                } else if (playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).size() > 0) {
                    playerMob.getStateMachine().changeState(MOVE_TO_PLAYER_MOB);
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
