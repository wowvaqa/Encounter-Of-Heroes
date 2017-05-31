package com.mygdx.eoh.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.animation.AnimationSpellCreator;
import com.mygdx.eoh.enums.InstantEffects;
import com.mygdx.eoh.enums.LongEffects;
import com.mygdx.eoh.gameClasses.FightManager;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.ModifierGetter;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.mob.FreeMob;
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
        setTouchable(Touchable.disabled);
        //this.longEffects = new SnapshotArray<LongEffect>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (instantEffects.equals(InstantEffects.HealthPotion) || instantEffects.equals(InstantEffects.ManaPotion)) {
            if (this.getAnimation().isAnimationFinished(this.getStateTime())) {
                GameStatus.getInstance().getMapStage().getActors().removeValue(this, true);
            }
        }
    }

    /**
     * Making action of instant effect
     *
     * @param castingPlayer Player who created effect.
     * @param defendingMob  PlayerMob or FreeMob  affected by the effect.
     */
    public void action(PlayerMob castingPlayer, Object defendingMob) {

        Random rnd = new Random();

        switch (instantEffects) {
            case FiraballDamage:

                int damage = 0;
                if (defendingMob.getClass().equals(PlayerMob.class)) {
                    damage = ((rnd.nextInt((castingPlayer.getActualPower()) + ModifierGetter.getPowerModifier(castingPlayer)) + 1) + 3) -
                            ((rnd.nextInt(((PlayerMob) defendingMob).getActualDefence()) + ModifierGetter.getDefenceModifier(defendingMob)) + 1);

                    if (damage < 0)
                        damage = 0;
                    this.damage = damage;
                    FightManager.setActualHPofMob(defendingMob, damage);
                    FightManager.chceckExpReward(castingPlayer, defendingMob);

                    if (NetStatus.getInstance().getClient() != null) {
                        Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                        instantEffectNet.damage = damage;
                        instantEffectNet.instantEffectNumber = 0;
                        instantEffectNet.locationXofDefender = ((PlayerMob) defendingMob).getCoordinateXonMap();
                        instantEffectNet.locationYofDefender = ((PlayerMob) defendingMob).getCoordinateYonMap();
                        instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                        instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                        instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                        NetStatus.getInstance().getClient().sendTCP(instantEffectNet);
                    }
                }

                if (defendingMob.getClass().equals(FreeMob.class)) {
                    damage = ((rnd.nextInt((castingPlayer.getActualPower()) + ModifierGetter.getPowerModifier(castingPlayer)) + 1) + 3) -
                            ((rnd.nextInt(((FreeMob) defendingMob).getActualDefence()) + ModifierGetter.getDefenceModifier(defendingMob)) + 1);

                    ((FreeMob) defendingMob).setAttacked(true);
                    ((FreeMob) defendingMob).setAttackingPlayerMob(castingPlayer);

                    if (damage < 0)
                        damage = 0;
                    this.damage = damage;
                    FightManager.setActualHPofMob(defendingMob, damage);
                    FightManager.chceckExpReward(castingPlayer, defendingMob);

                    if (NetStatus.getInstance().getClient() != null) {
                        Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                        instantEffectNet.damage = damage;
                        instantEffectNet.instantEffectNumber = 0;
                        instantEffectNet.locationXofDefender = ((FreeMob) defendingMob).getCoordinateXonMap();
                        instantEffectNet.locationYofDefender = ((FreeMob) defendingMob).getCoordinateYonMap();
                        instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                        instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                        instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                        NetStatus.getInstance().getClient().sendTCP(instantEffectNet);
                    }
                }

                break;
            case AttackUpgrade:

                castingPlayer.changeToCastAnimation(castingPlayer);

                LongEffect longEffect = new LongEffect(
                        AnimationSpellCreator.getInstance().makeLongEffectAnimation(LongEffects.AttackUpgrade, ((PlayerMob) defendingMob)),
                        false,
                        LongEffects.AttackUpgrade,
                        ((PlayerMob) defendingMob)
                );
                longEffect.setAttackModifier(1);

                ((PlayerMob) defendingMob).getLongEffects().add(longEffect);

                ((PlayerMob) defendingMob).getLongEffectsTable().clear();

                for (LongEffect longEffect1 : ((PlayerMob) defendingMob).getLongEffects()) {
                    ((PlayerMob) defendingMob).getLongEffectsTable().add(longEffect1).size(50, 50).padRight(5);
                }

                damage = 0;

                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()) {
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.damage = damage;
                    instantEffectNet.instantEffectNumber = 1;
                    instantEffectNet.locationXofDefender = ((PlayerMob) defendingMob).getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = ((PlayerMob) defendingMob).getCoordinateYonMap();
                    instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    NetStatus.getInstance().getClient().sendTCP(instantEffectNet);
                }

                break;
            case HealthPotion:
                ((PlayerMob) defendingMob).setActualhp(((PlayerMob) defendingMob).getActualhp() + 5);
                if (((PlayerMob) defendingMob).getActualhp() > ((PlayerMob) defendingMob).getMaxHp()) {
                    ((PlayerMob) defendingMob).setActualhp(((PlayerMob) defendingMob).getMaxHp());
                }

                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()) {
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    instantEffectNet.instantEffectNumber = 2;
                    instantEffectNet.locationXofDefender = ((PlayerMob) defendingMob).getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = ((PlayerMob) defendingMob).getCoordinateYonMap();
                    instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                    NetStatus.getInstance().getClient().sendTCP(instantEffectNet);

                }

                break;

            case Cure:

                int cureVarible = castingPlayer.getActualPower() + ModifierGetter.getPowerModifier(castingPlayer);

                ((PlayerMob) defendingMob).setActualhp(((PlayerMob) defendingMob).getActualhp() + cureVarible);

                this.damage = cureVarible;

                if (((PlayerMob) defendingMob).getActualhp() > ((PlayerMob) defendingMob).getMaxHp()) {
                    ((PlayerMob) defendingMob).setActualhp(((PlayerMob) defendingMob).getMaxHp());
                }


                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()) {
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    instantEffectNet.instantEffectNumber = 4;
                    instantEffectNet.damage = cureVarible;
                    instantEffectNet.locationXofDefender = ((PlayerMob) defendingMob).getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = ((PlayerMob) defendingMob).getCoordinateYonMap();
                    instantEffectNet.locationXofCaster = castingPlayer.getCoordinateXonMap();
                    instantEffectNet.locationYofCaster = castingPlayer.getCoordinateYonMap();
                    NetStatus.getInstance().getClient().sendTCP(instantEffectNet);
                }

                break;

            case ManaPotion:
                ((PlayerMob) defendingMob).setActualMana(((PlayerMob) defendingMob).getActualMana() + 5);
                if (((PlayerMob) defendingMob).getActualMana() > ((PlayerMob) defendingMob).getMaxMana() + ModifierGetter.getWisdomModifier(((PlayerMob) defendingMob))) {
                    ((PlayerMob) defendingMob).setActualMana(((PlayerMob) defendingMob).getMaxMana() + ModifierGetter.getWisdomModifier(((PlayerMob) defendingMob)));
                }

                // Turn off mana bar of player mob.
                if (((PlayerMob) defendingMob).getActualMana() >= ((PlayerMob) defendingMob).getMaxMana() + ModifierGetter.getWisdomModifier(((PlayerMob) defendingMob)) && ((PlayerMob) defendingMob).getManaBar().isManaBarAdd()) {
                    ((PlayerMob) defendingMob).getManaBar().remove();
                    ((PlayerMob) defendingMob).getManaBar().setManaBarAdd(false);
                }

                if (NetStatus.getInstance().getClient() != null && !NetStatus.getInstance().isInstantEffectNet()) {
                    Network.InstantEffectNet instantEffectNet = new Network.InstantEffectNet();
                    instantEffectNet.enemyId = NetStatus.getInstance().getEnemyId();
                    instantEffectNet.instantEffectNumber = 3;
                    instantEffectNet.locationXofDefender = ((PlayerMob) defendingMob).getCoordinateXonMap();
                    instantEffectNet.locationYofDefender = ((PlayerMob) defendingMob).getCoordinateYonMap();
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

    public InstantEffects getInstantEffects() {
        return instantEffects;
    }
}
