package com.mygdx.eoh.Equipment;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.mygdx.eoh.animation.AnimatedImage;
import com.mygdx.eoh.animation.AnimationCreator;
import com.mygdx.eoh.assets.AssetsGameScreen;
import com.mygdx.eoh.enums.AnimationTypes;
import com.mygdx.eoh.gameClasses.GameStatus;
import com.mygdx.eoh.gameClasses.Options;
import com.mygdx.eoh.gameClasses.Player;
import com.mygdx.eoh.gameClasses.PlayerMob;
import com.mygdx.eoh.gameClasses.Positioning;
import com.mygdx.eoh.net.NetStatus;
import com.mygdx.eoh.net.Network;

/**
 * Treasure Box where players can find equipment.
 * Created by wowvaqa on 10.04.17.
 */

public class Treasure extends AnimatedImage {

    //Content
    private SnapshotArray<Equip> equips;
    //Coordinates X & Y on map
    private int locXonMap;
    private int locYonMap;
    //When Player mob step in on treasure then True
    private boolean stepIn = false;

    private Treasure(Animation animation, boolean isLooped) {
        super(animation, isLooped);

        equips = new SnapshotArray<Equip>();
    }

    /**
     * Create treasure box with equipment.
     *
     * @param level       Level of equipment in treasure box .
     * @param locXonStage location X of treasure box on map.
     * @param locYonStage Location Y of treasure box on map.
     * @return Treasure box with equipment.
     */
    public static Treasure createTreasure(int level, int locXonStage, int locYonStage) {
        Treasure treasure = new Treasure(AnimationCreator.getInstance().makeAnimation(AnimationTypes.TreasureAnimation), true);

        treasure.locXonMap = locXonStage;
        treasure.locYonMap = locYonStage;

        treasure.setPosition(locXonStage * Options.tileSize, locYonStage * Options.tileSize);
        treasure.setSize(Options.tileSize, Options.tileSize);

        switch (level) {
            case 1:
                treasure.getEquips().add(Equip.createEquip(EquipmentLevels.getInstance().drawLevel1Equip()));
                //treasure.getEquips().add(Equip.createEquip(EquipmentLevels.getInstance().drawLevel1Equip()));
                break;
            case 2:
                treasure.getEquips().add(Equip.createEquip(EquipmentLevels.getInstance().drawLevel2Equip()));
                //treasure.getEquips().add(Equip.createEquip(EquipmentLevels.getInstance().drawLevel2Equip()));
                break;
        }

        return treasure;
    }


    /**
     * Returns Window with equipment when player mob step into treasure.
     *
     * @return Window with equipment.
     */
    public Window getTreasureWindow(PlayerMob playerMob) {
        final Window window = new Window("", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        final Treasure treasure = this;
        //playerMob = GameStatus.getInstance().getSelectedPlayerMob();

        window.setSize(600, 400);
        window.setModal(true);

        Positioning.setWindowToCenter(window);

        Table table = new Table();

        for (Equip equip : this.getEquips()) {
            table.add(equip).size(100, 100).pad(5);
        }

        TextButton textButtonClose = new TextButton("Zamknij", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));
        TextButton textButtonTakeAll = new TextButton("Weź wszystko", AssetsGameScreen.getInstance().getManager().get("styles/skin.json", Skin.class));

        textButtonClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                window.remove();
            }
        });

        final PlayerMob finalPlayerMob = playerMob;
        textButtonTakeAll.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                for (Equip equip : treasure.getEquips()) {
                    finalPlayerMob.getEquip().add(equip);

                    if (NetStatus.getInstance().getClient() != null) {
                        Network.CreateEquip createEquip = new Network.CreateEquip();
                        createEquip.enemyId = NetStatus.getInstance().getEnemyId();
                        createEquip.locXofPlayerMob = finalPlayerMob.getCoordinateXonMap();
                        createEquip.locYofPlayerMob = finalPlayerMob.getCoordinateYonMap();
                        createEquip.equipKind = Equip.getNetworkEquipKind(equip.getEquipKind());
                        NetStatus.getInstance().getClient().sendTCP(createEquip);
                    }
                }

                equips.clear();
                GameStatus.getInstance().getMap().getFields()[locXonMap][locYonMap].setTreasure(null);
                treasure.remove();

                window.remove();
            }
        });

        window.add(table).colspan(2).pad(5);
        window.row();
        window.add(textButtonClose).size(200, 50).pad(5);
        window.add(textButtonTakeAll).size(200, 50).pad(5);

        return window;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameStatus.getInstance().getMap().getFields()[locXonMap][locYonMap].getPlayerMob() == null &&
                stepIn) {
            stepIn = false;
        }
    }

    /***********************************************************************************************
     * GETTERS AND SETTERS
     **********************************************************************************************/

    private SnapshotArray<Equip> getEquips() {
        return equips;
    }

//    public int getLocXonMap() {
//        return locXonMap;
//    }

//    public int getLocYonMap() {
//        return locYonMap;
//    }

    public boolean isStepIn() {
        return stepIn;
    }

    public void setStepIn(boolean stepIn) {
        this.stepIn = stepIn;
    }
}
