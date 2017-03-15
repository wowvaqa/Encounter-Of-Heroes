package com.mygdx.eoh.items;

import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.PlayerMob;

/**
 * Class to draw items.
 * Created by v on 2017-01-31.
 */

public class Item extends AnimatedImage {

    AvailableItems item;

    public Item(AnimationTypes animationTypes, AvailableItems availableItems) {
        super(AnimationCreator.getInstance().makeAnimation(animationTypes), true);
        item = availableItems;
    }

    public void itemAction(PlayerMob playerMob){
        if (this.item.equals(AvailableItems.Gold)){
            playerMob.getFieldOfPlayerMob().getItem().remove();
            playerMob.getFieldOfPlayerMob().setItem(null);
            playerMob.getPlayerOwner().riseGold(50);

            if (GameStatus.getInstance().getCurrentPlayerTurn().equals(playerMob.getPlayerOwner())) {
                playerMob.getPlayerOwner().changeGoldLabel(playerMob.getPlayerOwner());
                //((Label) GameStatus.getInstance().getUpperBarTable().getCells().get(2).getActor()).setText("" + playerMob.getPlayerOwner().getGold());
            }
        }
    }

    public AvailableItems getItem() {
        return item;
    }

    public void setItem(AvailableItems item) {
        this.item = item;
    }
}
