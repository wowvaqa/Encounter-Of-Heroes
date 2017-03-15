package com.mygdx.eoh.effects;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.animation.AnimationSpellCreator;
import com.mygdx.eoh.enums.InstantEffects;
import com.mygdx.eoh.enums.LongEffects;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

import java.util.Random;

/**
 *
 * Created by v on 2017-02-21.
 */

public class InstantEffect extends AnimatedImage {

    private InstantEffects instantEffects;
    //private SnapshotArray<LongEffect> longEffects;
    private int damage;

    public InstantEffect(Animation animation, boolean isLooped, InstantEffects instantEffects) {
        super(animation, isLooped);

        this.setSize(Options.tileSize, Options.tileSize);

        this.instantEffects = instantEffects;
        //this.longEffects = new SnapshotArray<LongEffect>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getAnimation().isAnimationFinished(this.getStateTime())){
            GameStatus.getInstance().getMapStage().getActors().removeValue(this, true);
        }
    }

    public void action(PlayerMob castingPlayer, PlayerMob defendingPlayer){

        Random rnd = new Random();

        switch (instantEffects){
            case FiraballDamage:

                int damage;
                damage = ((rnd.nextInt(castingPlayer.getActualPower()) + 1) + 3) - (rnd.nextInt(defendingPlayer.getActualDefence()) + 1);
                if (damage < 0)
                    damage = 0;
                this.damage = damage;
                FightManager.setActualHPofMob(defendingPlayer, damage);

                if (NetStatus.getInstance().getClient() != null){
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.damage = damage;
                    instantEffectNet.instantEffectNumber = 0;
                    instantEffectNet.locationXofDefender = defendingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = defendingPlayer.getCoordinateYonMap();
                    instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    NetStatus.getInstance().getClient().sendTCP(instantEffectNet);
                }

                break;
            case AttackUpgrade:

                castingPlayer.changeToCastAnimation(castingPlayer);

                LongEffect longEffect = new LongEffect(
                        AnimationSpellCreator.getInstance().makeLongEffectAnimation(LongEffects.AttackUpgrade, defendingPlayer),
                        false,
                        LongEffects.AttackUpgrade,
                        defendingPlayer
                );
                longEffect.setAttackModificator(1);

                defendingPlayer.getLongEffects().add(longEffect);

                defendingPlayer.getLongEffectsTable().clear();

                for (LongEffect longEffect1: defendingPlayer.getLongEffects()){
                    defendingPlayer.getLongEffectsTable().add(longEffect1).size(50, 50).padRight(5);
                }

                damage = 0;

                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()){
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.damage = damage;
                    instantEffectNet.instantEffectNumber = 1;
                    instantEffectNet.locationXofDefender = defendingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = defendingPlayer.getCoordinateYonMap();
                    instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    NetStatus.getInstance().getClient().sendTCP(instantEffectNet);
                }

                break;
        }
    }

    /**
     * GETTERS AND SETTERS
     */
    public int getDamage() {
        return damage;
    }
}
