package com.mygdx.eoh.magic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.defaultClasses.DefaultDamageLabel;
import com.mygdx.eoh.effects.InstantEffect;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 *
 * Created by v on 2017-02-21.
 */

public class CastButton extends AnimatedImage {

    private PlayerMob buttonOwner;

    /**
     * Make cast button on stage.
     * @param animation Animation of button.
     * @param isLooped Is button looped.
     * @param locationXonMap Location X of button on stage.
     * @param locationYonMap Location Y of button on stage.
     * @param spell What spell button cast.
     */
    public CastButton(Animation animation, boolean isLooped, final int locationXonMap, final int locationYonMap, final Spell spell) {
        super(animation, isLooped);
        super.setPosition(locationXonMap * Options.tileSize, locationYonMap * Options.tileSize);
        super.setSize(Options.tileSize, Options.tileSize);

        this.buttonOwner = spell.getPlayerOwner();

        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                spell.getPlayerOwner().changeToCastAnimation(spell.getPlayerOwner());
                spell.getPlayerOwner().decreseMana(spell.getManaCost());

                if (NetStatus.getInstance().getClient() != null) {
                    Network.SpellCastNet spellCastNet = new Network.SpellCastNet();
                    spellCastNet.enemyId = NetStatus.getInstance().getEnemyId();
                    spellCastNet.locationXofCaster = spell.getPlayerOwner().getCoordinateXonMap();
                    spellCastNet.locationYofCaster = spell.getPlayerOwner().getCoordinateYonMap();
                    spellCastNet.spellManaCost = spell.getManaCost();
                    NetStatus.getInstance().getClient().sendTCP(spellCastNet);
                }

                for (InstantEffect instantEffect: spell.getInstantEffects()){
                    instantEffect.setPosition(getX(), getY());
                    instantEffect.setStateTime(0);
                    if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob() != null)
                        instantEffect.action(spell.getPlayerOwner(), GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob());
                    else if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob() != null)
                        instantEffect.action(spell.getPlayerOwner(), GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob());
                    GameStatus.getInstance().getMapStage().addActor(instantEffect);
                    showDamageLabel(instantEffect.getDamage(), locationXonMap, locationYonMap);
                }
            }
        });
    }

    /**
     * Show damage label on stage.
     * @param damage Amount of damge
     * @param locationXonMap Location X on stage to show damage.
     * @param locationYonMap Location Y on stage to show damage.
     */
    private void showDamageLabel(int damage, int locationXonMap, int locationYonMap) {
        DefaultDamageLabel defaultDamageLabel = new DefaultDamageLabel(
                Integer.toString(damage), (Skin) AssetsGameScreen.getInstance().getManager().get("styles/skin.json"), "fight",
                locationXonMap * Options.tileSize + Options.tileSize / 2,
                locationYonMap * Options.tileSize + Options.tileSize / 2);
        GameStatus.getInstance().getMapStage().addActor(defaultDamageLabel);
    }

    public PlayerMob getButtonOwner() {
        return buttonOwner;
    }
}
