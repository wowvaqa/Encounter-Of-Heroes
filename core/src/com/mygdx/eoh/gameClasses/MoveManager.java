package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.defaultClasses.DefaultDamageLabel;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.enums.LocationToCheck;
import com.mygdx.eoh.enums.SpellsKinds;
import com.mygdx.eoh.magic.CastButton;
import com.mygdx.eoh.magic.Spell;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 * Move manager to move heroes on the map.
 * Created by v on 2016-10-30.
 */
public class MoveManager {

    private GameStatus gs;

    private boolean showMoveButtons = false;
    private boolean showAttackButtons = false;

    private boolean moveButtonsCreated = false;
    private boolean attackButtonsCreated = false;

    public MoveManager(GameStatus gs) {
        this.gs = gs;
    }

    /**
     * Removes buttons from stage.
     */
    public static void removeButtons() {
        removeAllMoveButtons();
        removeAllAttackButtons();
        removeAllCastButtons();
    }

    public static void removeButtons(PlayerMob playerMob) {
        MoveManager.removeMoveButtons(playerMob);
        MoveManager.removeAttackButtons(playerMob);
        MoveManager.removeCastButtons(playerMob);
    }

    /**
     * Removes attack buttons from stage.
     *
     * @param playerMob Which player mob object buttons
     */
    public static void removeAttackButtons(PlayerMob playerMob) {
        for (AttackButton attackButton : GameStatus.getInstance().getAttackButtons()) {
            if (attackButton.getButtonOwner().equals(playerMob)) {
                attackButton.remove();
            }
        }
    }

    /**
     * Removes moves buttons from stage.
     *
     * @param playerMob Which player mob object buttons
     */
    public static void removeMoveButtons(PlayerMob playerMob) {
        for (MoveButton moveButton : GameStatus.getInstance().getMoveButtons()) {
            if (moveButton.getButtonOwner().equals(playerMob)) {
                moveButton.remove();
            }
        }
    }

    /**
     * Removes cast buttons from stage.
     *
     * @param playerMob Which player mob object buttons
     */
    public static void removeCastButtons(PlayerMob playerMob) {
        for (CastButton castButton : GameStatus.getInstance().getCastButtons()) {
            if (castButton.getButtonOwner().equals(playerMob)) {
                castButton.remove();
            }
        }
    }

    /**
     * Removes all move buttons from stage
     */
    private static void removeAllMoveButtons() {
        for (MoveButton moveButton : GameStatus.getInstance().getMoveButtons()) {
            moveButton.remove();
        }
    }

    private static void removeAllCastButtons() {
        for (CastButton castButton : GameStatus.getInstance().getCastButtons()) {
            castButton.remove();
        }
    }

    /**
     * Removes all move buttons from stage
     */
    private static void removeAllAttackButtons() {

        for (AttackButton attackButton : GameStatus.getInstance().getAttackButtons()) {
            attackButton.remove();
        }
    }

    /**
     * Check location to find possibility to show move button
     *
     * @param map             Map object where condition have to be check
     * @param playerMob       Player Mob object which has to be check
     * @param locationToCheck Location to check
     * @return If function returns true, then move button will be shown.
     */
    private static boolean checkMoveCondition(Map map, PlayerMob playerMob, LocationToCheck locationToCheck) {
        switch (locationToCheck) {
            case North:
                if (playerMob.getCoordinateYonMap() == map.getFieldsRows() - 1)
                    return false;
                else {
                    return map.getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap() + 1].getPlayerMob() == null;
                }
            case South:
                if (playerMob.getCoordinateYonMap() == 0)
                    return false;
                else if (map.getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap() - 1].getPlayerMob() != null)
                    return false;
                else
                    return true;
            case East:
                if (playerMob.getCoordinateXonMap() == map.getFieldsColumns() - 1)
                    return false;
                else if (map.getFields()[playerMob.getCoordinateXonMap() + 1][playerMob.getCoordinateYonMap()].getPlayerMob() != null)
                    return false;
                else
                    return true;
            case West:
                if (playerMob.getCoordinateXonMap() == 0)
                    return false;
                else if (map.getFields()[playerMob.getCoordinateXonMap() - 1][playerMob.getCoordinateYonMap()].getPlayerMob() != null)
                    return false;
                else
                    return true;
            case NorthEast:
                if (playerMob.getCoordinateXonMap() == map.getFieldsColumns() - 1 || playerMob.getCoordinateYonMap() == map.getFieldsRows() - 1)
                    return false;
                else if (map.getFields()[playerMob.getCoordinateXonMap() + 1][playerMob.getCoordinateYonMap() + 1].getPlayerMob() != null)
                    return false;
                else
                    return true;
            case NorthWest:
                if (playerMob.getCoordinateXonMap() == 0 || playerMob.getCoordinateYonMap() == map.getFieldsRows() - 1)
                    return false;
                else if (map.getFields()[playerMob.getCoordinateXonMap() - 1][playerMob.getCoordinateYonMap() + 1].getPlayerMob() != null)
                    return false;
                else
                    return true;
            case SouthEast:
                if (playerMob.getCoordinateXonMap() == map.getFieldsColumns() - 1 || playerMob.getCoordinateYonMap() == 0)
                    return false;
                else if (map.getFields()[playerMob.getCoordinateXonMap() + 1][playerMob.getCoordinateYonMap() - 1].getPlayerMob() != null)
                    return false;
                else
                    return true;
            case SouthWest:
                if (playerMob.getCoordinateXonMap() == 0 || playerMob.getCoordinateYonMap() == 0)
                    return false;
                else if (map.getFields()[playerMob.getCoordinateXonMap() - 1][playerMob.getCoordinateYonMap() - 1].getPlayerMob() != null)
                    return false;
                else
                    return true;
        }
        return false;
    }

    /**
     * Turn off selected Player Mobs
     */
    public static void turnOffSelectedPlayersMobs() {
        for (Player player : GameStatus.getInstance().getPlayers()) {
            for (PlayerMob playerMob : player.getPlayerMobs()) {
                playerMob.getMoveManager().setMoveButtonsCreated(false);
                playerMob.getMoveManager().setAttackButtonsCreated(false);
                playerMob.getMoveManager().setShowMoveButtons(false);
                playerMob.getMoveManager().setShowAttackButtons(false);
                playerMob.setSelected(false);
                playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(playerMob.getStanding()));
                playerMob.getAnimation().getKeyFrameIndex(0);
            }
        }
        GameStatus.getInstance().getEquipmentTable().setVisible(false);
        GameStatus.getInstance().getHeroTable().setVisible(false);
        GameStatus.getInstance().getSpellEffectsTable().clear();
        GameStatus.getInstance().getUpperBarRightTable().clear();
        removeButtons();
        //DefaultGameScreen.sortZindex();
    }

    public static void unselectCastles(Map map) {
        //MoveManager.turnOffSelectedPlayersMobs();
        for (int i = 0; i < map.getFieldsColumns(); i++) {
            for (int j = 0; j < map.getFieldsRows(); j++) {
                if (map.getFields()[i][j].getCastleMob() != null) {
                    if (map.getFields()[i][j].getCastleMob().isSelected()) {
                        map.getFields()[i][j].getCastleMob().setSelected(false);
                        map.getFields()[i][j].getCastleMob().changeAnimationToUnselected();
                    }
                }
            }
        }

        GameStatus.getInstance().getEquipmentTable().clear();
    }

    public void showMoveInterface(Stage stage, PlayerMob playerMob) {

        makeMoveButtons(stage, playerMob, gs);
    }

    public void showAttackInterface(Stage stage, PlayerMob playerMob) {

        if (showAttackButtons && !attackButtonsCreated && playerMob.getActionPoints() > 0) {
            this.setAttackButtonsCreated(true);

            for (int i = playerMob.getCoordinateXonMap() - 1; i < playerMob.getCoordinateXonMap() + 2; i++) {
                for (int j = playerMob.getCoordinateYonMap() - 1; j < playerMob.getCoordinateYonMap() + 2; j++) {
                    if (i >= 0 && j >= 0 && i < gs.getMap().getFieldsColumns() && j < gs.getMap().getFieldsRows()) {
                        if (checkEnemy(i, j, gs.getMap(), playerMob)) {
                            AttackButton attackButton = new AttackButton(
                                    AnimationCreator.getInstance().makeAnimation(AnimationTypes.AttackMove), true, i, j, playerMob);
                            GameStatus.getInstance().getAttackButtons().add(attackButton);
                            stage.addActor(attackButton);
                        }
                    }
                }
            }
        }
    }

    /**
     * Show spell cast interface on stage
     *
     * @param stage
     * @param spell
     */
    public void showCastInterface(Stage stage, Spell spell) {
        MoveManager.removeButtons(spell.getPlayerOwner());
        for (int i = spell.getPlayerOwner().getCoordinateXonMap() - 1; i < spell.getPlayerOwner().getCoordinateXonMap() + 2; i++) {
            for (int j = spell.getPlayerOwner().getCoordinateYonMap() - 1; j < spell.getPlayerOwner().getCoordinateYonMap() + 2; j++) {
                if (i >= 0 && j >= 0 && i < gs.getMap().getFieldsColumns() && j < gs.getMap().getFieldsRows()) {
                    if (spell.getSpellKind().equals(SpellsKinds.OnlyEnemys)) {
                        if (checkEnemy(i, j, gs.getMap(), spell.getPlayerOwner())) {
                            CastButton castButton = new CastButton(AnimationCreator.getInstance().makeAnimation(AnimationTypes.CastMove), true, i, j, spell);
                            GameStatus.getInstance().getCastButtons().add(castButton);
                            stage.addActor(castButton);
                        }
                    } else if (spell.getSpellKind().equals(SpellsKinds.OnlyFriends)) {
                        if (checkFriend(i, j, gs.getMap(), spell.getPlayerOwner())) {
                            CastButton castButton = new CastButton(AnimationCreator.getInstance().makeAnimation(AnimationTypes.CastMove), true, i, j, spell);
                            GameStatus.getInstance().getCastButtons().add(castButton);
                            stage.addActor(castButton);
                        }
                    }
                }
            }
        }
    }

    private boolean checkEnemy(int coordinateX, int coordinateY, Map map, PlayerMob playerMob) {
        if (map.getFields()[coordinateX][coordinateY].getPlayerMob() != null &&
                map.getFields()[coordinateX][coordinateY].getPlayerMob().getPlayerOwner() != playerMob.getPlayerOwner()) {
            return true;

        }
        return false;
    }

    private boolean checkFriend(int coordinateX, int coordinateY, Map map, PlayerMob playerMob) {
        if (map.getFields()[coordinateX][coordinateY].getPlayerMob() != null &&
                map.getFields()[coordinateX][coordinateY].getPlayerMob().getPlayerOwner().equals(playerMob.getPlayerOwner()))
            return true;
        else return false;
    }

    /**
     * Making move buttons
     *
     * @param stage     Stage where buttons may be created
     * @param playerMob Player mob who is owner of buttons
     */
    private void makeMoveButtons(final Stage stage, final PlayerMob playerMob, final GameStatus gs) {

        if (showMoveButtons && !moveButtonsCreated && playerMob.getActionPoints() > 0) {
            setMoveButtonsCreated(true);

            MoveButton moveButtonN = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveN), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize, playerMob.getCoordinateYonMap() * Options.tileSize +
                    Options.tileSize, playerMob);
            MoveButton moveButtonS = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveS), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize, playerMob.getCoordinateYonMap() * Options.tileSize -
                    Options.tileSize, playerMob);
            MoveButton moveButtonW = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveW), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize - Options.tileSize, playerMob.getCoordinateYonMap() *
                    Options.tileSize, playerMob);
            MoveButton moveButtonE = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveE), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize + Options.tileSize, playerMob.getCoordinateYonMap() *
                    Options.tileSize, playerMob);
            MoveButton moveButtonNW = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveNW), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize - Options.tileSize, playerMob.getCoordinateYonMap() *
                    Options.tileSize + Options.tileSize, playerMob);
            MoveButton moveButtonNE = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveNE), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize + Options.tileSize, playerMob.getCoordinateYonMap() *
                    Options.tileSize + Options.tileSize, playerMob);
            MoveButton moveButtonSW = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveSW), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize - Options.tileSize, playerMob.getCoordinateYonMap() *
                    Options.tileSize - Options.tileSize, playerMob);
            MoveButton moveButtonSE = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.ArrowMoveSE), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize + Options.tileSize, playerMob.getCoordinateYonMap() *
                    Options.tileSize - Options.tileSize, playerMob);
            MoveButton cancelMove = new MoveButton(AnimationCreator.getInstance().makeAnimation(
                    AnimationTypes.CancelMove), true, playerMob.getCoordinateXonMap() *
                    Options.tileSize, playerMob.getCoordinateYonMap() * Options.tileSize, playerMob);

            cancelMove.addAction(Actions.alpha(150));

            moveButtonN.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobNorth(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            moveButtonNW.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobNorthWest(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            moveButtonNE.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobNorthEast(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            moveButtonS.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobSouth(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            moveButtonSW.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobSouthWest(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            moveButtonSE.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobSouthEast(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            moveButtonE.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobEast(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            moveButtonW.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    movePlayerMobWest(playerMob);
                    redrawButtons(stage, playerMob);
                }
            });

            cancelMove.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    MoveManager.turnOffSelectedPlayersMobs();
                }
            });

            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.North))
                stage.addActor(moveButtonN);
            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.South))
                stage.addActor(moveButtonS);
            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.East))
                stage.addActor(moveButtonE);
            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.West))
                stage.addActor(moveButtonW);
            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.NorthWest))
                stage.addActor(moveButtonNW);
            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.NorthEast))
                stage.addActor(moveButtonNE);
            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.SouthWest))
                stage.addActor(moveButtonSW);
            if (checkMoveCondition(gs.getMap(), playerMob, LocationToCheck.SouthEast))
                stage.addActor(moveButtonSE);
            stage.addActor(cancelMove);

            GameStatus.getInstance().getMoveButtons().add(moveButtonN);
            GameStatus.getInstance().getMoveButtons().add(moveButtonS);
            GameStatus.getInstance().getMoveButtons().add(moveButtonE);
            GameStatus.getInstance().getMoveButtons().add(moveButtonW);
            GameStatus.getInstance().getMoveButtons().add(moveButtonNE);
            GameStatus.getInstance().getMoveButtons().add(moveButtonNW);
            GameStatus.getInstance().getMoveButtons().add(moveButtonSE);
            GameStatus.getInstance().getMoveButtons().add(moveButtonSW);
            GameStatus.getInstance().getMoveButtons().add(cancelMove);

            //DefaultGameScreen.sortZindex();
        }
    }

    /**
     * Redraws buttons of moving and attacking on the stage
     *
     * @param stage     Stage object
     * @param playerMob PlayerMob object
     */
    public void redrawButtons(Stage stage, PlayerMob playerMob) {
        playerMob.getMoveManager().setMoveButtonsCreated(false);
        playerMob.getMoveManager().setAttackButtonsCreated(false);

        removeButtons();

        makeMoveButtons(stage, playerMob, GameStatus.getInstance());
        showAttackInterface(stage, playerMob);
    }

    private void movePlayerMobNET(PlayerMob playerMob, int Xmove, int Ymove) {
        Network.MovePlayerMob movePlayerMob = new Network.MovePlayerMob();
        movePlayerMob.enemyId = NetStatus.getInstance().getEnemyId();
        movePlayerMob.amountXmove = Xmove;
        movePlayerMob.amountYmove = Ymove;
        movePlayerMob.indexInArray = PlayerMob.getPlayerMobIndex(playerMob, playerMob.getPlayerOwner());
        movePlayerMob.inedxPlayerOwner = playerMob.getPlayerOwner().getInedxOfPlayerInArrayOfPlayer();
        NetStatus.getInstance().getClient().sendTCP(movePlayerMob);
    }

    private void attackOfPlayerMobNET(int locationXofEnemy, int locationYofEnemy,
                                      int damage, int hpLeft, int locationXofAttacker, int locationYofAttacker) {
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
    }

    /**
     * Moves player mob to north.
     *
     * @param playerMob PlayerMob who may be moved.
     */
    private void movePlayerMobNorth(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, 0, 1);
        }
        movePlayerMobNorthRecivedFromNET(playerMob);
    }

    /**
     * Move PlayerMob into north when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobNorthRecivedFromNET(PlayerMob playerMob) {
        float act = 2.0f - playerMob.getActualSpeed() * 0.05f;
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(
                playerMob.getWalkN(), playerMob));
        playerMob.addAction(Actions.moveBy(0, Options.tileSize, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(0, Options.tileSize, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateYonMap(playerMob.getCoordinateYonMap() + 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    private void movePlayerMobSouth(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, 0, -1);
        }
        movePlayerMobSouthRecivedFromNET(playerMob);
    }

    /**
     * Move PlayerMob into south when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobSouthRecivedFromNET(PlayerMob playerMob) {
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(playerMob.getWalkS(), playerMob));
        playerMob.getAnimation().getKeyFrameIndex(0);
        playerMob.addAction(Actions.moveBy(0, -Options.tileSize, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(0, -Options.tileSize, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateYonMap(playerMob.getCoordinateYonMap() - 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    /**
     * Moves player mob to west.
     *
     * @param playerMob PlayerMob who may be moved.
     */
    private void movePlayerMobWest(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, -1, 0);
        }
        movePlayerMobWestRecivedFromNET(playerMob);
    }

    /**
     * Move PlayerMob into west when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobWestRecivedFromNET(PlayerMob playerMob) {
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(playerMob.getWalkW(), playerMob));
        playerMob.getAnimation().getKeyFrameIndex(0);
        playerMob.addAction(Actions.moveBy(-Options.tileSize, 0, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(-Options.tileSize, 0, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateXonMap(playerMob.getCoordinateXonMap() - 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    /**
     * Moves player mob to east.
     *
     * @param playerMob PlayerMob who may be moved.
     */
    private void movePlayerMobEast(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, 1, 0);
        }
        movePlayerMobEastRecivedFromNET(playerMob);
    }

    private float getActionDuration(PlayerMob playerMob) {
        return 2.0f - (playerMob.getActualSpeed() + ModifierGetter.getSpeedModifier(playerMob)) * 0.05f;
    }

    /**
     * Move PlayerMob into east when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobEastRecivedFromNET(PlayerMob playerMob) {
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(playerMob.getWalkE(), playerMob));
        playerMob.getAnimation().getKeyFrameIndex(0);
        playerMob.addAction(Actions.moveBy(Options.tileSize, 0, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(Options.tileSize, 0, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateXonMap(playerMob.getCoordinateXonMap() + 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    /**
     * Moves player mob to north-east.
     *
     * @param playerMob PlayerMob who may be moved.
     */
    private void movePlayerMobNorthEast(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, -1, 1);
        }
        movePlayerMobNorthEastRecivedFromNET(playerMob);
    }

    /**
     * Move PlayerMob into north-east when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobNorthEastRecivedFromNET(PlayerMob playerMob) {
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(playerMob.getWalkE(), playerMob));
        playerMob.addAction(Actions.moveBy(Options.tileSize, Options.tileSize, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(Options.tileSize, Options.tileSize, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateXonMap(playerMob.getCoordinateXonMap() + 1);
        playerMob.setCoordinateYonMap(playerMob.getCoordinateYonMap() + 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    /**
     * Moves player mob to north-west.
     *
     * @param playerMob PlayerMob who may be moved.
     */
    private void movePlayerMobNorthWest(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, 1, 1);
        }
        movePlayerMobNorthWestRecivedFromNET(playerMob);
    }

    /**
     * Move PlayerMob into north-west when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobNorthWestRecivedFromNET(PlayerMob playerMob) {
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(
                playerMob.getWalkW(), playerMob));
        playerMob.addAction(Actions.moveBy(-Options.tileSize, Options.tileSize, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(-Options.tileSize, Options.tileSize, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateXonMap(playerMob.getCoordinateXonMap() - 1);
        playerMob.setCoordinateYonMap(playerMob.getCoordinateYonMap() + 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    /**
     * Moves player mob to south-east.
     *
     * @param playerMob PlayerMob who may be moved.
     */
    private void movePlayerMobSouthEast(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, -1, -1);
        }
        movePlayerMobSouthEastRecivedFromNET(playerMob);
    }

    /**
     * Move PlayerMob into south-east when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobSouthEastRecivedFromNET(PlayerMob playerMob) {
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(playerMob.getWalkE(), playerMob));
        playerMob.getAnimation().getKeyFrameIndex(0);
        playerMob.addAction(Actions.moveBy(Options.tileSize, -Options.tileSize, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(Options.tileSize, -Options.tileSize, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateXonMap(playerMob.getCoordinateXonMap() + 1);
        playerMob.setCoordinateYonMap(playerMob.getCoordinateYonMap() - 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    /**
     * Moves player mob to south-west.
     *
     * @param playerMob PlayerMob who may be moved.
     */
    private void movePlayerMobSouthWest(PlayerMob playerMob) {
        if (NetStatus.getInstance().getClient() != null) {
            movePlayerMobNET(playerMob, 1, -1);
        }
        movePlayerMobSouthWestRecivedFromNET(playerMob);
    }

    /**
     * Move PlayerMob into south-east when move comes from network.
     *
     * @param playerMob Who may be moved.
     */
    public void movePlayerMobSouthWestRecivedFromNET(PlayerMob playerMob) {
        FightManager.decreseAP(playerMob, 1);
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(playerMob.getWalkW(), playerMob));
        playerMob.getAnimation().getKeyFrameIndex(0);
        playerMob.addAction(Actions.moveBy(-Options.tileSize, -Options.tileSize, getActionDuration(playerMob)));
        playerMob.getPlayerColorImage().addAction(Actions.moveBy(-Options.tileSize, -Options.tileSize, 0.50f));
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(null);
        playerMob.setCoordinateXonMap(playerMob.getCoordinateXonMap() - 1);
        playerMob.setCoordinateYonMap(playerMob.getCoordinateYonMap() - 1);
        gs.getMap().getFields()[playerMob.getCoordinateXonMap()][playerMob.getCoordinateYonMap()].setPlayerMob(playerMob);
        playerMob.getStepManager().setCheckStepping(true);
        //DefaultGameScreen.sortZindex();
    }

    public boolean isMoveButtonsCreated() {
        return moveButtonsCreated;
    }

    public void setMoveButtonsCreated(boolean moveButtonsCreated) {
        this.moveButtonsCreated = moveButtonsCreated;
    }

    public boolean isAttackButtonsCreated() {
        return attackButtonsCreated;
    }

    public void setAttackButtonsCreated(boolean attackButtonsCreated) {
        this.attackButtonsCreated = attackButtonsCreated;
    }

    public boolean isShowMoveButtons() {
        return showMoveButtons;
    }

    public void setShowMoveButtons(boolean showMoveButtons) {
        this.showMoveButtons = showMoveButtons;
    }

    public boolean isShowAttackButtons() {
        return showAttackButtons;
    }

    public void setShowAttackButtons(boolean showAttackButtons) {
        this.showAttackButtons = showAttackButtons;
    }

    /**
     * Create and show label of damage.
     *
     * @param damage         Amount of damage
     * @param locationXonMap Location X of label
     * @param locationYonMap Location Y of label
     * @param stage          Stage where damage may be shown
     */
    public void showDamageLabel(int damage, int locationXonMap, int locationYonMap, Stage stage) {
        DefaultDamageLabel defaultDamageLabel = new DefaultDamageLabel(
                Integer.toString(damage), (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"), "fight",
                locationXonMap * Options.tileSize + Options.tileSize / 2,
                locationYonMap * Options.tileSize + Options.tileSize / 2);
        stage.addActor(defaultDamageLabel);
    }
}
