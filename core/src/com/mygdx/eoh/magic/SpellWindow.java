package com.mygdx.eoh.magic;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.gameClasses.Positioning;

/**
 * Created by v on 2017-02-20.
 *
 */
public class SpellWindow {
    private static SpellWindow instance = new SpellWindow();

    private SpellWindow() {
    }

    public static SpellWindow getInstance() {
        return instance;
    }

    /**
     * Return window with spells of player mob.
     * @param playerMob Which player mob spells will show.
     */
    public Window getSpellWindow(PlayerMob playerMob){
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        window.setSize(500, 300);
        window.setModal(true);
        Positioning.setWindowToCenter(window);

        TextButton tbClose = new TextButton("CLOSE", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        tbClose.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        for (Spell spell: playerMob.getSpells()){
            spell.getSpellButton().addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    window.remove();
                }
            });
            window.add(spell.getSpellButton()).size(100, 100).pad(5);
        }

        window.row();
        window.add(tbClose);

        return window;
    }
}
