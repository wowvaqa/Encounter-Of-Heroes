package com.mygdx.eoh.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.enums.Spells;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.MoveManager;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.items.AvailableItems;
import com.mygdx.eoh.items.Item;
import com.mygdx.eoh.magic.Spell;

import java.util.ArrayList;

/**
 * States of playerMob
 * Created by v on 2017-09-17.
 */

public enum PlayerMobState implements State<PlayerMob> {

    MOVE_TO_FREE_MOB() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);

            if (playerMob.getAi().getFreeMobCells().size() > 0) {
                // Odczytanie współżędnych dla ruchu.
                int moveX, moveY;
                moveX = playerMob.getAi().getFreeMobCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getFreeMobCells().get(0).getMoveList().get(0).getMoveY();

                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getFreeMobCells().remove(0);

                MoveManager.redrawButtonsWhenAImove(playerMob);

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

                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getPlayerMobCells().remove(0);

                MoveManager.redrawButtonsWhenAImove(playerMob);

                playerMob.getStateMachine().changeState(WAIT);
            }
        }
    },

    ATTACK_PLAYER_MOB() {
        @Override
        public void update(PlayerMob playerMob) {
            //Gdx.app.log("Przechodzę w tryb ataku na przeciwnika", "");

            playerMob.setBusy(true);

            playerMob.changeToAttackAnimation(playerMob,
                    playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().getCoordinateXonMap(),
                    playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().getCoordinateYonMap());

            // Dla bohatera broniącego - ustawienie bohatera atakującego
            playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob().setAgressor(playerMob);
            // Dla bohatera atakującego ustawienie bohatera, który broni się przed atakiem.
            playerMob.setDefendig(playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob());

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

    MOVE_TO_TREASURE() {
        @Override
        public void update(PlayerMob playerMob) {

            playerMob.setBusy(true);

            if (playerMob.getAi().getTreasureCells().size() > 0) {
                // Odczytanie współżędnych dla ruchu.
                int moveX, moveY;
                moveX = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getTreasureCells().get(0).getMoveList().get(0).getMoveY();

                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getTreasureCells().remove(0);

                MoveManager.redrawButtonsWhenAImove(playerMob);
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    MOVE_TO_ITEM() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);

            if (playerMob.getAi().getItemCells().size() > 0) {
                // Odczytanie współżędnych dla ruchu.
                int moveX, moveY;
                moveX = playerMob.getAi().getItemCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getItemCells().get(0).getMoveList().get(0).getMoveY();

                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getItemCells().remove(0);

                MoveManager.redrawButtonsWhenAImove(playerMob);
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    MOVE_TO_CASTLE() {
        @Override
        public void update(PlayerMob playerMob) {

            playerMob.setBusy(true);

            if (playerMob.getAi().getCastleMobCells().size() > 0) {
                // Odczytanie współżędnych dla ruchu.
                int moveX, moveY;
                moveX = playerMob.getAi().getCastleMobCells().get(0).getMoveList().get(0).getMoveX();
                moveY = playerMob.getAi().getCastleMobCells().get(0).getMoveList().get(0).getMoveY();

                playerMob.getAi().movePlayerMob(playerMob, moveX, moveY);
                playerMob.getAi().getCastleMobCells().remove(0);

                MoveManager.redrawButtonsWhenAImove(playerMob);
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    DEFEND_YOURSELF() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);

            ArrayList<Move> moves = new ArrayList<Move>();

            if (playerMob.getAi().getPathFinder().findPath(playerMob.getFieldOfPlayerMob(),
                    playerMob.getAgressor().getFieldOfPlayerMob(),
                    moves, FindPath.SearchDestination.PLAYER_MOB)) {
                if (moves.size() < 2) {

                    playerMob.changeToAttackAnimation(playerMob,
                            playerMob.getAgressor().getCoordinateXonMap(),
                            playerMob.getAgressor().getCoordinateYonMap()
                    );

                    int damage = FightManager.getDamage(playerMob, playerMob.getAgressor());

                    playerMob.getAi().showDamageLabel(
                            damage, playerMob.getAgressor().getCoordinateXonMap(),
                            playerMob.getAgressor().getCoordinateYonMap(),
                            GameStatus.getInstance().getMapStage()
                    );

                    if (playerMob.getAgressor().getActualhp() < 1) {
                        playerMob.getAgressor().setDefendig(null);
                        playerMob.setAgressor(null);
                    }

                } else {
                    playerMob.getAgressor().setDefendig(null);
                    playerMob.setAgressor(null);
                }
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    DRINK_HEALTH_POTION() {
        @Override
        public void update(PlayerMob playerMob) {
            if (playerMob.getItems().size > 0) {
                for (Item item : playerMob.getItems()) {
                    if (item.getItemType().equals(AvailableItems.HealthPotion)) {
                        Item.drinkHealthPotion(item, playerMob);
                    }
                }
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    CAST_ATTACK_UPGRADE() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);
            Spell spellTmp;

            for (Spell spell : playerMob.getSpells()) {
                if (spell.getSpellType().equals(Spells.AttackUpgrade)) {
                    spellTmp = spell;
                    Spell.castSpell(spellTmp, playerMob, null);
                }
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    CAST_FIREBALL_ON_FREEMOB() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);
            Spell spellTmp;

            for (Spell spell : playerMob.getSpells()) {
                if (spell.getSpellType().equals(Spells.Fireball)) {
                    spellTmp = spell;

                    Spell.castSpell(spellTmp, playerMob, playerMob.getAi().getFreeMobCells().get(0).getFreeMob());
                }
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    CAST_FIREBALL_ON_PLAYERMOB() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);
            Spell spellTmp;

            for (Spell spell : playerMob.getSpells()) {
                if (spell.getSpellType().equals(Spells.Fireball)) {
                    spellTmp = spell;

                    Spell.castSpell(spellTmp, playerMob, playerMob.getAi().getPlayerMobCells().get(0).getPlayerMob());
                }
            }
            playerMob.getStateMachine().changeState(WAIT);
        }
    },

    CAST_CURE() {
        @Override
        public void update(PlayerMob playerMob) {
            playerMob.setBusy(true);
            Spell spellTmp;

            for (Spell spell : playerMob.getSpells()) {
                if (spell.getSpellType().equals(Spells.Cure)) {
                    spellTmp = spell;
                    Spell.castSpell(spellTmp, playerMob, null);
                }
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

                if (playerMob.getActualhp() < playerMob.getMaxHp() / 2 &&
                        Item.checkHealthPotion(playerMob.getItems())) {
                    Gdx.app.log("AI Status", "DRINK HEALTH POTION");
                    playerMob.getStateMachine().changeState(DRINK_HEALTH_POTION);

                } else if (playerMob.getActualhp() < playerMob.getMaxHp() / 2 &&
                        playerMob.getPlayerMobClass().equals(PlayerMobClasses.Wizard) &&
                        playerMob.getActualMana() > 0) {
                    Gdx.app.log("AI Status", "5. CAST CURE");
                    playerMob.getStateMachine().changeState(CAST_CURE);

                    // Sprawdzenie czy można rzucić czar kula ognia.


                    // Sprawdzenie czy bohater nie został zaatakowany przez wrogiego bohatera.
                } else if (playerMob.getAgressor() != null) {
                    Gdx.app.log("AI Status", "1. DEFEND YOURSELF");
                    playerMob.getStateMachine().changeState(DEFEND_YOURSELF);

                    // Sprawdzenie czy bohater może zdobyć item.
                } else if (playerMob.getAi().findAvailableItems(playerMob.getFieldOfPlayerMob()).size() > 0 &&
                        playerMob.getAi().getItemCells().get(0).getDistance() < 3) {
                    Gdx.app.log("AI Status", " MOVE TO ITEM");
                    playerMob.getStateMachine().changeState(MOVE_TO_ITEM);

                    // Sprawdzenie czy są dostępne skrzynie ze skarbami.
                } else if (playerMob.getAi().findAvailableTreasureBoxes(playerMob.getFieldOfPlayerMob()).size() > 0 &&
                        playerMob.getAi().getTreasureCells().get(0).getDistance() < 3) {
                    Gdx.app.log("AI Status", "2. MOVE TO TREASURE");
                    playerMob.getStateMachine().changeState(MOVE_TO_TREASURE);

                    // Sprawdzenie czy zdrowie gracza spadło poniżaj połowy maksymalnego zdrowia oraz czy lista zamków nie jest pusta.
                } else if (playerMob.getActualhp() < playerMob.getMaxHp() / 2 &&
                        playerMob.getFieldOfPlayerMob().getCastleMob() == null &&
                        playerMob.getAi().findAvailableCastleMobs(
                                playerMob.getFieldOfPlayerMob(), PlayerMobTypes.FRIEND).size() > 0) {
                    Gdx.app.log("AI Status", "3. MOVE TO CASTLE");
                    playerMob.getStateMachine().changeState(MOVE_TO_CASTLE);

                    // Sprawdzenie czy można wypić miksturę lecznia
                } else if (playerMob.getActualhp() < playerMob.getMaxHp() / 2 &&
                        Item.checkHealthPotion(playerMob.getItems())) {
                    playerMob.getStateMachine().changeState(DRINK_HEALTH_POTION);
                    playerMob.getStateMachine().changeState(WAIT);

                    // Sprawdzenie czy poziomu zdrowia oraz czy bohater znajduje się na polu z zamkeim.
                } else if (playerMob.getActualhp() < playerMob.getMaxHp() / 2 &&
                        playerMob.getFieldOfPlayerMob().getCastleMob() != null) {
                    Gdx.app.log("AI Status", "4. WAIT WHEN HP ");
                    playerMob.getStateMachine().changeState(WAIT);

                    // Sprawdzenie czy można rzucić czar ulepszajacy atak
                } else if (playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).size() > 0 &&
                        playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).get(0).getDistance() < 2 &&
                        playerMob.getPlayerMobClass().equals(PlayerMobClasses.Knight) &&
                        playerMob.getActualMana() > 0) {
                    Gdx.app.log("AI Status", "5. CAST ATTACK UPGRADE");
                    playerMob.getStateMachine().changeState(CAST_ATTACK_UPGRADE);

                    // Sprawdzenie czy można rzucić czar kula ognia.
                } else if (playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).size() > 0 &&
                        playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).get(0).getDistance() < 2 &&
                        playerMob.getPlayerMobClass().equals(PlayerMobClasses.Wizard) &&
                        playerMob.getActualMana() > 0) {
                    Gdx.app.log("AI Status", "5. CAST FIREBALL ON FREEMOB");
                    playerMob.getStateMachine().changeState(CAST_FIREBALL_ON_FREEMOB);

                    // Sprawdza czy moba można zaatakować.
                } else if (playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).size() > 0 &&
                        playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).get(0).getDistance() < 2) {
                    Gdx.app.log("AI Status", "5. ATTACK FREE MOB");
                    playerMob.getStateMachine().changeState(ATTACK_FREE_MOB);

                    // Sprawdzenie czy są dostępne wolne moby do zaatakowania.
                } else if (playerMob.getAi().findAvailableFreeMobs(playerMob.getFieldOfPlayerMob()).size() > 0) {
                    Gdx.app.log("AI Status", "6. MOVE TO FREE MOB");
                    playerMob.getStateMachine().changeState(MOVE_TO_FREE_MOB);

                } else if (playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).size() > 0 &&
                        playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).get(0).getDistance() < 2 &&
                        playerMob.getPlayerMobClass().equals(PlayerMobClasses.Wizard) &&
                        playerMob.getActualMana() > 0) {
                    Gdx.app.log("AI Status", "7. CAST FIREBALL ON PLAYER MOB");
                    playerMob.getStateMachine().changeState(CAST_FIREBALL_ON_PLAYERMOB);

                    // Sprawdzenie czy lista wrogich bohaterów nie jest pusta oraz sprawdzenie czy pole bohatera sąsiaduje z polem wrogiego bohatera
                } else if (playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).size() > 0 &&
                        playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).get(0).getDistance() < 2) {
                    Gdx.app.log("AI Status", "7. ATTACK PLAYER MOB");
                    playerMob.getStateMachine().changeState(ATTACK_PLAYER_MOB);

                    // Sprawdzenie dostępności wrogich bohaterów.
                } else if (playerMob.getAi().findAvailablePlayerMobs(playerMob.getFieldOfPlayerMob(), PlayerMobTypes.ENEMY).size() > 0) {
                    Gdx.app.log("AI Status", "8. MOVE TO PLAYER MOB");
                    playerMob.getStateMachine().changeState(MOVE_TO_PLAYER_MOB);
                } else {
                    Gdx.app.log("AI Status", "9. WAIT - FINAL");
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
