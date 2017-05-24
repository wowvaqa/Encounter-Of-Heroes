package com.mygdx.eoh.gameClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.Equipment.Equip;
import com.mygdx.eoh.Options.OptionsInGame;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.creators.SpellCreator;
import com.mygdx.eoh.defaultClasses.DefaultMob;
import com.mygdx.eoh.defaultClasses.DefaultPlayerColorIcon;
import com.mygdx.eoh.effects.LongEffect;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.enums.PlayerMobClasses;
import com.mygdx.eoh.enums.Spells;
import com.mygdx.eoh.items.Item;
import com.mygdx.eoh.magic.Spell;
import com.mygdx.eoh.mob.FreeMob;

import java.util.ArrayList;

/**
 * Representation of mob which can be selectable by player.
 * Created by v on 2016-10-25.
 */
public class PlayerMob extends DefaultMob {
    private Map map;
    private PlayerMobClasses playerMobClass;
    private MoveManager moveManager;
    private StepManager stepManager;
    private Player playerOwner;
    private DefaultPlayerColorIcon playerColorImage;
    private APBar apBar;
    private ManaBar manaBar;

    private Table infoPlayerMobTable;
    private Table longEffectsTable;
    private Label hpLabel;
    private Label apLabel;
    private Label manaLabel;

    private boolean isSelected = false;

    private AnimationTypes walkN;
    private AnimationTypes walkS;
    private AnimationTypes walkW;
    private AnimationTypes walkE;
    private AnimationTypes attackN;
    private AnimationTypes attackS;
    private AnimationTypes attackW;
    private AnimationTypes attackE;
    private AnimationTypes standing;
    private AnimationTypes selected;
    private AnimationTypes cast;

    private ArrayList<Spell> spells;
    private SnapshotArray<LongEffect> longEffects;
    private SnapshotArray<Item> items;
    private SnapshotArray<Equip> equip;

    private Equip armor;
    private Equip weapon;
    private Equip artifact;


    public PlayerMob(Animation animation, boolean isLooped, Map map, Player playerOwner, PlayerMobClasses playerMobClass) {
        super(animation, isLooped);

        this.spells = new ArrayList<Spell>();
        this.longEffects = new SnapshotArray<LongEffect>();
        this.items = new SnapshotArray<Item>();
        this.equip = new SnapshotArray<Equip>();
        this.playerMobClass = playerMobClass;
        setAnimationTypes();
        this.map = map;
        this.playerOwner = playerOwner;
        addListener(this);
        moveManager = new MoveManager(GameStatus.getInstance());
        stepManager = new StepManager(this);
        apBar = new APBar(AnimationCreator.getInstance().makeAnimation(AnimationTypes.ApBarAnimation, this), false, this);
        manaBar = new ManaBar(AnimationCreator.getInstance().makeAnimation(AnimationTypes.ManaBarAnimation, this), false, this);
        createPlayerMobColorImage();
        createInfoTable();
        createLongEffectsTable();
        createSpells();
    }

    /**
     * Returns index in array of PlayerMobs
     *
     * @param playerMob   Which PlayerMob object index will be return
     * @param playerOwner Player who is owner of PlayerMob
     * @return index
     */
    public static int getPlayerMobIndex(PlayerMob playerMob, Player playerOwner) {
        int index = 0;
        for (PlayerMob tmpPlayerMob : playerOwner.getPlayerMobs()) {
            if (tmpPlayerMob.equals(playerMob))
                break;
            index += 1;
        }
        return index;
    }

    /**
     * Unselect selected mobs on stage.
     */
    public static void unselectSelectedPlayerMobs() {
        GameStatus.getInstance().getUpperBarRightTable().clear();
        for (int i = 0; i < GameStatus.getInstance().getMap().getFieldsColumns(); i++) {
            for (int j = 0; j < GameStatus.getInstance().getMap().getFieldsRows(); j++) {
                if (GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob() != null) {
                    GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().setSelected(false);
                    GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().moveManager.setMoveButtonsCreated(false);
                    GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().moveManager.setAttackButtonsCreated(false);
                    GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().moveManager.setShowMoveButtons(false);
                    GameStatus.getInstance().getMap().getFields()[i][j].getPlayerMob().moveManager.setShowAttackButtons(false);
                    MoveManager.removeButtons();
                }
            }
        }
    }

    /**
     * Create spells.
     */
    private void createSpells() {
        switch (playerMobClass) {
            case Wizard:
                spells.add(SpellCreator.getInstance().createSpell(this, Spells.Fireball));
                spells.add(SpellCreator.getInstance().createSpell(this, Spells.Cure));
                break;
            case Knight:
                spells.add(SpellCreator.getInstance().createSpell(this, Spells.AttackUpgrade));
                break;
        }
    }

    /**
     * Setting up animation types for this player mob.
     */
    private void setAnimationTypes() {
        if (playerMobClass.equals(PlayerMobClasses.Knight)) {
            walkN = AnimationTypes.KnightWalkN;
            walkS = AnimationTypes.KnightWalkS;
            walkW = AnimationTypes.KnightWalkW;
            walkE = AnimationTypes.KnightWalkE;
            attackN = AnimationTypes.KnightAttackN;
            attackS = AnimationTypes.KnightAttackS;
            attackW = AnimationTypes.KnightAttackW;
            attackE = AnimationTypes.KnightAttackE;
            standing = AnimationTypes.KnightStanding;
            selected = AnimationTypes.KnightSelected;
            cast = AnimationTypes.KnightCast;

        } else if (playerMobClass.equals(PlayerMobClasses.Wizard)) {
            walkN = AnimationTypes.WizardWalkN;
            walkS = AnimationTypes.WizardWalkS;
            walkW = AnimationTypes.WizardWalkW;
            walkE = AnimationTypes.WizardWalkE;
            attackN = AnimationTypes.WizardAttackN;
            attackS = AnimationTypes.WizardAttackS;
            attackW = AnimationTypes.WizardAttackW;
            attackE = AnimationTypes.WizardAttackE;
            standing = AnimationTypes.WizardStanding;
            selected = AnimationTypes.WizardSelected;
            cast = AnimationTypes.WizardCast;
        }
    }

    /**
     * Creates info table which show mana, ap and hp of player mob.
     */
    private void createInfoTable() {
        infoPlayerMobTable = new Table();
        hpLabel = new Label("" + this.getActualhp() + "/" + this.getMaxHp(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        apLabel = new Label("" + this.getActualhp(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        manaLabel = new Label("" + this.getActualMana() + "/" + (this.getMaxMana() + ModifierGetter.getWisdomModifier(this)), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        Image hpImage = new Image(AssetsGameScreen.getInstance().getManager().get("game/interface/hpIcon.png", Texture.class));
        Image manaImage = new Image(AssetsGameScreen.getInstance().getManager().get("game/interface/manaIcon.png", Texture.class));
        Image apImage = new Image(AssetsGameScreen.getInstance().getManager().get("game/interface/apIcon.png", Texture.class));

        infoPlayerMobTable.add(hpImage).size(25, 25).padRight(2);
        infoPlayerMobTable.add(hpLabel).padRight(10);
        infoPlayerMobTable.add(manaImage).size(25, 25).padRight(2);
        infoPlayerMobTable.add(manaLabel).padRight(10);
        infoPlayerMobTable.add(apImage).size(25, 25).padRight(2);
        infoPlayerMobTable.add(apLabel).padRight(10);
    }

    private void createLongEffectsTable() {
        longEffectsTable = new Table();
    }

    private void createPlayerMobColorImage() {
        playerColorImage = new DefaultPlayerColorIcon(playerOwner.getPlayerColor(), this);
        playerColorImage.setSize(Options.tileSize, Options.tileSize);
    }

    private void addListener(final PlayerMob playerMob) {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                GameStatus.getInstance().getEquipmentTable().clear();
                GameStatus.getInstance().getSpellEffectsTable().clear();

                if (!playerMob.isSelected) {

                    MoveManager.unselectCastles(map);
                    unselectSelectedPlayerMobs();
                    FreeMob.unselectFreeMobs();

                    GameStatus.getInstance().getSpellEffectsTable().add(playerMob.getLongEffectsTable());

                    if (playerMob.getPlayerOwner() == GameStatus.getInstance().getCurrentPlayerTurn()) {
                        GameStatus.getInstance().getEquipmentTable().add(
                                GameStatus.getInstance().getPlayerMobTable()
                        );
                        GameStatus.getInstance().getEquipmentTable().setVisible(true);
                        GameStatus.getInstance().getHeroTable().setVisible(true);
                        GameStatus.getInstance().setSelectedPlayerMob(playerMob);
                    }

                    GameStatus.getInstance().getUpperBarRightTable().add(infoPlayerMobTable);

                    playerMob.isSelected = true;

                    playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(standing));

                    if (GameStatus.getInstance().getCurrentPlayerTurn().equals(playerMob.getPlayerOwner())) {
                        moveManager.setShowMoveButtons(true);
                        moveManager.setShowAttackButtons(true);
                        moveManager.showMoveInterface(GameStatus.getInstance().getMapStage(), playerMob);
                        moveManager.showAttackInterface(GameStatus.getInstance().getMapStage(), playerMob);
                    }

                    if (OptionsInGame.getInstance().isShowEquipInfo()) {
                        System.out.println("Bron: " + getWeapon().getDescription());
                        System.out.println("Pancerz: " + getArmor().getDescription());
                        System.out.println("Artefakt: " + getArtifact().getDescription());

                        for (int i = 0; i < playerMob.getEquip().size; i++) {
                            System.out.println("Ekwipunek (" + i + "): " + playerMob.getEquip().get(i).getDescription());
                        }
                    }
                }
            }
        });
    }

    /**
     * Changing animation of PlayerMob to attacking animation.
     *
     * @param playerMob              PlayerMob object
     * @param locationXofAttackedMob Location X on stage where animation will be located
     * @param locationYofAttackedMob Location Y on stage where animation will be located
     */
    public void changeToAttackAnimation(PlayerMob playerMob, int locationXofAttackedMob, int locationYofAttackedMob) {
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(playerMob.getAnimationForAttack(
                playerMob, locationXofAttackedMob, locationYofAttackedMob));
        playerMob.getAnimation().getKeyFrameIndex(0);
        playerMob.addAction(playerMob.getSequenceForAttack(
                playerMob, locationXofAttackedMob, locationYofAttackedMob));
    }

    public void changeToCastAnimation(PlayerMob playerMob) {
        playerMob.setLooped(false);
        playerMob.setStateTime(0);
        playerMob.setAnimation(AnimationCreator.getInstance().makeAnimation(cast, playerMob));

        float velocity = 2.0f - (playerMob.getActualWisdom() + ModifierGetter.getWisdomModifier(playerMob)) * 0.05f;

        playerMob.addAction(Actions.delay(velocity * 2));
    }

    /**
     * Adding Fade Out action when player mob is dead.
     *
     * @param playerMob PlayerMob object
     */
    public void addFadeOutActionWhenPlayerMobIsDead(PlayerMob playerMob) {
        playerMob.addAction(Actions.fadeOut(0.25f));
    }

    private Animation getAnimationForAttack(
            PlayerMob playerMob, int mapXcoordinateOfEnemy, int mapYcoordinageOfEnemy) {

        //  SOUTH
        if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackS, playerMob);
            //  NORTH
        } else if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackN, playerMob);
            // WEST
        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackW, playerMob);
            // EAST
        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackE, playerMob);
            // NORTH - EAST
        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackW, playerMob);
            // NORTH - WEST
        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackE, playerMob);
            // SOUTH - EAST
        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackW, playerMob);
            // SOUTH - WEST
        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return AnimationCreator.getInstance().makeAnimation(attackE, playerMob);
        }
        return AnimationCreator.getInstance().makeAnimation(attackS, playerMob);
    }

    private SequenceAction getSequenceForAttack(
            PlayerMob playerMob, int mapXcoordinateOfEnemy, int mapYcoordinageOfEnemy) {

        float velocity = 2.0f - (playerMob.getActualSpeed() + ModifierGetter.getSpeedModifier(playerMob)) * 0.05f;

        //  NORTH
        if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(0, -25, velocity), Actions.moveBy(0, 25, velocity));
            //  SOUTH
        } else if (playerMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(0, 25, velocity), Actions.moveBy(0, -25, velocity));
            // EAST
        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(-25, 0, velocity), Actions.moveBy(25, 0, velocity));
            // WEST
        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(25, 0, velocity), Actions.moveBy(-25, 0, velocity));
            // NORTH - EAST
        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(-25, -25, velocity), Actions.moveBy(25, 25, velocity));
            // NORTH - WEST
        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(25, -25, velocity), Actions.moveBy(-25, 25, velocity));
            // SOUTH - EAST
        } else if (playerMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(-25, 25, velocity), Actions.moveBy(25, -25, velocity));
            // SOUTH - WEST
        } else if (playerMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                playerMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(25, 25, velocity), Actions.moveBy(-25, -25, velocity));
        }
        return new SequenceAction(Actions.moveBy(-250, -250, velocity), Actions.moveBy(250, 250, velocity));
    }

    /**
     * Unselect seclected mobs on stage.
     */
//    private void unselectSelectedPlayerMobs() {
//        GameStatus.getInstance().getUpperBarRightTable().clear();
//        for (int i = 0; i < map.getFieldsColumns(); i++) {
//            for (int j = 0; j < map.getFieldsRows(); j++) {
//                if (map.getFields()[i][j].getPlayerMob() != null) {
//                    map.getFields()[i][j].getPlayerMob().setSelected(false);
//                    map.getFields()[i][j].getPlayerMob().moveManager.setMoveButtonsCreated(false);
//                    map.getFields()[i][j].getPlayerMob().moveManager.setAttackButtonsCreated(false);
//                    map.getFields()[i][j].getPlayerMob().moveManager.setShowMoveButtons(false);
//                    map.getFields()[i][j].getPlayerMob().moveManager.setShowAttackButtons(false);
//                    MoveManager.removeButtons();
//                }
//            }
//        }
//    }

    @Override
    public void act(float delta) {
        super.act(delta);

        ManaBar.recalculateManaBarFrameDuration(this);
        APBar.recalculateApBarFrameDuration(this);

        if (this.getActionPoints() < this.getActualSpeed() + ModifierGetter.getSpeedModifier(this) && !this.getApBar().isApBarAdd()) {
            this.getStage().addActor(this.getApBar());
            this.getApBar().setApBarAdd(true);
        }

        if (this.getActualMana() < this.getMaxMana() + ModifierGetter.getWisdomModifier(this) && !this.getManaBar().isManaBarAdd()) {
            this.getStage().addActor(this.getManaBar());
            this.getManaBar().setManaBarAdd(true);
        }

        if (this.getActions().size > 0) {
            MoveManager.removeButtons(this);
            this.moveManager.setMoveButtonsCreated(false);
            this.moveManager.setAttackButtonsCreated(false);
        }

        if (this.getActions().size == 0 && this.getActualhp() < 1) {
            MoveManager.removeButtons(this);
            this.moveManager.setMoveButtonsCreated(false);
            this.moveManager.setAttackButtonsCreated(false);
            DefaultMob.removeDeadMobs();
        } else if (this.getActions().size == 0) {
            this.setTouchable(Touchable.enabled);
            if (this.stepManager.isCheckStepping()) {
                this.stepManager.checkStep();
            }
            if (!isSelected) {
                //this.setLooped(true);
                this.setAnimation(AnimationCreator.getInstance().makeAnimation(standing));
                this.setLooped(true);

            } else {
                if (!this.moveManager.isMoveButtonsCreated()) {
                    moveManager.showMoveInterface(GameStatus.getInstance().getMapStage(), this);
                    moveManager.showAttackInterface(GameStatus.getInstance().getMapStage(), this);
                }

                this.setLooped(true);
                this.setAnimation(AnimationCreator.getInstance().makeAnimation(selected));
                this.setLooped(true);
            }
        } else if (this.getActions().size > 0) {
            this.setTouchable(Touchable.disabled);
        }
    }

    /**
     * Returns field where playerMob standing.
     *
     * @return Field object
     */
    public Field getFieldOfPlayerMob() {
        return map.getFields()[this.getCoordinateXonMap()][this.getCoordinateYonMap()];
    }

    /**
     * Gets player mob statistic window
     *
     * @return Statistics of selected player mob
     */
    public Window getPlayerMobWindow() {
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(700, 400);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        Table expirienceTable = new Table();
        expirienceTable.padBottom(20);
        expirienceTable.add(new Label("Level: " + getLevel(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32"));
        expirienceTable.row();
        expirienceTable.add(new Label("Experience points: " + getActualExp(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32"));
        expirienceTable.row();
        expirienceTable.add(new Label("Next level: " + getLevelingArray()[getLevel() + 1], AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32"));

        Table statisticTable = new Table();

        statisticTable.add(new Label("Attack", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("Defence", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("Speed", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("Power", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("Knowledge", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);

        statisticTable.row();

        statisticTable.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/attackIcon.png", Texture.class))).pad(5).size(100, 100);
        statisticTable.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/defenceIcon.png", Texture.class))).pad(5).size(100, 100);
        statisticTable.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/speedIcon.png", Texture.class))).pad(5).size(100, 100);
        statisticTable.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/powerIcon.png", Texture.class))).pad(5).size(100, 100);
        statisticTable.add(new Image(
                AssetsGameScreen.getInstance().getManager().get("game/interface/wisdomIcon.png", Texture.class))).pad(5).size(100, 100);

        statisticTable.row();

        int finalAttack = getActualAttack() + ModifierGetter.getAttackModifier(this);
        int finalDefence = getActualDefence() + ModifierGetter.getDefenceModifier(this);
        int finalSpeed = getActualSpeed() + ModifierGetter.getSpeedModifier(this);
        int finalPower = getActualPower() + ModifierGetter.getPowerModifier(this);
        int finalWisdom = getActualWisdom() + ModifierGetter.getWisdomModifier(this);

//        statisticTable.add(new Label("" + finalAttack + "/" + getAttack() + "", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class))).pad(5);
//        statisticTable.add(new Label("" + finalDefence + "/" + getDefence() + "", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class))).pad(5);
//        statisticTable.add(new Label("" + finalSpeed + "/" + getSpeed() + "", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class))).pad(5);
//        statisticTable.add(new Label("" + finalPower + "/" + getPower() + "", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class))).pad(5);
//        statisticTable.add(new Label("" + finalWisdom + "/" + getWisdom() + "", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class))).pad(5);

        statisticTable.add(new Label("" + finalAttack, AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("" + finalDefence, AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("" + finalSpeed, AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("" + finalPower, AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);
        statisticTable.add(new Label("" + finalWisdom, AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class), "black32")).pad(5);


        TextButton tbCancel = new TextButton("CLOSE", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        window.add(expirienceTable).align(Align.top);
        window.row();
        window.add(statisticTable).align(Align.center);
        window.row();
        window.add(tbCancel).pad(5).size(175, 50);

        return window;
    }

    /**
     * Decresing actual mana.
     *
     * @param mana How many point
     */
    public void decreseMana(int mana) {
        this.setActualMana(this.getActualMana() - mana);
    }

    /**
     * Returns index in array of PlayerMobs
     *
     * @return number of index.
     */
    public int getPlayerMobIndex() {
        int index = 0;
        for (PlayerMob tmpPlayerMob : playerOwner.getPlayerMobs()) {
            if (tmpPlayerMob.equals(this))
                break;
            index += 1;
        }
        return index;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Player getPlayerOwner() {
        return playerOwner;
    }

    public Image getPlayerColorImage() {
        return playerColorImage;
    }

    public MoveManager getMoveManager() {
        return moveManager;
    }

    public Label getHpLabel() {
        return hpLabel;
    }

    public Label getApLabel() {
        return apLabel;
    }

    public Label getManaLabel() {
        return manaLabel;
    }

    public StepManager getStepManager() {
        return stepManager;
    }

    public APBar getApBar() {
        return apBar;
    }

    public void setApBar(APBar apBar) {
        this.apBar = apBar;
    }

    public ManaBar getManaBar() {
        return manaBar;
    }

    public PlayerMobClasses getPlayerMobClass() {
        return playerMobClass;
    }

    public void setPlayerMobClass(PlayerMobClasses playerMobClass) {
        this.playerMobClass = playerMobClass;
    }

    public AnimationTypes getWalkE() {
        return walkE;
    }

    public AnimationTypes getWalkN() {
        return walkN;
    }

    public AnimationTypes getWalkS() {
        return walkS;
    }

    public AnimationTypes getWalkW() {
        return walkW;
    }

    public AnimationTypes getStanding() {
        return standing;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public SnapshotArray<LongEffect> getLongEffects() {
        return longEffects;
    }

    public Table getLongEffectsTable() {
        return longEffectsTable;
    }

    public SnapshotArray<Item> getItems() {
        return items;
    }

    public SnapshotArray<Equip> getEquip() {
        return equip;
    }

    public Equip getArmor() {
        return armor;
    }

    public void setArmor(Equip armor) {
        this.armor = armor;
        APBar.recalculateApBarFrameDuration(this);
        ManaBar.recalculateManaBarFrameDuration(this);
    }

    public Equip getWeapon() {
        return weapon;
    }

    public void setWeapon(Equip weapon) {
        this.weapon = weapon;
        APBar.recalculateApBarFrameDuration(this);
        ManaBar.recalculateManaBarFrameDuration(this);
    }

    public Equip getArtifact() {
        return artifact;
    }

    public void setArtifact(Equip artifact) {
        this.artifact = artifact;
        APBar.recalculateApBarFrameDuration(this);
        ManaBar.recalculateManaBarFrameDuration(this);
    }

    @Override
    public void setActualhp(int actualhp) {
        super.setActualhp(actualhp);
        getHpLabel().setText("" + actualhp + "/" + getMaxHp());
    }

    @Override
    public void setActionPoints(int actionPoints) {
        super.setActionPoints(actionPoints);
        getApLabel().setText("" + actionPoints);
    }

    @Override
    public void setActualMana(int actualMana) {
        super.setActualMana(actualMana);
        getManaLabel().setText("" + actualMana + "/" + (getMaxMana() + ModifierGetter.getWisdomModifier(this)));
    }

    @Override
    public void setMaxHp(int maxHp) {
        super.setMaxHp(maxHp);
        getHpLabel().setText("" + getActualhp() + "/" + maxHp);
    }

    @Override
    public void setMaxMana(int maxMana) {
        super.setMaxMana(maxMana);
        getManaLabel().setText("" + getActualMana() + "/" + maxMana);
    }
}
