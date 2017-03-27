package com.mygdx.eoh.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
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
 * Instant effect class.
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
        if (this.getAnimation().isAnimationFinished(this.getStateTime())) {
            GameStatus.getInstance().getMapStage().getActors().removeValue(this, true);
        }
    }

    /**
     * Making action of instant effect
     *
     * @param castingPlayer   Player who created effect.
     * @param defendingPlayer Player affected by the effect.
     */
    public void action(PlayerMob castingPlayer, PlayerMob defendingPlayer) {

        Random rnd = new Random();

        switch (instantEffects) {
            case FiraballDamage:

                int damage;
                damage = ((rnd.nextInt(castingPlayer.getActualPower()) + 1) + 3) - (rnd.nextInt(defendingPlayer.getActualDefence()) + 1);
                if (damage < 0)
                    damage = 0;
                this.damage = damage;
                FightManager.setActualHPofMob(defendingPlayer, damage);

                if (NetStatus.getInstance().getClient() != null) {
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

                for (LongEffect longEffect1 : defendingPlayer.getLongEffects()) {
                    defendingPlayer.getLongEffectsTable().add(longEffect1).size(50, 50).padRight(5);
                }

                damage = 0;

                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()) {
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
            case HealthPotion:
                defendingPlayer.setActualhp(defendingPlayer.getActualhp() + 5);
                if (defendingPlayer.getActualhp() > defendingPlayer.getMaxHp()) {
                    defendingPlayer.setActualhp(defendingPlayer.getMaxHp());
                }

                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()) {
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    instantEffectNet.instantEffectNumber = 2;
                    instantEffectNet.locationXofDefender = defendingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = defendingPlayer.getCoordinateYonMap();
                    instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                    NetStatus.getInstance().getClient().sendTCP(instantEffectNet);

                }

                break;
            case ManaPotion:
                defendingPlayer.setActualMana(defendingPlayer.getActualMana() + 5);
                if (defendingPlayer.getActualMana() > defendingPlayer.getMaxMana()) {
                    defendingPlayer.setActualMana(defendingPlayer.getMaxMana());
                }

                // Turn off mana bar of player mob.
                if (defendingPlayer.getActualMana() >= defendingPlayer.getMaxMana() && defendingPlayer.getManaBar().isManaBarAdd()) {
                    defendingPlayer.getManaBar().remove();
                    defendingPlayer.getManaBar().setManaBarAdd(false);
                }

                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()) {
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    instantEffectNet.instantEffectNumber = 3;
                    instantEffectNet.locationXofDefender = defendingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = defendingPlayer.getCoordinateYonMap();
                    instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
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
