package com.mygdx.eoh.mob;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.animation.AnimationFreeMobCreator;
import com.mygdx.eoh.defaultClasses.DefaultMob;
import com.mygdx.eoh.enums.FreeMobAnimationTypes;
import com.mygdx.eoh.enums.FreeMobsKinds;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.MoveManager;

/**
 * Represents free mobs on map like skeleton etc.
 * Created by wowvaqa on 11.04.17.
 */

public class FreeMob extends DefaultMob {

    private FreeMobsKinds freeMobsKind;
    private boolean selected;

    public FreeMob(Animation animation, boolean isLooped) {
        super(animation, isLooped);

        addListener();
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

                    setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonStandingSelected));

                    setSelected(true);
                }
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!isSelected()) {
            setAnimation(AnimationFreeMobCreator.getInstance().makeAnimation(FreeMobAnimationTypes.SkeletonStanding));
            setLooped(true);
        }

        if (this.getActions().size == 0 && getActualhp() < 1) {
            DefaultMob.removeDeadMobs();
        }
    }

    /**
     * Adding Fade Out action when free mob is dead.
     *
     * @param freeMob Free mob object
     */
    public void addFadeOutActionWhenPlayerMobIsDead(FreeMob freeMob) {
        freeMob.addAction(Actions.fadeOut(0.5f));
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
}
