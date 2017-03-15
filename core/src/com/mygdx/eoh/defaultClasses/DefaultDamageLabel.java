package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Damage label for visalite damage given to mob
 * Created by v on 2016-11-03.
 */
public class DefaultDamageLabel extends Label {
    public DefaultDamageLabel(CharSequence text, Skin skin, String styleName,
                              float coordinateXonStage, float coordinateYonStage) {
        super(text, skin, styleName);

        this.setPosition(coordinateXonStage, coordinateYonStage);

        ParallelAction parallelAction = new ParallelAction(
                Actions.fadeOut(2.0f),
                Actions.moveBy(0, 200, 2.0f));

        this.addAction(parallelAction);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getActions().size < 1){
            this.remove();
        }
    }
}
