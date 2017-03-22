package com.mygdx.eoh.items;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.gameClasses.Positioning;

/**
 * Items Window
 * Created by wowvaqa on 22.03.17.
 */

public class ItemsWindow {
    private static final ItemsWindow instance = new ItemsWindow();

    public static ItemsWindow getInstance() {
        return instance;
    }

    private ItemsWindow() {
    }

    /**
     * Gets window with items.
     * @return Items of selected player mob.
     */
    public Window getItemsWindow(PlayerMob playerMob){
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(700, 400);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        Table itemsTable = new Table();

        for (Item item: playerMob.getItems()){
            itemsTable.add(item.getButton()).size(100, 100).pad(5);
            item.getButton().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    window.remove();
                }
            });
        }

        itemsTable.row();

        for (Item item: playerMob.getItems()){
            itemsTable.add(new Label(item.getItemName(),AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class))).pad(5);
        }

        TextButton tbCancel = new TextButton("Zamknij", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        tbCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        window.add(itemsTable).align(Align.top);
        window.row();
        window.add(tbCancel).pad(5).size(175, 50);

        return window;
    }
}
