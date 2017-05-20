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
import com.mygdx.eoh.gameClasses.PlayerMob;

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
