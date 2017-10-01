package com.mygdx.eoh.magic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.animation.AnimationSpellCreator;
import com.mygdx.eoh.assets.AssetsGameInterface;
import com.mygdx.eoh.effects.InstantEffect;
import com.mygdx.eoh.enums.InstantEffects;
import com.mygdx.eoh.enums.Spells;
import com.mygdx.eoh.enums.SpellsKinds;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.InfoLabels;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.mob.FreeMob;

/**
 *
 * Created by v on 2017-02-20.
 */

public class Spell {

    private PlayerMob playerOwner;
    private Spells spellType;
    private SpellsKinds spellKind;
    private ImageButton spellButton;
    private int manaCost;

    private SnapshotArray<InstantEffect> instantEffects;

    public Spell(PlayerMob playerOwner, Spells spellType) {
        this.playerOwner = playerOwner;
        this.spellType = spellType;

        instantEffects = new SnapshotArray<InstantEffect>();

        createButton(this);
    }

    public static void castSpell(Spell spell, PlayerMob castingPlayer, Object target) {
        spell.getPlayerOwner().changeToCastAnimation(spell.getPlayerOwner());
        spell.getPlayerOwner().decreseMana(spell.getManaCost());

        int x = 0;
        int y = 0;
        int locationXonMap = 0;
        int locationYonMap = 0;

        if (spell.getSpellType().equals(Spells.AttackUpgrade) ||
                spell.getSpellType().equals(Spells.Cure)) {
            x = (int) castingPlayer.getX();
            y = (int) castingPlayer.getY();
            locationXonMap = castingPlayer.getCoordinateXonMap();
            locationYonMap = castingPlayer.getCoordinateYonMap();
        }

        if (spell.getSpellType().equals(Spells.Fireball)) {
            if (target.getClass().equals(FreeMob.class)) {
                x = (int) ((FreeMob) target).getX();
                y = (int) ((FreeMob) target).getY();
                locationXonMap = ((FreeMob) target).getCoordinateXonMap();
                locationYonMap = ((FreeMob) target).getCoordinateYonMap();
            } else if (target.getClass().equals(PlayerMob.class)) {
                x = (int) ((PlayerMob) target).getX();
                y = (int) ((PlayerMob) target).getY();
                locationXonMap = ((PlayerMob) target).getCoordinateXonMap();
                locationYonMap = ((PlayerMob) target).getCoordinateYonMap();
            }
        }

        for (InstantEffect instantEffect : spell.getInstantEffects()) {
            instantEffect.setPosition(x, y);
            instantEffect.setStateTime(0);
            if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob() != null)
                instantEffect.action(spell.getPlayerOwner(), GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getPlayerMob());
            else if (GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob() != null)
                instantEffect.action(spell.getPlayerOwner(), GameStatus.getInstance().getMap().getFields()[locationXonMap][locationYonMap].getFreeMob());

            instantEffect.setAnimation(AnimationSpellCreator.getInstance().makeSpellAnimation(instantEffect.getInstantEffects()));
            instantEffect.setStateTime(0);
            instantEffect.setLooped(false);
            GameStatus.getInstance().getMapStage().addActor(instantEffect);

            if (instantEffect.getInstantEffects().equals(InstantEffects.Cure)) {
                InfoLabels.getInstance().showGoodEffectLabel("" + instantEffect.getDamage(), locationXonMap, locationYonMap);
            } else if (instantEffect.getInstantEffects().equals(InstantEffects.AttackUpgrade)) {
                InfoLabels.getInstance().showGoodEffectLabel("ATTACK UPGRADE ", locationXonMap, locationYonMap);
            } else {
                InfoLabels.getInstance().showDamageLabel(instantEffect.getDamage(), locationXonMap, locationYonMap);
            }
        }
    }

    private void createButton(final Spell spell) {
        switch (spellType) {
            case Fireball:

                manaCost = 1;

                spellKind = SpellsKinds.OnlyEnemys;

                instantEffects.add(
                        new InstantEffect(
                                AnimationSpellCreator.getInstance().makeSpellAnimation(InstantEffects.FiraballDamage), false, InstantEffects.FiraballDamage
                        )
                );

                ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();

                imageButtonStyle.imageUp =  new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/spellButtons/fireballButtonUP.png", Texture.class)
                ));

                imageButtonStyle.imageDown =  new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/spellButtons/fireballButtonDOWN.png", Texture.class)
                ));

                spellButton = new ImageButton(imageButtonStyle);

                spellButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if (playerOwner.getActualMana() >= manaCost)
                            spell.getPlayerOwner().getMoveManager().showCastInterface(GameStatus.getInstance().getMapStage(), spell);
                    }
                });

                break;

            case AttackUpgrade:

                manaCost = 1;

                spellKind = SpellsKinds.OnlyFriends;

                instantEffects.add(
                        new InstantEffect(
                                AnimationSpellCreator.getInstance().makeSpellAnimation(InstantEffects.AttackUpgrade), false, InstantEffects.AttackUpgrade
                        )
                );

                imageButtonStyle = new ImageButton.ImageButtonStyle();

                imageButtonStyle.imageUp =  new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/spellButtons/AttackUpgradeButtonUP.png", Texture.class)
                ));

                imageButtonStyle.imageDown =  new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/spellButtons/AttackUpgradeButtonDOWN.png", Texture.class)
                ));

                spellButton = new ImageButton(imageButtonStyle);

                spellButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if (playerOwner.getActualMana() >= manaCost)
                            spell.getPlayerOwner().getMoveManager().showCastInterface(GameStatus.getInstance().getMapStage(), spell);
                    }
                });

                break;

            case Cure:

                manaCost = 1;

                spellKind = SpellsKinds.OnlyFriends;

                instantEffects.add(
                        new InstantEffect(
                                AnimationSpellCreator.getInstance().makeSpellAnimation(InstantEffects.AttackUpgrade), false, InstantEffects.Cure
                        )
                );

                imageButtonStyle = new ImageButton.ImageButtonStyle();

                imageButtonStyle.imageUp =  new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/spellButtons/CureButtonUP.png", Texture.class)
                ));

                imageButtonStyle.imageDown =  new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/spellButtons/CureButtonDOWN.png", Texture.class)
                ));

                spellButton = new ImageButton(imageButtonStyle);

                spellButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        if (playerOwner.getActualMana() >= manaCost)
                            spell.getPlayerOwner().getMoveManager().showCastInterface(GameStatus.getInstance().getMapStage(), spell);
                    }
                });

                break;
        }
    }

    /**
     * GETTERS AND SETTERS
     */

    public ImageButton getSpellButton() {
        return spellButton;
    }

    public Spells getSpellType() {
        return spellType;
    }

    public PlayerMob getPlayerOwner() {
        return playerOwner;
    }

    public SpellsKinds getSpellKind() {
        return spellKind;
    }

    public SnapshotArray<InstantEffect> getInstantEffects() {
        return instantEffects;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}
