package com.mygdx.eoh.items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.gameClasses.Options;

/**
 * Creator of items.
 * Created by v on 2017-01-31.
 */
public class ItemCreator {
    private static ItemCreator instance = new ItemCreator();

    private ItemCreator() {
    }

    public static ItemCreator getInstance() {
        return instance;
    }

    public Item createItem(AvailableItems availableItems, int locXonMap, int locYonMap){

        switch (availableItems) {
            case Gold:
                return createGold(locXonMap, locYonMap);
            case ManaPotion:
                return createManaPotion(locXonMap, locYonMap);
            case HealthPotion:
                return createHealthPotion(locXonMap, locYonMap);
        }
        return null;
    }

    private Item createGold(int locXonMap, int locYonMap) {
        //Item item = new Item(AnimationTypes.ItemGold, AvailableItems.Gold);
        Item item = new Item(AssetsGameScreen.getInstance().getManager().get("game/items/gold/itemGold.png", Texture.class), AvailableItems.Gold);
        item.setPosition(locXonMap * Options.tileSize, locYonMap * Options.tileSize);
        item.setSize(Options.tileSize, Options.tileSize);
        item.setCoordinateXonMap(locXonMap);
        item.setCoordinateYonMap(locYonMap);
        return item;
    }

    private Item createManaPotion(int locXonMap, int locYonMap) {
        //Item item = new Item(AnimationTypes.ManaPotionAnimation, AvailableItems.ManaPotion);
        Item item = new Item(AssetsGameScreen.getInstance().getManager().get("game/items/mixtures/manaPotion.png", Texture.class), AvailableItems.ManaPotion);
        item.setPosition(locXonMap * Options.tileSize, locYonMap * Options.tileSize);
        item.setSize(Options.tileSize, Options.tileSize);
        item.setCoordinateXonMap(locXonMap);
        item.setCoordinateYonMap(locYonMap);
        return item;
    }

    private Item createHealthPotion(int locXonMap, int locYonMap) {
        //Item item = new Item(AnimationTypes.HealthPotionAnimation, AvailableItems.HealthPotion);
        Item item = new Item(AssetsGameScreen.getInstance().getManager().get("game/items/mixtures/healthPotion.png", Texture.class), AvailableItems.HealthPotion);
        item.setPosition(locXonMap * Options.tileSize, locYonMap * Options.tileSize);
        item.setSize(Options.tileSize, Options.tileSize);
        item.setCoordinateXonMap(locXonMap);
        item.setCoordinateYonMap(locYonMap);
        return item;
    }

}
