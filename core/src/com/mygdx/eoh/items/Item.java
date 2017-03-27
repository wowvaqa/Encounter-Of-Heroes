package com.mygdx.eoh.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.assets.AssetsGameInterface;
import com.mygdx.eoh.effects.InstantEffect;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.enums.InstantEffects;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * Class to draw items.
 * Created by v on 2017-01-31.
 */

public class Item extends AnimatedImage {

    private PlayerMob playerOwner;
    private AvailableItems itemType;
    private ImageButton button;
    private String itemName;
    private String itemDescription;
    private SnapshotArray<InstantEffect> instantEffects;

    public Item(AnimationTypes animationTypes, AvailableItems availableItems) {
        super(AnimationCreator.getInstance().makeAnimation(animationTypes), true);
        itemType = availableItems;
        instantEffects = new SnapshotArray<InstantEffect>();
        createActions();
        this.createButton(this);
    }

    public void itemAction(PlayerMob playerMob) {
        if (this.itemType.equals(AvailableItems.Gold)) {
            playerMob.getFieldOfPlayerMob().getItem().remove();
            playerMob.getFieldOfPlayerMob().setItem(null);
            playerMob.getPlayerOwner().riseGold(50);

            if (GameStatus.getInstance().getCurrentPlayerTurn().equals(playerMob.getPlayerOwner())) {
                playerMob.getPlayerOwner().changeGoldLabel(playerMob.getPlayerOwner());
            }
        }

        if (this.itemType.equals(AvailableItems.HealthPotion)) {
            playerMob.getItems().add(this);
            this.playerOwner = playerMob;
            playerMob.getFieldOfPlayerMob().getItem().remove();
            playerMob.getFieldOfPlayerMob().setItem(null);

        }

        if (this.itemType.equals(AvailableItems.ManaPotion)) {
            playerMob.getItems().add(this);
            this.playerOwner = playerMob;
            playerMob.getFieldOfPlayerMob().getItem().remove();
            playerMob.getFieldOfPlayerMob().setItem(null);

        }

        if (this.itemType.equals(AvailableItems.TresureBoxLvl1)) {

        }

        if (this.itemType.equals(AvailableItems.TresureBoxLvl2)) {

        }
    }

    private void createActions(){
        if (this.itemType.equals(AvailableItems.ManaPotion)){
            this.itemName = "Mikstura many";
            this.itemDescription = "Zwiększa ilość many o 5";
            instantEffects.add(new InstantEffect(
                    AnimationCreator.getInstance().makeAnimation(AnimationTypes.PotionUseAnimation),
                    false, InstantEffects.ManaPotion));
        }

        if (this.itemType.equals(AvailableItems.HealthPotion)){
            this.itemName = "Mikstura zdrowia";
            this.itemDescription = "Zwiększa ilość zdrowia o 4";
            instantEffects.add(new InstantEffect(
                    AnimationCreator.getInstance().makeAnimation(AnimationTypes.PotionUseAnimation),
                    false, InstantEffects.HealthPotion));
        }
    }

    private void createButton(final Item item) {
        switch (itemType) {
            case HealthPotion:
                ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();

                imageButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/mixturesButtons/healthPotionButtonUP.png", Texture.class)
                ));

                imageButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/mixturesButtons/healthPotionButtonDOWN.png", Texture.class)
                ));

                button = new ImageButton(imageButtonStyle);

                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        for (InstantEffect instantEffect: item.instantEffects){
                            instantEffect.setPosition(getX(), getY());
                            instantEffect.setPosition(
                                    GameStatus.getInstance().getSelectedPlayerMob().getX(),
                                    GameStatus.getInstance().getSelectedPlayerMob().getY()
                            );
                            instantEffect.setStateTime(0);
                            instantEffect.action(GameStatus.getInstance().getSelectedPlayerMob(), GameStatus.getInstance().getSelectedPlayerMob());
                            GameStatus.getInstance().getMapStage().addActor(instantEffect);
                        }
                        item.remove();
                        GameStatus.getInstance().getSelectedPlayerMob().getItems().removeValue(item, true);
                    }
                });
                break;

            case ManaPotion:
                imageButtonStyle = new ImageButton.ImageButtonStyle();

                imageButtonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/mixturesButtons/manaPotionButtonUP.png", Texture.class)
                ));

                imageButtonStyle.imageDown = new TextureRegionDrawable(new TextureRegion(
                        AssetsGameInterface.getInstance().getManager().get("game/interface/mixturesButtons/manaPotionButtonDOWN.png", Texture.class)
                ));

                button = new ImageButton(imageButtonStyle);

                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        for (InstantEffect instantEffect: item.instantEffects){
                            instantEffect.setPosition(
                                    GameStatus.getInstance().getSelectedPlayerMob().getX(),
                                    GameStatus.getInstance().getSelectedPlayerMob().getY()
                            );
                            instantEffect.setStateTime(0);
                            instantEffect.action(GameStatus.getInstance().getSelectedPlayerMob(), GameStatus.getInstance().getSelectedPlayerMob());
                            GameStatus.getInstance().getMapStage().addActor(instantEffect);
                        }
                        item.remove();
                        GameStatus.getInstance().getSelectedPlayerMob().getItems().removeValue(item, true);
                    }
                });
                break;

        }
    }

    /***********************************************************************************************
     * GETTERS & SETTERS
     **********************************************************************************************/

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemName() {
        return itemName;
    }

    public ImageButton getButton() {
        return button;
    }

    public PlayerMob getPlayerOwner() {
        return playerOwner;
    }

    public SnapshotArray<InstantEffect> getInstantEffects() {
        return instantEffects;
    }
}
