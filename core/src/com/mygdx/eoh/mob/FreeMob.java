package com.mygdx.eoh.mob;

import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.animation.AnimationFreeMobCreator;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.assets.AssetsSounds;
import com.mygdx.eoh.defaultClasses.DefaultDamageLabel;
import com.mygdx.eoh.defaultClasses.DefaultMob;
import com.mygdx.eoh.effects.LongEffect;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.enums.FreeMobAnimationTypes;
import com.mygdx.eoh.enums.FreeMobsKinds;
import com.mygdx.eoh.gameClasses.APBar;
import com.mygdx.eoh.gameClasses.Field;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.HpBar;
import com.mygdx.eoh.gameClasses.ModifierGetter;
import com.mygdx.eoh.gameClasses.MoveManager;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 * Represents free mobs on map like skeleton etc.
 * Created by wowvaqa on 11.04.17.
 */

public class FreeMob extends DefaultMob {

    private FreeMobsKinds freeMobsKind;
    private boolean selected;
    private boolean attacked;

    private PlayerMob attackingPlayerMob;
    private int locationXofAttackingPlayerMOb;
    private int locationYofAttackingPlayerMOb;

    private Table infoPlayerMobTable;
    private Table longEffectsTable;
    private Label hpLabel;
    private Label apLabel;
    private Label manaLabel;

    private SnapshotArray<LongEffect> longEffects;

    private APBar apBar;
    private HpBar hpBar;

    public FreeMob(Animation animation, boolean isLooped) {
        super(animation, isLooped);

        longEffects = new SnapshotArray<LongEffect>();

        addListener();

        createInfoTable();
        createLongEffectsTable();

        apBar = new APBar(AnimationCreator.getInstance().makeAnimation(AnimationTypes.ApBarAnimation, this), false, this);
        hpBar = new HpBar(AnimationCreator.getInstance().makeAnimation(AnimationTypes.HpBarAnimation, this), false, this);
    }

    /**
     * Unselect all free mobs
     */
    public static void unselectFreeMobs() {
        GameStatus.getInstance().getUpperBarRightTable().clear();
        for (int i = 0; i < GameStatus.getInstance().getMap().getFieldsColumns(); i++) {
            for (int j = 0; j < GameStatus.getInstance().getMap().getFieldsRows(); j++) {
                if (GameStatus.getInstance().getMap().getFields()[i][j].getFreeMob() != null) {
                    GameStatus.getInstance().getMap().getFields()[i][j].getFreeMob().setSelected(false);
                }
            }
        }
    }

    /**
     * Add listener to mob.
     */
    private void addListener() {
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                GameStatus.getInstance().getEquipmentTable().clear();
                GameStatus.getInstance().getSpellEffectsTable().clear();

                if (!isSelected()) {

                    MoveManager.unselectCastles(GameStatus.getInstance().getMap());
                    MoveManager.turnOffSelectedPlayersMobs();
                    FreeMob.unselectFreeMobs();

                    if (getFreeMobsKind().equals(FreeMobsKinds.Barbarian))
                        setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianStandingSelected));
                    else if (getFreeMobsKind().equals(FreeMobsKinds.LavaGolem))
                        setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemStandingSelected));
                    else
                        setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonStandingSelected));

                    GameStatus.getInstance().getSpellEffectsTable().add(getLongEffectsTable());
                    GameStatus.getInstance().getUpperBarRightTable().add(getInfoPlayerMobTable());

                    setSelected(true);
                }
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        HpBar.recalculateHpBarFrameDuration(this);
        APBar.recalculateApBarFrameDuration(this);

        if (this.getActionPoints() < this.getActualSpeed() + ModifierGetter.getSpeedModifier(this) && !this.getApBar().isApBarAdd()) {
            this.getStage().addActor(this.getApBar());
            this.getApBar().setApBarAdd(true);
        }

        if (this.getActualhp() < this.getMaxHp() + ModifierGetter.getHpModifier(this) &&
                this.hpBar != null &&
                !this.getHpBar().isHpBarAdd() &&
                this.getHpBar() != null
                ) {
            this.getStage().addActor(this.getHpBar());
            this.getHpBar().setHpBarAdd(true);
        }

        if (!isSelected() && !isAttacked() && getActions().size == 0) {
            if (getFreeMobsKind().equals(FreeMobsKinds.Barbarian))
                setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianStanding));
            else if (getFreeMobsKind().equals(FreeMobsKinds.LavaGolem))
                setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemStanding));
            else
                setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonStanding));
            setLooped(true);
        }

        if (isAttacked() && getActionPoints() < 1 && getActions().size == 0) {
            if (getFreeMobsKind().equals(FreeMobsKinds.Barbarian))
                setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianStanding));
            else if (getFreeMobsKind().equals(FreeMobsKinds.LavaGolem))
                setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemStanding));
            else
                setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonStanding));
            setLooped(true);
        }

        if (this.getActions().size == 0 && getActualhp() < 1) {
            DefaultMob.removeDeadMobs();
        }

        if (isAttacked() && this.getActualhp() > 0) {
            if (locationXofAttackingPlayerMOb == attackingPlayerMob.getCoordinateXonMap() &&
                    locationYofAttackingPlayerMOb == attackingPlayerMob.getCoordinateYonMap()) {

                if (getActions().size < 1 && getActionPoints() > 0)
                    attack();

            } else {
                attacked = false;
            }

        }

        if (getActions().size > 0)
            setTouchable(Touchable.disabled);
        else
            setTouchable(Touchable.enabled);
    }

    /**
     * Create table contain long effects.
     */
    private void createLongEffectsTable() {
        longEffectsTable = new Table();
    }

    /**
     * Creates info table which show mana, ap and hp of free mob.
     */
    private void createInfoTable() {
        infoPlayerMobTable = new Table();

        hpLabel = new Label("" + getActualhp() + "/" + getMaxHp(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        apLabel = new Label("" + getActionPoints(), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        manaLabel = new Label("" + getActualMana() + "/" + (getMaxMana() + ModifierGetter.getWisdomModifier(this)), AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

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

    /**
     * Free mob attack another mob.
     */
    private void attack() {
        changeToAttackAnimation(this, attackingPlayerMob.getCoordinateXonMap(), attackingPlayerMob.getCoordinateYonMap());
        int damage = FightManager.getDamage(this, attackingPlayerMob);
        showDamageLabel(damage, attackingPlayerMob.getCoordinateXonMap(), attackingPlayerMob.getCoordinateYonMap());

        if (NetStatus.getInstance().getClient() != null)
            attackNet(damage);

        if (attackingPlayerMob.getActualhp() < 1) {
            setAttacked(false);
            attackingPlayerMob = null;
        }
    }

    /**
     * Sending attack data through network
     */
    private void attackNet(int damage) {
        Network.AttackPlayerMob attackPlayerMob = new Network.AttackPlayerMob();
        attackPlayerMob.enemyId = NetStatus.getInstance().getEnemyId();
        attackPlayerMob.locationXofEnemy = locationXofAttackingPlayerMOb;
        attackPlayerMob.locationYofEnemy = locationYofAttackingPlayerMOb;
        attackPlayerMob.indexInArray = PlayerMob.getPlayerMobIndex(
                attackingPlayerMob, attackingPlayerMob.getPlayerOwner());
        attackPlayerMob.indexPlayerOwner =
                attackingPlayerMob.getPlayerOwner().getInedxOfPlayerInArrayOfPlayer();
        attackPlayerMob.damage = damage;
        attackPlayerMob.hpLeft = attackingPlayerMob.getActualhp();
        attackPlayerMob.locationXofAttacker = getCoordinateXonMap();
        attackPlayerMob.locationYofAttacker = getCoordinateYonMap();
        NetStatus.getInstance().getClient().sendTCP(attackPlayerMob);
    }

    /**
     * Adding Fade Out action when free mob is dead.
     *
     * @param freeMob Free mob object
     */
    public void addFadeOutActionWhenPlayerMobIsDead(FreeMob freeMob) {
        freeMob.addAction(Actions.fadeOut(0.5f));
    }

    public void changeToAttackAnimation(FreeMob freeMob, int locationXofAttackedMob, int locationYofAttackedMob) {
        freeMob.setLooped(false);
        freeMob.setStateTime(0);
        freeMob.setAnimation(freeMob.getAnimationForAttack(
                freeMob, locationXofAttackedMob, locationYofAttackedMob));
        freeMob.getAnimation().getKeyFrameIndex(0);
        freeMob.getAnimation().setFrameDuration((2.0f - (getActualSpeed() +
                ModifierGetter.getSpeedModifier(freeMob)) * 0.05f) / 24);
        freeMob.addAction(freeMob.getSequenceForAttack(
                freeMob, locationXofAttackedMob, locationYofAttackedMob));

        if (GameStatus.getInstance().getMap().getFields()[locationXofAttackedMob][locationYofAttackedMob].getPlayerMob() != null){
            if (GameStatus.getInstance().getMap().getFields()[locationXofAttackedMob][locationYofAttackedMob].getPlayerMob().getPlayerOwner().equals(
                    GameStatus.getInstance().getCurrentPlayerTurn()
            )){
                AssetsSounds.getInstance().getManager().get("sounds/swordSound.wav", Sound.class).play();
            }
        }

    }

    private Animation getAnimationForAttack(
            FreeMob freeMob, int mapXcoordinateOfEnemy, int mapYcoordinageOfEnemy) {

        if (this.getFreeMobsKind().equals(FreeMobsKinds.Barbarian)) {
            //  SOUTH
            if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackS);
                //  NORTH
            } else if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackN);
                // WEST
            } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackW);
                // EAST
            } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackE);
                // NORTH - EAST
            } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackW);
                // NORTH - WEST
            } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackE);
                // SOUTH - EAST
            } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackW);
                // SOUTH - WEST
            } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackE);
            }
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.BarbarianAttackS);
        }

        if (this.getFreeMobsKind().equals(FreeMobsKinds.LavaGolem)) {
            //  SOUTH
            if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackS);
                //  NORTH
            } else if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackN);
                // WEST
            } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackW);
                // EAST
            } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackE);
                // NORTH - EAST
            } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackW);
                // NORTH - WEST
            } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackE);
                // SOUTH - EAST
            } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackW);
                // SOUTH - WEST
            } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                    freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
                return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackE);
            }
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.LavaGolemAttackS);
        }

        //  SOUTH
        if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackS);
            //  NORTH
        } else if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackN);
            // WEST
        } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackW);
            // EAST
        } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackE);
            // NORTH - EAST
        } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackW);
            // NORTH - WEST
        } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackE);
            // SOUTH - EAST
        } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackW);
            // SOUTH - WEST
        } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackE);
        }
        return AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonAttackS);
    }

    private SequenceAction getSequenceForAttack(
            FreeMob freeMob, int mapXcoordinateOfEnemy, int mapYcoordinageOfEnemy) {

        float velocity = 2.0f - (freeMob.getActualSpeed() + ModifierGetter.getSpeedModifier(freeMob)) * 0.05f;

        //  NORTH
        if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(0, -25, velocity), Actions.moveBy(0, 25, velocity));
            //  SOUTH
        } else if (freeMob.getCoordinateXonMap() == mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(0, 25, velocity), Actions.moveBy(0, -25, velocity));
            // EAST
        } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(-25, 0, velocity), Actions.moveBy(25, 0, velocity));
            // WEST
        } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() == mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(25, 0, velocity), Actions.moveBy(-25, 0, velocity));
            // NORTH - EAST
        } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(-25, -25, velocity), Actions.moveBy(25, 25, velocity));
            // NORTH - WEST
        } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() > mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(25, -25, velocity), Actions.moveBy(-25, 25, velocity));
            // SOUTH - EAST
        } else if (freeMob.getCoordinateXonMap() > mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(-25, 25, velocity), Actions.moveBy(25, -25, velocity));
            // SOUTH - WEST
        } else if (freeMob.getCoordinateXonMap() < mapXcoordinateOfEnemy &&
                freeMob.getCoordinateYonMap() < mapYcoordinageOfEnemy) {
            return new SequenceAction(Actions.moveBy(25, 25, velocity), Actions.moveBy(-25, -25, velocity));
        }
        return new SequenceAction(Actions.moveBy(-250, -250, velocity), Actions.moveBy(250, 250, velocity));
    }

    private void showDamageLabel(int damage, int locationXonMap, int locationYonMap) {
        DefaultDamageLabel defaultDamageLabel = new DefaultDamageLabel(
                Integer.toString(damage), (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"), "fight",
                locationXonMap * Options.tileSize + Options.tileSize / 2,
                locationYonMap * Options.tileSize + Options.tileSize / 2);
        GameStatus.getInstance().getMapStage().addActor(defaultDamageLabel);
    }

    public Field getFieldOfFreeMob() {
        return GameStatus.getInstance().getMap().getFields()[this.getCoordinateXonMap()][this.getCoordinateYonMap()];
    }

    /***********************************************************************************************
     * GETTERS & SETTERS
     **********************************************************************************************/
    public FreeMobsKinds getFreeMobsKind() {
        return freeMobsKind;
    }

    public void setFreeMobsKind(FreeMobsKinds freeMobsKind) {
        this.freeMobsKind = freeMobsKind;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public PlayerMob getAttackingPlayerMob() {
        return attackingPlayerMob;
    }

    public void setAttackingPlayerMob(PlayerMob attackingPlayerMob) {
        this.attackingPlayerMob = attackingPlayerMob;
        locationXofAttackingPlayerMOb = attackingPlayerMob.getCoordinateXonMap();
        locationYofAttackingPlayerMOb = attackingPlayerMob.getCoordinateYonMap();
    }

    public APBar getApBar() {
        return apBar;
    }

    public void setApBar(APBar apBar) {
        this.apBar = apBar;
    }

    public HpBar getHpBar() {
        return hpBar;
    }

    public void setHpBar(HpBar hpBar) {
        this.hpBar = hpBar;
    }

    public SnapshotArray<LongEffect> getLongEffects() {
        return longEffects;
    }

    public Table getInfoPlayerMobTable() {
        return infoPlayerMobTable;
    }

    public void setInfoPlayerMobTable(Table infoPlayerMobTable) {
        this.infoPlayerMobTable = infoPlayerMobTable;
    }

    public Table getLongEffectsTable() {
        return longEffectsTable;
    }

    public void setLongEffectsTable(Table longEffectsTable) {
        this.longEffectsTable = longEffectsTable;
    }

    public Label getHpLabel() {
        return hpLabel;
    }

    public void setHpLabel(Label hpLabel) {
        this.hpLabel = hpLabel;
    }

    public Label getApLabel() {
        return apLabel;
    }

    public void setApLabel(Label apLabel) {
        this.apLabel = apLabel;
    }

    public Label getManaLabel() {
        return manaLabel;
    }

    public void setManaLabel(Label manaLabel) {
        this.manaLabel = manaLabel;
    }

    @Override
    public void setActualhp(int actualhp) {
        super.setActualhp(actualhp);
        getHpLabel().setText("" + actualhp + "/" + getMaxHp());
    }

    @Override
    public void setActualMana(int actualMana) {
        super.setActualMana(actualMana);
        getManaLabel().setText("" + actualMana + "/" + (getMaxMana() + ModifierGetter.getWisdomModifier(this)));
    }

    @Override
    public void setActionPoints(int actionPoints) {
        super.setActionPoints(actionPoints);
        getApLabel().setText("" + actionPoints);
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
