package com.mygdx.eoh.items;

import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.gameClasses.Options;

/**
 * Creator of items.
 * Created by v on 2017-01-31.
 */
public class ItemCreator {
    private static ItemCreator instance = new ItemCreator();

    public static ItemCreator getInstance() {
        return instance;
    }

    private ItemCreator() {
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

    private Item createGold(float locXonMap, float locYonMap) {
        Item item = new Item(AnimationTypes.ItemGold, AvailableItems.Gold);
        item.setPosition(locXonMap * Options.tileSize, locYonMap * Options.tileSize);
        item.setSize(Options.tileSize, Options.tileSize);
        return item;
    }

    private Item createManaPotion(float locXonMap, float locYonMap) {
        Item item = new Item(AnimationTypes.ManaPotionAnimation, AvailableItems.ManaPotion);
        item.setPosition(locXonMap * Options.tileSize, locYonMap * Options.tileSize);
        item.setSize(Options.tileSize, Options.tileSize);
        return item;
    }

    private Item createHealthPotion(float locXonMap, float locYonMap) {
        Item item = new Item(AnimationTypes.HealthPotionAnimation, AvailableItems.HealthPotion);
        item.setPosition(locXonMap * Options.tileSize, locYonMap * Options.tileSize);
        item.setSize(Options.tileSize, Options.tileSize);
        return item;
    }

}
